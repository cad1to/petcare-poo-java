package app;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import modelo.clinica.Cita;
import modelo.clinica.Clinica;
import modelo.facturacion.Comprobante;
import modelo.mascotas.Ave;
import modelo.mascotas.Gato;
import modelo.mascotas.Mascota;
import modelo.mascotas.Perro;
import modelo.personas.Dueno;
import modelo.personas.Veterinario;
import modelo.servicios.Cirugia;
import modelo.servicios.ConsultaGeneral;
import modelo.servicios.Servicio;
import modelo.servicios.Vacunacion;

/**
 * Programa principal: menú de consola de la clínica veterinaria PetCare.
 */
public class Main {
    private static final DateTimeFormatter FORMATO_FECHA =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private final Scanner entrada = new Scanner(System.in);
    private final Clinica clinica = new Clinica();

    /**
     * Punto de entrada del programa.
     */
    public static void main(String[] args) {
        new Main().ejecutar();
    }

    private void ejecutar() {
        int opcion = -1;
        while (opcion != 0) {
            mostrarMenu();
            opcion = leerEntero("Elige una opción: ");
            try {
                procesarOpcion(opcion);
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        System.out.println("Hasta luego.");
    }

    private void mostrarMenu() {
        System.out.println("\n===== CLÍNICA PETCARE =====");
        System.out.println("1. Registrar dueño y mascota");
        System.out.println("2. Registrar veterinario");
        System.out.println("3. Agendar una cita");
        System.out.println("4. Atender o cancelar una cita");
        System.out.println("5. Generar comprobante de una cita atendida");
        System.out.println("6. Consultar historial clínico de una mascota");
        System.out.println("7. Listar mascotas y su sonido");
        System.out.println("8. Ver total cobrado");
        System.out.println("9. Dar de baja una mascota");
        System.out.println("11. Prue");
        System.out.println("0. Salir");
    }

    private void procesarOpcion(int opcion) {
        switch (opcion) {
            case 1 -> registrarDuenoYMascota();
            case 2 -> registrarVeterinario();
            case 3 -> agendarCita();
            case 4 -> atenderOCancelarCita();
            case 5 -> generarComprobante();
            case 6 -> consultarHistorial();
            case 7 -> listarMascotas();
            case 8 -> mostrarTotalesCobrados();
            case 9 -> darDeBajaMascota();
            case 11 -> cargarDemostracion();
            case 0 -> { }
            default -> System.out.println("Opción no válida.");
        }
    }

    // ---------- Opciones del menú ----------

    private void registrarDuenoYMascota() {
        Dueno dueno = elegirDueno();
        Mascota mascota = crearMascota(dueno);
        clinica.registrarMascota(mascota);
        System.out.println("Mascota registrada: " + mascota);
    }

    private void registrarVeterinario() {
        Veterinario veterinario = new Veterinario(
                leerTexto("Nombre: "), leerTexto("Apellido: "),
                leerTexto("Teléfono: "), leerTexto("Correo: "),
                leerTexto("Cédula profesional: "), leerTexto("Especialidad: "));
        clinica.agregarVeterinario(veterinario);
        System.out.println("Veterinario registrado: " + veterinario);
    }

    private void agendarCita() {
        Mascota mascota = elegirDeLista(clinica.getMascotas(), "mascotas");
        Veterinario veterinario = elegirDeLista(clinica.getVeterinarios(), "veterinarios");
        int consultorio = leerEntero("Número de consultorio (1-"
                + clinica.getConsultorios().size() + "): ");
        LocalDateTime fechaHora = leerFechaHora();
        Cita cita = clinica.agendarCita(mascota, veterinario, consultorio, fechaHora);
        System.out.println("Cita agendada: " + cita);
    }

    private void atenderOCancelarCita() {
        Cita cita = elegirDeLista(clinica.getCitas(), "citas");
        int accion = leerEntero("1. Atender  2. Cancelar: ");
        if (accion == 1) {
            clinica.atenderCita(cita, elegirServicio());
            System.out.println("Cita atendida.");
        } else if (accion == 2) {
            cita.cancelar();
            System.out.println("Cita cancelada.");
        } else {
            System.out.println("Opción no válida.");
        }
    }

    private void generarComprobante() {
        Cita cita = elegirDeLista(clinica.getCitas(), "citas");
        Comprobante comprobante = clinica.generarComprobante(cita);
        System.out.println(comprobante);
    }

    private void consultarHistorial() {
        Mascota mascota = elegirDeLista(clinica.getMascotas(), "mascotas");
        System.out.println("Historial clínico de " + mascota.getNombre() + ":");
        System.out.println(mascota.getHistorial());
    }

    private void listarMascotas() {
        if (clinica.getMascotas().isEmpty()) {
            System.out.println("No hay mascotas registradas.");
        }
        for (Mascota mascota : clinica.getMascotas()) {
            System.out.println(mascota.getNombre() + " dice: " + mascota.hacerSonido());
        }
    }

    private void mostrarTotalesCobrados() {
        double total = 0;
        System.out.println("--- Por veterinario ---");
        for (Veterinario veterinario : clinica.getVeterinarios()) {
            double monto = clinica.totalCobradoPorVeterinario(veterinario);
            System.out.println(veterinario.getNombre() + " "
                    + veterinario.getApellido() + ": $" + monto);
            total += monto;
        }
        System.out.println("--- Por mascota ---");
        for (Mascota mascota : clinica.getMascotas()) {
            System.out.println(mascota.getNombre() + ": $"
                    + clinica.totalCobradoPorMascota(mascota));
        }
        System.out.println("TOTAL COBRADO: $" + total);
    }

    private void darDeBajaMascota() {
        Mascota mascota = elegirDeLista(clinica.getMascotas(), "mascotas");
        String respuesta = leerTexto("¿Confirmas dar de baja a "
                + mascota.getNombre() + " y su historial? (s/n): ");
        if (respuesta.equalsIgnoreCase("s")) {
            clinica.darDeBajaMascota(mascota);
            System.out.println("Mascota dada de baja.");
        } else {
            System.out.println("Operación cancelada.");
        }
    }

    // ---------- Creación de objetos desde la consola ----------

    private Dueno elegirDueno() {
        List<Dueno> duenos = obtenerDuenos();
        if (!duenos.isEmpty()
                && leerTexto("¿Dueño ya registrado? (s/n): ").equalsIgnoreCase("s")) {
            return elegirDeLista(duenos, "dueños");
        }
        return new Dueno(leerTexto("Nombre del dueño: "), leerTexto("Apellido: "),
                leerTexto("Teléfono: "), leerTexto("Correo: "),
                leerTexto("Dirección: "));
    }

    private List<Dueno> obtenerDuenos() {
        List<Dueno> duenos = new ArrayList<>();
        for (Mascota mascota : clinica.getMascotas()) {
            if (!duenos.contains(mascota.getDueno())) {
                duenos.add(mascota.getDueno());
            }
        }
        return duenos;
    }

    private Mascota crearMascota(Dueno dueno) {
        System.out.println("Tipo de mascota: 1. Perro  2. Gato  3. Ave");
        int tipo = leerEntero("Tipo: ");
        if (tipo < 1 || tipo > 3) {
            throw new IllegalArgumentException("Tipo de mascota no válido");
        }
        String nombre = leerTexto("Nombre de la mascota: ");
        int edad = leerEntero("Edad (años): ");
        double peso = leerDecimal("Peso (kg): ");
        return switch (tipo) {
            case 1 -> new Perro(nombre, edad, peso, dueno,
                    leerTexto("Raza: "), leerTexto("Nivel de energía: "));
            case 2 -> new Gato(nombre, edad, peso, dueno,
                    leerTexto("¿Es doméstico? (s/n): ").equalsIgnoreCase("s"));
            default -> new Ave(nombre, edad, peso, dueno,
                    leerDecimal("Longitud de alas (cm): "));
        };
    }

    private Servicio elegirServicio() {
        System.out.println("Servicio: 1. Consulta general  2. Vacunación  3. Cirugía");
        int tipo = leerEntero("Servicio: ");
        return switch (tipo) {
            case 1 -> new ConsultaGeneral();
            case 2 -> new Vacunacion(leerTexto("Nombre de la vacuna: "),
                    leerDecimal("Costo de la vacuna: "));
            case 3 -> new Cirugia(leerEntero("Complejidad (1-3): "),
                    leerDecimal("Horas de quirófano: "));
            default -> throw new IllegalArgumentException("Servicio no válido");
        };
    }

    /**
     * Muestra una lista numerada y devuelve el elemento elegido.
     * Es genérico (T) para reutilizarlo con mascotas, veterinarios y citas.
     */
    private <T> T elegirDeLista(List<T> lista, String nombre) {
        if (lista.isEmpty()) {
            throw new IllegalStateException("No hay " + nombre + " registrados");
        }
        for (int i = 0; i < lista.size(); i++) {
            System.out.println((i + 1) + ". " + lista.get(i));
        }
        int indice = leerEntero("Elige el número: ") - 1;
        if (indice < 0 || indice >= lista.size()) {
            throw new IllegalArgumentException("Selección fuera de rango");
        }
        return lista.get(indice);
    }

    // ---------- Lectura segura de datos ----------

    private String leerTexto(String mensaje) {
        String texto = "";
        while (texto.isBlank()) {
            System.out.print(mensaje);
            texto = entrada.nextLine().trim();
            if (texto.isBlank()) {
                System.out.println("El dato no puede estar vacío.");
            }
        }
        return texto;
    }

    private int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Integer.parseInt(entrada.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Ingresa un número entero válido.");
            }
        }
    }

    private double leerDecimal(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Double.parseDouble(entrada.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Ingresa un número válido (ejemplo: 12.5).");
            }
        }
    }

    private LocalDateTime leerFechaHora() {
        while (true) {
            System.out.print("Fecha y hora (aaaa-mm-dd hh:mm): ");
            try {
                return LocalDateTime.parse(entrada.nextLine().trim(), FORMATO_FECHA);
            } catch (DateTimeParseException e) {
                System.out.println("Formato no válido. Ejemplo: 2026-11-05 10:00");
            }
        }
    }

    // ---------- Escenario de demostración ----------

    private void cargarDemostracion() {
        Dueno ana = new Dueno("Ana", "López", "9211111111",
                "ana@correo.com", "Calle Reforma 10");
        Dueno luis = new Dueno("Luis", "Pérez", "9212222222",
                "luis@correo.com", "Av. Central 25");
        Perro rocky = new Perro("Rocky", 4, 18.5, ana, "Labrador", "Alta");
        Gato misu = new Gato("Misu", 3, 4.2, ana, true);
        Ave kiwi = new Ave("Kiwi", 2, 0.9, luis, 35.0);
        clinica.registrarMascota(rocky);
        clinica.registrarMascota(misu);
        clinica.registrarMascota(kiwi);
        Veterinario marta = new Veterinario("Marta", "Ruiz", "9213333333",
                "marta@petcare.com", "CED1001", "Cirugía");
        Veterinario jorge = new Veterinario("Jorge", "Salas", "9214444444",
                "jorge@petcare.com", "CED1002", "Medicina general");
        clinica.agregarVeterinario(marta);
        clinica.agregarVeterinario(jorge);
        System.out.println("Dueños, mascotas y veterinarios registrados.");
        agendarCitasDemostracion(rocky, misu, kiwi, marta, jorge);
    }

    private void agendarCitasDemostracion(Mascota rocky, Mascota misu, Mascota kiwi,
                                          Veterinario marta, Veterinario jorge) {
        LocalDateTime base = LocalDateTime.of(2026, 11, 5, 10, 0);
        Cita cita1 = clinica.agendarCita(rocky, marta, 1, base);
        Cita cita2 = clinica.agendarCita(misu, jorge, 2, base.plusHours(1));
        Cita cita3 = clinica.agendarCita(kiwi, marta, 3, base.plusHours(2));
        System.out.println("Citas agendadas:\n" + cita1 + "\n" + cita2 + "\n" + cita3);
        try {
            clinica.agendarCita(misu, marta, 1, base.plusHours(2));
        } catch (IllegalStateException e) {
            System.out.println("Regla de negocio violada: " + e.getMessage());
        }
        clinica.atenderCita(cita1, new Cirugia(2, 1.5));
        clinica.atenderCita(cita2, new Vacunacion("Rabia", 200.0));
        System.out.println("Atendidas las citas 1 (cirugía) y 2 (vacunación).");
    }
}