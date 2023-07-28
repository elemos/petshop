package br.com.metaway.petshop.repository;

import br.com.metaway.petshop.model.Atendimento;
import br.com.metaway.petshop.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AtendimentoRepository extends JpaRepository<Atendimento, Integer> {

    @Query(value = "Select * From Atendimento where id_cliente = :id_cliente", nativeQuery = true)
    List<Atendimento> findbyClientId(Integer id_cliente);

    @Query(value = "Select * From Atendimento where id_pet = :id_pet", nativeQuery = true)
    List<Atendimento> findByPetId(Integer id_pet);

}
