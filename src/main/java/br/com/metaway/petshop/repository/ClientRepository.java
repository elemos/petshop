package br.com.metaway.petshop.repository;

import br.com.metaway.petshop.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ClientRepository extends JpaRepository<Client , Integer> {

    List<Client> findBycpf(String cpf);
}
