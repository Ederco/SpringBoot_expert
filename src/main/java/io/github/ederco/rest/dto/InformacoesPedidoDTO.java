package io.github.ederco.rest.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class InformacoesPedidoDTO {
    private Integer codigo;
    private String cpf;
    private String nomeCliente;
    private BigDecimal total;
    private String dataPedido;
    private String status;
    private List<InformacaoItemPedidoDTO> items;

    public InformacoesPedidoDTO(Integer codigo,
                                String cpf,
                                String nomeCliente,
                                BigDecimal total,
                                String dataPedido,
                                String status,
                                List<InformacaoItemPedidoDTO> items) {
        this.codigo = codigo;
        this.cpf = cpf;
        this.nomeCliente = nomeCliente;
        this.total = total;
        this.dataPedido = dataPedido;
        this.status = status;
        this.items = items;
    }
}
