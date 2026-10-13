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
     * Calcula el bono según las curaciones realizadas.
     */
    @Override
    public float getBono() {
        return curaciones * BONO_POR_CURACION;
    }

    /**
     * Recupera a un jugador lesionado y registra la curación.
     *
     * @param jugador jugador a recuperar
     * @return true si fue curado
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
     * Recupera hasta dos jugadores lesionados del equipo.
     *
     * @param equipo equipo cuyos jugadores se recuperan
     * @return cantidad de jugadores curados
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
     * Calcula el salario con el bono y la retención.
     */
    @Override
    public float liquidarSalario() {
        return (salario + getBono()) - retencion;
    }

}