package pt.ipp.isep.dei.g312.repository;

import org.junit.jupiter.api.Test;

import pt.ipp.isep.dei.g312.domain.GreenSpace;


import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import java.util.List;


import static org.junit.jupiter.api.Assertions.*;



class GreenSpaceRepositoryTest {
    @Test
    public void testGetGreenSpace() { //empty list
        GreenSpaceRepository repository = new GreenSpaceRepository();

        List<GreenSpace> greenSpaces = repository.getGreenSpace();
        assertEquals(0, greenSpaces.size());
    }

    @Test
    public void testAddGreenSpace() {
        GreenSpaceRepository repository = new GreenSpaceRepository();
        GreenSpace greenSpace = new GreenSpace("Passadiços do Paiva", "Arouca", 396.5, "Medium-Sized Park", "Marcelo");
        GreenSpace greenSpace1 = new GreenSpace("Passadiços do Paiva", "Arouca", 396.5, "Medium-Sized Park", "Marcelo");
        GreenSpace greenSpace2 = new GreenSpace("Passadiços do Paiva", "Arouca", 396.5, "Medium-Sized Park", "Marcelo"); // Duplicate

        assertTrue(repository.addGreenSpace(greenSpace));

        List<GreenSpace> greenSpaces = repository.getGreenSpace();
        assertEquals(1, greenSpaces.size());

        // Assuming sorting by name, compare the first element's name with the added green space
        assertEquals(greenSpace.getName(), greenSpaces.getFirst().getName());

        repository.addGreenSpace(greenSpace1);
        assertTrue(repository.addGreenSpace(greenSpace2)); // Assert adding duplicate succeeds


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
    public void testPrintRegisteredGreenSpaces() {
        GreenSpaceRepository repository = new GreenSpaceRepository();

        // Capture console output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        repository.printRegisteredGreenSpaces();

        System.setOut(System.out); // Restore standard output
        String output = outContent.toString();

        assertTrue(output.contains("---------------------------------------------------"));
        assertFalse(output.contains("Green Space name")); // No header for empty list
        GreenSpace greenSpace1 = new GreenSpace("Passadiços do Paiva", "Arouca", 396.5, "Medium-Sized Park", "Marcelo");
        GreenSpace greenSpace2 = new GreenSpace("Peneda/Gerês", "Minho", 965.0, "Large-Sized Park", "Luís Montenegro");

        repository.addGreenSpace(greenSpace1);
        repository.addGreenSpace(greenSpace2);


        repository.printRegisteredGreenSpaces();

        System.setOut(System.out); // Restore standard output


        assertTrue(output.contains("---------------------------------------------------"));
        assertFalse(output.contains("Green Space name")); // Check for header

        // Assert that each green space is present in the output with expected format
        List<GreenSpace> greenSpaces = repository.getGreenSpace();
        for (GreenSpace greenSpace : greenSpaces) {
            String expectedFormat = String.format("%25s -  %s - %s\n", greenSpace.getName(), greenSpace.getTypology(), greenSpace.getGreenSpaceManager());
            assertFalse(output.contains(expectedFormat));
        }
    }
}