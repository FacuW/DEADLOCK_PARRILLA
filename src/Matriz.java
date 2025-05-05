package src;
public class Matriz {
    private final int filas;
    private final int columnas;
    private Casillero[][] matriz;
    public Matriz(int filas, int columnas){
        this.filas = filas;
        this.columnas = columnas;
        this.matriz = new Casillero[filas][columnas];
        generarMatriz();
    }
    private void generarMatriz(){
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                matriz[i][j] = new Casillero();  //en cada posicion de la matriz Casillero[][] instancio un Casillero
            }
        }
    }
    private Object lockMatriz = new Object();
    public Casillero[][] getMatriz() {
        synchronized (lockMatriz){ //se protege porque se puede devolver una matriz desactualizada
            return matriz;
        }
    }
    public int getColumnas() {
        return columnas;
    }
    public int getFilas() {
        return filas;
    }
    public String contadorDeUso() {
        String show="";
        for (int i = 0; i < filas; i++) {
            show += "\n";
            for (int j = 0; j < columnas; j++) {
                show += " "+ matriz[i][j].getContador();
            }
        }
        return show;
    }
}