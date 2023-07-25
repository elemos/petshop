package br.com.metaway.petshop.repository;

import br.com.metaway.petshop.model.Atendimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AtendimentoRepository extends JpaRepository<Atendimento, Integer> {

}
