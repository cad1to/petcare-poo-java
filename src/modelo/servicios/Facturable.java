package modelo.servicios;

/**
 * Contrato para todo lo que la clínica puede cobrar.
 */
public interface Facturable {

    /**
     * Calcula el costo del servicio. Nunca debe ser negativo.
     */
    double calcularCosto();
}