package br.com.metaway.petshop.controller;

import br.com.metaway.petshop.UserRoles;
import br.com.metaway.petshop.model.Client;
import br.com.metaway.petshop.model.User;
import br.com.metaway.petshop.Utils;
import br.com.metaway.petshop.controller.dto.UserR;
import br.com.metaway.petshop.controller.dto.UserRq;
import br.com.metaway.petshop.repository.ClientRepository;
import br.com.metaway.petshop.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    @GetMapping("/")
    public List<UserR> findAll(){
        var user = userRepository.findAll();
        return user.stream().map(UserR::converter).collect(Collectors.toList());
    }
    @PostMapping("/")
    public void cadastroUseradmin(@RequestBody UserRq user){
        var u = new User();
        u.setCpf(user.getCpf());
        u.setNome(user.getNome());
        u.setPs(Utils.md5(user.getPs()));
        u.setTipo(user.getTipo());
        userRepository.save(u);
    }

    @PostMapping("/register")
    public void cadastroUserClient(@RequestBody UserRq user) {
        String encryptedPassword = new BCryptPasswordEncoder().encode(user.getPs());
        System.out.println("aqui2");
        var u = new User();
        u.setCpf(user.getCpf());
        u.setNome(user.getNome());
        u.setPs(encryptedPassword);
        u.setTipo(user.getTipo());
        userRepository.save(u);

        var c = new Client();
        c.setCpf(user.getCpf());
        c.setNome(user.getNome());
        c.setDtcadastro(new Date(System.currentTimeMillis()));
        clientRepository.save(c);
    }

}
