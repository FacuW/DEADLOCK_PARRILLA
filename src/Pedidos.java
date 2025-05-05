package src;
import java.util.ArrayList;
public class Pedidos {
    private ArrayList<Pedido> listaPedidos = new ArrayList<>();
    private ArrayList<Pedido> listaEnPreparacion = new ArrayList<>();
    private ArrayList<Pedido> listaEnTransicion = new ArrayList<>();
    private ArrayList<Pedido> listaEntregados = new ArrayList<>();
    private ArrayList<Pedido> listaFallidos = new ArrayList<>();
    private ArrayList<Pedido> listaVerifcados = new ArrayList<>();
    private final int cantPedidos;
    public Pedidos(int cantPedidos) {
        this.cantPedidos = cantPedidos;
        generarListaPedidos();
    }
    private void generarListaPedidos() {  //al instanciar el objeto Pedidos se genera la lista
        for (int i = 0; i < cantPedidos; i++) {
            listaPedidos.add(new Pedido());
        }
    }
    //////////////----Locks para las listas----//////////////
    private Object lockListaPedidos = new Object();
    private Object lockListaEnPreparacion = new Object();
    private Object lockListaEnTransicion = new Object();
    private Object lockListaEntregados = new Object();
    private Object lockListaVerifcados = new Object();
    private Object lockListaFallidos = new Object();
    public int getCantPedidos() {
        return cantPedidos; //no se modifica, no hace falta synchronized
    }

    //setters de pedido->listas
    public void setPedidoEnPreparacion(Pedido enPreparacion) {
        synchronized (lockListaEnPreparacion){
            this.listaEnPreparacion.add(enPreparacion);
        }
    }
    public void setPedidoEnTransicion(Pedido enTransicion) {
        synchronized (lockListaEnTransicion) {
            this.listaEnTransicion.add(enTransicion);
        }
    }
    public void setPedidoEntregado(Pedido entregado) {
        synchronized (lockListaEntregados){
            this.listaEntregados.add(entregado);
        }
    }
    public void setPedidoVerificado(Pedido entregado) {
        synchronized (lockListaVerifcados){
            this.listaVerifcados.add(entregado);
        }
    }
    public void setPedidoFallido(Pedido fallido) {
        synchronized (lockListaFallidos){
            this.listaFallidos.add(fallido);
        }
    }
    ///getters listas->pedido
    public Pedido getListaPedidos() {
        synchronized (lockListaPedidos){
            if (!(listaPedidos.isEmpty())){ //me aseguro de que la lista no este vacia
                return listaPedidos.remove(0); //saco el primer pedido de la lista y se borran
            }
        }
        return null;
    }
    public Pedido getPedidoEnPreparacion(int index) {
        synchronized (lockListaEnPreparacion){
            if (!(listaEnPreparacion.isEmpty())){ //me aseguro de que la lista no este vacia
                return listaEnPreparacion.remove(index); //si se saca se borra
            }
        }
        return null;
    }
    public Pedido getPedidoEnTransicion(int index) {
        synchronized (lockListaEnTransicion){
            if (!(listaEnTransicion.isEmpty())){ //me aseguro de que la lista no este vacia
                return listaEnTransicion.remove(index); //si se saca se borra
            }
        }
        return null;
    }
    public Pedido getPedidoEnEntregados(int index) {
        synchronized (lockListaEntregados){
            if (!(listaEntregados.isEmpty())){ //me aseguro de que la lista no este vacia
                return listaEntregados.remove(index); //si se saca se borra
            }
        }
        return null;
    }
    //no hace falta getPedidoEnVerificados()

    ///getters de cantidades de las listas
    public int cantPedidosEnPreparacion(){
        synchronized (lockListaEnPreparacion){ //se protege porque se puede modificar la cantidad en paralero y dar un size erroneo
            return listaEnPreparacion.size();
        }
    }
    public int cantPedidosEnTransicion(){
        synchronized (lockListaEnTransicion){ //se protege porque se puede modificar la cantidad en paralero y dar un size erroneo
            return listaEnTransicion.size();
        }

    }
    public int cantPedidosEntregados(){
        synchronized (lockListaEntregados){ //se protege porque se puede modificar la cantidad en paralero y dar un size erroneo
            return listaEntregados.size();
        }
    }
    public int cantPedidosVerificados(){
        synchronized (lockListaVerifcados){ //se protege porque se puede modificar la cantidad en paralero y dar un size erroneo
            return listaVerifcados.size();
        }
    }
    public int cantPedidosFallidos(){
        synchronized (lockListaFallidos){ //se protege porque se puede modificar la cantidad en paralero y dar un size erroneo
            return listaFallidos.size();
        }
    }
}