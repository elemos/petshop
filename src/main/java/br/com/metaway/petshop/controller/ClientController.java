package br.com.metaway.petshop.controller;

import br.com.metaway.petshop.controller.dto.ClientR;
import br.com.metaway.petshop.controller.dto.ClientRq;
import br.com.metaway.petshop.model.Client;
import br.com.metaway.petshop.repository.ClientRepository;
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
        var clients = clientRepository.findAll();
        return clients.stream().map(ClientR::converter).collect(Collectors.toList());
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
