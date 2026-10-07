
package negocio;


public class Persona {
    /*
    Utilizamos protected en los atributos para que solo puedan ser accedidos
    por las clases hijas
    */
    protected String nombre;
    
    protected String apellido;
    
    protected String cedula;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }
    
    
    
}
