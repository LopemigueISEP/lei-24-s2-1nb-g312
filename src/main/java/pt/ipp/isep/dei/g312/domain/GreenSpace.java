package pt.ipp.isep.dei.g312.domain;

import pt.ipp.isep.dei.g312.repository.Repositories;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

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
     * @param greenSpaceManager  The username of the employee managing the green space.
     */

    public GreenSpace(String name, String address, double area, String typology, String greenSpaceManager){
        this.name = name;
        this.address = address;
        this.area = area;
        this.typology = typology;
        this.greenSpaceManager = greenSpaceManager;
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
    public static void printRegisteredGreenSpaces(List<GreenSpace> greenSpaces) {
        System.out.println("\n\n------------------ Green Spaces List --------------------");
        System.out.printf("%25s -  %s - %s\n",  "Green Space name", "Type", "Manager");
        System.out.println("-----------------------------------------------------------");

        for (GreenSpace greenSpace : greenSpaces) {
            System.out.printf("%25s -  %s - %s\n", greenSpace.getName(), greenSpace.getTypology(), greenSpace.getGreenSpaceManager());
        }
    }

    /**
     * Implements the Comparable interface to define an ordering for GreenSpace objects.
     * This method compares green spaces by their names.
     *
     * @param o The GreenSpace object to compare with.
     * @return A negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object.
     */
    @Override
    public int compareTo(GreenSpace o) {
        return this.name.compareTo(o.getName());
    }
}
