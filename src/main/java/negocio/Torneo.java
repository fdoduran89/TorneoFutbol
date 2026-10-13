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
     * Agrega un equipo y su registro a la tabla.
     */
    public void agregarEquipo(Equipo equipo) {
        equipos.add(equipo);
        tabla.agregarEquipo(equipo);
    }

    /**
     * Genera los partidos entre todos los equipos.
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
     * Juega los partidos, actualiza la tabla y cura a los lesionados.
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
     * Intenta curar a los jugadores lesionados del equipo.
     *
     * @param equipo equipo cuyos jugadores se recuperan
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
     * Muestra el nombre, la tabla y el campeón del torneo.
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