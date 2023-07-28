package br.com.metaway.petshop.controller;

import br.com.metaway.petshop.UserRoles;
import br.com.metaway.petshop.Utils;
import br.com.metaway.petshop.controller.dto.ContatoR;
import br.com.metaway.petshop.controller.dto.PetR;
import br.com.metaway.petshop.model.Contato;
import br.com.metaway.petshop.controller.dto.ContatoRq;
import br.com.metaway.petshop.model.User;
import br.com.metaway.petshop.repository.ClientRepository;
import br.com.metaway.petshop.repository.ContatoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path="/contato")
public class ContatoController {

    private final ContatoRepository contatoRepository;
    private final ClientRepository clientRepository;

    public ContatoController(ContatoRepository contatoRepository, ClientRepository clientRepository){
        this.contatoRepository = contatoRepository;
        this.clientRepository = clientRepository;
    }

    @GetMapping("/")
    public List<ContatoR> findAll(){

        User loggedUser = Utils.getLoggedUser();
        var role = Utils.getUserRole();

        if(role.equals(UserRoles.ADMIN)){
            var contatos = contatoRepository.findAll();
            return contatos.stream().map(ContatoR::converter).collect(Collectors.toList());
        }

        var contatos = contatoRepository.findbyClientId(clientRepository.findBycpf(loggedUser.getCpf()).get(0).getId());
        return contatos.stream().map(ContatoR::converter).collect(Collectors.toList());
    }

    @PostMapping("/")
    public void cadastroContato(@RequestBody ContatoRq contato){

        User loggedUser = Utils.getLoggedUser();
        var role = Utils.getUserRole();

        if(role.equals(UserRoles.ADMIN) ||
                clientRepository.findBycpf(loggedUser
                                            .getCpf())
                                            .get(0)
                                            .getId().equals(contato.getId_cliente())){
            var c = new Contato();
            c.setId_cliente(contato.getId_cliente());
            c.setTipo(contato.getTipo());
            c.setValor(contato.getValor());
            c.setTag(contato.getTag());
            contatoRepository.save(c);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

    }

    @PutMapping("/")
    public void updateContato(@RequestBody ContatoRq contato){
        User loggedUser = Utils.getLoggedUser();
        var role = Utils.getUserRole();

        var oldContato = contatoRepository.findById(contato.getId()).get();
        if(role.equals(UserRoles.ADMIN)) {
            oldContato.setId_cliente(contato.getId_cliente());
            oldContato.setTipo(contato.getTipo());
            oldContato.setValor(contato.getValor());
            oldContato.setTag(contato.getTag());
            contatoRepository.save(oldContato);
        }else if (clientRepository.findBycpf(loggedUser.getCpf()).get(0).getId().equals(contato.getId_cliente())){
            oldContato.setTipo(contato.getTipo());
            oldContato.setValor(contato.getValor());
            oldContato.setTag(contato.getTag());
            contatoRepository.save(oldContato);
        }else{
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteEndereco(@PathVariable("id") int id_contato){
        var oldContato = contatoRepository.findById(id_contato).get();

        User loggedUser = Utils.getLoggedUser();
        var role = Utils.getUserRole();

        if(role.equals(UserRoles.ADMIN) || clientRepository.findBycpf(loggedUser.getCpf()).get(0).getId().equals(oldContato.getId_cliente())) {
            try{
                contatoRepository.delete(oldContato);
            } catch (Exception e){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to delete contato");
            }
        }
    }
}
