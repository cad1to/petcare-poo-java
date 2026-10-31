package modelo.mascotas;

import modelo.personas.Dueno;

/**
 * Perro, con raza y nivel de energía.
 */
public class Perro extends Mascota {
    private String raza;
    private String nivelEnergia;

    public Perro(String nombre, int edad, double peso, Dueno dueno, String raza, String nivelEnergia) {
        super(nombre, edad, peso, dueno);
        setRaza(raza);
        setNivelEnergia(nivelEnergia);
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        if (raza == null || raza.isBlank()) {
            throw new IllegalArgumentException("La raza no puede estar vacía");
        }
        this.raza = raza.trim();
    }

    public String getNivelEnergia() {
        return nivelEnergia;
    }

    public void setNivelEnergia(String nivelEnergia) {
        if (nivelEnergia == null || nivelEnergia.isBlank()) {
            throw new IllegalArgumentException("El nivel de energía no puede estar vacío");
        }
        this.nivelEnergia = nivelEnergia.trim();
    }

    @Override
    public String hacerSonido() {
        return "Guau";
    }

    @Override
    public String toString() {
        return "Perro: " + super.toString() + " - Raza: " + raza
                + ", Energía: " + nivelEnergia;
    }
}