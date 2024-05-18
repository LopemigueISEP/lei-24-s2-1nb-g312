package pt.ipp.isep.dei.g312.domain;

public class CSVLine {
    private String x;
    private String y;
    private double cost;

    public CSVLine(String x, String y, double cost) {
        this.x = x;
        this.y = y;
        this.cost = cost;
    }

    public String getX() {
        return x;
    }

    public String getY() {
        return y;
    }

    public double getCost() {
        return cost;
    }

}
