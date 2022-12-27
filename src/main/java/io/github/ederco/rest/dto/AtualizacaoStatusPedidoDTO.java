package io.github.ederco.rest.dto;


public class AtualizacaoStatusPedidoDTO {
    private String novoStatus;

    public AtualizacaoStatusPedidoDTO() {
    }

    public AtualizacaoStatusPedidoDTO(String novoStatus) {
        this.novoStatus = novoStatus;
    }

    public String getNovoStatus() {
        return novoStatus;
    }

    public void setNovoStatus(String novoStatus) {
        this.novoStatus = novoStatus;
    }
}
