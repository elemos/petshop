package br.com.metaway.petshop.repository;

import br.com.metaway.petshop.model.Raca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RacaRepository extends JpaRepository<Raca, Integer> {
    //List<Pessoa> findByNomeContains(String name);
}
