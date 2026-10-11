package core;

import java.util.ArrayList;
import java.util.Random;
import negocio.DT;
import negocio.Empleado;
import negocio.EntrenadorFisico;
import negocio.Equipo;
import negocio.Jugador;
import negocio.Medico;
import negocio.Torneo;
import servicios.ServLiquidacionSalario;


public class Main {

    public static void main(String[] args) {

        Random rnd = new Random();

        Torneo torneo = new Torneo();
        torneo.setNombre("Mundial 2026");
        torneo.setDescripcion("Torneo de prueba todos contra todos");
        torneo.setLocal(true);

        Equipo colombia = crearEquipo("Colombia", rnd);
        Equipo brasil = crearEquipo("Brasil", rnd);
        Equipo argentina = crearEquipo("Argentina", rnd);
        Equipo chile = crearEquipo("Chile", rnd);

        torneo.agregarEquipo(colombia);
        torneo.agregarEquipo(brasil);
        torneo.agregarEquipo(argentina);
        torneo.agregarEquipo(chile);

        torneo.generarCalendario();
        torneo.jugarTorneo();

        // Tabla de posiciones y campeón.
        System.out.println(torneo);

        // Liquidación de salarios de todos los equipos.
        imprimirSalariosDeTodosLosEquipos(torneo);
    }

    /**
     * Crea un equipo con su cuerpo técnico completo (DT, entrenador
     * físico y médico) y sus 5 jugadores.
     *
     * @param nombre nombre del equipo
     * @param rnd    generador de números aleatorios compartido
     * @return el Equipo ya armado con todo su personal y jugadores
     */
    private static Equipo crearEquipo(String nombre, Random rnd) {
        Equipo equipo = new Equipo();
        equipo.setNombre(nombre);

        // DT del equipo.
        DT dt = new DT();
        dt.setNombre("DT de " + nombre);
        dt.setSalario(200_000);
        dt.setRetencion(50_000);
        equipo.setDt(dt);

        // Entrenador físico del equipo.
        EntrenadorFisico entrenadorFisico = new EntrenadorFisico();
        entrenadorFisico.setNombre("Preparador físico de " + nombre);
        entrenadorFisico.setSalario(175_000);
        entrenadorFisico.setRetencion(40_000);
        entrenadorFisico.setEspecialidad("Fuerza");
        equipo.setEntrenadorFisico(entrenadorFisico);

        // Médico del equipo.
        Medico medico = new Medico();
        medico.setNombre("Médico de " + nombre);
        medico.setSalario(175_000);
        medico.setRetencion(40_000);
        medico.setEspecialidad("Fisioterapia");
        medico.setNumeroLicencia("LIC-" + nombre);
        equipo.setMedico(medico);

        // 5 jugadores con habilidades aleatorias entre 1 y 8.
        // Cada atributo recibe un valor distinto.
        for (int i = 0; i < 5; i++) {
            Jugador jugador = new Jugador();

            jugador.setNombre(i + "-" + nombre);
            jugador.setSalario(200_000);
            jugador.setRetencion(50_000);
            jugador.setCabeza(rnd.nextInt(8) + 1);
            jugador.setRegate(rnd.nextInt(8) + 1);
            jugador.setVelocidad(rnd.nextInt(8) + 1);

            equipo.addJugador(jugador);
            jugador.obtenerPromedio();
        }

        return equipo;
    }

    /**
     * Imprime los salarios de todos los equipos del torneo.
     *
     * Por cada equipo:
     *   - Imprime un encabezado con el nombre del equipo.
     *   - Arma una lista de Empleado con el DT, el entrenador físico,
     *     el médico y todos los jugadores, en ese orden.
     *   - Pasa la lista a ServLiquidacionSalario, que recorre los
     *     empleados y llama a liquidarSalario() en cada uno.
     *
     * @param torneo el torneo cuyos equipos se van a liquidar
     */
    private static void imprimirSalariosDeTodosLosEquipos(Torneo torneo) {
        ServLiquidacionSalario servicio = new ServLiquidacionSalario();

        for (Equipo equipo : torneo.getEquipos()) {
            System.out.println("#########################");
            System.out.println("SALARIOS DE " + equipo.getNombre().toUpperCase());

            ArrayList<Empleado> empleados = new ArrayList<>();

            empleados.add(equipo.getDt());
            empleados.add(equipo.getEntrenadorFisico());
            empleados.add(equipo.getMedico());
            empleados.addAll(equipo.getJugadores());

            servicio.liquidarSueldo(empleados);
        }
    }

}