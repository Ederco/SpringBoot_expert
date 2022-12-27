package io.github.ederco.service;

import io.github.ederco.domain.entity.Pedido;
import io.github.ederco.domain.enums.StatusPedido;
import io.github.ederco.rest.dto.PedidoDTO;

import java.util.Optional;

public interface PedidoService {
    Pedido salvar(PedidoDTO dto);
    Optional<Pedido> obterPedidoCompleto(Integer id);
    void atualizaStatus(Integer id, StatusPedido statusPedido);


}
