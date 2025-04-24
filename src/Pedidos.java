package src;

import java.util.ArrayList;

public class Pedidos {
    private ArrayList<Pedido> listaEnPreparacion = new ArrayList<>();
    private ArrayList<Pedido> listaEnTransicion = new ArrayList<>();
    private ArrayList<Pedido> listaEntragados = new ArrayList<>();
    private ArrayList<Pedido> listaFallidos = new ArrayList<>();
    private int cantPedidos;

    public Pedidos(int cantPedidos){
        this.cantPedidos = cantPedidos;
    }
    ///getters y setters
    ///se podria instanciar un Objets() para cada lista y utilizarlo como Lock, para hacer un bloque synchronized

}
