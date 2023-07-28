package br.com.metaway.petshop.controller;

import br.com.metaway.petshop.UserRoles;
import br.com.metaway.petshop.Utils;
import br.com.metaway.petshop.controller.dto.*;
import br.com.metaway.petshop.model.Atendimento;
import br.com.metaway.petshop.model.Pet;
import br.com.metaway.petshop.model.User;
import br.com.metaway.petshop.repository.AtendimentoRepository;
import br.com.metaway.petshop.repository.ClientRepository;
import br.com.metaway.petshop.repository.PetRepository;
import org.springframework.http.HttpStatus;
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

    /**
     * Função que retorna todos os Atendimentos se for admin.
     * @returnlista com todos atendimentos
     */

    @GetMapping("/")
    public List<AtendimentoR> findAll(){

        var role = Utils.getUserRole();

        if(!role.equals(UserRoles.ADMIN)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        var atendimento = atendimentoRepository.findAll();
        return atendimento.stream().map(AtendimentoR::converter).collect(Collectors.toList());

    }

    /**
     * Função que retorna todos os Atendimentos para um pet se for admin.
     * Caso seja um cliente irá retornar todos atendimentos para o pet desde que
     * o id do pet passado pertença ao cliente logado.
     * @param id_pet id do pet para buscar os atendimento
     * @return Lista com atendimentos do pet
     */
    @GetMapping("/{id_pet}")
    public List<AtendimentoR> findAllbyPet(@PathVariable Integer id_pet){
        User loggedUser = Utils.getLoggedUser();
        var role = Utils.getUserRole();

        if(role.equals(UserRoles.ADMIN)){
            var atendimentos = atendimentoRepository.findByPetId(id_pet);
            return atendimentos.stream().map(AtendimentoR::converter).collect(Collectors.toList());
        }

        var pets = petRepository.findbyClientId(clientRepository.findBycpf(loggedUser.getCpf()).get(0).getId());

        for (Pet pet : pets){
            if (pet.getId() == id_pet){
                var atendimento = atendimentoRepository.findByPetId(id_pet);
                return atendimento.stream().map(AtendimentoR::converter).collect(Collectors.toList());
            }
        }
        throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }

    /**
    * Retorna todos atendimento caso for admin
    * Caso seja um cliente irá retornar todos atendimento para o cliente logado.
     */
    @GetMapping("/cliente")
    public List<AtendimentoR> findAllbyClient(){

        User loggedUser = Utils.getLoggedUser();
        var role = Utils.getUserRole();

        if(role.equals(UserRoles.ADMIN)){
            var atendimentos = atendimentoRepository.findAll();
            return atendimentos.stream().map(AtendimentoR::converter).collect(Collectors.toList());
        }

        var atendimento = atendimentoRepository.findbyClientId(clientRepository.findBycpf(loggedUser.getCpf()).get(0).getId());
        return atendimento.stream().map(AtendimentoR::converter).collect(Collectors.toList());
    }

    /**
     * Cadastra um novo atendimento, acessivel apenas para ADM
     * @param atendimento dados do atendimento
     */
    @PostMapping("/")
    public void cadastroAtendimento(@RequestBody AtendimentoRq atendimento){

        User loggedUser = Utils.getLoggedUser();
        var role = Utils.getUserRole();

        if(!role.equals(UserRoles.ADMIN)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        var at = AtendimentoRq.converter(atendimento);
        atendimentoRepository.save(at);
    }

    /**
     * Atualia um atendimento já cadastrado, disponivel apenas para ADM
     * @param atendimento dados a serem alterados
     */
    @PutMapping("/")
    public void updateAtendimento(@RequestBody AtendimentoRq atendimento){
        var oldAtendimento = atendimentoRepository.findById(atendimento.getId()).get();
        oldAtendimento = AtendimentoRq.converter(atendimento);
        atendimentoRepository.save(oldAtendimento);
    }

    /**
     * Deleta um atendimento criado, disponivel apenas para ADM
     * @param id_atendimento id do atendimento a ser deletado
     */
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
