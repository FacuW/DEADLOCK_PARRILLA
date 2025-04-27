package src;

import java.util.ArrayList;

public class Pedidos {
    private ArrayList<Pedido> listaPedidos = new ArrayList<>();  //500 pedidos
    private ArrayList<Pedido> listaEnPreparacion = new ArrayList<>();
    private ArrayList<Pedido> listaEnTransicion = new ArrayList<>();
    private ArrayList<Pedido> listaEntregados = new ArrayList<>();
    private ArrayList<Pedido> listaFallidos = new ArrayList<>();
    private int cantPedidos;

    public Pedidos(int cantPedidos) {
        this.cantPedidos = cantPedidos;
        generarListaPedidos();
    }

    private void generarListaPedidos() {  //al instanciar el objeto Pedidos se genera la lista
        for (int i = 0; i < cantPedidos; i++) {
            listaPedidos.add(i, new Pedido());   //agrego la cantidad de pedidos que se quiera a la listaPedidos
        }
    }
    ///////----Locks para las listas----//////////////
    private Object lockListaPedidos = new Object();  //no necesita setter
    private Object lockListaEnPreparacion = new Object();
    private Object lockListaEnTransicion = new Object();
    private Object lockListaEntregados = new Object();
    private Object lockListaFallidos = new Object();

    /////---quizas usar ReetrantLockReadWrite sea mejor---///
    ////////////////////////////////////

    public int getCantPedidos() {
        return cantPedidos;
    }

    //setters de pedido->listas

    public void setPedidoEnPreparacion(Pedido enPreparacion) {
        synchronized (lockListaEntregados){
            this.listaEnPreparacion.add(enPreparacion);
        }
    }
    public void setPedidoEnTransicion(Pedido enTransicion) {
        synchronized (lockListaEnTransicion) {
            this.listaEnTransicion.add(enTransicion);
        }
    }
    public void setPedidoEntregado(Pedido entregado) {
        synchronized (lockListaEnPreparacion){
            this.listaEntregados.add(entregado);
        }
    }
    public void setPedidoFallido(Pedido fallido) {
        synchronized (lockListaFallidos){
            this.listaFallidos = listaFallidos;
        }
    }
    /////////

    ///getters listas->pedido

    public Pedido getListaPedidos() {
        if (!(listaPedidos.isEmpty())){ //me aseguro de que la lista no este vacia
            synchronized (lockListaPedidos){
                return listaPedidos.remove(0); //saco el primer pedido de la lista, y se borran porque no van a volver a esta lista
            }
        }
        return null;
    }

    public Pedido getPedidoEnPreparacion(int index) {
        synchronized (lockListaEnPreparacion){
            return listaEnPreparacion.get(index);
        }
    }

    ///getters de cantidades de las listas para Logger
    public int cantListaPedidos(){
        return listaPedidos.size();
    }
    public int cantPedidosEnPreparacion(){
        return listaEnPreparacion.size();
    }
    public int cantPedidosEnTransicion(){
        return listaEnTransicion.size();
    }
    public int cantPedidosEntregados(){
        return listaEntregados.size();
    }
    public int cantPedidosFallidos(){
        return listaFallidos.size();
    }

    @Override
    public String toString() { //solo de prueba
        return listaPedidos.size() + "";
    }
}
