
package negocio;


public class DT extends Empleado{
    
    private float variable;
    
    //Bono que recibe el FT por ganar partidos
    private float bono;

    public float getVariable() {
        return variable;
    }

    public void setVariable(float variable) {
        this.variable = variable;
    }
    
    @Override
    public float liquidarSalario(){
        bono += 1.5f;
        
        return (salario * bono) - retencion;
    }
    
    
}
