package src;

import java.util.concurrent.ThreadLocalRandom;

public class Entregas implements Runnable {

    private matrizCasilleros matriz;
    private Pedidos pedidos;
    private int contadorDePedidos = 0;
    public Entregas (Pedidos pedidos,matrizCasilleros matriz) {
        this.pedidos = pedidos;
        this.matriz  = matriz;
    }
    private Object lockMatrizEnviados = new Object();

    // Transicion --> Entregados
    @Override
    public void run() {
            while (contadorDePedidos < pedidos.getCantPedidos()){
                synchronized (lockMatrizEnviados){
                    if (pedidos.cantPedidosEnTransicion() == 0){ //si no hay pedidos en transito pasa a la siguiente itereacion
                        try {
                            Thread.sleep(50);  //cada iteracion debe tener una demora fija
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        continue;
                    }
                    int pedidoRandom = ThreadLocalRandom.current().nextInt(0, pedidos.cantPedidosEnTransicion());
                    Pedido pedido = pedidos.getPedidoEnTransicion(pedidoRandom); //tomo el pedido aleatorio de la lista de pedidos en preparacion, y se borra de la lista
                    boolean infoCorrecta = ThreadLocalRandom.current().nextInt(0, 100) < 90;
                    if (infoCorrecta){
                        pedidos.setPedidoEntregado(pedido); //seteo el pedido a PedidoEnTransicion
                    }else {
                        pedidos.setPedidoFallido(pedido); //seteo el pedido a PedidoFallido
                    }
                    contadorDePedidos++; //aumento contadorDePedidos para salir del while()
                } //salgo del synchronized para devolver el lock y duermo
                try {
                    Thread.sleep(50);  //cada iteracion debe tener una demora fija
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            System.out.println("Termino : " + Thread.currentThread().getName()); //solo para ver que corra

    }
}
