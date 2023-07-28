package br.com.metaway.petshop.controller;

import br.com.metaway.petshop.UserRoles;
import br.com.metaway.petshop.model.Client;
import br.com.metaway.petshop.model.User;
import br.com.metaway.petshop.Utils;
import br.com.metaway.petshop.controller.dto.UserR;
import br.com.metaway.petshop.controller.dto.UserRq;
import br.com.metaway.petshop.repository.ClientRepository;
import br.com.metaway.petshop.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path="/user")
public class UserController {

    private final UserRepository userRepository;
    private final ClientRepository clientRepository;

    public UserController(UserRepository userRepository, ClientRepository clientRepository){

        this.userRepository = userRepository;
        this.clientRepository = clientRepository;
    }

    /**
     * Reorna lista de todos usuarios, apenas para adm
     * @return List of UserR
     */
    @GetMapping("/")
    public List<UserR> findAll(){
        var user = userRepository.findAll();
        return user.stream().map(UserR::converter).collect(Collectors.toList());
    }

    /**
     * Cadastra um novo adm, apenas par adm
     * @param user
     */
    @PostMapping("/")
    public void cadastroUseradmin(@RequestBody UserRq user){
        var u = UserRq.converter(user);
        userRepository.save(u);
    }

    /**
     * Endpoint para cadastro de novo cliente, adiciona as info na tabela User e replica pra Cliente
     * @param user
     */
    @PostMapping("/register")
    public void cadastroUserClient(@RequestBody UserRq user) {
        var u = UserRq.converter(user);
        userRepository.save(u);

        var c = new Client();
        c.setCpf(user.getCpf());
        c.setNome(user.getNome());
        c.setDtcadastro(new Date(System.currentTimeMillis()));
        clientRepository.save(c);
    }

    /**
     * Edita um cliente já cadastrado
     * @param user
     */
    @PutMapping("/")
    public void updateCliente(@RequestBody UserRq user){

        User loggedUser = Utils.getLoggedUser();
        var role = Utils.getUserRole();
        var oldCliente = clientRepository.findBycpf(user.getCpf()).get(0);
        var olduser    = (User) userRepository.findBycpf(user.getCpf());

        if(role.equals(UserRoles.ADMIN) || oldCliente.getCpf().equals(loggedUser.getCpf())) {
            oldCliente.setNome(user.getNome());
            oldCliente.setCpf(user.getCpf());
            olduser.setNome(user.getNome());
            olduser.setCpf(user.getCpf());
            if (user.getPs() != null && !user.getPs().isEmpty()) {
                olduser.setPs(new BCryptPasswordEncoder().encode(user.getPs()));
            }
        }
        clientRepository.save(oldCliente);
        userRepository.save(olduser);
    }

    /**
     * Deleta um usuário já cadastrado
     * @param id_cliente
     */
    @DeleteMapping("/{id}")
    public void deletePet(@PathVariable("id") int id_cliente){
        var oldCliente = clientRepository.findById(id_cliente).get();
        var olduser    = (User) userRepository.findBycpf(oldCliente.getCpf());

        User loggedUser = Utils.getLoggedUser();
        var role = Utils.getUserRole();

        if(role.equals(UserRoles.ADMIN) || clientRepository.findBycpf(loggedUser.getCpf()).get(0).getId().equals(id_cliente)) {
            try{
                clientRepository.delete(oldCliente);
                userRepository.delete(olduser);
            } catch (Exception e){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to delete user");
            }

        }
    }
}
