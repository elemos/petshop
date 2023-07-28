package br.com.metaway.petshop.controller;

import br.com.metaway.petshop.UserRoles;
import br.com.metaway.petshop.Utils;
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

    /**
     * Retorna todos os pets cadastrados se for adm
     * Se for cliente reorna todos os pets do id do cliente logado
     * @return
     */
    @GetMapping("/")
    public List<PetR> findAll(){

        User loggedUser = Utils.getLoggedUser();
        var role = Utils.getUserRole();

        if(role.equals(UserRoles.ADMIN)){
            var pet = petRepository.findAll();
            return pet.stream().map(PetR::converter).collect(Collectors.toList());
        }

        var pet = petRepository.findbyClientId(clientRepository.findBycpf(loggedUser.getCpf()).get(0).getId());
        return pet.stream().map(PetR::converter).collect(Collectors.toList());
    }

    /**
     * Cadastra um novo pet se for adm
     * Se for cliente cadastra o pet apenas se o id do form passado for igual do user logado
     * @param pet
     */
    @PostMapping("/")
    public void cadastroPet(@RequestBody PetRq pet){

        User loggedUser = Utils.getLoggedUser();
        var role = Utils.getUserRole();

        if(role.equals(UserRoles.ADMIN) ||
                clientRepository.findBycpf(loggedUser
                                .getCpf())
                        .get(0)
                        .getId().equals(pet.getId_cliente())) {

            var p = PetRq.converter(pet);
            petRepository.save(p);
        }else{
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

    /**
     * Edita um pet já criado se for adm
     * se for um cliente só edita o pet se o id do cliente for igual ao user logado
     * @param pet
     */
    @PutMapping("/")
    public void updatePet(@RequestBody PetRq pet){
        User loggedUser = Utils.getLoggedUser();
        var role = Utils.getUserRole();

        var oldpet = petRepository.findById(pet.getId()).get();
        if(role.equals(UserRoles.ADMIN)) {
            oldpet = PetRq.converter(pet);
            petRepository.save(oldpet);
        }else if (clientRepository.findBycpf(loggedUser.getCpf()).get(0).getId().equals(oldpet.getId_cliente())){
            oldpet.setNome(pet.getNome());
            oldpet.setId_raca(pet.getId_raca());
            oldpet.setDtnascimento(pet.getDtnascimento());
            petRepository.save(oldpet);
        }else{
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }
    /**
     * Deleta um pet cadastrado
     * se for cliente só deleta se o id do cliente recebido for igual ao id do user logado
     * @param id_pet
     */
    @DeleteMapping("/{id}")
    public void deletePet(@PathVariable("id") int id_pet){
        var oldPet = petRepository.findById(id_pet).get();

        User loggedUser = Utils.getLoggedUser();
        var role = Utils.getUserRole();

        if(role.equals(UserRoles.ADMIN) || clientRepository.findBycpf(loggedUser.getCpf()).get(0).getId().equals(oldPet.getId_cliente())) {
            try{
                petRepository.delete(oldPet);
            } catch (Exception e){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to delete user");
            }
        }
    }
}
