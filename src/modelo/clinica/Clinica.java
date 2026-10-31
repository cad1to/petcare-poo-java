package modelo.clinica;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import modelo.facturacion.Comprobante;
import modelo.mascotas.Mascota;
import modelo.personas.Dueno;
import modelo.personas.Veterinario;
import modelo.servicios.Facturable;
import modelo.servicios.Servicio;

/**
 * Clínica veterinaria. Agrupa veterinarios (agregación) y crea y posee
 * sus consultorios (composición).
 */
public class Clinica {
    private final String nombre;
    private final List<Veterinario> veterinarios = new ArrayList<>();
    private final List<Consultorio> consultorios = new ArrayList<>();
    private final List<Cita> citas = new ArrayList<>();
    private final List<Mascota> mascotas = new ArrayList<>();
    private final List<Dueno> duenos = new ArrayList<>();

    /**
     * Constructor por defecto: clínica PetCare con tres consultorios.
     */
    public Clinica() {
        this("PetCare", 3);
    }

    /**
     * Constructor con todos los datos. Los consultorios se crean aquí
     * dentro: nacen con la clínica y desaparecen con ella (composición).
     */
    public Clinica(String nombre, int numeroConsultorios) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre de la clínica no puede estar vacío");
        }
        if (numeroConsultorios <= 0) {
            throw new IllegalArgumentException("Debe haber al menos un consultorio");
        }
        this.nombre = nombre.trim();
        for (int i = 1; i <= numeroConsultorios; i++) {
            consultorios.add(new Consultorio(i));
        }
    }

    /**
     * Agrega un veterinario que ya existe fuera de la clínica (agregación).
     * Si la clínica se elimina, el veterinario sigue existiendo.
     */
    public void agregarVeterinario(Veterinario veterinario) {
        if (veterinario == null) {
            throw new IllegalArgumentException("El veterinario no puede ser nulo");
        }
        for (Veterinario existente : veterinarios) {
            if (existente.getCedulaProfesional()
                    .equalsIgnoreCase(veterinario.getCedulaProfesional())) {
                throw new IllegalArgumentException("Ya existe un veterinario con esa cédula");
            }
        }
        veterinarios.add(veterinario);
    }

    /**
     * Registra una mascota y, si hace falta, a su dueño.
     */
    public void registrarMascota(Mascota mascota) {
        if (mascota == null) {
            throw new IllegalArgumentException("La mascota no puede ser nula");
        }
        if (!duenos.contains(mascota.getDueno())) {
            duenos.add(mascota.getDueno());
        }
        mascotas.add(mascota);
    }

    /**
     * Agenda una cita validando las reglas de negocio.
     *
     * @throws IllegalArgumentException si algún dato no existe en la clínica
     * @throws IllegalStateException si el veterinario o el consultorio están ocupados
     */
    public Cita agendarCita(Mascota mascota, Veterinario veterinario,
                            int numeroConsultorio, LocalDateTime fechaHora) {
        if (!mascotas.contains(mascota)) {
            throw new IllegalArgumentException("La mascota no está registrada en la clínica");
        }
        if (!veterinarios.contains(veterinario)) {
            throw new IllegalArgumentException("El veterinario no pertenece a la clínica");
        }
        Consultorio consultorio = buscarConsultorio(numeroConsultorio);
        validarDisponibilidad(veterinario, consultorio, fechaHora);
        Cita cita = new Cita(mascota, veterinario, consultorio, fechaHora);
        citas.add(cita);
        return cita;
    }

    private Consultorio buscarConsultorio(int numero) {
        for (Consultorio consultorio : consultorios) {
            if (consultorio.getNumero() == numero) {
                return consultorio;
            }
        }
        throw new IllegalArgumentException("No existe el consultorio " + numero);
    }

    private void validarDisponibilidad(Veterinario veterinario,
                                       Consultorio consultorio,
                                       LocalDateTime fechaHora) {
        for (Cita cita : citas) {
            boolean programadaEnEsaHora = cita.getEstado() == EstadoCita.PROGRAMADA
                    && cita.getFechaHora().equals(fechaHora);
            if (programadaEnEsaHora && cita.getVeterinario() == veterinario) {
                throw new IllegalStateException("El veterinario ya tiene una cita a esa hora");
            }
            if (programadaEnEsaHora && cita.getConsultorio() == consultorio) {
                throw new IllegalStateException("El consultorio ya está ocupado a esa hora");
            }
        }
    }

    /**
     * Atiende una cita con el servicio realizado y la guarda en el
     * historial clínico de la mascota.
     */
    public void atenderCita(Cita cita, Servicio servicio) {
        cita.atender(servicio);
        cita.getMascota().getHistorial().agregarCita(cita);
    }

    /**
     * Genera el comprobante de una cita atendida.
     */
    public Comprobante generarComprobante(Cita cita) {
        return new Comprobante(cita);
    }

    /**
     * Busca citas por nombre de mascota (sobrecarga 1).
     */
    public List<Cita> buscarCitas(String nombreMascota) {
        List<Cita> resultado = new ArrayList<>();
        for (Cita cita : citas) {
            if (cita.getMascota().getNombre().equalsIgnoreCase(nombreMascota)) {
                resultado.add(cita);
            }
        }
        return resultado;
    }

    /**
     * Busca citas por fecha (sobrecarga 2).
     */
    public List<Cita> buscarCitas(LocalDate fecha) {
        List<Cita> resultado = new ArrayList<>();
        for (Cita cita : citas) {
            if (cita.getFechaHora().toLocalDate().equals(fecha)) {
                resultado.add(cita);
            }
        }
        return resultado;
    }

    /**
     * Da de baja una mascota junto con sus citas. Al quitar las referencias,
     * los objetos quedan inalcanzables y el recolector de basura (garbage
     * collector) libera su memoria; el dueño sigue existiendo.
     */
    public void darDeBajaMascota(Mascota mascota) {
        if (!mascotas.remove(mascota)) {
            throw new IllegalArgumentException("La mascota no está registrada");
        }
        citas.removeIf(cita -> cita.getMascota() == mascota);
    }

    /**
     * Total cobrado en citas atendidas por un veterinario.
     */
    public double totalCobradoPorVeterinario(Veterinario veterinario) {
        List<Facturable> cobros = new ArrayList<>();
        for (Cita cita : citas) {
            if (cita.getEstado() == EstadoCita.ATENDIDA
                    && cita.getVeterinario() == veterinario) {
                cobros.add(cita.getServicio());
            }
        }
        return sumarCostos(cobros);
    }

    /**
     * Total cobrado en citas atendidas de una mascota.
     */
    public double totalCobradoPorMascota(Mascota mascota) {
        List<Facturable> cobros = new ArrayList<>();
        for (Cita cita : citas) {
            if (cita.getEstado() == EstadoCita.ATENDIDA
                    && cita.getMascota() == mascota) {
                cobros.add(cita.getServicio());
            }
        }
        return sumarCostos(cobros);
    }

    /**
     * Suma los costos sin preguntar el tipo de cada servicio (polimorfismo).
     */
    private double sumarCostos(List<Facturable> cobros) {
        double total = 0;
        for (Facturable cobro : cobros) {
            total += cobro.calcularCosto();
        }
        return total;
    }

    public String getNombre() {
        return nombre;
    }

    public List<Veterinario> getVeterinarios() {
        return Collections.unmodifiableList(veterinarios);
    }

    public List<Consultorio> getConsultorios() {
        return Collections.unmodifiableList(consultorios);
    }

    public List<Cita> getCitas() {
        return Collections.unmodifiableList(citas);
    }

    public List<Mascota> getMascotas() {
        return Collections.unmodifiableList(mascotas);
    }

    @Override
    public String toString() {
        return "Clínica " + nombre + " (" + veterinarios.size() + " veterinarios, "
                + consultorios.size() + " consultorios)";
    }
}