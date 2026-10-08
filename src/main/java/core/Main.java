
package core;

import java.util.ArrayList;
import java.util.Random;
import negocio.DT;
import negocio.Empleado;
import negocio.Equipo;
import negocio.Estadio;
import negocio.Jugador;
import negocio.Partido;
import negocio.Torneo;
import servicios.ServLiquidacionSalario;


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
        
        /*
        DEFINICION DE LAS VARIABLES DEL SALARIO
        */
        
        float salarioJuagador = 200_000;
        float retencionJugador = 50_000;
        float salarioDT = 200_000;
        float retencionDT = 50_000;
        
        
        //CREACION DE DT
        
        DT dtEquipoA = new DT();
        dtEquipoA.setSalario(salarioDT);
        dtEquipoA.setNombre("Nestor Lorenzo");
        dtEquipoA.setRetencion(retencionDT);   
        
        DT dtEquipoB = new DT();
        dtEquipoB.setSalario(salarioDT);
        dtEquipoB.setNombre("Ancelloti");
        dtEquipoB.setRetencion(retencionDT);
        
        //Se crean los objetos de tipo empleado que pertenecen a un equipo
        ArrayList<Empleado> empleadoEquipoA = new ArrayList<Empleado>();
        ArrayList<Empleado> empleadoEquipoB = new ArrayList<Empleado>();
        
        empleadoEquipoA.add(dtEquipoA);
        empleadoEquipoB.add(dtEquipoB);
        //Equipo A
        for(int i=0;i<5;i++){
            Jugador jugador = new Jugador();
            capacidadJugador = rnd.nextInt(8) + 1;
            
            jugador.setNombre(i + "-"+ equipoA.getNombre());
            
            jugador.setSalario(salarioJuagador);
            jugador.setRetencion(retencionJugador);
            
            jugador.setCabeza(capacidadJugador);
            jugador.setRegate(capacidadJugador);
            jugador.setVelocidad(capacidadJugador);
            
            equipoA.addJugador(jugador);
            jugador.obtenerPromedio();
            
            empleadoEquipoA.add(jugador);
            
        }
        //Equipo B
            for(int j=0;j<5;j++){
            Jugador jugador = new Jugador();
            capacidadJugador = rnd.nextInt(8) + 1;
            
            jugador.setNombre(j + "-"+ equipoB.getNombre());
            
            jugador.setSalario(salarioJuagador);
            jugador.setRetencion(retencionJugador);
            
            jugador.setCabeza(capacidadJugador);
            jugador.setRegate(capacidadJugador);
            jugador.setVelocidad(capacidadJugador);
            
            equipoB.addJugador(jugador);
            jugador.obtenerPromedio();
            
            empleadoEquipoB.add(jugador);
            
        }
            
            Estadio estadio = new Estadio();
            estadio.setNombre("Atanasio Girardot");
            
            Partido partido = new Partido();
            partido.setEstadio(estadio);
            partido.setLocal(equipoA);
            partido.setVisitante(equipoB);
            
            partido.jugarResultado();
            
            System.out.println("#########################");
            System.out.println("SALARIOS DEL EQUIPO A");
            
            ServLiquidacionSalario serviceLiquidadorA = new ServLiquidacionSalario();
            
            serviceLiquidadorA.liquidarSueldo(empleadoEquipoA);
            
            System.out.println("SALARIOS DEL EQUIPO B");
            
            ServLiquidacionSalario serviceLiquidadorB = new ServLiquidacionSalario();
            
            serviceLiquidadorB.liquidarSueldo(empleadoEquipoB);
    }
       
}
