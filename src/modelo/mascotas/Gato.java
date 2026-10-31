package modelo.mascotas;

import modelo.personas.Dueno;

/**
 * Gato, que puede ser doméstico o no.
 */
public class Gato extends Mascota {
    private boolean esDomestico;

    public Gato(String nombre, int edad, double peso, Dueno dueno, boolean esDomestico) {
        super(nombre, edad, peso, dueno);
        this.esDomestico = esDomestico;
    }

    public boolean isEsDomestico() {
        return esDomestico;
    }

    public void setEsDomestico(boolean esDomestico) {
        this.esDomestico = esDomestico;
    }

    @Override
    public String hacerSonido() {
        return "Miau";
    }

    @Override
    public String toString() {
        return "Gato: " + super.toString() + " - Doméstico: "
                + (esDomestico ? "sí" : "no");
    }
}