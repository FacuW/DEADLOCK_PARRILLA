package src;

public class Main {
    public static void main(String[] args) {

        long inicio = System.currentTimeMillis();  //inicio del programa

        matrizCasilleros matrizCasilleros = new matrizCasilleros(10,20);
        Pedidos pedidos = new Pedidos(500);

        Logger logger = new Logger(matrizCasilleros, pedidos, inicio);
        createThreads threadLogger = new createThreads(1, logger, "Logger");

        Preparacion preparacion = new Preparacion(matrizCasilleros, pedidos);
        createThreads threadsPreparacion = new createThreads(3, preparacion, "Preparacion");

        Despacho despacho = new Despacho(matrizCasilleros, pedidos);
        createThreads threadsDespacho = new createThreads(2, despacho, "Despacho");

        Entregas entregas = new Entregas(matrizCasilleros, pedidos);
        createThreads threadsEntregas = new createThreads(3, entregas, "Entregas");

        Verificacion verificacion = new Verificacion(matrizCasilleros, pedidos);
        createThreads threadsVerificacion = new createThreads(2, verificacion, "Verificacion");
    }
}