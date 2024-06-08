package pt.ipp.isep.dei.g312.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Represents a vehicle with attributes to manage registration, brand, model, type,
 * weight parameters, kilometers, and check-up details.
 */
public class Vehicle implements Comparable<Vehicle>, Serializable {


    private String registrationPlate;
    private String brand;
    private String model;
    private String type;
    private double tare;
    private double grossWeight;
    private double currentKm;
    private Date registerDate;
    private Date acquisitionDate;
    private double checkUpKmFrequency;
    private double checkUpKm;
    private double KmAtLastCheckUp;

    private ArrayList<CheckUp> checkUps = new ArrayList<>();
    /**
     * Constructs a new Vehicle with specified parameters, initializing its check-up list.
     * @param registrationPlate the vehicle's unique identifier
     * @param brand the brand of the vehicle
     * @param model the model of the vehicle
     * @param type the type of the vehicle
     * @param tare the tare weight of the vehicle
     * @param grossWeight the gross weight of the vehicle
     * @param currentKm the current kilometers of the vehicle
     * @param registerDate the registration date of the vehicle
     * @param acquisitionDate the acquisition date of the vehicle
     * @param checkUpKmFrequency the frequency in kilometers for check-ups
     */
    public Vehicle(String registrationPlate, String brand, String model
            , String type, double tare, double grossWeight
            , double currentKm, Date registerDate, Date acquisitionDate
            , double checkUpKmFrequency) {
        validateVehicle(registrationPlate);
        this.registrationPlate = registrationPlate;
        this.brand = brand;
        this.model = model;
        this.type = type;
        this.tare = tare;
        this.grossWeight = grossWeight;
        this.currentKm = currentKm;
        this.registerDate = registerDate;
        this.acquisitionDate = acquisitionDate;
        this.checkUpKmFrequency = checkUpKmFrequency;
        this.checkUps.add(new CheckUp(currentKm, new Date())); // add first checkup when car is bought
    }
    public void setRegistrationPlate(String registrationPlate) {
        this.registrationPlate = registrationPlate;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getTare() {
        return tare;
    }

    public void setTare(double tare) {
        this.tare = tare;
    }

    public double getGrossWeight() {
        return grossWeight;
    }

    public void setGrossWeight(double grossWeight) {
        this.grossWeight = grossWeight;
    }

    public double getCurrentKm() {
        return currentKm;
    }

    public void setCurrentKm(double currentKm) {
        this.currentKm = currentKm;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public Date getAcquisitionDate() {
        return acquisitionDate;
    }

    public void setAcquisitionDate(Date acquisitionDate) {
        this.acquisitionDate = acquisitionDate;
    }

    public double getCheckUpKmFrequency() {
        return checkUpKmFrequency;
    }

    public void setCheckUpKmFrequency(double checkUpKmFrequency) {
        this.checkUpKmFrequency = checkUpKmFrequency;
    }

    public double getCheckUpKm() {
        return checkUpKm;
    }

    public void setCheckUpKm(double checkUpKm) {
        this.checkUpKm = checkUpKm;
    }

    public double getKmAtLastCheckUp() {
        return this.checkUps.get(this.checkUps.size() - 1).getKmAtLastCheckUp();
    }

    public void setKmAtLastCheckUp(double kmAtLastCheckUp) {
        KmAtLastCheckUp = kmAtLastCheckUp;
    }

    /**
     * Validates the registration plate of the vehicle to ensure it is neither null nor empty.
     * This method throws an IllegalArgumentException if the registration plate does not meet the criteria,
     * ensuring that no vehicle is instantiated with an invalid registration plate.
     *
     * @param registrationPlate the registration plate to be validated
     * @throws IllegalArgumentException if the registration plate is null or empty
     */
    private void validateVehicle(String registrationPlate) {
        if (registrationPlate == null || registrationPlate.isEmpty()) {
            throw new IllegalArgumentException("Registration Plate cannot be null or empty");
        }
    }

    /** Register a new check-up for the vehicle with the typed kilometers and date.
     *
     * @param kmAtLastCheckUp The kilometers covered by the vehicle at the time of the new check-up.
     * @param checkUpDate     The date when the last check-up was done.
     */
    // register a new checkUp
    public void registerCheckUp(double kmAtLastCheckUp, Date checkUpDate) {
        CheckUp checkUp = new CheckUp(kmAtLastCheckUp, checkUpDate);
        this.checkUps.add(checkUp);
    }


    /** Boolean method to determines if the vehicle is due for a check-up or not, based on the check-up frequency and its current kilometers.
     * A check-up is considered due if the current kilometers are within 5% of the kilometers calculated for the next check-up
     * or if the current kilometers have exceeded the calculated kilometers for the next check-up.
     *
     * @return {@code true} if the vehicle needs a check-up, {@code false} if it doens't need a check-up.
     */

    // check if vehicle needs checkUp (is within 5% of the checkUpKmFrequency or is overdue to checkUp)
    public boolean isCheckUpDue() {


        double kmAtNextCheckUp = this.checkUpKmFrequency + this.checkUps.get(this.checkUps.size() - 1).getKmAtLastCheckUp();
        if (kmAtNextCheckUp <= this.currentKm) {
            return true;
        }
        if (this.currentKm >= (kmAtNextCheckUp * 0.95)) {
            return true;
        }

        return false;
    }

    /**
     * Returns a list of all check-ups performed on the vehicle.
     */
    public ArrayList<CheckUp> getCheckUpList() {
        return checkUps;
    }

    public String getRegistrationPlate() {
        return registrationPlate;
    }



    public Vehicle clone(){
        return new Vehicle(this.registrationPlate,this.brand,this.model,this.type,
                this.tare,this.grossWeight,this.currentKm,this.registerDate,this.acquisitionDate,this.checkUpKmFrequency);
    }


    /** Method to Compare vehicles based on their registration dates. It orders the vehicles due to check-up by their registration date.
     *
     * @param vehicle The vehicle to compare to.
     * @return -1 if this vehicle's registration date is earlier, 1 if later, and 0 if the same.
     */
    // method compareTo to compare two vehicles by register date
    @Override
    public int compareTo(Vehicle vehicle) {
        if (this.registerDate.before(vehicle.registerDate)) {
            return -1;
        }
        if (this.registerDate.after(vehicle.registerDate)) {
            return 1;
        }
        else {
            return 0;
        }
    }
    /**
     * Indicates whether some other object is "equal to" this one. This method compares the registration plates of two vehicles.
     *
     * @param obj The object with which to compare.
     * @return True if the registration plate of this vehicle is equal to the registration plate of the specified object; false otherwise.
     */

    public boolean equals(Vehicle obj) {
        return this.getRegistrationPlate().equals(obj.getRegistrationPlate());
    }
}
