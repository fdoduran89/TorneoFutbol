package negocio;


public class RegistroEquipo {

    private Equipo equipo;

    private int puntos;

    private int partidosJugados;

    private int partidosGanados;

    private int partidosEmpatados;

    private int partidosPerdidos;

    private int golesFavor;

    private int golesContra;

    public RegistroEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public int getPuntos() {
        return puntos;
    }

    public int getPartidosJugados() {
        return partidosJugados;
    }

    public int getPartidosGanados() {
        return partidosGanados;
    }

    public int getPartidosEmpatados() {
        return partidosEmpatados;
    }

    public int getPartidosPerdidos() {
        return partidosPerdidos;
    }

    public int getGolesFavor() {
        return golesFavor;
    }

    public int getGolesContra() {
        return golesContra;
    }

    public int getDiferenciaGol() {
        return golesFavor - golesContra;
    }

    /**
     * Registra el resultado de un partido para este equipo.
     * Como se explica en TablaPosiciones, los partidos con penales
     * entran aquí como empate (goles iguales), y luego se ajustan
     * con ajustarVictoriaPorPenales o ajustarDerrotaPorPenales.
     */
    public void registrarPartido(int golesFavor, int golesContra) {
        this.partidosJugados++;
        this.golesFavor += golesFavor;
        this.golesContra += golesContra;

        if (golesFavor > golesContra) {
            this.partidosGanados++;
            this.puntos += 3;
        } else if (golesFavor == golesContra) {
            this.partidosEmpatados++;
            this.puntos += 1;
        } else {
            this.partidosPerdidos++;
        }
    }

    /**
     * Ajusta los contadores y puntos cuando el equipo ganó por penales
     * un partido que había terminado empatado en goles.
     *
     * Estado previo tras registrarPartido:
     *   - partidosEmpatados +1
     *   - puntos +1
     *
     * Estado deseado:
     *   - partidosGanados +1
     *   - puntos +3
     *
     * Por tanto:
     *   - partidosEmpatados -1, partidosGanados +1
     *   - puntos +2 (1 + 2 = 3)
     */
    public void ajustarVictoriaPorPenales() {
        this.partidosEmpatados--;
        this.partidosGanados++;
        this.puntos += 2;
    }

    /**
     * Ajusta los contadores y puntos cuando el equipo perdió por penales
     * un partido que había terminado empatado en goles.
     *
     * Estado previo tras registrarPartido:
     *   - partidosEmpatados +1
     *   - puntos +1
     *
     * Estado deseado:
     *   - partidosPerdidos +1
     *   - puntos 0
     *
     * Por tanto:
     *   - partidosEmpatados -1, partidosPerdidos +1
     *   - puntos -1 (1 - 1 = 0)
     */
    public void ajustarDerrotaPorPenales() {
        this.partidosEmpatados--;
        this.partidosPerdidos++;
        this.puntos -= 1;
    }

    @Override
    public String toString() {
        return String.format("%-10s %2d  %2d  %2d  %2d  %2d  %2d  %3d  %3d",
                equipo.getNombre(),
                partidosJugados,
                partidosGanados,
                partidosEmpatados,
                partidosPerdidos,
                golesFavor,
                golesContra,
                getDiferenciaGol(),
                puntos);
    }

}