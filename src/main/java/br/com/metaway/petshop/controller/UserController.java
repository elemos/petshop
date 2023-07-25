package br.com.metaway.petshop.controller;

import br.com.metaway.petshop.model.User;
import br.com.metaway.petshop.Utils;
import br.com.metaway.petshop.controller.dto.UserR;
import br.com.metaway.petshop.controller.dto.UserRq;
import br.com.metaway.petshop.repository.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path="/user")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    @GetMapping("/")
    public List<UserR> findAll(){
        var user = userRepository.findAll();
        return user.stream().map(UserR::converter).collect(Collectors.toList());
    }
    @PostMapping("/")
    public void cadastroUser(@RequestBody UserRq user){
        var u = new User();
        u.setCpf(user.getCpf());
        u.setNome(user.getNome());
        u.setPs(Utils.md5(user.getPs()));
        u.setTipo(user.getTipo());
        userRepository.save(u);
    }

}
