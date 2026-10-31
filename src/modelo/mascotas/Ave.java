package modelo.mascotas;

import modelo.personas.Dueno;

/**
 * Ave exótica, con longitud de alas.
 */
public class Ave extends Mascota {
    private double longitudAlas;

    public Ave(String nombre, int edad, double peso, Dueno dueno, double longitudAlas) {
        super(nombre, edad, peso, dueno);
        setLongitudAlas(longitudAlas);
    }

    public double getLongitudAlas() {
        return longitudAlas;
    }

    public void setLongitudAlas(double longitudAlas) {
        if (longitudAlas <= 0) {
            throw new IllegalArgumentException("La longitud de alas debe ser mayor que cero");
        }
        this.longitudAlas = longitudAlas;
    }

    @Override
    public String hacerSonido() {
        return "Pío";
    }

    @Override
    public String toString() {
        return "Ave: " + super.toString() + " - Alas: " + longitudAlas + " cm";
    }
}