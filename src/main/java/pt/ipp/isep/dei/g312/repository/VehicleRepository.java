package pt.ipp.isep.dei.g312.repository;

import pt.ipp.isep.dei.g312.domain.Organization;
import pt.ipp.isep.dei.g312.domain.Vehicle;

import java.util.*;

public class VehicleRepository {

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

    public boolean existsVehicle(Vehicle vehicle){
        if (vehiclesList.contains(vehicle)) {
            return true;
        }
        return false;
    }
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

    private boolean validateVehicle(Vehicle vehicle) {
        boolean isValid = !vehiclesList.contains(vehicle);

        return isValid;
    }


}
