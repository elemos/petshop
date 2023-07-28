package br.com.metaway.petshop.controller;

import br.com.metaway.petshop.UserRoles;
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

    @GetMapping("/")
    public List<EnderecoR>finAll(){

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var role = user.getTipo();

        if(role.equals(UserRoles.ADMIN)){
            var enderecos = enderecoRepository.findAll();
            return enderecos.stream().map(EnderecoR::converter).collect(Collectors.toList());
        }

        var enderecos = enderecoRepository.findbyClientId(clientRepository.findBycpf(user.getCpf()).get(0).getId());
        return enderecos.stream().map(EnderecoR::converter).collect(Collectors.toList());
    }

    @PostMapping("/")
    public void cadastroEndereco(@RequestBody EnderecoRq endereco){

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var role = user.getTipo();

        if(role.equals(UserRoles.ADMIN) ||
                clientRepository.findBycpf(user
                                .getCpf())
                        .get(0)
                        .getId().equals(endereco.getId_cliente())) {
            var e = new Endereco();
            e.setId_cliente(endereco.getId_cliente());
            e.setCidade(endereco.getCidade());
            e.setBairro(endereco.getBairro());
            e.setLogradouro(endereco.getLogradouro());
            e.setComplemento(endereco.getComplemento());
            e.setTag(endereco.getTag());
            enderecoRepository.save(e);
        } else{
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }
    @PutMapping("/")
    public void updateEndereco(@RequestBody EnderecoRq endereco){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var role = user.getTipo();

        var oldEndereco = enderecoRepository.findById(endereco.getId()).get();
        if(role.equals(UserRoles.ADMIN)) {
            oldEndereco.setId_cliente(endereco.getId_cliente());
            oldEndereco.setCidade(endereco.getCidade());
            oldEndereco.setBairro(endereco.getBairro());
            oldEndereco.setComplemento(endereco.getComplemento());
            oldEndereco.setTag(endereco.getTag());
            enderecoRepository.save(oldEndereco);
        }else if (clientRepository.findBycpf(user.getCpf()).get(0).getId().equals(endereco.getId_cliente())){
            oldEndereco.setCidade(endereco.getCidade());
            oldEndereco.setBairro(endereco.getBairro());
            oldEndereco.setComplemento(endereco.getComplemento());
            oldEndereco.setTag(endereco.getTag());
            enderecoRepository.save(oldEndereco);
        }else{
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteEndereco(@PathVariable("id") int id_endereco){
        var oldEndereco = enderecoRepository.findById(id_endereco).get();

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var role = user.getTipo();

        if(role.equals(UserRoles.ADMIN) || clientRepository.findBycpf(user.getCpf()).get(0).getId().equals(oldEndereco.getId_cliente())) {
            try{
                enderecoRepository.delete(oldEndereco);
            } catch (Exception e){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to delete endereco");
            }
        }
    }
}
