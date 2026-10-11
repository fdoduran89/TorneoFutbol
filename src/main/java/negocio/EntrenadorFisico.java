package negocio;


public class EntrenadorFisico extends Empleado {

    private String especialidad;

    private int curaciones;

    private static final float BONO_POR_CURACION = 15_000f;

    private static final int MAX_CURACIONES_POR_LLAMADA = 2;

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public int getCuraciones() {
        return curaciones;
    }

    /**
     * Devuelve el bono acumulado por este entrenador físico.
     *
     * El bono se calcula como curaciones * BONO_POR_CURACION.
     * No se almacena como atributo: se deriva del contador de curaciones.
     * Así, si en el futuro cambia el valor del bono por curación, solo
     * hay que tocar la constante.
     */
    @Override
    public float getBono() {
        return curaciones * BONO_POR_CURACION;
    }

    /**
     * Recupera a un jugador lesionado.
     *
     * Si el jugador ya estaba sano, no se hace nada y no se cuenta
     * como curación. Solo se cuenta (y por tanto solo genera bono)
     * cuando el jugador pasa de lesionado a sano.
     *
     * @param jugador el jugador a recuperar
     * @return true si el jugador fue curado en esta llamada, false si
     *         ya estaba sano o si el jugador es null
     */
    public boolean recuperar(Jugador jugador) {
        if (jugador == null) {
            return false;
        }
        if (!jugador.isLesionado()) {
            return false;
        }
        jugador.setLesionado(false);
        curaciones++;
        return true;
    }

    /**
     * Recupera hasta MAX_CURACIONES_POR_LLAMADA jugadores lesionados
     * del equipo.
     *
     * @param equipo el equipo cuyos jugadores se van a recuperar
     * @return el número de jugadores efectivamente curados en esta llamada
     */
    public int recuperarEquipo(Equipo equipo) {
        if (equipo == null) {
            return 0;
        }
        int curados = 0;
        for (Jugador jugador : equipo.getJugadores()) {
            if (curados >= MAX_CURACIONES_POR_LLAMADA) {
                break;
            }
            if (recuperar(jugador)) {
                curados++;
            }
        }
        return curados;
    }

    /**
     * Liquidación del salario del entrenador físico.
     *
     * El salario depende de:
     *   - salario base (heredado de Empleado)
     *   - bono acumulado: curaciones * BONO_POR_CURACION
     *   - retención (heredada de Empleado)
     *
     * Este método es puro: no modifica el estado del objeto, así que
     * llamarlo varias veces devuelve siempre el mismo resultado.
     * El bono se acumula en el torneo (vía recuperar/recuperarEquipo),
     * no aquí.
     */
    @Override
    public float liquidarSalario() {
        return (salario + getBono()) - retencion;
    }

}