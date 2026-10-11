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
     * Registra un partido en la tabla.
     *
     * Casos:
     *   1. El partido NO tuvo penales (no hubo empate en goles):
     *      - El ganador suma 3 puntos como victoria.
     *      - El perdedor suma 0 puntos como derrota.
     *      - Si por alguna razón hubo empate sin penales, ambos suman 1.
     *
     *   2. El partido SÍ tuvo penales (hubo empate en goles y se
     *      resolvió por tanda):
     *      - El ganador de la tanda suma 3 puntos como victoria.
     *      - El perdedor de la tanda suma 0 puntos como derrota.
     *      - No se cuenta como empate en la tabla, aunque los goles
     *        hayan sido iguales. Los goles a favor y en contra sí se
     *        registran con el marcador del tiempo reglamentario.
     *
     * @param partido el partido ya jugado
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
            // Los goles ya reflejan empate; los puntos se ajustan abajo.
            ajustarPuntosPorPenales(local, visitante, ganoLocal);
        } else {
            local.registrarPartido(gfLocal, gcLocal);
            visitante.registrarPartido(gcLocal, gfLocal);
        }
    }

    /**
     * Ajusta los puntos de un partido resuelto por penales.
     *
     * El método registrarPartido de RegistroEquipo ya sumó 1 punto a
     * cada uno por el empate en goles. Aquí se corrige para que el
     * ganador de la tanda quede con 3 puntos y el perdedor con 0.
     *
     * La corrección es:
     *   - Al ganador: sumar 2 puntos más (1 + 2 = 3).
     *   - Al perdedor: restar 1 punto (1 - 1 = 0).
     *
     * Además, se corrige el contador de partidos ganados/empatados:
     *   - Al ganador: -1 empatado, +1 ganado.
     *   - Al perdedor: -1 empatado, +1 perdido.
     */
    private void ajustarPuntosPorPenales(RegistroEquipo local, RegistroEquipo visitante,
            boolean ganoLocal) {
        RegistroEquipo ganador = ganoLocal ? local : visitante;
        RegistroEquipo perdedor = ganoLocal ? visitante : local;

        ganador.ajustarVictoriaPorPenales();
        perdedor.ajustarDerrotaPorPenales();
    }

    private RegistroEquipo buscarRegistro(Equipo equipo) {
        for (RegistroEquipo registro : registros) {
            if (registro.getEquipo() == equipo) {
                return registro;
            }
        }
        return null;
    }

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

    private boolean hayEmpate(RegistroEquipo a, RegistroEquipo b) {
        return a.getPuntos() == b.getPuntos()
                && a.getDiferenciaGol() == b.getDiferenciaGol()
                && a.getGolesFavor() == b.getGolesFavor();
    }

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