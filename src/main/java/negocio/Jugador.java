
package negocio;


public class Jugador extends Empleado{
    
    /*
    Se definen los atributos especificos de un jugador
    */
    
    private boolean capitan;
    
    private boolean lesionado;
    
    private int numeroCamiseta;
    
    /*
    Definimos las habilidades del jugador
    */
    
    private int cabeza;
    
    private int regate;
    
    private int velocidad;
    
    /*
    Se define el promedio de las habilidades del jugador
    */
    
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
    
    
    //El promedio se va a calcular dentro de la clase
    
    public void obtenerPromedio(){
        promedio = calcularPromedioJugador();
        System.out.println("El promedio del jugador: "+ nombre + " es:"+ promedio);
    }
    
    private float calcularPromedioJugador(){
        this.promedio = (cabeza+regate+velocidad)/3;
        if(promedio<0){
            promedio = 0;
        }
        return promedio;
    }
}
