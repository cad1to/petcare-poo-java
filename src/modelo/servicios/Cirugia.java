package modelo.servicios;

/**
 * Cirugía: el costo depende de la complejidad (1 a 3) y de las horas de quirófano.
 */
public class Cirugia extends Servicio {
    public static final double TARIFA_HORA_QUIROFANO = 800.0;
    private int complejidad;
    private double horasQuirofano;

    public Cirugia(int complejidad, double horasQuirofano) {
        super("Cirugía");
        if (complejidad < 1 || complejidad > 3) {
            throw new IllegalArgumentException("La complejidad debe ser de 1 a 3");
        }
        if (horasQuirofano <= 0) {
            throw new IllegalArgumentException("Las horas de quirófano deben ser mayores que cero");
        }
        this.complejidad = complejidad;
        this.horasQuirofano = horasQuirofano;
    }

    public int getComplejidad() {
        return complejidad;
    }

    public double getHorasQuirofano() {
        return horasQuirofano;
    }

    @Override
    public double calcularCosto() {
        return TARIFA_HORA_QUIROFANO * horasQuirofano * complejidad;
    }

    @Override
    public String toString() {
        return getDescripcion() + " (complejidad " + complejidad + ", "
                + horasQuirofano + " h) - Costo: $" + calcularCosto();
    }
}