package br.com.metaway.petshop;

import br.com.metaway.petshop.controller.AtendimentoController;
import br.com.metaway.petshop.controller.dto.AtendimentoR;
import br.com.metaway.petshop.model.Atendimento;
import br.com.metaway.petshop.model.Client;
import br.com.metaway.petshop.model.Pet;
import br.com.metaway.petshop.repository.AtendimentoRepository;
import br.com.metaway.petshop.repository.ClientRepository;
import br.com.metaway.petshop.repository.PetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class AtendimentoControllerTest {

    @Mock
    private AtendimentoRepository atendimentoRepository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private PetRepository petRepository;

    @InjectMocks
    private AtendimentoController atendimentoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        // ArrangeInteger id, Integer id_pet, Integer id_cliente, String descricao, Double valor, Date dtatendimento
        List<Atendimento> atendimentos = Arrays.asList(
                new Atendimento(1, 1,1,"Banho", 50.50, new Date(2023,07,28)),
                new Atendimento(2, 1,1,"Tosa", 60.50, new Date(2023,07,20))
        );
        when(atendimentoRepository.findAll()).thenReturn(atendimentos);

        // Act
        List<AtendimentoR> result = atendimentoController.findAll();

        // Assert
        assertEquals(atendimentos.size(), result.size());
    }

    @Test
    void testFindAllByPet_AdminRole() {
        // Arrange
        int petId = 1;
        List<Atendimento> atendimentos = Arrays.asList(
                new Atendimento(1, 1,1,"Banho", 50.50, new Date(2023,07,28)),
                new Atendimento(2, 2,1,"Tosa", 60.50, new Date(2023,07,20))
        );
        when(atendimentoRepository.findByPetId(petId)).thenReturn(atendimentos);

        // Act
        List<AtendimentoR> result = atendimentoController.findAllbyPet(petId);

        // Assert
        assertEquals(atendimentos.size(), result.size());
    }

    @Test
    void testFindAllByPet_ClientRole_PetExists() {
        // Arrange
        int petId = 1;
        int clientId = 1;
        String clientcpf = "000.000.000-10";
        List<Client> clients = Arrays.asList(
                new Client(1,"Eduardo", "000.000.000-10", new Date(2023,07,28)),
                new Client(2,"Ana", "000.000.000-05", new Date(2023,07,28))
        );
        List<Pet> pets = Arrays.asList(
                new Pet(1, 1, 1, new Date(2023,05,20), "Fifi"),
                new Pet(1, 1, 1, new Date(2023,05,20), "Fifi")
        );
        List<Atendimento> atendimentos = Arrays.asList(
                new Atendimento(1, 1,1,"Banho", 50.50, new Date(2023,07,28)),
                new Atendimento(2, 2,1,"Tosa", 60.50, new Date(2023,07,20))
       );
        when(clientRepository.findBycpf(clientcpf)).thenReturn(clients);
        when(petRepository.findbyClientId(clientId)).thenReturn(pets);
        when(atendimentoRepository.findByPetId(petId)).thenReturn(atendimentos);

        // Act
        List<AtendimentoR> result = atendimentoController.findAllbyPet(petId);

        // Assert
        assertEquals(atendimentos.size(), result.size());
    }

    @Test
    void testFindAllByPet_ClientRole_PetDoesNotExist() {
        // Arrange
        int petId = 10;
        int clientId = 1;
        String clientcpf = "000.000.000-10";
        List<Client> clients = Arrays.asList(
                new Client(1,"Eduardo", "000.000.000-10", new Date(2023,07,28)),
                new Client(2,"Ana", "000.000.000-05", new Date(2023,07,28))
        );
        List<Pet> pets = Arrays.asList(
                new Pet(1, 1, 1, new Date(2023,05,20), "Fifi"),
                new Pet(1, 1, 1, new Date(2023,05,20), "Fifi")
        );
        List<Atendimento> atendimentos = Arrays.asList(
                new Atendimento(1, 1,1,"Banho", 50.50, new Date(2023,07,28)),
                new Atendimento(2, 2,1,"Tosa", 60.50, new Date(2023,07,20))
        );
        when(clientRepository.findBycpf(clientcpf)).thenReturn(clients);
        when(petRepository.findbyClientId(clientId)).thenReturn(pets);
        when(atendimentoRepository.findByPetId(petId)).thenReturn(atendimentos);

        // Act & Assert
        assertThrows(ResponseStatusException.class, () -> atendimentoController.findAllbyPet(petId));
    }

    // Add more tests for other methods as needed

}
