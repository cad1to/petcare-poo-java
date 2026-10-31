package modelo.mascotas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import modelo.clinica.Cita;
import modelo.clinica.EstadoCita;

/**
 * Historial clínico de una mascota. Solo tiene sentido dentro de ella
 * (composición): la mascota lo crea y desaparece junto con ella.
 */
public class HistorialClinico {
    private final List<Cita> citasAtendidas = new ArrayList<>();

    // Constructor de paquete: solo Mascota (mismo paquete) puede crearlo,
    // así ningún historial existe suelto, sin mascota.
    HistorialClinico() {
    }

    /**
     * Registra una cita ya atendida.
     */
    public void agregarCita(Cita cita) {
        if (cita == null || cita.getEstado() != EstadoCita.ATENDIDA) {
            throw new IllegalArgumentException("Solo se registran citas atendidas");
        }
        citasAtendidas.add(cita);
    }

    public List<Cita> getCitasAtendidas() {
        return Collections.unmodifiableList(citasAtendidas);
    }

    @Override
    public String toString() {
        if (citasAtendidas.isEmpty()) {
            return "Sin consultas registradas";
        }
        StringBuilder texto = new StringBuilder();
        for (Cita cita : citasAtendidas) {
            texto.append("- ").append(cita.getFechaHora()).append(" | ")
                    .append(cita.getVeterinario().getNombre()).append(" | ")
                    .append(cita.getServicio().getDescripcion()).append("\n");
        }
        return texto.toString();
    }
}