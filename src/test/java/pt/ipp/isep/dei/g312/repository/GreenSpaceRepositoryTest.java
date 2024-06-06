package pt.ipp.isep.dei.g312.repository;

import org.junit.jupiter.api.Test;

import pt.ipp.isep.dei.g312.application.session.UserSession;
import pt.ipp.isep.dei.g312.domain.GreenSpace;



import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;



class GreenSpaceRepositoryTest {
    @Test
    public void testGetGreenSpace() { //empty list
        GreenSpaceRepository repository = new GreenSpaceRepository();

        List<GreenSpace> greenSpaces = repository.getGreenSpaceList();
        assertEquals(0, greenSpaces.size());
    }

    @Test
    void addGreenSpace() {
        // Arrange
        GreenSpaceRepository repository = new GreenSpaceRepository();
        GreenSpace greenSpace = new GreenSpace("Park", "Street A", 100.0, "Medium-Sized Park", "John Doe");

        // Act
        Optional<GreenSpace> addedGreenSpace = repository.addGreenSpace(greenSpace);

        // Assert
        assertTrue(addedGreenSpace.isPresent());
        assertEquals(greenSpace, addedGreenSpace.get());

        // Additional tests
        assertFalse(repository.addGreenSpace(greenSpace).isPresent()); // Try adding the same green space again
        List<GreenSpace> greenSpaceList = repository.getGreenSpaceList();
        assertEquals(1, greenSpaceList.size()); // Ensure only one green space is added
        assertEquals(greenSpace, greenSpaceList.getFirst()); // Ensure the added green space is in the list
        assertEquals(greenSpaceList, repository.getGreenSpaceList()); // Ensure the list is correctly maintained
    }

    @Test
    public void testValidateGreenSpace() {
        GreenSpaceRepository repository = new GreenSpaceRepository();
        GreenSpace greenSpace = new GreenSpace("Passadiços do Paiva", "Arouca", 396.5, "Medium-Sized Park", "Marcelo");
        GreenSpace greenSpace1 = new GreenSpace("Passadiços do Paiva", "Arouca", 396.5, "Medium-Sized Park", "Marcelo");
        GreenSpace greenSpace2 = new GreenSpace("Peneda/Gerês", "Minho", 965.0, "Large-Sized Park", "Luís Montenegro");

        repository.addGreenSpace(greenSpace1);
        assertTrue(repository.validateGreenSpace(greenSpace2));
        assertFalse(repository.validateGreenSpace(greenSpace));
    }

    @Test
    public void testExistsWithName() {
        GreenSpaceRepository repository = new GreenSpaceRepository();
        GreenSpace greenSpace1 = new GreenSpace("Passadiços do Paiva", "Arouca", 396.5, "Medium-Sized Park", "Marcelo");
        GreenSpace greenSpace2 = new GreenSpace("Peneda/Gerês", "Minho", 965.0, "Large-Sized Park", "Luís Montenegro");

        repository.addGreenSpace(greenSpace1);
        assertTrue(repository.existsWithName("Passadiços do Paiva"));
        assertFalse(repository.existsWithName("Peneda/Gerês"));
    }

    @Test
    public void testExistsWithAddress() {
        GreenSpaceRepository repository = new GreenSpaceRepository();
        GreenSpace greenSpace1 = new GreenSpace("Passadiços do Paiva", "Arouca", 396.5, "Medium-Sized Park", "Marcelo");
        GreenSpace greenSpace2 = new GreenSpace("Peneda/Gerês", "Minho", 965.0, "Large-Sized Park", "Luís Montenegro");

        repository.addGreenSpace(greenSpace1);
        assertTrue(repository.existsWithAddress("Arouca"));
        assertFalse(repository.existsWithAddress("Minho"));
    }

    @Test
    void getGreenSpaceManagedByMe() {
        GreenSpaceRepository repository = new GreenSpaceRepository();
        GreenSpace greenSpace3 = new GreenSpace("Parque Eduardo VII", "Lisboa", 25.0, "Urban Park", "Luís Montenegro");
        GreenSpace greenSpace2 = new GreenSpace("Passadiços do Paiva", "Arouca", 396.5, "Medium-Sized Park", "Marcelo");
        GreenSpace greenSpace1 = new GreenSpace("Peneda/Gerês", "Minho", 965.0, "Large-Sized Park", "Luís Montenegro");
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