package src;
import java.util.concurrent.ThreadLocalRandom;
public class Preparacion implements Runnable{
    private Matriz matriz; //para tener referencia de la matriz
    private Pedidos pedidos; //para tener referencia de los pedidos (todas las listas)
    private int contadorDePedidos = 0; //para poder comparar con la cantidad de pedidos
    public Preparacion(Matriz matriz, Pedidos pedidos ){
        this.matriz = matriz;  ///hay que protegerla de una SC al tomar un mismo casillero
        this.pedidos = pedidos;
    }
    private Object lockContador = new Object();
    @Override
    public void run() {
        while (contadorDePedidos < pedidos.getCantPedidos()){
            int randomFila = ThreadLocalRandom.current().nextInt(0, matriz.getFilas());
            int randomColumna = ThreadLocalRandom.current().nextInt(0, matriz.getColumnas());
            if (matriz.getMatriz()[randomFila][randomColumna].getVacio()) { //si esta OCUPADO itera de vuelta, si esta vacio lo pasa a ocupado y entra
                Pedido pedido = pedidos.getListaPedidos(); //pedido a settear
                if (pedido == null) { // controlo que el pedido no sea null, porque si mas de un hilo
                    // entro en el while y justo termina la Lista de Pedidos me va a devolver un null
                    continue;
                }
                matriz.getMatriz()[randomFila][randomColumna].aumentaContador(); //contador del casillero ++
                matriz.getMatriz()[randomFila][randomColumna].setPedido(pedido); //setteo el pedido al casillero
                pedidos.setPedidoEnPreparacion(pedido);//setteo el pedido a la lista de pedidos en preparación
                //---inicio SC---//
                synchronized (lockContador) {
                    contadorDePedidos++; //aumento contadorDePedidos para salir del while()
                }//---Fin SC---//
            }
            try {
                Thread.sleep(50);  //cada iteracion debe tener una demora fija
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("Termino : " + Thread.currentThread().getName());  //solo para ver que corra
    }
}