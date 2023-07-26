package br.com.metaway.petshop.repository;

import br.com.metaway.petshop.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {
}
