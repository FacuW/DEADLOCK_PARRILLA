package src;

public class Main {
    public static void main(String[] args) {
        matrizCasilleros matrizCasilleros = new matrizCasilleros(10,20);
        Pedidos pedidos = new Pedidos(500);
        try {
            Thread.sleep(200);  /// delay para que forme primero la matriz
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(matrizCasilleros.toString()); ///solo para prueba

        Preparacion preparacion = new Preparacion(matrizCasilleros, pedidos);
        createThreads threadsPreparacion = new createThreads(3, preparacion, "Preparacion");
        try {
            Thread.sleep(10000);  /// delay para que termine bien preparacion, si no anda agregar mas tiempo
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        ///solo para prueba
        System.out.println(matrizCasilleros.toString()); //matriz todos OCUPADOS
        System.out.println(pedidos.cantListaPedidos()); //deberia devolver 300
        System.out.println(pedidos.cantPedidosEnPreparacion());//deveria devolver 200
    }
}