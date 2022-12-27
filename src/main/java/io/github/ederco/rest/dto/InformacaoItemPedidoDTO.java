package io.github.ederco.rest.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
@NoArgsConstructor
@Builder
public class InformacaoItemPedidoDTO {
    private String descricaoProduto;
    private BigDecimal precoUnitario;
    private Integer quantidade;

    public InformacaoItemPedidoDTO(String descricaoProduto,
                                   BigDecimal precoUnitario,
                                   Integer quantidade) {
        this.descricaoProduto = descricaoProduto;
        this.precoUnitario = precoUnitario;
        this.quantidade = quantidade;
    }
}
