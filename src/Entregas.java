package src;

import java.util.concurrent.ThreadLocalRandom;
public class Entregas implements Runnable{
    private Matriz matriz; //para tener referencia de la matriz
    private Pedidos pedidos; //para tener referencia de los pedidos (todas las listas)
    public Entregas(Matriz matriz, Pedidos pedidos){
        this.matriz = matriz;  ///hay que protegerla de una SC al tomar un mismo casillero
        this.pedidos = pedidos;
    }
    private Object lockEntregas = new Object();
    @Override
    public void run() {
        while ((pedidos.cantPedidosVerificados() + pedidos.cantPedidosFallidos()) < pedidos.getCantPedidos()){
            //---inicio SC---//
            synchronized (lockEntregas){
                if (pedidos.cantPedidosEnTransicion() == 0){ //si no hay pedidos en transicion pasa a la siguiente itereacion
                    try {
                        Thread.sleep(70);  //cada iteracion debe tener una demora fija
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    continue;
                }
                //no hace falta controlar si el pedido es null en esta intancia porque este es el unico proceso
                //que saca pedidos de la lista anterior
                int pedidoRandom = ThreadLocalRandom.current().nextInt(0, pedidos.cantPedidosEnTransicion());
                Pedido pedido = pedidos.getPedidoEnTransicion(pedidoRandom); //tomo el pedido aleatorio de la lista de pedidos en transicion, y se borra de la lista
                boolean infoCorrecta = ThreadLocalRandom.current().nextInt(0, 100) < 90;
                if (infoCorrecta){
                    pedidos.setPedidoEntregado(pedido);//seteo el pedido a PedidoEntregados
                }
                else {
                    pedido.setEstado(EstadoPedido.FALLIDO);
                    pedidos.setPedidoFallido(pedido); //seteo el pedido a PedidoFallido
                }
            } //salgo del synchronized para devolver el lock y duermo
            //---Fin SC---//
            try {
                Thread.sleep(70);  //cada iteracion debe tener una demora fija
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("Termino : " + Thread.currentThread().getName()); //solo para ver que corra
    }
}