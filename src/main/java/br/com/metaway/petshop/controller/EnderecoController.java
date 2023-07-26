package br.com.metaway.petshop.controller;

import br.com.metaway.petshop.controller.dto.ClientRq;
import br.com.metaway.petshop.controller.dto.EnderecoR;
import br.com.metaway.petshop.controller.dto.EnderecoRq;
import br.com.metaway.petshop.model.Client;
import br.com.metaway.petshop.model.Endereco;
import br.com.metaway.petshop.repository.EnderecoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path="/endereco")
public class EnderecoController {

    private final EnderecoRepository enderecoRepository;

    public EnderecoController(EnderecoRepository enderecoRepository){
        this.enderecoRepository = enderecoRepository;
    }

    @GetMapping("/")
    public List<EnderecoR>finAll(){
        var enderecos = enderecoRepository.findAll();
        return enderecos.stream().map(EnderecoR::converter).collect(Collectors.toList());
    }

    @PostMapping("/")
    public void cadastroEndereco(@RequestBody EnderecoRq endereco){
        var e = new Endereco();
        e.setId_cliente(endereco.getId_cliente());
        e.setCidade(endereco.getCidade());
        e.setBairro(endereco.getBairro());
        e.setLogradouro(endereco.getLogradouro());
        e.setComplemento(endereco.getComplemento());
        e.setTag(endereco.getTag());
        enderecoRepository.save(e);
    }



}
