package io.github.ederco.exception;

public class PedidoNaoEncontradoException extends RuntimeException{
    public PedidoNaoEncontradoException() {
        super("Pedido não encontrado.");
    }
}
