package negocio;

import java.util.ArrayList;
import java.util.Random;

public class Partido {

    private int golesLocal;
    private int golesVisitante;
    private int penalesLocal;
    private int penalesVisitante;
    private Estadio estadio;
    private Fecha fecha;
    private Equipo local;
    private Equipo visitante;
    private boolean jugado;

    // Máximo de goles por equipo.
    private static final int MAX_GOLES = 9;

    // Variación aleatoria de los goles.
    private static final float FACTOR_AZAR = 1.0f;

    // Generador aleatorio compartido.
    private static final Random RANDOM = new Random();

    // Tiros reglamentarios por equipo.
    private static final int TIROS_REGLAMENTARIOS = 5;

    // Divisor para calcular la probabilidad de penal.
    private static final float DIVISOR_PROBABILIDAD_PENAL = 10.0f;

    // Rendimiento de un jugador lesionado (70 %).
    private static final float FACTOR_LESION = 0.7f;

    // Probabilidad de lesión (20 %).
    private static final float PROBABILIDAD_LESION = 0.2f;

    public int getGolesLocal() {
        return golesLocal;
    }

    public int getGolesVisitante() {
        return golesVisitante;
    }

    public int getPenalesLocal() {
        return penalesLocal;
    }

    public int getPenalesVisitante() {
        return penalesVisitante;
    }

    public boolean huboPenales() {
        return penalesLocal > 0 || penalesVisitante > 0;
    }

    public boolean isJugado() {
        return jugado;
    }

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

    // Ejecuta el partido y genera su resultado.
    public void jugarResultado() {

        if (jugado) {
            System.out.println("Este partido ya fue jugado. No se puede volver a jugar.");
            return;
        }

        golesLocal = calcularGol(local);
        golesVisitante = calcularGol(visitante);

        // Si hay empate, se define por penales.
        if (golesLocal == golesVisitante) {
            jugarTandaPenales();
        }

        // Cada equipo puede sufrir una lesión.
        lesionarJugadorAlAzar(local);
        lesionarJugadorAlAzar(visitante);

        jugado = true;

        imprimirResultado();
    }

    /**
     * Calcula los goles según el promedio de los jugadores y el azar.
     *
     * @param equipo equipo cuyos goles se calculan
     * @return cantidad de goles
     */
    private int calcularGol(Equipo equipo) {
        ArrayList<Jugador> jugadores = equipo.getJugadores();

        if (jugadores == null || jugadores.isEmpty()) {
            return 0;
        }

        float sumaPromedios = 0.0f;
        for (Jugador jugador : jugadores) {
            float aporte = jugador.getPromedio();
            if (jugador.isLesionado()) {
                aporte = aporte * FACTOR_LESION;
            }
            sumaPromedios += aporte;
        }

        float promedio = sumaPromedios / jugadores.size();

        float azar = (RANDOM.nextFloat() * 2.0f - 1.0f) * FACTOR_AZAR;

        float golesConAzar = promedio + azar;
        if (golesConAzar < 0.0f) {
            golesConAzar = 0.0f;
        }

        int goles = Math.round(golesConAzar);

        if (goles > MAX_GOLES) {
            goles = MAX_GOLES;
        }

        return goles;
    }

    /**
     * Intenta lesionar aleatoriamente a un jugador.
     *
     * @param equipo equipo donde se evalúa la lesión
     */
    private void lesionarJugadorAlAzar(Equipo equipo) {
        if (equipo == null || equipo.getJugadores().isEmpty()) {
            return;
        }
        if (RANDOM.nextFloat() >= PROBABILIDAD_LESION) {
            return;
        }

        ArrayList<Jugador> jugadores = equipo.getJugadores();
        int indice = RANDOM.nextInt(jugadores.size());
        Jugador jugador = jugadores.get(indice);

        if (jugador.isLesionado()) {
            return;
        }

        jugador.setLesionado(true);
        System.out.println(">>> " + jugador.getNombre() + " se lesiono.");
    }

    /**
     * Ejecuta los penales reglamentarios y la muerte súbita.
     */
    private void jugarTandaPenales() {
        penalesLocal = 0;
        penalesVisitante = 0;

        ArrayList<Jugador> jugadoresLocal = local.getJugadores();
        ArrayList<Jugador> jugadoresVisitante = visitante.getJugadores();

        // Cada equipo ejecuta sus tiros reglamentarios.
        for (int i = 0; i < TIROS_REGLAMENTARIOS; i++) {
            if (i < jugadoresLocal.size() && tirarPenal(jugadoresLocal.get(i))) {
                penalesLocal++;
            }
            if (i < jugadoresVisitante.size() && tirarPenal(jugadoresVisitante.get(i))) {
                penalesVisitante++;
            }
        }

        // Si persiste el empate, se juega muerte súbita.
        while (penalesLocal == penalesVisitante) {
            boolean golLocal = tirarPenal(elegirTiradorAlAzar(jugadoresLocal));
            boolean golVisitante = tirarPenal(elegirTiradorAlAzar(jugadoresVisitante));

            if (golLocal) {
                penalesLocal++;
            }
            if (golVisitante) {
                penalesVisitante++;
            }
        }
    }

    /**
     * Selecciona un jugador aleatorio.
     *
     * @param jugadores lista de jugadores
     * @return jugador seleccionado
     */
    private Jugador elegirTiradorAlAzar(ArrayList<Jugador> jugadores) {
        int indice = RANDOM.nextInt(jugadores.size());
        return jugadores.get(indice);
    }

    /**
     * Simula un penal según el promedio y la lesión del jugador.
     *
     * @param jugador jugador que ejecuta el penal
     * @return true si marca, false si falla
     */
    private boolean tirarPenal(Jugador jugador) {
        float probabilidad = jugador.getPromedio() / DIVISOR_PROBABILIDAD_PENAL;

        if (jugador.isLesionado()) {
            probabilidad = probabilidad * FACTOR_LESION;
        }

        if (probabilidad <= 0.0f) {
            return false;
        }

        return RANDOM.nextFloat() < probabilidad;
    }

    // Imprime el resultado del partido, incluidos los penales si existen.
    private void imprimirResultado() {
        System.out.println("=====================================");
        System.out.println("  |  RESULTADO DEL PARTIDO  |");
        System.out.println("=====================================");

        if (huboPenales()) {
            System.out.println(local.getNombre() + " " + golesLocal
                    + "(" + penalesLocal + ") - "
                    + golesVisitante + "(" + penalesVisitante + ") "
                    + visitante.getNombre());
        } else {
            System.out.println(local.getNombre() + " " + golesLocal
                    + " - "
                    + golesVisitante + " "
                    + visitante.getNombre());
        }

        System.out.println("=====================================");
    }

}