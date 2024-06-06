package pt.ipp.isep.dei.g312.domain;

import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.g312.repository.GreenSpaceRepository;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class GreenSpaceTest {

    private GreenSpace greenSpace;




    @Test
    public void testClone() {
        GreenSpace clonedGreenSpace = greenSpace.clone();
        assertEquals(greenSpace, clonedGreenSpace);
        assertNotSame(greenSpace, clonedGreenSpace);
    }
    @Test
    public void testConstructor() {
        // Arrange
        String name = "Park A";
        String address = "Address A";
        double area = 1000.0;
        GreenSpaceTypology typology = GreenSpaceTypology.MEDIUM;
        String greenSpaceManager = "Manager A";

        // Act
        GreenSpace greenSpace = new GreenSpace(name, address, area, typology, greenSpaceManager);

        // Assert
        assertEquals(name, greenSpace.getName());
        assertEquals(address, greenSpace.getAddress());
        assertEquals(area, greenSpace.getArea(), 0.001); // Use delta for double comparison
        assertEquals(typology, greenSpace.getTypology());
        assertEquals(greenSpaceManager, greenSpace.getGreenSpaceManager());
    }
}
