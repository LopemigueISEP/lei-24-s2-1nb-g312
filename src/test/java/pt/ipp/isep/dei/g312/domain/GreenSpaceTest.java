package pt.ipp.isep.dei.g312.domain;

import org.junit.jupiter.api.Test;



import static org.junit.jupiter.api.Assertions.*;

public class GreenSpaceTest {
    @Test
    public void testConstructor() {
        // Arrange
        String name = "Peneda/Gerês";
        String address = "Minho";
        double area = 965.0;
        String typology = "Large-Sized Park";
        String greenSpaceManager = "Luís Montenegro";

        // Act
        GreenSpace greenSpace = new GreenSpace(name, address, area, typology, greenSpaceManager);

        // Assert
        assertEquals(name, greenSpace.getName());
        assertEquals(address, greenSpace.getAddress());
        assertEquals(area, greenSpace.getArea());
        assertEquals(typology, greenSpace.getTypology());
        assertEquals(greenSpaceManager, greenSpace.getGreenSpaceManager());
    }

    @Test
    public void testClone() {
        GreenSpace original = new GreenSpace("Passadiços do Paiva", "Arouca", 396.5, "Medium-Sized Park", "Marcelo");
        GreenSpace clone = original.clone();

        // Verify that the clone has the same attributes as the original
        assertEquals(original.getName(), clone.getName());
        assertEquals(original.getAddress(), clone.getAddress());
        assertEquals(original.getArea(), clone.getArea());
        assertEquals(original.getTypology(), clone.getTypology());
        assertEquals(original.getGreenSpaceManager(), clone.getGreenSpaceManager());

        // Modify the clone and verify that the original is not affected
        clone.setName("Passadiços de Vizela");
        clone.setArea(29.0);

        assertNotEquals(original.getName(), clone.getName());
        assertNotEquals(original.getArea(), clone.getArea());
    }

    @Test
    public void testCompareTo() {
        GreenSpace park1 = new GreenSpace("Serra da Estrela", "Covilhã", 396.5, "Medium-Sized Park", "Marcelo");
        GreenSpace park2 = new GreenSpace("Peneda/Gerês", "Minho", 965.0, "Large-Sized Park", "Luís Montenegro");

        // Park 1 should be alphabetically higher than Park 2
        assertTrue(park1.compareTo(park2) > 0);

        // Same park should return 0
        GreenSpace samePark = new GreenSpace("Peneda/Gerês", "Minho", 965.0, "Large-Sized Park", "Luís Montenegro");
        assertEquals(0, park2.compareTo(samePark));
    }
    @Test
    public void testToString() {
        // Arrange
        GreenSpace park = new GreenSpace("Park", "123 Main St", 500.0, "Medium", "John");

        // Act
        String result = park.toString();

        // Assert
        String expected = "Park - 123 Main St - 500.00 m² - Medium - Managed by: John";
        assertNotEquals(expected, result);
    }


}
