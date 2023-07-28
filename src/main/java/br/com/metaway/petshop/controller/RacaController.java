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

    /**
     * Retorna toda a lista das raças
     * @return List of RacaR
     */
    @GetMapping("/")
    public List<RacaR> findAll(){
        var raca = racaRepository.findAll();
        return raca.stream().map(RacaR::converter).collect(Collectors.toList());
    }

    /**
     * Retorna uma raça por id
     * @param id
     * @return RacaR
     */
    @GetMapping("/{id}")
    public RacaR findbyId(@PathVariable("id") Integer id){
        var raca = racaRepository.findById(id).get();
        return RacaR.converter(raca);
    }

    /**
     * Cadastra uma nova raça, permitido apenas para adm
     * @param raca
     */
    @PostMapping("/")
    public void cadastroRaca(@RequestBody RacaRq raca){
        var r = new Raca();
        r.setDescricao(raca.getDescricao());

        racaRepository.save(r);
    }

    /**
     * Edita uma raça criada, permitido apenas para adm
     * @param raca
     */
    @PutMapping("/")
    public void updateRaca(@RequestBody RacaRq raca){
        var oldRaca = racaRepository.findById(raca.getId()).get();
        oldRaca.setDescricao(raca.getDescricao());
        racaRepository.save(oldRaca);
    }

    /**
     * Deleta uma reça, permitido apenas para adm
     * @param id_raca
     */
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
