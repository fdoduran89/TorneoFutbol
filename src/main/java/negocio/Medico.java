package negocio;


public class Medico extends Empleado {

    private String especialidad;

    private String numeroLicencia;

    private int curaciones;

    private static final float BONO_POR_CURACION = 15_000f;

    private static final int MAX_CURACIONES_POR_LLAMADA = 2;

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getNumeroLicencia() {
        return numeroLicencia;
    }

    public void setNumeroLicencia(String numeroLicencia) {
        this.numeroLicencia = numeroLicencia;
    }

    public int getCuraciones() {
        return curaciones;
    }

    /**
     * Devuelve el bono acumulado por este médico.
     *
     * El bono se calcula como curaciones * BONO_POR_CURACION.
     * No se almacena como atributo: se deriva del contador de curaciones.
     */
    @Override
    public float getBono() {
        return curaciones * BONO_POR_CURACION;
    }

    /**
     * Recupera a un jugador lesionado.
     *
     * Misma lógica que en EntrenadorFisico: solo cuenta como curación
     * si el jugador pasa de lesionado a sano.
     *
     * @param jugador el jugador a recuperar
     * @return true si el jugador fue curado en esta llamada, false en
     *         caso contrario
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
     * Liquidación del salario del médico.
     *
     * Misma fórmula que EntrenadorFisico. El bono se acumula por
     * curaciones durante el torneo y se materializa al liquidar.
     * Método puro: no modifica el estado del objeto.
     */
    @Override
    public float liquidarSalario() {
        return (salario + getBono()) - retencion;
    }

}