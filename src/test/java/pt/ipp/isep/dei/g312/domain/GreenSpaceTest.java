package pt.ipp.isep.dei.g312.domain;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class GreenSpaceTest {
    @Test
    public void testConstructor() {
        String name = "Peneda/Gerês";
        String address = "Minho";
        double area = 965.0;
        String typology = "Large-Sized Park";
        String manager = "Luís Montenegro";

        GreenSpace greenSpace = new GreenSpace(name, address, area, typology, manager);

        assertEquals(name, greenSpace.getName());
        assertEquals(address, greenSpace.getAddress());
        assertEquals(area, greenSpace.getArea());
        assertEquals(typology, greenSpace.getTypology());
        assertEquals(manager, greenSpace.getGreenSpaceManager());
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
    public void testRegisterGreenSpace_ValidPermissions() {
        // Simulate successful repository interaction (replace with actual mocking if needed) - valid
        GreenSpace greenSpace = new GreenSpace("Peneda/Gerês", "Minho", 965.0, "Large-Sized Park", "Luís Montenegro");
        assertTrue(GreenSpace.registerGreenSpace(greenSpace.getName(), greenSpace.getAddress(), greenSpace.getArea(),
                greenSpace.getTypology(), greenSpace.getGreenSpaceManager(), true).isPresent());
        //invalid
        assertFalse(GreenSpace.registerGreenSpace("Peneda/Gerês", "Minho", 965.0, "Large-Sized Park", "Luís Montenegro", false).isPresent());

    }
}
