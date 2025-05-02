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
    public EstadoCasillero getEstado() {
        return estado;
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