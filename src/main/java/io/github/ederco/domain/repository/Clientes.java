package io.github.ederco.domain.repository;

import io.github.ederco.domain.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface Clientes extends JpaRepository<Cliente, Integer> {

    // select c from Cliente c where c.nome like :nome
    @Query(value = " select * from cliente c where c.nome like '%:nome%' ",nativeQuery = true)
    List<Cliente> encontrarPorNome(@Param("nome") String nome);

    @Query(value = " delete from Cliente c where c.nome =:nome")
    @Modifying
    void deleteByNome(String nome);

    //List<Cliente> findByNomeOrIdOrderById(String nome, Integer id);

    //Cliente findOneByNome(String nome);

    boolean existsByNome(String nome);

    @Query( "select c from Cliente c left join fetch c.pedidos where c.id = :id ")
    Cliente findClientFetchPedidos(@Param("id") Integer id);

}
