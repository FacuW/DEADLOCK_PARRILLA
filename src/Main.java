package src;

public class Main {
    public static void main(String[] args) {
        matrizCasilleros matrizCasilleros = new matrizCasilleros(10,20);
        System.out.println(matrizCasilleros.toString()); ///solo para prueba

        try {
            Thread.sleep(200);  /// delay para que forme primero la matriz
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Preparacion preparacion = new Preparacion(); //despues se agrega lo que haga falta entre ()
        createThreads threadsPreparacion = new createThreads(3, preparacion, "Preparacion");
    }
}