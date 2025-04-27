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
    private Object lockMatriz = new Object(); // por ahora no hace falta
    private int contadorElse = 0;

    @Override
    public void run() {
        while (contadorDePedidos < pedidos.getCantPedidos()){
            int randomFila = ThreadLocalRandom.current().nextInt(0, matriz.getFilas());
            int randomColumna = ThreadLocalRandom.current().nextInt(0, matriz.getColumnas());
            //---inicio SC---//
            if (matriz.getMatrizCasilleros()[randomFila][randomColumna].getEstado() == estadoCasillero.VACIO){ //si esta OCUPADO itera de vuelta
                matriz.getMatrizCasilleros()[randomFila][randomColumna].setEstado(estadoCasillero.OCUPADO); //casillero->OCUPADO
                matriz.getMatrizCasilleros()[randomFila][randomColumna].aumentaContador();//contador del casillero ++
                Pedido pedido = pedidos.getListaPedidos(); //pedido a settear
                matriz.getMatrizCasilleros()[randomFila][randomColumna].setPedido(pedido); //setteo el pedido al casillero
                pedidos.setPedidoEnPreparacion(pedido);//setteo el pedido a la lista de pedidos en preparación
                contadorDePedidos++; //aumento contadorDePedidos para salir del while()
                try {
                    Thread.sleep(50);  //cada iteracion debe tener una demora fija
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            //////////////---solo para prueba---/////////
            else {
                contadorElse++; //para saber si en algun momento no se cumple el if()
            }
            if (contadorDePedidos == 200){
                break;  //para salir del while cuando lleno los casilleros, porque son 200 y hay 500 pedidos
            }           // todavia los casilleros no pasan de OCUPADO a VACIO
            /////////////////////////////
        }
        System.out.println("Termino : " + Thread.currentThread().getName() + ", y contadorELse es: " + contadorElse);  //solo para ver que corra
    }
}
