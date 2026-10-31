package modelo.personas;

/**
 * Dueño de una o más mascotas de la clínica.
 */
public class Dueno extends Persona {
    private String direccion;

    /**
     * Crea un dueño con todos sus datos.
     */
    public Dueno(String nombre, String apellido, String telefono, String correo, String direccion) {
        super(nombre, apellido, telefono, correo);
        setDireccion(direccion);
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = validarTexto(direccion, "La dirección");
    }

    @Override
    public String describir() {
        return "Dueño: " + getNombre() + " " + getApellido()
                + ", vive en " + direccion;
    }

    @Override
    public String toString() {
        return super.toString() + " - Dirección: " + direccion;
    }
}