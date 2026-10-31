package modelo.personas;

/**
 * Clase abstracta que agrupa los datos comunes de las personas de la clínica.
 */
public abstract class Persona {
    private String nombre;
    private String apellido;
    private String telefono;
    private String correo;

    /**
     * Crea una persona validando todos sus datos.
     */
    public Persona(String nombre, String apellido, String telefono, String correo) {
        setNombre(nombre);
        setApellido(apellido);
        setTelefono(telefono);
        setCorreo(correo);
    }

    /**
     * Describe a la persona según su rol. Cada hija lo implementa a su manera.
     */
    public abstract String describir();

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = validarTexto(nombre, "El nombre");
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = validarTexto(apellido, "El apellido");
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = validarTexto(telefono, "El teléfono");
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = validarTexto(correo, "El correo");
    }

    /**
     * Valida que un texto no sea nulo ni vacío.
     */
    // Es protected porque solo las clases hijas deben reutilizarlo;
    // no tiene sentido que sea public ni que quede privado a Persona.
    protected String validarTexto(String valor, String campo) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException(campo + " no puede estar vacío");
        }
        return valor.trim();
    }

    @Override
    public String toString() {
        return nombre + " " + apellido + " (" + telefono + ", " + correo + ")";
    }
}