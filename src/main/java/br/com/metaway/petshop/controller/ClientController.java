package br.com.metaway.petshop.controller;

import br.com.metaway.petshop.UserRoles;
import br.com.metaway.petshop.Utils;
import br.com.metaway.petshop.controller.dto.ClientR;
import br.com.metaway.petshop.controller.dto.ClientRq;
import br.com.metaway.petshop.model.User;
import br.com.metaway.petshop.repository.ClientRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path="/cliente")
public class ClientController {

    private final ClientRepository clientRepository;

    public ClientController(ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }

    /**
     * Lista todos Clientes, se for ADM
     * Caso contrário retorna só a info do cliente logado
     * @return lista com todos cliente gravados
     */
    @GetMapping("/")
    public List<ClientR> findAll(){
        User loggedUser = Utils.getLoggedUser();
        var role = Utils.getUserRole();

        if(role.equals(UserRoles.ADMIN)){
            var clients = clientRepository.findAll();
            return clients.stream().map(ClientR::converter).collect(Collectors.toList());
        }

        var client = clientRepository.findBycpf(loggedUser.getCpf());
        return client.stream().map(ClientR::converter).collect(Collectors.toList());
    }

    /**
     * Busca um cliente pelo ID dele se for adm
     * Se não for adm ele só busca se o id passado for o mesmo do usuário logado
     * @param id_cliente
     * @return Retona o objeto do cliente
     */
    @GetMapping("/{id}")
    public ClientR findAll(@PathVariable Integer id_cliente){
        User loggedUser = Utils.getLoggedUser();
        var role = Utils.getUserRole();

        if(role.equals(UserRoles.ADMIN)|| clientRepository.findBycpf(loggedUser
                        .getCpf())
                        .get(0)
                        .getId().equals(id_cliente)){
            return ClientR.converter(clientRepository.findById(id_cliente).get());
        }
        throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }
    /**
     * Cadastra um novo cliente, visível apenas para adm
     * @param cliente recebe um Objeto ClientRequest.
     */
    @PostMapping("/")
    public void cadastraCliente(@RequestBody ClientRq cliente){
        var c = ClientRq.converter(cliente);
        clientRepository.save(c);
    }

}
