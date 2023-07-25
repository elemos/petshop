package br.com.metaway.petshop.controller;

import br.com.metaway.petshop.Utils;
import br.com.metaway.petshop.controller.dto.RacaR;
import br.com.metaway.petshop.controller.dto.RacaRq;
import br.com.metaway.petshop.model.Raca;
import br.com.metaway.petshop.repository.RacaRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path="/raca")
public class RacaController {

    private final RacaRepository racaRepository;

    public RacaController(RacaRepository racaRepository){
        this.racaRepository = racaRepository;
    }
    @GetMapping("/")
    public List<RacaR> findAll(){
        var raca = racaRepository.findAll();
        return raca.stream().map(RacaR::converter).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public RacaR findbyId(@PathVariable("id") Integer id){
        var raca = racaRepository.findById(id).get();
        return RacaR.converter(raca);
    }

    @PostMapping("/")
    public void cadastroRaca(@RequestBody RacaRq raca){
        var r = new Raca();
        r.setDescricao(raca.getDescricao());

        racaRepository.save(r);
    }

    public boolean racaExist(Integer id){
        return racaRepository.existsById(id);
    }

}
