
package negocio;


public class Empleado extends Persona{
    
    protected float salario;
    
    protected float retencion;

    public float getSalario() {
        return salario;
    }

    public void setSalario(float salario) {
        this.salario = salario;
    }

    public float getRetencion() {
        return retencion;
    }

    public void setRetencion(float retencion) {
        this.retencion = retencion;
    }
    
    /*
    Se hace el calculo del salario del empleado
    tomando el salario y restando la retención
    */
    public float liquidarSalario(){
        
        /*
        Se hace el calculo del salario del empleado
        Tomando el salario y restandole la retencion
        */
        
        return salario - retencion;
        
    }
    
}
