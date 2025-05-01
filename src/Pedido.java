package src;

public class Pedido {

    private EstadoPedido estado;
    public Pedido() {
        estado = EstadoPedido.BIEN;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }
}
