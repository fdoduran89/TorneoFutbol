package negocio;

import java.util.ArrayList;
import java.util.Random;

public class Partido {

    private int golesLocal;

    private int golesVisitante;

    private Estadio estadio;

    private Fecha fecha;

    private Equipo local;

    private Equipo visitante;

    //Goles maximos
    private static final int MAX_GOLES = 9;

    public Estadio getEstadio() {
        return estadio;
    }

    public void setEstadio(Estadio estadio) {
        this.estadio = estadio;
    }

    public Fecha getFecha() {
        return fecha;
    }

    public void setFecha(Fecha fecha) {
        this.fecha = fecha;
    }

    public Equipo getLocal() {
        return local;
    }

    public void setLocal(Equipo local) {
        this.local = local;
    }

    public Equipo getVisitante() {
        return visitante;
    }

    public void setVisitante(Equipo visitante) {
        this.visitante = visitante;
    }

    /*Implementar el comportamiento del partido
    Se realiza encapsulamiento: Dentro de esta clase se van a ejecutar
    los métodos que va a permitir generar el resultado del partido
     */
    public void jugarResultado() {

        golesLocal = calcularGol(local);
        golesVisitante = calcularGol(visitante);

        System.out.println("=====================================");
        System.out.println("  |  RESUTLADO DEL PARTIDO  |");
        System.out.println("=====================================");
        System.out.print(" (" + golesLocal + ")" + local.getNombre() + " - ");
        System.out.print("" + visitante.getNombre() + "(" + golesVisitante + ")");
        System.out.println("");
        System.out.println("=====================================");

    }

    private int calcularGol(Equipo equipo) {
        ArrayList<Jugador> jugadores = equipo.getJugadores();
        int promedio = 0;
        for (Jugador jugador : jugadores) {
            promedio += jugador.getPromedio();

        }
        promedio /= jugadores.size();
        System.out.println("El promedio TOTAL es: " + promedio);

        return promedio;
    }

}
