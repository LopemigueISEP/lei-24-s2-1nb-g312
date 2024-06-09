package pt.ipp.isep.dei.g312.repository;

import pt.ipp.isep.dei.g312.domain.Job;
import pt.ipp.isep.dei.g312.domain.Organization;
import pt.ipp.isep.dei.g312.domain.Vehicle;

import java.io.*;
import java.util.*;

public class VehicleRepository implements Serializable {

    // list of vehicles
    public List<Vehicle> vehiclesList = new ArrayList<>();


    /** Retrieves a Vehicle object from the repository based on its registration plate.
     * This method iterates through the list of vehicles and returns the vehicle if its registration
     * plate matches the provided string.
     *
     * @param registrationPlate the registration plate of the vehicle to be retrieved.
     * @return Vehicle object with the matching registration plate.
     * @throws NoSuchElementException if no vehicle with the given registration plate is found in the repository.
     */
    public Vehicle getVehicle(String registrationPlate) {
        for (Vehicle vehicle : vehiclesList) {
            if (vehicle.getRegistrationPlate().equals(registrationPlate)) {
                return vehicle;
            }
        }
        throw new NoSuchElementException("Vehicle not found");
    }

    public List<Vehicle> getVehicles() {
        return new ArrayList<>(vehiclesList);
    }

    /** Updates the details of an existing vehicle in the repository. This method iterates through the
     * list of vehicles and replaces the old vehicle with the provided vehicle object if their
     * registration plates match. The operation is based on the registration plate.
     *
     * @param vehicle the object that contains the updated details. It must have a
     *                registration plate that matches an existing vehicle in the repository.
     */
    public void updateVehicle(Vehicle vehicle) {
        for (int i = 0; i < vehiclesList.size(); i++) {
            if (vehiclesList.get(i).getRegistrationPlate().equals(vehicle.getRegistrationPlate())) {
                vehiclesList.set(i, vehicle);
                return;
            }
        }
    }
    /**
     * Checks if a vehicle exists in the repository.
     *
     * @param vehicle The vehicle to check.
     * @return True if the vehicle exists in the repository, false otherwise.
     */
    public boolean existsVehicle(Vehicle vehicle){
        if (vehiclesList.contains(vehicle)) {
            return true;
        }
        return false;
    }
    /**
     * Checks if a vehicle with the given registration plate exists in the repository.
     *
     * @param registrationPlate The registration plate to check.
     * @return True if a vehicle with the given registration plate exists, false otherwise.
     */
    public boolean existsVehicle(String registrationPlate){
        if (registrationPlate == null) {
            return false;
        }
        List<Vehicle> vehicles = new ArrayList<>(vehiclesList);
        if (vehicles == null) {
            return false;
        }
        for (Vehicle v : vehicles) {
            if (registrationPlate.equalsIgnoreCase(v.getRegistrationPlate())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds a new vehicle to the repository.
     *
     * @param vehicle The vehicle to add.
     * @return An Optional containing the added vehicle if the operation is successful, empty otherwise.
     */
    public Optional<Vehicle> add(Vehicle vehicle) {
        Optional<Vehicle> newVehicle=Optional.empty();
        boolean operationSuccess=false;

        if (validateVehicle(vehicle) && !existsVehicle(vehicle.getRegistrationPlate())){
            newVehicle=Optional.of(vehicle.clone());
            operationSuccess=vehiclesList.add(newVehicle.get());
        }
        if (!operationSuccess){
            newVehicle=Optional.empty();
        }
        return newVehicle;
    }
    /**
     * Validates whether a vehicle is not already present in the repository.
     *
     * @param vehicle The vehicle to validate.
     * @return True if the vehicle is not already present in the repository, false otherwise.
     */
    private boolean validateVehicle(Vehicle vehicle) {
        boolean isValid = !vehiclesList.contains(vehicle);

        return isValid;
    }


    /**
     * Serializes the VehicleRepository object to a file.
     * The repository is saved to a file named after the class with a ".bin" extension.
     * This method handles the serialization process and writes the object state to a file.
     */
    public void serializateData() {

        String filename = this.getClass().getSimpleName()+".bin";

        // Serialization
        try {

            // Saving of object in a file
            FileOutputStream file = new FileOutputStream
                    (filename);
            ObjectOutputStream out = new ObjectOutputStream
                    (file);

            // Method for serialization of object
            out.writeObject(this);


            out.close();
            file.close();

            System.out.println(this.getClass().getSimpleName()+" Has Been Serialized successfully! ");
        } catch (FileNotFoundException ex) {
            System.out.println("IOException is caught");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Deserializes the VehicleRepository object from a file and adds the vehicles to the current repository.
     * The repository is read from a file named after the class with a ".bin" extension.
     * This method handles the deserialization process and reads the object state from a file.
     */
    public void getSeralizatedData() {
        String filename = this.getClass().getSimpleName()+".bin";

        try {

            // Reading the object from a file
            FileInputStream file = new FileInputStream
                    (filename);
            ObjectInputStream in = new ObjectInputStream
                    (file);

            // Method for deserialization of object
            VehicleRepository vehicleRepository = (VehicleRepository) in.readObject();

            for (Vehicle j :vehicleRepository.getVehicles()){
                this.add(j);
            }

            in.close();
            file.close();

        }

        catch (IOException ex) {
            System.out.printf("\n%s not found!",filename);
        }

        catch (ClassNotFoundException ex) {
            System.out.println("ClassNotFoundException" +
                    " is caught");
        }
    }



}
