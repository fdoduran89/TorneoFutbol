package negocio;

import java.util.ArrayList;


public class Equipo {
    
    private String color;
    
    private DT dt;

    private EntrenadorFisico entrenadorFisico;

    private Medico medico;
    
    private String escudo;
    
    private String fundacion;
    
    private ArrayList<Jugador> jugadores = new ArrayList<>();
    
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

    public EntrenadorFisico getEntrenadorFisico() {
        return entrenadorFisico;
    }

    public void setEntrenadorFisico(EntrenadorFisico entrenadorFisico) {
        this.entrenadorFisico = entrenadorFisico;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public void addJugador(Jugador jugador){
        jugadores.add(jugador);
    }
    
    public ArrayList<Jugador> getJugadores(){
        return jugadores;
    }
    
}