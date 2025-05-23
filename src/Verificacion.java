package src;
import java.util.concurrent.ThreadLocalRandom;
public class Verificacion implements Runnable{
    private Pedidos pedidos;
    public Verificacion(Pedidos pedidos) {
        this.pedidos = pedidos;
    }
    private Object lockVerificacion = new Object();
    @Override
    public void run(){
        while (pedidos.cantPedidosVerificados() < (pedidos.getCantPedidos() - pedidos.cantPedidosFallidos())){
            //---inicio SC---//
            synchronized (lockVerificacion){
                if (pedidos.cantPedidosEntregados() > 0){ //si no hay pedidos en Entregados pasa a la siguiente itereacion
                    //no hace falta controlar si el pedido es null en esta intancia porque este es el unico proceso
                    //que saca pedidos de la lista anterior
                    int pedidoRandom = ThreadLocalRandom.current().nextInt(0, pedidos.cantPedidosEntregados());
                    Pedido pedido = pedidos.getPedidoEnEntregados(pedidoRandom); //tomo el pedido aleatorio de la lista de pedidos entregados, y se borra de la lista
                    boolean infoCorrecta = ThreadLocalRandom.current().nextInt(0, 100) < 95;
                    if (infoCorrecta){
                        pedidos.setPedidoVerificado(pedido);//seteo el pedido a PedidoEntregados
                    }
                    else {
                        pedido.setEstado(EstadoPedido.FALLIDO);
                        pedidos.setPedidoFallido(pedido); //seteo el pedido a PedidoFallido
                    }
                }
            } //salgo del synchronized para devolver el lock y duermo
            //---Fin SC---//
            try {
                Thread.sleep(80);  //cada iteracion debe tener una demora fija
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("Termino : " + Thread.currentThread().getName()); //solo para ver que corra
    }
}