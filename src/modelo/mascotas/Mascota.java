package modelo.mascotas;

import modelo.personas.Dueno;

/**
 * Clase abstracta con los datos y comportamientos comunes de las mascotas.
 * Crea y posee su historial clínico (composición).
 */
public abstract class Mascota {
    private String nombre;
    private int edad;
    private double peso;
    private Dueno dueno;
    private final HistorialClinico historial = new HistorialClinico();

    /**
     * Crea una mascota validando sus datos.
     */
    public Mascota(String nombre, int edad, double peso, Dueno dueno) {
        setNombre(nombre);
        setEdad(edad);
        setPeso(peso);
        setDueno(dueno);
    }

    /**
     * Devuelve el sonido de la mascota. Cada especie lo implementa distinto.
     */
    public abstract String hacerSonido();

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        this.nombre = nombre.trim();
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        if (edad <= 0) {
            throw new IllegalArgumentException("La edad debe ser mayor que cero");
        }
        this.edad = edad;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        if (peso <= 0) {
            throw new IllegalArgumentException("El peso debe ser mayor que cero");
        }
        this.peso = peso;
    }

    public Dueno getDueno() {
        return dueno;
    }

    public void setDueno(Dueno dueno) {
        if (dueno == null) {
            throw new IllegalArgumentException("La mascota debe tener un dueño");
        }
        this.dueno = dueno;
    }

    public HistorialClinico getHistorial() {
        return historial;
    }

    @Override
    public String toString() {
        return nombre + " (" + edad + " años, " + peso + " kg) - Dueño: "
                + dueno.getNombre() + " " + dueno.getApellido();
    }
}