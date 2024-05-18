package pt.ipp.isep.dei.g312.domain;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CSVFileTest {

    private CSVFile csvFile;
    private List<CSVLine> lines;

    @BeforeEach
    public void setUp() {
        lines = new ArrayList<>();
        lines.add(new CSVLine("Header1", "Header2", 1.0));
        lines.add(new CSVLine("Data1", "Data2", 2.0));
        csvFile = new CSVFile("test.csv", lines);
    }

    @Test
    public void testConstructor() {
        assertNotNull(csvFile.getCsvLines());
        assertEquals("test.csv", csvFile.getFileName());
        assertEquals(2, csvFile.getCsvLines().size());
    }

    @Test
    public void testEmptyConstructor() {
        CSVFile emptyCsvFile = new CSVFile();
        assertNotNull(emptyCsvFile.getCsvLines());
        assertTrue(emptyCsvFile.getCsvLines().isEmpty());
    }

    @Test
    public void testSetFileName() {
        csvFile.setFileName("newTest.csv");
        assertEquals("newTest.csv", csvFile.getFileName());
    }

    @Test
    public void testSetCsvLines() {
        List<CSVLine> newLines = new ArrayList<>();
        newLines.add(new CSVLine("NewHeader1", "NewHeader2", 3.0));
        csvFile.setCsvLines(newLines);
        assertEquals(1, csvFile.getCsvLines().size());
        assertEquals(3.0, csvFile.getCsvLines().get(0).getCost());
    }

    @Test
    public void testAdd() {
        csvFile.add("Added1", "Added2", 5.0);
        assertEquals(3, csvFile.getCsvLines().size());
        assertEquals("Added1", csvFile.getCsvLines().get(2).getX());
        assertEquals("Added2", csvFile.getCsvLines().get(2).getY());
        assertEquals(5.0, csvFile.getCsvLines().get(2).getCost());
    }

    @Test
    public void testClone() {
        CSVFile clone = csvFile.clone();
        assertNotSame(csvFile, clone);
        assertEquals(csvFile.getFileName(), clone.getFileName());
        assertEquals(csvFile.getCsvLines().size(), clone.getCsvLines().size());
        assertNotSame(csvFile.getCsvLines(), clone.getCsvLines());
    }
}
