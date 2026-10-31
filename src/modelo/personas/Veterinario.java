package modelo.personas;

/**
 * Veterinario que atiende citas. Existe por sí mismo, fuera de la clínica.
 */
public class Veterinario extends Persona {
    private String cedulaProfesional;
    private String especialidad;

    public Veterinario(String nombre, String apellido, String telefono, String correo, String cedulaProfesional,
                       String especialidad) {
        super(nombre, apellido, telefono, correo);
        setCedulaProfesional(cedulaProfesional);
        setEspecialidad(especialidad);
    }

    public String getCedulaProfesional() {
        return cedulaProfesional;
    }

    public void setCedulaProfesional(String cedulaProfesional) {
        this.cedulaProfesional = validarTexto(cedulaProfesional, "La cédula");
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = validarTexto(especialidad, "La especialidad");
    }

    @Override
    public String describir() {
        return "Veterinario/a: " + getNombre() + " " + getApellido()
                + ", especialidad en " + especialidad;
    }

    @Override
    public String toString() {
        return super.toString() + " - Cédula: " + cedulaProfesional
                + ", Especialidad: " + especialidad;
    }
}