package pt.ipp.isep.dei.g312.repository;

import org.junit.jupiter.api.Test;

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
        assertNotEquals(greenSpace, addedGreenSpace.get());

        // Additional tests
        assertTrue(repository.addGreenSpace(greenSpace).isPresent()); // Try adding the same green space again
        List<GreenSpace> greenSpaceList = repository.getGreenSpaceList();
        assertNotEquals(1, greenSpaceList.size()); // Ensure only one green space is added
        assertNotEquals(greenSpace, greenSpaceList.getFirst()); // Ensure the added green space is in the list
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
        assertTrue(repository.validateGreenSpace(greenSpace));
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

}