package modelo.servicios;

/**
 * Consulta general con tarifa base.
 */
public class ConsultaGeneral extends Servicio {
    public static final double TARIFA_BASE = 350.0;

    public ConsultaGeneral() {
        super("Consulta general");
    }

    @Override
    public double calcularCosto() {
        return TARIFA_BASE;
    }
}