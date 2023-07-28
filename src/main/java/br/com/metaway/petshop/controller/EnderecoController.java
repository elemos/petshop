package br.com.metaway.petshop.controller;

import br.com.metaway.petshop.UserRoles;
import br.com.metaway.petshop.Utils;
import br.com.metaway.petshop.controller.dto.EnderecoR;
import br.com.metaway.petshop.controller.dto.EnderecoRq;
import br.com.metaway.petshop.model.Endereco;
import br.com.metaway.petshop.model.User;
import br.com.metaway.petshop.repository.ClientRepository;
import br.com.metaway.petshop.repository.EnderecoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path="/endereco")
public class EnderecoController {

    private final EnderecoRepository enderecoRepository;
    private final ClientRepository clientRepository;

    public EnderecoController(EnderecoRepository enderecoRepository, ClientRepository clientRepository){
        this.enderecoRepository = enderecoRepository;
        this.clientRepository = clientRepository;
    }

    /**
     * Retorna a lista de todos endereços de for adm
     * Se for cliente retorna a lista de todos endereços do user logado
     * @return
     */
    @GetMapping("/")
    public List<EnderecoR>finAll(){

        User loggedUser = Utils.getLoggedUser();
        var role = Utils.getUserRole();

        if(role.equals(UserRoles.ADMIN)){
            var enderecos = enderecoRepository.findAll();
            return enderecos.stream().map(EnderecoR::converter).collect(Collectors.toList());
        }

        var enderecos = enderecoRepository.findbyClientId(clientRepository.findBycpf(loggedUser.getCpf()).get(0).getId());
        return enderecos.stream().map(EnderecoR::converter).collect(Collectors.toList());
    }

    /**
     * Cadastra um novo endereço se for adm
     * Se for cliente cadastra o novo endereço se o id do cliente passado for igual ao id do user logado
     * @param endereco
     */
    @PostMapping("/")
    public void cadastroEndereco(@RequestBody EnderecoRq endereco){

        User loggedUser = Utils.getLoggedUser();
        var role = Utils.getUserRole();

        if(role.equals(UserRoles.ADMIN) ||
                clientRepository.findBycpf(loggedUser
                                .getCpf())
                        .get(0)
                        .getId().equals(endereco.getId_cliente())) {
            var e = EnderecoRq.converter(endereco);
            enderecoRepository.save(e);
        } else{
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

    /**
     * Edita um endereço cadastrado se for adm
     * Se for cliente edita se o id do cliente recebido for igual ao user logado
     * @param endereco
     */
    @PutMapping("/")
    public void updateEndereco(@RequestBody EnderecoRq endereco){
        User loggedUser = Utils.getLoggedUser();
        var role = Utils.getUserRole();

        var oldEndereco = enderecoRepository.findById(endereco.getId()).get();
        if(role.equals(UserRoles.ADMIN)) {
            oldEndereco = EnderecoRq.converter(endereco);
            enderecoRepository.save(oldEndereco);
        }else if (clientRepository.findBycpf(loggedUser.getCpf()).get(0).getId().equals(endereco.getId_cliente())){
            oldEndereco.setCidade(endereco.getCidade());
            oldEndereco.setBairro(endereco.getBairro());
            oldEndereco.setComplemento(endereco.getComplemento());
            oldEndereco.setTag(endereco.getTag());
            enderecoRepository.save(oldEndereco);
        }else{
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

    /**
     * Deleta um endereco cadastrado
     * se for cliente só deleta se o id do cliente recebido for igual ao id do user logado
     * @param id_endereco
     */
    @DeleteMapping("/{id}")
    public void deleteEndereco(@PathVariable("id") int id_endereco){
        var oldEndereco = enderecoRepository.findById(id_endereco).get();

        User loggedUser = Utils.getLoggedUser();
        var role = Utils.getUserRole();

        if(role.equals(UserRoles.ADMIN) || clientRepository.findBycpf(loggedUser.getCpf()).get(0).getId().equals(oldEndereco.getId_cliente())) {
            try{
                enderecoRepository.delete(oldEndereco);
            } catch (Exception e){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to delete endereco");
            }
        }
    }
}
