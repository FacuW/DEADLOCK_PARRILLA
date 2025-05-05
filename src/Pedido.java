package src;

public class Pedido {
    private EstadoPedido estado;
    public Pedido() {
        this.estado = EstadoPedido.BIEN;
    }
    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }
}
