package src;

public class Casillero {
    private estadoCasillero estado;
    private int contador;
    private Pedido pedido;
    public Casillero() {
        this.estado = estadoCasillero.VACIO; //se instancia como "vacio"
        this.contador = 0; //se instacia contador en "0"
        this.pedido = null; //pedido asociado nulo
    }
    public void setEstado(estadoCasillero estado) {
        this.estado = estado;
    }
    public estadoCasillero getEstado() {
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

    public void borrarPedido(){ //por si hace falta sacar del casillero al finalizar la ejecución,
        this.pedido = null;     //mientras se este ejecutando se van a ir pisando
    }
}
