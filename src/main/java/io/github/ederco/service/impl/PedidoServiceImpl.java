package io.github.ederco.service.impl;

import io.github.ederco.domain.entity.Cliente;
import io.github.ederco.domain.entity.ItemPedido;
import io.github.ederco.domain.entity.Pedido;
import io.github.ederco.domain.entity.Produto;
import io.github.ederco.domain.enums.StatusPedido;
import io.github.ederco.domain.repository.Clientes;
import io.github.ederco.domain.repository.ItemsPedido;
import io.github.ederco.domain.repository.Pedidos;
import io.github.ederco.domain.repository.Produtos;
import io.github.ederco.exception.PedidoNaoEncontradoException;
import io.github.ederco.exception.RegraNegocioException;
import io.github.ederco.rest.dto.ItemPedidoDTO;
import io.github.ederco.rest.dto.PedidoDTO;
import io.github.ederco.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
//@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private final Pedidos pedidosRepository;
    private final Clientes clientesRepository;
    private final Produtos produtosRepository;
    private final ItemsPedido itemsPedidoRepository;

    public PedidoServiceImpl(Pedidos pedidosRepository,
                             Clientes clientesRepository,
                             Produtos produtosRepository,
                             ItemsPedido itemsPedidoRepository) {
        this.pedidosRepository = pedidosRepository;
        this.clientesRepository = clientesRepository;
        this.produtosRepository = produtosRepository;
        this.itemsPedidoRepository = itemsPedidoRepository;
    }
    @Override
    @Transactional
    public Pedido salvar(PedidoDTO dto) {
        Integer idCliente = dto.getCliente();
        Cliente cliente = clientesRepository
                .findById(idCliente)
                .orElseThrow(() ->
                new RegraNegocioException("Código de cliente inválido."));

        Pedido pedido = new Pedido();
        pedido.setTotal(dto.getTotal());
        pedido.setDataPedido(LocalDate.now());
        pedido.setCliente(cliente);
        pedido.setStatus(StatusPedido.REALIZADO);

        List<ItemPedido> itemsPedido = converterItems(pedido, dto.getItems());
        pedidosRepository.save(pedido);
        itemsPedidoRepository.saveAll(itemsPedido);
        pedido.setItems(itemsPedido);
        return pedido;
    }

    @Override
    public Optional<Pedido> obterPedidoCompleto(Integer id) {
        return pedidosRepository.findByIdFetchItems(id);
    }

    @Override
    @Transactional
    public void atualizaStatus(Integer id, StatusPedido statusPedido) {
        pedidosRepository
                .findById(id)
                .map(pedido -> {
                    pedido.setStatus(statusPedido);
                    return pedidosRepository.save(pedido);
                }).orElseThrow(() -> new PedidoNaoEncontradoException() );
    }

    private List<ItemPedido> converterItems(Pedido pedido, List<ItemPedidoDTO> items) {
        if (items.isEmpty()) {
            throw new RegraNegocioException("Não é possível realizar um pedido sem itens.");
        }

        return items
                .stream()
                .map( dto -> {
                    Integer idProduto = dto.getProduto();
                    Produto produto = produtosRepository
                            .findById(idProduto)
                            .orElseThrow(() -> new RegraNegocioException("Código de produto inválido." + idProduto));

                    ItemPedido itemPedido = new ItemPedido();
                    itemPedido.setQuantidade(dto.getQuantidade());
                    itemPedido.setPedido(pedido);
                    itemPedido.setProduto(produto);
                    return itemPedido;
                }).collect(Collectors.toList());
    }
}
