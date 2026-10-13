package servicios;

import java.util.List;
import negocio.Empleado;


public class ServLiquidacionSalario {

    /**
     * Liquida los salarios e imprime los bonos correspondientes.
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