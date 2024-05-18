package pt.ipp.isep.dei.g312.domain;

import java.util.ArrayList;
import java.util.List;

public class CSVFile implements Cloneable {

    private String fileName;
    private List<CSVLine> csvLines;


    public CSVFile() {
        this.csvLines = new ArrayList<>();
    }

    public CSVFile(String fileName, List<CSVLine> csvLines) {
        this.fileName=fileName;
        this.csvLines=csvLines;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public List<CSVLine> getCsvLines() {
        return csvLines;
    }

    public void setCsvLines(List<CSVLine> csvLines) {
        this.csvLines = csvLines;
    }
    public void add(String x, String y, double cost) {
        CSVLine csvLine = new CSVLine(x,y,cost);
        csvLines.add(csvLine);
    }

    /** @Override
    public CSVFile clone(){
        return new CSVFile(this.fileName,this.csvLines);
    } */

    @Override
    public CSVFile clone() {
        try {
            CSVFile cloned = (CSVFile) super.clone();
            cloned.csvLines = new ArrayList<>(csvLines); // Deep copy of lines
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("Cloning not supported for CSVFile", e);
        }
    }


}
