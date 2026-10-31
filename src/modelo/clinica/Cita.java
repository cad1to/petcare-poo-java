package modelo.clinica;

import java.time.LocalDateTime;
import modelo.mascotas.Mascota;
import modelo.personas.Veterinario;
import modelo.servicios.Servicio;

/**
 * Cita que relaciona una mascota con un veterinario en un consultorio
 * y en una fecha y hora (asociación).
 */
public class Cita {
    private final Mascota mascota;
    private final Veterinario veterinario;
    private final Consultorio consultorio;
    private final LocalDateTime fechaHora;
    private EstadoCita estado;
    private Servicio servicio;

    /**
     * Crea una cita en estado PROGRAMADA.
     */
    public Cita(Mascota mascota, Veterinario veterinario, Consultorio consultorio, LocalDateTime fechaHora) {
        if (mascota == null || veterinario == null
                || consultorio == null || fechaHora == null) {
            throw new IllegalArgumentException("La cita necesita mascota, veterinario, consultorio y fecha");
        }
        this.mascota = mascota;
        this.veterinario = veterinario;
        this.consultorio = consultorio;
        this.fechaHora = fechaHora;
        this.estado = EstadoCita.PROGRAMADA;
    }

    /**
     * Marca la cita como atendida y registra el servicio realizado.
     *
     * @throws IllegalStateException si la cita no está programada
     */
    public void atender(Servicio servicio) {
        if (servicio == null) {
            throw new IllegalArgumentException("Debes indicar el servicio realizado");
        }
        if (estado == EstadoCita.CANCELADA) {
            throw new IllegalStateException("Una cita cancelada no puede marcarse como atendida");
        }
        if (estado == EstadoCita.ATENDIDA) {
            throw new IllegalStateException("La cita ya fue atendida");
        }
        this.servicio = servicio;
        this.estado = EstadoCita.ATENDIDA;
    }

    /**
     * Cancela la cita si todavía está programada.
     */
    public void cancelar() {
        if (estado == EstadoCita.ATENDIDA) {
            throw new IllegalStateException("Una cita atendida no puede cancelarse");
        }
        this.estado = EstadoCita.CANCELADA;
    }

    public Mascota getMascota() {
        return mascota;
    }

    public Veterinario getVeterinario() {
        return veterinario;
    }

    public Consultorio getConsultorio() {
        return consultorio;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public EstadoCita getEstado() {
        return estado;
    }

    public Servicio getServicio() {
        return servicio;
    }

    @Override
    public String toString() {
        return "Cita [" + estado + "] " + fechaHora + " - " + mascota.getNombre()
                + " con " + veterinario.getNombre() + " en " + consultorio;
    }
}