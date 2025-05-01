package src;

public class matrizCasilleros {
    private final int filas;
    private final int columnas;
    private Casillero[][] matriz;
    public matrizCasilleros(int filas, int columnas){
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
    private Object lockMatrizCasilleros = new Object();
    public Casillero[][] getMatrizCasilleros() {
        synchronized (lockMatrizCasilleros){ //se protege porque se puede devolver una matriz desactualizada
            return matriz;
        }
    }

    public int getColumnas() {
        return columnas;
    }
    public int getFilas() {
        return filas;
    }

    /////////////////////////////////////////
    @Override
    public String toString() { ///////// solo para ver si anda
        String show="";
        for (int i = 0; i < filas; i++) {
            show += "\n";
            for (int j = 0; j < columnas; j++) {
                show += " "+ matriz[i][j].getEstado();
            }
        }
        return show;
    }
    public String contadorDeUso() { ///////// solo para ver si anda
        String show="";
        for (int i = 0; i < filas; i++) {
            show += "\n";
            for (int j = 0; j < columnas; j++) {
                show += " "+ matriz[i][j].getContador();
            }
        }
        return show;
    }
    public int sumaContadorDeUso() { ///////// solo para ver si anda
        int contador = 0;
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                contador += matriz[i][j].getContador();
            }
        }
        return contador;
    }
}
