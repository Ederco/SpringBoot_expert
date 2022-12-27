package io.github.ederco.domain.repository;

import io.github.ederco.domain.entity.Cliente;
import io.github.ederco.domain.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface Pedidos extends JpaRepository<Pedido, Integer> {

    List<Pedido> findByCliente(Cliente cliente);

    @Query(" select p from Pedido p left join fetch p.items where p.id = :id ")
    Optional<Pedido> findByIdFetchItems(@Param("id") Integer id);
}
