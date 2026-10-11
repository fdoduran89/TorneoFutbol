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

    public void setBono(float bono) {
        this.bono = bono;
    }

    /**
     * Devuelve el bono actual del DT.
     *
     * OJO: el bono del DT se modifica dentro de liquidarSalario(),
     * donde se hace bono += 1.5f. Eso significa que el valor que
     * devuelve este método depende de cuántas veces se haya llamado
     * a liquidarSalario() antes. Es el comportamiento original del
     * profesor, y no se cambia aquí.
     */
    @Override
    public float getBono() {
        return bono;
    }
    
    @Override
    public float liquidarSalario(){
        bono += 1.5f;
        
        return (salario * bono) - retencion;
    }
    
    
}