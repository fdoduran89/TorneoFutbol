
package negocio;

import java.util.ArrayList;


public class Fecha {
    
    private boolean esFifa;
    
    private int numero;
    
    private ArrayList<Partido> partidos;

    public boolean isEsFifa() {
        return esFifa;
    }

    public void setEsFifa(boolean esFifa) {
        this.esFifa = esFifa;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public ArrayList<Partido> getPartidos() {
        return partidos;
    }

    public void setPartidos(ArrayList<Partido> partidos) {
        this.partidos = partidos;
    }
    
    
    
}
