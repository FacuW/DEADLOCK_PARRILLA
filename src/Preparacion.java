package src;

import java.util.concurrent.ThreadLocalRandom;

public class Preparacion implements Runnable{

    private matrizCasilleros matriz; //para tener referencia de la matriz
    private Pedidos pedidos; //para tener referencia de los pedidos (todas las listas)
    private int contadorDePedidos = 0; //para poder comparar con la cantidad de pedidos a hacer

    public Preparacion(matrizCasilleros matriz, Pedidos pedidos ){
        this.matriz = matriz;  ///hay que protegerla de una SC al tomar un mismo casillero
        this.pedidos = pedidos;
    }
    private Object lockMatriz = new Object();

    @Override
    public void run() {
        while (contadorDePedidos < pedidos.getCantPedidos()){
            int randomFila = ThreadLocalRandom.current().nextInt(0, matriz.getFilas());
            int randomColumna = ThreadLocalRandom.current().nextInt(0, matriz.getColumnas());
            //---inicio SC---//
            synchronized (lockMatriz){
                if (matriz.getMatrizCasilleros()[randomFila][randomColumna].getEstado() == estadoCasillero.VACIO){ //si esta OCUPADO itera de vuelta
                    Pedido pedido = pedidos.getListaPedidos(); //pedido a settear
                    if (pedido == null){ // controlo que el pedido no sea null, porque si mas de un hilo
                                    // entro en el while y justo termina la Lista de Pedidos me va a dolver un null
                        continue;
                    }
                    matriz.getMatrizCasilleros()[randomFila][randomColumna].setEstado(estadoCasillero.OCUPADO); //casillero->OCUPADO
                    matriz.getMatrizCasilleros()[randomFila][randomColumna].aumentaContador();//contador del casillero ++
                    matriz.getMatrizCasilleros()[randomFila][randomColumna].setPedido(pedido); //setteo el pedido al casillero
                    pedidos.setPedidoEnPreparacion(pedido);//setteo el pedido a la lista de pedidos en preparación
                    contadorDePedidos++; //aumento contadorDePedidos para salir del while()
                }
            }
            //---Fin SC---//
            try {
                Thread.sleep(75);  //cada iteracion debe tener una demora fija
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("Termino : " + Thread.currentThread().getName());  //solo para ver que corra
    }
}