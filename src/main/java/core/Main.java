
package core;

import java.util.Random;
import negocio.Equipo;
import negocio.Estadio;
import negocio.Jugador;
import negocio.Partido;
import negocio.Torneo;


public class Main {


    public static void main(String[] args) {
        
        Torneo torneo = new Torneo();
        
        torneo.setNombre("Mundial 2026");
        
        Equipo equipoA = new Equipo();
        equipoA.setNombre("Colombia");
        
        Equipo equipoB = new Equipo();
        equipoB.setNombre("Brasil");
        
        Random rnd = new Random();
        int capacidadJugador = 0;
        
        //Equipo A
        for(int i=0;i<5;i++){
            Jugador jugador = new Jugador();
            capacidadJugador = rnd.nextInt(8) + 1;
            
            jugador.setNombre(i + "-"+ equipoA.getNombre());
            
            jugador.setCabeza(capacidadJugador);
            jugador.setRegate(capacidadJugador);
            jugador.setVelocidad(capacidadJugador);
            
            equipoA.addJugador(jugador);
            jugador.obtenerPromedio();
            
        }
        //Equipo B
            for(int j=0;j<5;j++){
            Jugador jugador = new Jugador();
            capacidadJugador = rnd.nextInt(8) + 1;
            
            jugador.setNombre(j + "-"+ equipoB.getNombre());
            
            jugador.setCabeza(capacidadJugador);
            jugador.setRegate(capacidadJugador);
            jugador.setVelocidad(capacidadJugador);
            
            equipoB.addJugador(jugador);
            jugador.obtenerPromedio();
            
        }
            
            Estadio estadio = new Estadio();
            estadio.setNombre("Atanasio Girardot");
            
            Partido partido = new Partido();
            partido.setEstadio(estadio);
            partido.setLocal(equipoA);
            partido.setVisitante(equipoB);
            
            partido.jugarResultado();
    }
    
}
