package src;
public class Casillero {
    private EstadoCasillero estado;
    private int contador;
    private Pedido pedido;
    public Casillero() {
        this.estado = EstadoCasillero.VACIO; //se instancia como "vacio"
        this.contador = 0; //se instacia contador en "0"
        this.pedido = null; //pedido asociado nulo
    }
    public void setEstado(EstadoCasillero estado) {
        this.estado = estado;
    }
    private Object lockCasillero = new Object();
    public boolean getVacio() { //metodo para preguntar si esta vacio, y si lo esta cambia de estado
        synchronized (lockCasillero){ //
            if (estado == EstadoCasillero.VACIO){
                setEstado(EstadoCasillero.OCUPADO);
                return true;
            }
            return false;
        }
    }
    public void getOcupado() { //metodo para preguntar si esta ocupado, y si lo esta cambia de estado y borra el pedido asociado
        synchronized (lockCasillero){
            if (estado == EstadoCasillero.OCUPADO){
                setEstado(EstadoCasillero.VACIO);
                borrarPedido();
            }
        }
    }
    public void aumentaContador(){
        this.contador++;
    }
    public int getContador() {
        return contador;
    }
    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }
    public Pedido getPedido() { return pedido;  }
    public void borrarPedido(){  this.pedido = null;  }
}