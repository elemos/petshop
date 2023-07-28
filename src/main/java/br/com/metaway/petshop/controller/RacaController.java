package br.com.metaway.petshop.controller;

import br.com.metaway.petshop.controller.dto.RacaR;
import br.com.metaway.petshop.controller.dto.RacaRq;
import br.com.metaway.petshop.model.Raca;
import br.com.metaway.petshop.repository.RacaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

    @PutMapping("/")
    public void updateRaca(@RequestBody RacaRq raca){
        var oldRaca = racaRepository.findById(raca.getId()).get();
        oldRaca.setDescricao(raca.getDescricao());
        racaRepository.save(oldRaca);
    }

    @DeleteMapping("/{id}")
    public void deleteRaca(@PathVariable("id") int id_raca){
        var oldRaca = racaRepository.findById(id_raca).get();
        try{
            racaRepository.delete(oldRaca);
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to delete raca");
        }
    }

    public boolean racaExist(Integer id){
        return racaRepository.existsById(id);
    }

}
