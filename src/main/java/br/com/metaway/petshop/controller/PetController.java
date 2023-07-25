package br.com.metaway.petshop.controller;

import br.com.metaway.petshop.controller.dto.PetR;
import br.com.metaway.petshop.controller.dto.PetRq;
import br.com.metaway.petshop.model.Pet;
import br.com.metaway.petshop.repository.PetRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path="/pet")
public class PetController {

    private final PetRepository petRepository;

    public PetController(PetRepository petRepository){
        this.petRepository = petRepository;
    }

    @GetMapping("/")
    public List<PetR> findAll(){
        var pet = petRepository.findAll();
        return pet.stream().map(PetR::converter).collect(Collectors.toList());
    }

    @PostMapping("/")
    public void cadastroPet(@RequestBody PetRq pet){
        var p = new Pet();
        p.setId_cliente(pet.getId_cliente());
        p.setNome(pet.getNome());
        p.setId_raca(pet.getId_raca());
        p.setDtnascimento(pet.getDtnascimento());
        petRepository.save(p);
    }

}
