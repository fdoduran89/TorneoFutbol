package servicios;

import java.util.List;
import negocio.Empleado;


public class ServLiquidacionSalario {
    
    /**
     * Liquida el sueldo de una lista de empleados e imprime el
     * resultado por consola.
     *
     * Por cada empleado:
     *   - Llama a liquidarSalario() para obtener el monto final.
     *   - Imprime el nombre y el monto.
     *   - Si el empleado tiene bono (getBono() > 0), imprime una
     *     segunda línea con el monto del bono.
     *
     * El método getBono() está definido en Empleado (devuelve 0 por
     * defecto) y sobrescrito en DT, EntrenadorFisico y Medico. Así
     * este servicio no necesita conocer los tipos concretos: solo
     * pregunta cuánto bono tiene cada empleado.
     *
     * @param empleados lista de empleados a liquidar
     */
    public void liquidarSueldo(List<Empleado> empleados){
        
        for(Empleado empleado : empleados){
            
            float salario = empleado.liquidarSalario();
            System.out.println("Salario del empleado: "+ empleado.getNombre()+ " -: $"+ salario);

            float bono = empleado.getBono();
            if (bono > 0.0f) {
                System.out.println("    -> Bono recibido: $" + bono);
            }
        }
    }
}