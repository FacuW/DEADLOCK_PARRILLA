package src;

import java.util.concurrent.ThreadLocalRandom;

public class Despacho implements Runnable{

    private matrizCasilleros matriz; //para tener referencia de la matriz
    private Pedidos pedidos; //para tener referencia de los pedidos (todas las listas)
    private int contadorDePedidos = 0; //para poder comparar con la cantidad de pedidos a hacer

    public Despacho(matrizCasilleros matriz, Pedidos pedidos){
        this.matriz = matriz;  ///hay que protegerla de una SC al tomar un mismo casillero
        this.pedidos = pedidos;
    }
    private Object lockMatriz = new Object();

    @Override
    public void run() {
        while (contadorDePedidos < pedidos.getCantPedidos()){
            //---inicio SC---//
            synchronized (lockMatriz){
                if (pedidos.cantPedidosEnPreparacion() == 0){ //si no hay pedidos en preparacion pasa a la siguiente itereacion
                    try {
                        Thread.sleep(60);  //cada iteracion debe tener una demora fija
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    continue;
                }
                //no hace falta controlar si el pedido es null en esta intancia porque este es el unico proceso
                //que saca pedidos y de lista anterior, y esta bloqueada
                int pedidoRandom = ThreadLocalRandom.current().nextInt(0, pedidos.cantPedidosEnPreparacion());
                Pedido pedido = pedidos.getPedidoEnPreparacion(pedidoRandom); //tomo el pedido aleatorio de la lista de pedidos en preparacion, y se borra de la lista
                boolean infoCorrecta = ThreadLocalRandom.current().nextInt(0, 100) < 85;
                if (infoCorrecta){
                    pedidos.setPedidoEnTransicion(pedido); //seteo el pedido a PedidoEnTransicion
                    for (Casillero[] casilleros : matriz.getMatrizCasilleros()){ //forEach anidado para recorrer la matriz y poder manuipular los casilleros
                        for (Casillero casillero : casilleros){
                            if (casillero.getPedido() == pedido){ //busco el pedido en la matriz
                                casillero.borrarPedido(); //borro el pedido asociado al casillero
                                casillero.setEstado(estadoCasillero.VACIO); //libero el casillero
                            }
                        }
                    }
                }else {
                    pedido.setEstado(EstadoPedido.FALLIDO);
                    pedidos.setPedidoFallido(pedido); //seteo el pedido a PedidoFallido
                    for ( Casillero[] casilleros : matriz.getMatrizCasilleros()){ //forEach anidado para recorrer la matriz y poder manuipular los casilleros
                        for (Casillero casillero : casilleros){
                            if (casillero.getPedido() == pedido){ //busco el pedido en la matriz
                                casillero.getPedido().setEstado(EstadoPedido.FALLIDO);// busco el pedido y lo marco como fallido.
                                casillero.setEstado(estadoCasillero.FUERADESERVICIO); //anulo el casillero
                            }
                        }
                    }
                }
                contadorDePedidos++; //aumento contadorDePedidos para salir del while()
            } //salgo del synchronized para devolver el lock y duermo
            //---Fin SC---//
            try {
                Thread.sleep(60);  //cada iteracion debe tener una demora fija
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("Termino : " + Thread.currentThread().getName()); //solo para ver que corra
    }
}
