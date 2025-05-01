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

        Preparacion preparacion = new Preparacion(matrizCasilleros, pedidos);
        createThreads threadsPreparacion = new createThreads(3, preparacion, "Preparacion");

        Despacho despacho = new Despacho(matrizCasilleros, pedidos);
        createThreads threadsDespacho = new createThreads(2, despacho, "Despacho");

        Entregas entregas = new Entregas(matrizCasilleros, pedidos);
        createThreads threadsEntregas = new createThreads(3, entregas, "Entregas");

        try {
            Thread.sleep(15000);  /// delay para que termine bien despacho, si no anda agregar mas tiempo
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        ///solo para prueba
        System.out.println("cantPedidosEnPreparacion: " + pedidos.cantPedidosEnPreparacion()); //deveria devolver 0
        System.out.println("cantPedidosEnTransicion: " + pedidos.cantPedidosEnTransicion()); //deveria devolver 0
        System.out.println("cantPedidosEntregados: " + pedidos.cantPedidosEntregados());
        System.out.println("cantPedidosFallidos: " + pedidos.cantPedidosFallidos());  //la suma de Fallidos y Entregados deveria la cantidad de pedidos (500)
        System.out.println(matrizCasilleros.contadorDeUso()); //matriz todos contadores
        System.out.println(matrizCasilleros.sumaContadorDeUso()); //suma de todos contadores -> deberia dar 500
    }
}