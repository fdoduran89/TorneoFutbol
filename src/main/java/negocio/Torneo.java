package negocio;

import java.util.ArrayList;
import java.util.List;


public class Torneo {

    private String descripcion;

    private String nombre;

    private boolean local;

    private ArrayList<Equipo> equipos;

    private ArrayList<Partido> partidos;

    private TablaPosiciones tabla;

    public Torneo() {
        this.equipos = new ArrayList<>();
        this.partidos = new ArrayList<>();
        this.tabla = new TablaPosiciones();
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isLocal() {
        return local;
    }

    public void setLocal(boolean local) {
        this.local = local;
    }

    /**
     * Añade un equipo al torneo y, en la misma operación, crea su
     * registro en la tabla de posiciones.
     */
    public void agregarEquipo(Equipo equipo) {
        equipos.add(equipo);
        tabla.agregarEquipo(equipo);
    }

    /**
     * Genera el calendario de partidos "todos contra todos".
     * Con 4 equipos se generan 6 partidos.
     */
    public void generarCalendario() {
        partidos.clear();
        for (int i = 0; i < equipos.size(); i++) {
            for (int j = i + 1; j < equipos.size(); j++) {
                Partido partido = new Partido();
                partido.setLocal(equipos.get(i));
                partido.setVisitante(equipos.get(j));
                partidos.add(partido);
            }
        }
    }

    /**
     * Juega todos los partidos del calendario y actualiza la tabla
     * de posiciones con cada resultado.
     *
     * Después de cada partido, se pide al entrenador físico y al
     * médico de los DOS equipos que intenten curar a sus jugadores
     * lesionados. Cada uno cura hasta 2 jugadores por partido.
     * Si el jugador se cura, el preparador o el médico suman una
     * curación, que luego se convierte en bono al liquidar el salario.
     *
     * El orden es importante:
     *   1. Se juega el partido (puede haber lesiones al final).
     *   2. Se registra el resultado en la tabla.
     *   3. Se intenta curar a los lesionados de ambos equipos.
     *
     * Así, un jugador lesionado en este partido puede curarse antes
     * del siguiente, y el siguiente partido ya lo tiene disponible
     * al 100% (o no, según la suerte).
     */
    public void jugarTorneo() {
        for (Partido partido : partidos) {
            partido.jugarResultado();
            tabla.registrarPartido(partido);

            curarJugadoresDeEquipo(partido.getLocal());
            curarJugadoresDeEquipo(partido.getVisitante());
        }
    }

    /**
     * Pide al entrenador físico y al médico de un equipo que curen
     * a sus jugadores lesionados. Cada uno cura hasta 2 por llamada.
     *
     * Si el equipo no tiene entrenador físico o médico, no se hace
     * nada por ese lado.
     *
     * @param equipo el equipo cuyos jugadores se van a intentar curar
     */
    private void curarJugadoresDeEquipo(Equipo equipo) {
        if (equipo == null) {
            return;
        }

        EntrenadorFisico entrenadorFisico = equipo.getEntrenadorFisico();
        if (entrenadorFisico != null) {
            int curados = entrenadorFisico.recuperarEquipo(equipo);
            if (curados > 0) {
                System.out.println(">>> El preparador fisico de " + equipo.getNombre()
                        + " curo a " + curados + " jugador(es).");
            }
        }

        Medico medico = equipo.getMedico();
        if (medico != null) {
            int curados = medico.recuperarEquipo(equipo);
            if (curados > 0) {
                System.out.println(">>> El medico de " + equipo.getNombre()
                        + " curo a " + curados + " jugador(es).");
            }
        }
    }

    public TablaPosiciones getTabla() {
        return tabla;
    }

    public Equipo getCampeon() {
        return tabla.obtenerCampeon();
    }

    public List<Partido> getPartidos() {
        return partidos;
    }

    public ArrayList<Equipo> getEquipos() {
        return equipos;
    }

    /**
     * Devuelve la representación completa del torneo: nombre, tabla
     * de posiciones y campeón.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n##############################################\n");
        sb.append("  TORNEO: ").append(nombre).append("\n");
        sb.append("##############################################\n");
        sb.append(tabla.toString()).append("\n");
        sb.append("\n  CAMPEON: ").append(getCampeon().getNombre()).append("\n");
        sb.append("##############################################\n");
        return sb.toString();
    }

}