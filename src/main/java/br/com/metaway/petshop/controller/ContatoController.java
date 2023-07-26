package br.com.metaway.petshop.controller;

import br.com.metaway.petshop.controller.dto.ContatoR;
import br.com.metaway.petshop.model.Contato;
import br.com.metaway.petshop.controller.dto.ContatoRq;
import br.com.metaway.petshop.repository.ContatoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path="/contato")
public class ContatoController {

    private final ContatoRepository contatoRepository;

    public ContatoController(ContatoRepository contatoRepository){
        this.contatoRepository = contatoRepository;
    }

    @GetMapping("/")
    public List<ContatoR> findAll(){
        var contatos = contatoRepository.findAll();
        return contatos.stream().map(ContatoR::converter).collect(Collectors.toList());
    }

    @PostMapping("/")
    public void cadastroContato(@RequestBody ContatoRq contato){
        var c = new Contato();
        c.setId_cliente(contato.getId_cliente());
        c.setTipo(contato.getTipo());
        c.setValor(contato.getValor());
        c.setTag(contato.getTag());
        contatoRepository.save(c);
    }
}
