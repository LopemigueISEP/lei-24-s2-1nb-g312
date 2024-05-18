package pt.ipp.isep.dei.g312.domain;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CheckUpTest {

    @Test
    public void testCheckUpConstructor() {
        // Arrange
        double kmAtLastCheckUp = 20000;
        Date checkUpDate = new Date();

        // Act
        CheckUp checkUp = new CheckUp(kmAtLastCheckUp, checkUpDate);

        // Assert
        assertEquals(kmAtLastCheckUp, checkUp.getKmAtLastCheckUp(), "KmAtLastCheckUp should be initialized correctly");
        assertEquals(checkUpDate, checkUp.checkUpDate, "CheckUpDate should be initialized correctly");
    }
}
