
package servicios;

import java.util.List;
import negocio.Empleado;


public class ServLiquidacionSalario {
    
    public void liquidarSueldo(List<Empleado> empleados){
        /*
        Esta clase de servicio no tiene conocimiento de Empleado
        Su responsabilidad es calcular el salario
        */
        
        for(Empleado empleado : empleados){
            /*
            Se recorre el listado de empleados
            */
            
            float salario = empleado.liquidarSalario();
            System.out.println("Salario del empleado: "+ empleado.getNombre()+ " -: $"+ salario);
            
        }
    }
}
