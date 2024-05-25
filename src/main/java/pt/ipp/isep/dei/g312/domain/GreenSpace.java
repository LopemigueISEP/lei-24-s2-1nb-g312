package pt.ipp.isep.dei.g312.domain;

import pt.ipp.isep.dei.g312.repository.Repositories;

import java.util.Optional;

public class GreenSpace implements Comparable<GreenSpace> {
    private String name;
    private String address;
    private double area;
    private String typology;
    private String greenSpaceManager;

    /**
     * Main constructor for creating an `GreenSpace` object.
     * @param name              The green space's name.
     * @param address           The green space's address.
     * @param area          The green space's area.
     * @param typology              The green space's typology.
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
     * Creates a clone of the current green space object.
     *
     * @return A new `Green Space` object that is a copy of the current instance.
     */

    @Override
    public GreenSpace clone() {
        return new GreenSpace(this.name, this.address, this.area, this.typology, this.greenSpaceManager);
    }

    public static Optional<GreenSpace> registerGreenSpace(String name, String address, double area, String typology, String greenSpaceManager, boolean userValidation) {
        boolean validateAddedRepository;
        try {
            if (userValidation) {
                GreenSpace greenSpace = new GreenSpace(name,address,area,typology, greenSpaceManager);
                validateAddedRepository = Repositories.getInstance().getGreenSpaceRepository().addGreenSpace(greenSpace);
                if (validateAddedRepository) {
                    return Optional.of(greenSpace);
                } else {
                    return Optional.empty();
                }
            } else {
                System.out.println("This user don't have permissions to register green spaces");
                return Optional.empty();
            }
        } catch (Exception e) {
            return Optional.empty();
        }

    }

    @Override
    public int compareTo(GreenSpace o) {
        return this.name.compareTo(o.getName());
    }
}
