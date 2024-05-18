package pt.ipp.isep.dei.g312.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CSVLineTest {

    @Test
    public void testConstructorAndGetters() {
        // Given
        String x = "Column1";
        String y = "Column2";
        double cost = 15.5;

        // When
        CSVLine csvLine = new CSVLine(x, y, cost);

        // Then
        assertEquals(x, csvLine.getX(), "The X value should match the input.");
        assertEquals(y, csvLine.getY(), "The Y value should match the input.");
        assertEquals(cost, csvLine.getCost(), "The cost should match the input.");
    }

    @Test
    public void testCostPrecision() {
        // Given
        CSVLine csvLine = new CSVLine("x", "y", 0.123456789);

        // Then
        assertEquals(0.123456789, csvLine.getCost(), 0.000000001, "The cost should have high precision.");
    }

}