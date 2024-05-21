package pt.ipp.isep.dei.g312.domain;

public class GreenSpace implements Cloneable {
    private String name;
    private String address;
    private double area;
    private String typology;

    /**
     * Main constructor for creating an `GreenSpace` object.
     * @param name              The green space's name.
     * @param address           The green space's address.
     * @param area          The green space's area.
     * @param typology              The green space's typology.
     */

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

    /**
     * Creates a clone of the current green space object.
     *
     * @return A new `Green Space` object that is a copy of the current instance.
     */

    @Override
    public GreenSpace clone() {
        return new GreenSpace(this.name, this.address, this.area, this.typology);
    }
}
