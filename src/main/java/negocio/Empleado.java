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

    /**
     * Devuelve el bono que recibe este empleado.
     *
     * Por defecto, un Empleado base no tiene bono, así que devuelve 0.
     * Las subclases que sí tienen bono (DT, EntrenadorFisico, Medico)
     * sobrescriben este método para devolver el valor correspondiente.
     *
     * Se usa desde ServLiquidacionSalario para imprimir el bono junto
     * al salario, sin que el servicio tenga que conocer los tipos
     * concretos de empleado.
     */
    public float getBono() {
        return 0.0f;
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