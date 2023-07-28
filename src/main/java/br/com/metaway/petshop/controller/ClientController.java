package br.com.metaway.petshop.controller;

import br.com.metaway.petshop.UserRoles;
import br.com.metaway.petshop.controller.dto.ClientR;
import br.com.metaway.petshop.controller.dto.ClientRq;
import br.com.metaway.petshop.model.Client;
import br.com.metaway.petshop.model.User;
import br.com.metaway.petshop.repository.ClientRepository;
import br.com.metaway.petshop.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path="/cliente")
public class ClientController {

    private final ClientRepository clientRepository;

    public ClientController(ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }

    @GetMapping("/")
    public List<ClientR> findAll(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var role = user.getTipo();

        if(role.equals(UserRoles.ADMIN)){
            var clients = clientRepository.findAll();
            return clients.stream().map(ClientR::converter).collect(Collectors.toList());
        }

        var client = clientRepository.findBycpf(user.getCpf());
        return client.stream().map(ClientR::converter).collect(Collectors.toList());
    }

    @PostMapping("/")
    public void cadastraCliente(@RequestBody ClientRq cliente){
        var c = new Client();
        c.setCpf(cliente.getCpf());
        c.setDtcadastro(cliente.getDtcadastro());
        c.setNome(cliente.getNome());
        clientRepository.save(c);
    }

}
