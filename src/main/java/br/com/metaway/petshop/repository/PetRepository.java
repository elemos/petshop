package br.com.metaway.petshop.repository;
import br.com.metaway.petshop.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Integer> {
    @Query(value = "Select * From Pets where id_cliente = :id", nativeQuery = true)
    List<Pet> findbyClientId(Integer id);
}
