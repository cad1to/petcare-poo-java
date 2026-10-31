package modelo.clinica;

/**
 * Consultorio numerado que pertenece a la clínica (composición).
 */
public class Consultorio {
    private final int numero;

    // Constructor de paquete (sin public): solo Clinica, que está en este
    // mismo paquete, puede crear consultorios. Así el consultorio no existe
    // fuera de la clínica, como pide la composición.
    Consultorio(int numero) {
        if (numero <= 0) {
            throw new IllegalArgumentException("El número de consultorio debe ser mayor que cero");
        }
        this.numero = numero;
    }

    public int getNumero() {
        return numero;
    }

    @Override
    public String toString() {
        return "Consultorio " + numero;
    }
}