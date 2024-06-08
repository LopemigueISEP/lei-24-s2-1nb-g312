package pt.ipp.isep.dei.g312.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pt.ipp.isep.dei.g312.domain.GreenSpace;
import pt.ipp.isep.dei.g312.domain.GreenSpaceTypology;


import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;



class GreenSpaceRepositoryTest {
    private GreenSpaceRepository repository;
    private GreenSpace greenSpace1;
    private GreenSpace greenSpace2;
    @BeforeEach
    public void setUp() {
        repository = new GreenSpaceRepository();
        greenSpace1 = new GreenSpace("Park A", "Address A", 1000.0, GreenSpaceTypology.MEDIUM, "GSM");
        greenSpace2 = new GreenSpace("Garden B", "Address B", 2000.0, GreenSpaceTypology.GARDEN, "Admin");
    }

    @Test
    public void testAddGreenSpace() {
        // Test adding a valid green space
        Optional<GreenSpace> addedGreenSpace = repository.addGreenSpace(greenSpace1);
        assertTrue(addedGreenSpace.isPresent());
        assertTrue(repository.getGreenSpaceList().contains(greenSpace1));

        // Test adding an invalid green space (already exists)
        Optional<GreenSpace> addedGreenSpace2 = repository.addGreenSpace(greenSpace1);
        assertFalse(addedGreenSpace2.isPresent());
    }
    @Test
    public void testValidateGreenSpace() {
        // Test validating a new green space
        assertTrue(repository.validateGreenSpace(greenSpace1));

        // Add a green space to the repository
        repository.addGreenSpace(greenSpace1);

        // Test validating an existing green space
        assertFalse(repository.validateGreenSpace(greenSpace1));
    }

    @Test
    public void testExistsWithName() {
        // Add green spaces to the repository
        repository.addGreenSpace(greenSpace1);
        repository.addGreenSpace(greenSpace2);

        // Test existing green space name
        assertTrue(repository.existsWithName("Park A"));

        // Test non-existing green space name
        assertFalse(repository.existsWithName("Square C"));
    }

    @Test
    public void testExistsWithAddress() {
        // Add green spaces to the repository
        repository.addGreenSpace(greenSpace1);
        repository.addGreenSpace(greenSpace2);

        // Test existing green space address
        assertTrue(repository.existsWithAddress("Address A"));

        // Test non-existing green space address
        assertFalse(repository.existsWithAddress("Address C"));
    }

    @Test
    void getGreenSpaceManagedByMe() {
        GreenSpaceRepository repository = new GreenSpaceRepository();
        GreenSpace greenSpace3 = new GreenSpace("Parque Eduardo VII", "Lisboa", 25.0, GreenSpaceTypology.GARDEN, "Luís Montenegro");
        GreenSpace greenSpace2 = new GreenSpace("Passadiços do Paiva", "Arouca", 396.5, GreenSpaceTypology.MEDIUM, "Marcelo");
        GreenSpace greenSpace1 = new GreenSpace("Peneda/Gerês", "Minho", 965.0, GreenSpaceTypology.LARGE, "Luís Montenegro");
        repository.addGreenSpace(greenSpace1);
        repository.addGreenSpace(greenSpace2);
        repository.addGreenSpace(greenSpace3);

        assertEquals(repository.getGreenSpaceManagedByMe("Marcelo").getFirst(),greenSpace2); //Teste 1 - verifica se devolve o greenSpace correto
        assertNotEquals(repository.getGreenSpaceManagedByMe("Luís Montenegro").getFirst(),greenSpace2); //Teste 2 - verifica se não devolve o greenspace gerido por outro ator

        // Teste 3 - Verifica se devolve múltiplos greenSpaces geridos pelo mesmo ator
        List<GreenSpace> greenSpacesLuis = repository.getGreenSpaceManagedByMe("Luís Montenegro");
        assertTrue(greenSpacesLuis.contains(greenSpace1));
        assertTrue(greenSpacesLuis.contains(greenSpace3));

        // Teste 4 - Verifica se devolve uma lista vazia quando o usuário não gere nenhum espaço verde
        assertTrue(repository.getGreenSpaceManagedByMe("João").isEmpty());
    }
}