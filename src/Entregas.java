package src;

import java.util.concurrent.ThreadLocalRandom;
public class Entregas implements Runnable{
    private matrizCasilleros matriz; //para tener referencia de la matriz
    private Pedidos pedidos; //para tener referencia de los pedidos (todas las listas)
    private int contadorDePedidos = 0; //para poder comparar con la cantidad de pedidos a hacer
    public Entregas(matrizCasilleros matriz, Pedidos pedidos){
        this.matriz = matriz;  ///hay que protegerla de una SC al tomar un mismo casillero
        this.pedidos = pedidos;
    }
    private Object lockEntregas = new Object(); //cambiar nombre
    @Override
    public void run() {
        while (contadorDePedidos < (pedidos.getCantPedidos() - pedidos.cantPedidosFallidos())){
            //---inicio SC---//
            synchronized (lockEntregas){
                if (pedidos.cantPedidosEnTransicion() == 0){ //si no hay pedidos en transicion pasa a la siguiente itereacion
                    try {
                        Thread.sleep(50);  //cada iteracion debe tener una demora fija
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    continue;
                }
                //no hace falta controlar si el pedido es null en esta intancia porque este es el unico proceso
                //que saca pedidos y de lista anterior, y esta bloqueada
                int pedidoRandom = ThreadLocalRandom.current().nextInt(0, pedidos.cantPedidosEnTransicion());
                Pedido pedido = pedidos.getPedidoEnTransicion(pedidoRandom); //tomo el pedido aleatorio de la lista de pedidos en transicion, y se borra de la lista
                boolean infoCorrecta = ThreadLocalRandom.current().nextInt(0, 100) < 90;
                if (infoCorrecta){
                    pedidos.setPedidoEntregado(pedido);//seteo el pedido a PedidoEntregados
                    contadorDePedidos++; //aumento contadorDePedidos para salir del while()
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
        }
        System.out.println("Termino : " + Thread.currentThread().getName()); //solo para ver que corra
    }
}
