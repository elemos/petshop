package br.com.metaway.petshop.controller;

import br.com.metaway.petshop.UserRoles;
import br.com.metaway.petshop.controller.dto.PetR;
import br.com.metaway.petshop.controller.dto.PetRq;
import br.com.metaway.petshop.model.Pet;
import br.com.metaway.petshop.model.User;
import br.com.metaway.petshop.repository.ClientRepository;
import br.com.metaway.petshop.repository.PetRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path="/pet")
public class PetController {

    private final PetRepository petRepository;
    private final ClientRepository clientRepository;
    public PetController(PetRepository petRepository, ClientRepository clientRepository){
        this.petRepository = petRepository;
        this.clientRepository = clientRepository;
    }

    @GetMapping("/")
    public List<PetR> findAll(){

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var role = user.getTipo();

        if(role.equals(UserRoles.ADMIN)){
            var pet = petRepository.findAll();
            return pet.stream().map(PetR::converter).collect(Collectors.toList());
        }

        var pet = petRepository.findbyClientId(clientRepository.findBycpf(user.getCpf()).get(0).getId());
        return pet.stream().map(PetR::converter).collect(Collectors.toList());
    }

    @PostMapping("/")
    public void cadastroPet(@RequestBody PetRq pet){

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var role = user.getTipo();

        if(role.equals(UserRoles.ADMIN) ||
                clientRepository.findBycpf(user
                                .getCpf())
                        .get(0)
                        .getId().equals(pet.getId_cliente())) {

            var p = new Pet();
            p.setId_cliente(pet.getId_cliente());
            p.setNome(pet.getNome());
            p.setId_raca(pet.getId_raca());
            p.setDtnascimento(pet.getDtnascimento());
            petRepository.save(p);
        }else{
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("/")
    public void updatePet(@RequestBody PetRq pet){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var role = user.getTipo();
        var oldpet = petRepository.findById(pet.getId()).get();
        if(role.equals(UserRoles.ADMIN)) {

            oldpet.setId_cliente(pet.getId_cliente());
            oldpet.setNome(pet.getNome());
            oldpet.setId_raca(pet.getId_raca());
            oldpet.setDtnascimento(pet.getDtnascimento());
            petRepository.save(oldpet);
        }else if (clientRepository.findBycpf(user.getCpf()).get(0).getId().equals(oldpet.getId_cliente())){
            oldpet.setNome(pet.getNome());
            oldpet.setId_raca(pet.getId_raca());
            oldpet.setDtnascimento(pet.getDtnascimento());
            petRepository.save(oldpet);
        }else{
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/{id}")
    public void deletePet(@PathVariable("id") int id_pet){
        var oldPet = petRepository.findById(id_pet).get();

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var role = user.getTipo();

        if(role.equals(UserRoles.ADMIN) || clientRepository.findBycpf(user.getCpf()).get(0).getId().equals(oldPet.getId_cliente())) {
            try{
                petRepository.delete(oldPet);
            } catch (Exception e){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to delete user");
            }
        }
    }
}
