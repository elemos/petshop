package br.com.metaway.petshop.repository;

import br.com.metaway.petshop.model.Contato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ContatoRepository extends JpaRepository<Contato, Integer> {

    @Query(value = "Select * From Contato where id_cliente = :id", nativeQuery = true)
    List<Contato> findbyClientId(Integer id);
}
