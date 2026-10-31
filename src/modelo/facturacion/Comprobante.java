package modelo.facturacion;

import modelo.clinica.Cita;
import modelo.clinica.EstadoCita;

/**
 * Comprobante que se genera al terminar una cita atendida.
 */
public class Comprobante {
    // static: el contador es de la clase, no de cada comprobante;
    // se comparte para que cada folio sea distinto.
    private static int comprobantesGenerados = 0;

    private final int folio;
    private final Cita cita;
    private final double total;

    /**
     * Genera el comprobante de una cita atendida.
     */
    public Comprobante(Cita cita) {
        if (cita == null || cita.getEstado() != EstadoCita.ATENDIDA) {
            throw new IllegalArgumentException("Solo se genera comprobante de citas atendidas");
        }
        comprobantesGenerados++;
        this.folio = comprobantesGenerados;
        this.cita = cita;
        this.total = cita.getServicio().calcularCosto();
    }

    public static int getComprobantesGenerados() {
        return comprobantesGenerados;
    }

    public int getFolio() {
        return folio;
    }

    public Cita getCita() {
        return cita;
    }

    public double getTotal() {
        return total;
    }

    @Override
    public String toString() {
        return "=== Comprobante #" + folio + " ===\n"
                + "Mascota: " + cita.getMascota().getNombre() + "\n"
                + "Dueño: " + cita.getMascota().getDueno().getNombre() + "\n"
                + "Veterinario: " + cita.getVeterinario().getNombre() + "\n"
                + "Consultorio: " + cita.getConsultorio().getNumero() + "\n"
                + "Servicio: " + cita.getServicio().getDescripcion() + "\n"
                + "Total: $" + total;
    }
}