package io.github.ederco.rest.controller;

import io.github.ederco.domain.entity.ItemPedido;
import io.github.ederco.domain.entity.Pedido;
import io.github.ederco.domain.enums.StatusPedido;
import io.github.ederco.exception.RegraNegocioException;
import io.github.ederco.rest.dto.AtualizacaoStatusPedidoDTO;
import io.github.ederco.rest.dto.InformacaoItemPedidoDTO;
import io.github.ederco.rest.dto.InformacoesPedidoDTO;
import io.github.ederco.rest.dto.PedidoDTO;
import io.github.ederco.service.PedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("api/pedidos")
public class PedidoController {

    private PedidoService service;

    public PedidoController(PedidoService service){
        this.service = service;
    }
    @PostMapping
    @ResponseStatus(CREATED)
    public Integer save(@RequestBody @Valid PedidoDTO dto){
        Pedido pedido = service.salvar(dto);
        return pedido.getId();
    }
    @GetMapping("{id}")
    public InformacoesPedidoDTO getById(@PathVariable Integer id){
        return service
                .obterPedidoCompleto(id)
                .map(p -> converter(p))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado"));
    }

    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateStatus( @PathVariable Integer id,
                              @RequestBody AtualizacaoStatusPedidoDTO dto ){
        String novoStatus = dto.getNovoStatus();
        service.atualizaStatus(id, StatusPedido.valueOf(novoStatus));
    }


    private InformacoesPedidoDTO converter(Pedido pedido){
        return InformacoesPedidoDTO
                .builder()
                .codigo(pedido.getId())
                .dataPedido(pedido.getDataPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .cpf(pedido.getCliente().getCpf())
                .nomeCliente(pedido.getCliente().getNome())
                .total(pedido.getTotal())
                .status(pedido.getStatus().name())
                .items(converter(pedido.getItems()))
                .build();

    }
    public List<InformacaoItemPedidoDTO> converter(List<ItemPedido> items){
        if(CollectionUtils.isEmpty(items)){
            return Collections.emptyList();
        }
        return items.stream().map(item ->  InformacaoItemPedidoDTO
                .builder().descricaoProduto(item.getProduto().getDescricao())
                .precoUnitario(item.getProduto().getPreco())
                .quantidade(item.getQuantidade())
                .build()
        ).collect(Collectors.toList());

    }

}
