package br.com.metaway.petshop.repository;

import br.com.metaway.petshop.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client , Integer> {
}
