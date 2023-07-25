package br.com.metaway.petshop.repository;

import br.com.metaway.petshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    //List<Pessoa> findByNomeContains(String name);
}
