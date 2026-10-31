package modelo.servicios;

/**
 * Clase abstracta base de los servicios veterinarios.
 * No implementa calcularCosto(): cada servicio lo calcula a su manera.
 */
public abstract class Servicio implements Facturable {
    private String descripcion;

    public Servicio(String descripcion) {
        if (descripcion == null || descripcion.isBlank()) {
            throw new IllegalArgumentException("La descripción no puede estar vacía");
        }
        this.descripcion = descripcion.trim();
    }

    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Valida que un monto no sea negativo.
     */
    protected double validarMonto(double monto, String campo) {
        if (monto < 0) {
            throw new IllegalArgumentException(campo + " no puede ser negativo");
        }
        return monto;
    }

    @Override
    public String toString() {
        return descripcion + " - Costo: $" + calcularCosto();
    }
}