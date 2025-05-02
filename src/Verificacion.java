package src;

import java.util.concurrent.ThreadLocalRandom;

public class Verificacion implements Runnable{
    private matrizCasilleros matriz;
    private Pedidos pedidos;
    private int contadorDePedidos =0;

    public Verificacion(matrizCasilleros matriz,Pedidos pedidos) {
        this.matriz  = matriz;
        this.pedidos = pedidos;
    }

    private Object LockVerificacion = new Object();

    @Override
    public void run(){
        while (contadorDePedidos != (pedidos.cantPedidosEntregados())){
            //---inicio SC---//
            synchronized (LockVerificacion){
                if (pedidos.cantPedidosEntregados() == 0){ //si no hay pedidos en transicion pasa a la siguiente itereacion
                    try {
                        Thread.sleep(50);  //cada iteracion debe tener una demora fija
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    continue;
                }
                //no hace falta controlar si el pedido es null en esta intancia porque este es el unico proceso
                //que saca pedidos y de lista anterior, y esta bloqueada
                int pedidoRandom = ThreadLocalRandom.current().nextInt(0, pedidos.cantPedidosEntregados());
                Pedido pedido = pedidos.getPedidoEnEntregados(pedidoRandom); //tomo el pedido aleatorio de la lista de pedidos en transicion, y se borra de la lista
                boolean infoCorrecta = ThreadLocalRandom.current().nextInt(0, 100) < 95;
                if (infoCorrecta){
                    pedidos.setPedidoVerificado(pedido);//seteo el pedido a PedidoEntregados
                    //contadorDePedidos++; //aumento contadorDePedidos para salir del while()
                }
                else {
                    pedidos.setPedidoFallido(pedido); //seteo el pedido a PedidoFallido
                }
            } //salgo del synchronized para devolver el lock y duermo
            //---Fin SC---//
            try {
                Thread.sleep(50);  //cada iteracion debe tener una demora fija
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(contadorDePedidos + "/" + (pedidos.cantPedidosEntregados()));
        }
        System.out.println("Termino : " + Thread.currentThread().getName()); //solo para ver que corra

    }
}