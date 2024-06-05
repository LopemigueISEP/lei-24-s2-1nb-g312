package pt.ipp.isep.dei.g312.domain;


import java.io.Serializable;
import java.util.Objects;

/**
 * Represents a green space entity.
 */
public class GreenSpace implements Comparable<GreenSpace>, Serializable {
    private String name;
    private String address;
    private double area;
    private String typology;
    private String greenSpaceManager;

    /**
     * Main constructor for creating a new `GreenSpace` object.
     *
     * @param name              The green space's name.
     * @param address           The green space's address.
     * @param area              The green space's area in square meters.
     * @param typology          The green space's typology (e.g., Garden, Medium-Sized Park, Large-Sized Park).
     * @param greenSpaceManager The username of the employee managing the green space.
     */

    public GreenSpace(String name, String address, double area, String typology, String greenSpaceManager) {
        this.name = name;
        this.address = address;
        this.area = area;
        this.typology = typology;
        this.greenSpaceManager = greenSpaceManager;
    }
    /**
     * This constructor creates a new GreenSpace object by copying the properties of another GreenSpace object.
     *
     * @param greenSpace The GreenSpace object to copy from.
     */
    public GreenSpace(GreenSpace greenSpace) {
        this.name = greenSpace.getName();
        this.address = greenSpace.getAddress();
        this.area = greenSpace.getArea();
        this.typology = greenSpace.getTypology();
        this.greenSpaceManager = greenSpace.getGreenSpaceManager();

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

    public String getGreenSpaceManager() {
        return greenSpaceManager;
    }

    public void setGreenSpaceManager(String greenSpaceManager) {
        this.greenSpaceManager = greenSpaceManager;
    }

    /**
     * Creates a deep copy of the current `GreenSpace` object.
     *
     * @return A new `GreenSpace` object that is a copy of the current instance.
     */
    @Override
    public GreenSpace clone() {
        return new GreenSpace(this.name, this.address, this.area, this.typology, this.greenSpaceManager);
    }


    /**
     * Compares this green space with another based on their names.
     *
     * @param o The GreenSpace object to compare with.
     * @return A negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object.
     */
    @Override
    public int compareTo(GreenSpace o) {
        return this.name.compareTo(o.getName());
    }

    /**
     * Returns a string representation of the green space.
     *
     * @return A string representation of the green space.
     */
    @Override
    public String toString() {
        return String.format("%s - %s - %.2f m² - %s - Managed by: %s", name, address, area, typology, greenSpaceManager);
    }
    /**
     * This method overrides the default `equals` method from the `Object` class.
     * It checks if two GreenSpace objects are equal based on their properties.
     *
     * @param o The object to compare with.
     * @return True if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GreenSpace that = (GreenSpace) o;
        return Double.compare(that.area, area) == 0 &&
                Objects.equals(name, that.name) &&
                Objects.equals(address, that.address) &&
                Objects.equals(typology, that.typology) &&
                Objects.equals(greenSpaceManager, that.greenSpaceManager);
    }
    /**
     * This method overrides the default `hashCode` method from the `Object` class.
     * It generates a hash code for a GreenSpace object based on its properties.
     *
     * @return The hash code for this GreenSpace object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, address, area, typology, greenSpaceManager);


    }
}
