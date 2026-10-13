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

    // Calcula la diferencia entre goles a favor y en contra.
    public int getDiferenciaGol() {
        return golesFavor - golesContra;
    }

    /**
     * Registra el resultado y asigna los puntos correspondientes.
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
     * Actualiza el resultado cuando el equipo gana por penales.
     */
    public void ajustarVictoriaPorPenales() {
        this.partidosEmpatados--;
        this.partidosGanados++;
        this.puntos += 2;
    }

    /**
     * Actualiza el resultado cuando el equipo pierde por penales.
     */
    public void ajustarDerrotaPorPenales() {
        this.partidosEmpatados--;
        this.partidosPerdidos++;
        this.puntos -= 1;
    }

    // Devuelve los datos del equipo en formato de tabla.
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