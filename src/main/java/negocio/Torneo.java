
package negocio;

import java.util.ArrayList;

public class Torneo {
    
    private String descripcion;
    
    private String nombre;
    
    private boolean local;
    
    private ArrayList<Fecha> fecha;

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isLocal() {
        return local;
    }

    public void setLocal(boolean local) {
        this.local = local;
    }

    public ArrayList<Fecha> getFecha() {
        return fecha;
    }

    public void setFecha(ArrayList<Fecha> fecha) {
        this.fecha = fecha;
    }
    
    
    
}
