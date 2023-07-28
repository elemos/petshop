package br.com.metaway.petshop.controller;

import br.com.metaway.petshop.UserRoles;
import br.com.metaway.petshop.Utils;
import br.com.metaway.petshop.controller.dto.ContatoR;
import br.com.metaway.petshop.model.Contato;
import br.com.metaway.petshop.controller.dto.ContatoRq;
import br.com.metaway.petshop.model.User;
import br.com.metaway.petshop.repository.ClientRepository;
import br.com.metaway.petshop.repository.ContatoRepository;
import org.springframework.http.HttpStatus;
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

    /**
     * Retorna a lista de todos os contatos se for adm
     * Se for cliente retorna todos os contatos do user logado
     * @return
     */
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

    /**
     * Cadastra um novo contato se for adm
     * Se for cliente só vai cadastrar um novo contato se o id recebido for igual ao logado
     * @param contato
     */
    @PostMapping("/")
    public void cadastroContato(@RequestBody ContatoRq contato){

        User loggedUser = Utils.getLoggedUser();
        var role = Utils.getUserRole();

        if(role.equals(UserRoles.ADMIN) ||
                clientRepository.findBycpf(loggedUser
                                            .getCpf())
                                            .get(0)
                                            .getId().equals(contato.getId_cliente())){
            var c = ContatoRq.converter(contato);
            contatoRepository.save(c);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

    }

    /**
     * Altera um contato já criado se for adm
     * Se for cliente altera apenas se o id do cliente contato recebido é igual o id do user logado
     * @param contato
     */
    @PutMapping("/")
    public void updateContato(@RequestBody ContatoRq contato){
        User loggedUser = Utils.getLoggedUser();
        var role = Utils.getUserRole();

        var oldContato = contatoRepository.findById(contato.getId()).get();
        if(role.equals(UserRoles.ADMIN)) {
            oldContato = ContatoRq.converter(contato);
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

    /**
     * Deleta um contato se for adm
     * se não for só deleta se o id passado for de um contato do user logado
     * @param id_contato
     */
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
