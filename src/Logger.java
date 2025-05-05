package src;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
public class Logger implements Runnable{
    private static final String LOG_FILE = "Registro de Pedidos.log";
    private Matriz matriz;
    private Pedidos pedidos;
    private long inicio;
    public Logger(Matriz matriz, Pedidos pedidos, long inicio){
        this.matriz = matriz;
        this.pedidos = pedidos;
        this.inicio = inicio;
    }
    public void escribirLog(String mensaje, boolean borrar) { //si borrar es true borra lo que habia antes en el archivo .log
        try (FileWriter fw = new FileWriter(LOG_FILE, !borrar);
             PrintWriter pw = new PrintWriter(fw)) {
            pw.println(mensaje);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        escribirLog("----Inicio de Registro de Pedidos----", true);
        while ((pedidos.cantPedidosVerificados() + pedidos.cantPedidosFallidos()) < pedidos.getCantPedidos()){
            String log = "------------------------------------";
            log += "\n Cantidad de Pedidos en Preparacion: " + pedidos.cantPedidosEnPreparacion();
            log += "\n Cantidad de Pedidos en Transicion: " + pedidos.cantPedidosEnTransicion();
            log += "\n Cantidad de Pedidos por Entregar: " + pedidos.cantPedidosEntregados();
            log += "\n Cantidad de Pedidos Fallidos: " + pedidos.cantPedidosFallidos();
            log += "\n Cantidad de Pedidos Verificados: " + pedidos.cantPedidosVerificados();
            escribirLog(log, false);
            try {
                Thread.sleep(200);  /// duermo 200 ms
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        //Tiene que haber una última escritura fuera del while() para que figure como terminaron las variables
        String log = "------------------------------------";
        log += "\n Cantidad de Pedidos en Preparacion: " + pedidos.cantPedidosEnPreparacion();
        log += "\n Cantidad de Pedidos en Transicion: " + pedidos.cantPedidosEnTransicion();
        log += "\n Cantidad de Pedidos por Entregados: " + pedidos.cantPedidosEntregados();
        log += "\n Cantidad de Pedidos Fallidos: " + pedidos.cantPedidosFallidos();
        log += "\n Cantidad de Pedidos Verificados: " + pedidos.cantPedidosVerificados();
        log += "\n------------------------------------";
        escribirLog(log, false);
        escribirLog(matriz.contadorDeUso(), false); //imprimo contadores de los casilleros
        long fin = System.currentTimeMillis();
        escribirLog( "\n------Tiempo de ejecución: " + (fin - inicio) + " ms------", false);
        System.out.println("Termino : " + Thread.currentThread().getName()); //solo para ver que corra
    }
}