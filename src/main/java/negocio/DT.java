package negocio;

public class DT extends Empleado {

    private float variable;

    // Bono por ganar partidos.
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

    // Devuelve el bono actual del DT.
    @Override
    public float getBono() {
        return bono;
    }

    // Calcula el salario con el bono y la retención.
    @Override
    public float liquidarSalario() {
        bono += 1.5f;

        return (salario * bono) - retencion;
    }

}