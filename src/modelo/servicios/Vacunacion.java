package modelo.servicios;

/**
 * Vacunación: tarifa base más el costo de la vacuna aplicada.
 */
public class Vacunacion extends Servicio {
    public static final double TARIFA_APLICACION = 150.0;
    private String nombreVacuna;
    private double costoVacuna;

    public Vacunacion(String nombreVacuna, double costoVacuna) {
        super("Vacunación");
        if (nombreVacuna == null || nombreVacuna.isBlank()) {
            throw new IllegalArgumentException("El nombre de la vacuna no puede estar vacío");
        }
        this.nombreVacuna = nombreVacuna.trim();
        this.costoVacuna = validarMonto(costoVacuna, "El costo de la vacuna");
    }

    public String getNombreVacuna() {
        return nombreVacuna;
    }

    @Override
    public double calcularCosto() {
        return TARIFA_APLICACION + costoVacuna;
    }

    @Override
    public String toString() {
        return getDescripcion() + " (" + nombreVacuna + ") - Costo: $" + calcularCosto();
    }
}