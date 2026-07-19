package com.mycompany.torrehanoi.model;

import java.util.Stack;

public class ModeloHanoi {

    private int numeroDiscos;
    private final Stack<Integer>[] torre = new Stack[4];

    public ModeloHanoi(int numeroDiscos) {
        reiniciar(numeroDiscos);
    }

    public void reiniciar(int numeroDiscos) {
        this.numeroDiscos = numeroDiscos;
        for (int i = 1; i <= 3; i++) {
            torre[i] = new Stack<>();
        }
        for (int i = numeroDiscos; i > 0; i--) {
            torre[1].push(i);
        }
    }

    public int getNumeroDiscos() {
        return numeroDiscos;
    }

    public Stack<Integer> getTorre(int indice) {
        return torre[indice];
    }

    public boolean esMovimientoValido(int origen, int destino) {
        return torre[destino].isEmpty() || torre[destino].peek() > torre[origen].peek();
    }

    public void mover(int origen, int destino) {
        torre[destino].push(torre[origen].pop());
    }

    public boolean esVictoria() {
        return torre[3].size() == numeroDiscos;
    }

    public boolean estaEnEstadoInicial() {
        return torre[1].size() == numeroDiscos;
    }
}