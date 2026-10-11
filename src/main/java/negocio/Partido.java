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

    //Goles maximos
    private static final int MAX_GOLES = 9;

    //Factor de azar maximo que se suma o resta al promedio del equipo
    //antes de redondear para obtener los goles.
    private static final float FACTOR_AZAR = 1.0f;

    //Random compartido por todos los partidos.
    private static final Random RANDOM = new Random();

    //Cantidad de tiros reglamentarios por equipo en la tanda de penales.
    private static final int TIROS_REGLAMENTARIOS = 5;

    //Divisor del promedio del jugador para obtener su probabilidad
    //base de convertir un penal. promedio 5 -> 0.5, promedio 8 -> 0.8.
    private static final float DIVISOR_PROBABILIDAD_PENAL = 10.0f;

    //Factor de rendimiento de un jugador lesionado. 0.7 significa
    //que rinde al 70% de su capacidad, tanto en cancha como en penales.
    private static final float FACTOR_LESION = 0.7f;

    //Probabilidad de que un equipo sufra una lesion al final del partido.
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

    /*Implementar el comportamiento del partido
    Se realiza encapsulamiento: Dentro de esta clase se van a ejecutar
    los métodos que va a permitir generar el resultado del partido
     */
    public void jugarResultado() {

        if (jugado) {
            System.out.println("Este partido ya fue jugado. No se puede volver a jugar.");
            return;
        }

        golesLocal = calcularGol(local);
        golesVisitante = calcularGol(visitante);

        // Si hay empate en goles al final del tiempo reglamentario,
        // se juega la tanda de penales.
        if (golesLocal == golesVisitante) {
            jugarTandaPenales();
        }

        // Al final del partido, cada equipo puede sufrir una lesion.
        lesionarJugadorAlAzar(local);
        lesionarJugadorAlAzar(visitante);

        jugado = true;

        imprimirResultado();
    }

    /**
     * Calcula los goles que anota un equipo en un partido.
     *
     * Pasos:
     *   1. Se obtiene la lista de jugadores del equipo.
     *   2. Por cada jugador se suma su promedio, PERO si el jugador
     *      esta lesionado se suma solo su promedio * FACTOR_LESION.
     *      Asi, un equipo con lesionados marca menos goles.
     *   3. Se divide entre el numero de jugadores (siempre 5).
     *   4. Se aplica un factor de azar uniforme.
     *   5. Se redondea, se garantiza no negativo y se capa a MAX_GOLES.
     *
     * @param equipo el equipo cuyos goles se calculan
     * @return los goles anotados por el equipo (entero >= 0)
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
     * Intenta lesionar a un jugador al azar del equipo.
     *
     * Con probabilidad PROBABILIDAD_LESION, elige un jugador al azar
     * y lo marca como lesionado. Si el jugador ya estaba lesionado,
     * no se hace nada (no se "relesiona").
     *
     * Si no habia lesion, no imprime nada. Si hay lesion, imprime
     * una linea avisando.
     *
     * @param equipo el equipo en el que se intenta la lesion
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
     * Juega la tanda de penales completa.
     *
     * Reglas:
     *   - Se tiran TIROS_REGLAMENTARIOS tiros por equipo.
     *   - En los tiros reglamentarios tiran todos los jugadores, en el
     *     orden en que aparecen en la lista del equipo.
     *   - Si tras los tiros reglamentarios sigue el empate, se pasa a
     *     muerte subita: tira un jugador al azar de cada equipo, tiro a
     *     tiro, hasta que uno acierte y el otro falle.
     *
     * El local tira primero en los tiros reglamentarios y tambien en
     * la primera ronda de muerte subita.
     *
     * Al terminar, deja el resultado en penalesLocal y penalesVisitante.
     */
    private void jugarTandaPenales() {
        penalesLocal = 0;
        penalesVisitante = 0;

        ArrayList<Jugador> jugadoresLocal = local.getJugadores();
        ArrayList<Jugador> jugadoresVisitante = visitante.getJugadores();

        // Tiros reglamentarios: tiran todos los jugadores, en orden de lista.
        for (int i = 0; i < TIROS_REGLAMENTARIOS; i++) {
            if (i < jugadoresLocal.size() && tirarPenal(jugadoresLocal.get(i))) {
                penalesLocal++;
            }
            if (i < jugadoresVisitante.size() && tirarPenal(jugadoresVisitante.get(i))) {
                penalesVisitante++;
            }
        }

        // Si tras los tiros reglamentarios siguen empatados, muerte subita.
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
     * Devuelve un jugador al azar de la lista recibida.
     * Se usa en muerte subita, donde no importa quien tira.
     *
     * @param jugadores lista de jugadores del equipo
     * @return un jugador al azar
     */
    private Jugador elegirTiradorAlAzar(ArrayList<Jugador> jugadores) {
        int indice = RANDOM.nextInt(jugadores.size());
        return jugadores.get(indice);
    }

    /**
     * Ejecuta un tiro de penal de un jugador.
     *
     * La probabilidad base de convertir es promedio / DIVISOR_PROBABILIDAD_PENAL.
     * Si el jugador esta lesionado, se multiplica por FACTOR_LESION.
     * Si el promedio es 0, la probabilidad es 0 y siempre falla.
     *
     * @param jugador el jugador que tira
     * @return true si convierte el penal, false si falla
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

    /**
     * Imprime el marcador del partido.
     *
     * Formato con penales:
     *   Colombia 5(3) - 5(2) Brasil
     *
     * Formato sin penales:
     *   Colombia 5 - 5 Brasil
     */
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