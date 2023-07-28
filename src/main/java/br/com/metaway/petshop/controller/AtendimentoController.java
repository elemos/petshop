package br.com.metaway.petshop.controller;

import br.com.metaway.petshop.UserRoles;
import br.com.metaway.petshop.controller.dto.*;
import br.com.metaway.petshop.model.Atendimento;
import br.com.metaway.petshop.model.Pet;
import br.com.metaway.petshop.model.User;
import br.com.metaway.petshop.repository.AtendimentoRepository;
import br.com.metaway.petshop.repository.ClientRepository;
import br.com.metaway.petshop.repository.PetRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path="/atendimento")
public class AtendimentoController {

    private final AtendimentoRepository atendimentoRepository;
    private final ClientRepository clientRepository;

    private final PetRepository petRepository;

    public AtendimentoController(AtendimentoRepository atendimentoRepository, ClientRepository clientRepository, PetRepository petRepository){
        this.atendimentoRepository = atendimentoRepository;
        this.clientRepository = clientRepository;
        this.petRepository = petRepository;
    }

    @GetMapping("/")
    public List<AtendimentoR> findAll(){

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var role = user.getTipo();

        if(!role.equals(UserRoles.ADMIN)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        var atendimento = atendimentoRepository.findAll();
        return atendimento.stream().map(AtendimentoR::converter).collect(Collectors.toList());

    }

    @GetMapping("/{id_pet}")
    public List<AtendimentoR> findAllbyPet(@PathVariable Integer id_pet){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var role = user.getTipo();

        if(role.equals(UserRoles.ADMIN)){
            var atendimentos = atendimentoRepository.findById(id_pet);
            return atendimentos.stream().map(AtendimentoR::converter).collect(Collectors.toList());
        }

        var pets = petRepository.findbyClientId(clientRepository.findBycpf(user.getCpf()).get(0).getId());

        for (Pet pet : pets){
            if (pet.getId() == id_pet){
                var atendimento = atendimentoRepository.findByPetId(id_pet);
                return atendimento.stream().map(AtendimentoR::converter).collect(Collectors.toList());
            }
        }

        return null;
    }

    @GetMapping("/cliente")
    public List<AtendimentoR> findAllbyClient(){

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var role = user.getTipo();

        if(role.equals(UserRoles.ADMIN)){
            var atendimentos = atendimentoRepository.findAll();
            return atendimentos.stream().map(AtendimentoR::converter).collect(Collectors.toList());
        }

        var atendimento = atendimentoRepository.findbyClientId(clientRepository.findBycpf(user.getCpf()).get(0).getId());
        return atendimento.stream().map(AtendimentoR::converter).collect(Collectors.toList());
    }

    @PostMapping("/")
    public void cadastroAtendimento(@RequestBody AtendimentoRq atendimento){

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var role = user.getTipo();

        if(!role.equals(UserRoles.ADMIN)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        var at = new Atendimento();
        at.setId_pet(atendimento.getId_pet());
        at.setId_cliente(atendimento.getId_cliente());
        at.setDescricao(atendimento.getDescricao());
        at.setDtatendimento(atendimento.getDtatendimento());
        at.setValor(atendimento.getValor());
        atendimentoRepository.save(at);
    }

    @PutMapping("/")
    public void updateAtendimento(@RequestBody AtendimentoRq atendimento){
        var oldAtendimento = atendimentoRepository.findById(atendimento.getId()).get();
        oldAtendimento.setId_cliente(atendimento.getId_cliente());
        oldAtendimento.setId_pet(atendimento.getId_pet());
        oldAtendimento.setDtatendimento(atendimento.getDtatendimento());
        oldAtendimento.setDescricao(atendimento.getDescricao());
        oldAtendimento.setValor(atendimento.getValor());
        atendimentoRepository.save(oldAtendimento);
    }

    @DeleteMapping("/{id}")
    public void deleteRaca(@PathVariable("id") int id_atendimento){
        var oldRaca = atendimentoRepository.findById(id_atendimento).get();
        try{
            atendimentoRepository.delete(oldRaca);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to delete user");
        }
    }

}
