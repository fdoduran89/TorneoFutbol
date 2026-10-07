
package negocio;

import java.util.ArrayList;


public class Equipo {
    
    private String color;
    
    private DT dt;
    
    private String escudo;
    
    private String fundacion;
    
    private ArrayList<Jugador> jugadores;
    
    private String nombre;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public DT getDt() {
        return dt;
    }

    public void setDt(DT dt) {
        this.dt = dt;
    }

    public String getEscudo() {
        return escudo;
    }

    public void setEscudo(String escudo) {
        this.escudo = escudo;
    }

    public String getFundacion() {
        return fundacion;
    }

    public void setFundacion(String fundacion) {
        this.fundacion = fundacion;
    }

    /*public ArrayList<Jugador> getJugadores() {
        return jugadores;
    }

    public void setJugadores(ArrayList<Jugador> jugadores) {
        this.jugadores = jugadores;
    }*/

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public void addJugador(Jugador jugador){
        if(jugadores == null){
            jugadores = new ArrayList<>();
        }
        jugadores.add(jugador);
    }
    
    public ArrayList<Jugador> getJugadores(){
        return jugadores;
    }
    
}
