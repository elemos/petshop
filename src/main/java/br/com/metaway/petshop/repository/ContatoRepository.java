package br.com.metaway.petshop.repository;

import br.com.metaway.petshop.model.Contato;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContatoRepository extends JpaRepository<Contato, Integer> {
}
