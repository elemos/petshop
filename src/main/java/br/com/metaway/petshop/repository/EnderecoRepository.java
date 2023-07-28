package br.com.metaway.petshop.repository;

import br.com.metaway.petshop.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {
    @Query(value = "Select * From Endereco where id_cliente = :id", nativeQuery = true)
    List<Endereco> findbyClientId(Integer id);
}
