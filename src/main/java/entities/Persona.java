package entities;

public class Persona {

    private Integer idPersona;
    private String nombre;
    private Integer edad;
    private Integer idDireccion;

    public Persona(Integer idPersona, String nombre, int edad, Integer idDireccion) {
        this.idPersona = idPersona;
        this.nombre = nombre;
        this.edad = edad;
        this.idDireccion = idDireccion;
    }

    public Persona() {

    }

    public Integer getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Integer idPersona) {
        this.idPersona = idPersona;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public Integer getIdDireccion() {
        return idDireccion;
    }

    public void setIdDireccion(Integer idDireccion) {
        this.idDireccion = idDireccion;
    }

    @Override
    public String toString() {
        return "Persona{" +
                "idPersona=" + idPersona +
                ", nombre='" + nombre + '\'' +
                ", edad=" + edad +
                ", idDireccion=" + idDireccion +
                '}';
    }
}
