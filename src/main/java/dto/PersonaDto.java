package dto;

public class PersonaDto {
    private String nombre;
    private int edad;
    private String ciudad;
    private String calle;
    private int numero;

    public PersonaDto() {
    }

    public PersonaDto(String nombre, int edad, String ciudad, String calle, int numero) {
        this.nombre = nombre;
        this.edad = edad;
        this.ciudad = ciudad;
        this.calle = calle;
        this.numero = numero;
    }

    public String getNombre() {
        return nombre;
    }

    public int getEdad() {
        return edad;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String getCalle() {
        return calle;
    }

    public int getNumero() {
        return numero;
    }

    @Override
    public String toString() {
        return "PersonaDTO{" +
                "nombre='" + nombre + '\'' +
                ", edad=" + edad +
                ", ciudad='" + ciudad + '\'' +
                ", calle='" + calle + '\'' +
                ", numero=" + numero +
                '}';
    }
}
