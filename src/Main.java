package src;

public class Main {
    public static void main(String[] args) {

        long inicio = System.currentTimeMillis();  //inicio del programa

        Matriz matriz = new Matriz(10,20);
        Pedidos pedidos = new Pedidos(500);

        Logger logger = new Logger(matriz, pedidos, inicio);
        CreateThreads threadLogger = new CreateThreads(1, logger, "Logger");

        Preparacion preparacion = new Preparacion(matriz, pedidos);
        CreateThreads threadsPreparacion = new CreateThreads(3, preparacion, "Preparacion");

        Despacho despacho = new Despacho(matriz, pedidos);
        CreateThreads threadsDespacho = new CreateThreads(2, despacho, "Despacho");

        Entregas entregas = new Entregas(matriz, pedidos);
        CreateThreads threadsEntregas = new CreateThreads(3, entregas, "Entregas");

        Verificacion verificacion = new Verificacion(matriz, pedidos);
        CreateThreads threadsVerificacion = new CreateThreads(2, verificacion, "Verificacion");
    }
}