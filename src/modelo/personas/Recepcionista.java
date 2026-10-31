package modelo.personas;

/**
 * Recepcionista de la clínica, identificado por su turno de trabajo.
 */
public class Recepcionista extends Persona {
    private String turno;

    public Recepcionista(String nombre, String apellido, String telefono, String correo, String turno) {
        super(nombre, apellido, telefono, correo);
        setTurno(turno);
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = validarTexto(turno, "El turno");
    }

    @Override
    public String describir() {
        return "Recepcionista: " + getNombre() + " " + getApellido()
                + ", turno " + turno;
    }

    @Override
    public String toString() {
        return super.toString() + " - Turno: " + turno;
    }
}