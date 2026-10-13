package negocio;

public class Empleado extends Persona {

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

    // Devuelve 0 si el empleado no tiene bono.
    public float getBono() {
        return 0.0f;
    }

    // Calcula el salario restando la retención.
    public float liquidarSalario() {
        return salario - retencion;
    }

}