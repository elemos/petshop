package br.com.metaway.petshop.controller;

import br.com.metaway.petshop.controller.dto.AtendimentoR;
import br.com.metaway.petshop.controller.dto.AtendimentoRq;
import br.com.metaway.petshop.controller.dto.PetR;
import br.com.metaway.petshop.controller.dto.PetRq;
import br.com.metaway.petshop.model.Atendimento;
import br.com.metaway.petshop.repository.AtendimentoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path="/atendimento")
public class AtendimentoController {

    private final AtendimentoRepository atendimentoRepository;

    public AtendimentoController(AtendimentoRepository atendimentoRepository){
        this.atendimentoRepository = atendimentoRepository;
    }

    @GetMapping("/")
    public List<AtendimentoR> findAll(){
        var atendimento = atendimentoRepository.findAll();
        return atendimento.stream().map(AtendimentoR::converter).collect(Collectors.toList());
    }

    @PostMapping("/")
    public void cadastroAtendimento(@RequestBody AtendimentoRq atendimento){
        var at = new Atendimento();
        at.setId_pet(atendimento.getId_pet());
        at.setDescricao(atendimento.getDescricao());
        at.setDtatendimento(atendimento.getDtatendimento());
        at.setValor(atendimento.getValor());
    }
}
