package entities;


public class Direccion {

    private Integer idDireccion;
    private String ciudad;
    private String calle;
    private Integer numero;

    public Direccion() {
    }

    public Direccion(Integer idDireccion, String ciudad, String calle, int numero) {
        this.idDireccion = idDireccion;
        this.ciudad = ciudad;
        this.calle = calle;
        this.numero = numero;
    }

    public Integer getIdDireccion() {
        return idDireccion;
    }

    public void setIdDireccion(Integer idDireccion) {
        this.idDireccion = idDireccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    @Override
    public String toString() {
        return "Direccion{" +
                "idDireccion=" + idDireccion +
                ", ciudad='" + ciudad + '\'' +
                ", calle='" + calle + '\'' +
                ", numero=" + numero +
                '}';
    }
}
