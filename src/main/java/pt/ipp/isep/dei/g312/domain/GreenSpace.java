package pt.ipp.isep.dei.g312.domain;

public class GreenSpace implements Cloneable {
    private String name;
    private String address;
    private double area;
    private String typology;

    public GreenSpace(String name, String address, double area, String typology){
        this.name = name;
        this.address = address;
        this.area = area;
        this.typology = typology;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public double getArea() {
        return area;
    }
    public void setArea(Double area) {
        this.area = area;
    }
    public void setTypology(String typology) {
        this.typology = typology;
    }
    public String getTypology() {
        return typology;
    }


    @Override
    public GreenSpace clone() {
        return new GreenSpace(this.name, this.address, this.area, this.typology);
    }
}
