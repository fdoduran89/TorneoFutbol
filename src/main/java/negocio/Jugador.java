package negocio;


public class Jugador extends Empleado{

    // Atributos específicos del jugador.

    private boolean capitan;

    private boolean lesionado;

    private int numeroCamiseta;

    // Habilidades del jugador.

    private int cabeza;

    private int regate;

    private int velocidad;

    // Promedio de las habilidades.

    private float promedio;

    public boolean isCapitan() {
        return capitan;
    }

    public void setCapitan(boolean capitan) {
        this.capitan = capitan;
    }

    public boolean isLesionado() {
        return lesionado;
    }

    public void setLesionado(boolean lesionado) {
        this.lesionado = lesionado;
    }

    public int getNumeroCamiseta() {
        return numeroCamiseta;
    }

    public void setNumeroCamiseta(int numeroCamiseta) {
        this.numeroCamiseta = numeroCamiseta;
    }

    public int getCabeza() {
        return cabeza;
    }

    public void setCabeza(int cabeza) {
        this.cabeza = cabeza;
    }

    public int getRegate() {
        return regate;
    }

    public void setRegate(int regate) {
        this.regate = regate;
    }

    public int getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }

    public float getPromedio() {
        return promedio;
    }

    /*public void setPromedio(float promedio) {
        this.promedio = promedio;
    }*/

    // Calcula y muestra el promedio del jugador.

    public void obtenerPromedio(){
        promedio = calcularPromedioJugador();
        System.out.println("El promedio del jugador: "+ nombre + " es: "+ promedio);
    }

    // Calcula el promedio y lo redondea a un decimal.

    private float calcularPromedioJugador(){
        float calculo = (cabeza + regate + velocidad) / 3.0f;
        this.promedio = Math.round(calculo * 10.0f) / 10.0f;
        if(promedio < 0){
            promedio = 0;
        }
        return promedio;
    }
}