package negocio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class TablaPosiciones {

    private ArrayList<RegistroEquipo> registros;

    public TablaPosiciones() {
        this.registros = new ArrayList<>();
    }

    public void agregarEquipo(Equipo equipo) {
        registros.add(new RegistroEquipo(equipo));
    }

    /**
     * Registra el resultado del partido y ajusta los puntos si hubo penales.
     *
     * @param partido partido jugado
     */
    public void registrarPartido(Partido partido) {
        RegistroEquipo local = buscarRegistro(partido.getLocal());
        RegistroEquipo visitante = buscarRegistro(partido.getVisitante());

        if (local == null || visitante == null) {
            return;
        }

        int gfLocal = partido.getGolesLocal();
        int gcLocal = partido.getGolesVisitante();

        if (partido.huboPenales()) {
            boolean ganoLocal = partido.getPenalesLocal() > partido.getPenalesVisitante();
            if (ganoLocal) {
                local.registrarPartido(gfLocal, gcLocal);
                visitante.registrarPartido(gcLocal, gfLocal);
            } else {
                local.registrarPartido(gfLocal, gcLocal);
                visitante.registrarPartido(gcLocal, gfLocal);
            }
            // Ajusta los puntos y contadores según el ganador de los penales.
            ajustarPuntosPorPenales(local, visitante, ganoLocal);
        } else {
            local.registrarPartido(gfLocal, gcLocal);
            visitante.registrarPartido(gcLocal, gfLocal);
        }
    }

    /**
     * Ajusta los puntos y contadores del partido resuelto por penales.
     */
    private void ajustarPuntosPorPenales(RegistroEquipo local, RegistroEquipo visitante,
            boolean ganoLocal) {
        RegistroEquipo ganador = ganoLocal ? local : visitante;
        RegistroEquipo perdedor = ganoLocal ? visitante : local;

        ganador.ajustarVictoriaPorPenales();
        perdedor.ajustarDerrotaPorPenales();
    }

    // Busca el registro del equipo.
    private RegistroEquipo buscarRegistro(Equipo equipo) {
        for (RegistroEquipo registro : registros) {
            if (registro.getEquipo() == equipo) {
                return registro;
            }
        }
        return null;
    }

    // Devuelve una copia de la tabla ordenada por puntos y goles.
    public List<RegistroEquipo> obtenerOrdenada() {
        ArrayList<RegistroEquipo> copia = new ArrayList<>(registros);

        Collections.sort(copia, new Comparator<RegistroEquipo>() {
            @Override
            public int compare(RegistroEquipo a, RegistroEquipo b) {
                if (b.getPuntos() != a.getPuntos()) {
                    return b.getPuntos() - a.getPuntos();
                }
                if (b.getDiferenciaGol() != a.getDiferenciaGol()) {
                    return b.getDiferenciaGol() - a.getDiferenciaGol();
                }
                return b.getGolesFavor() - a.getGolesFavor();
            }
        });

        return copia;
    }

    // Devuelve el equipo campeón y advierte si hay empate en los criterios.
    public Equipo obtenerCampeon() {
        List<RegistroEquipo> ordenada = obtenerOrdenada();

        if (ordenada.isEmpty()) {
            return null;
        }

        RegistroEquipo primero = ordenada.get(0);
        RegistroEquipo segundo = ordenada.size() > 1 ? ordenada.get(1) : null;

        if (segundo != null && hayEmpate(primero, segundo)) {
            System.out.println("ADVERTENCIA: empate no resuelto entre "
                    + primero.getEquipo().getNombre() + " y "
                    + segundo.getEquipo().getNombre());
        }

        return primero.getEquipo();
    }

    // Comprueba si dos equipos tienen los mismos criterios de clasificación.
    private boolean hayEmpate(RegistroEquipo a, RegistroEquipo b) {
        return a.getPuntos() == b.getPuntos()
                && a.getDiferenciaGol() == b.getDiferenciaGol()
                && a.getGolesFavor() == b.getGolesFavor();
    }

    // Muestra la tabla de posiciones ordenada.
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("==============================================\n");
        sb.append("  TABLA DE POSICIONES\n");
        sb.append("==============================================\n");
        sb.append(String.format("%-10s %2s  %2s  %2s  %2s  %2s  %2s  %3s  %3s\n",
                "Equipo", "PJ", "PG", "PE", "PP", "GF", "GC", "DG", "PTS"));
        sb.append("----------------------------------------------\n");

        for (RegistroEquipo registro : obtenerOrdenada()) {
            sb.append(registro.toString()).append("\n");
        }

        sb.append("==============================================");
        return sb.toString();
    }

}