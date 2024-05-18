package pt.ipp.isep.dei.g312.application.controller;

import pt.ipp.isep.dei.g312.domain.Vehicle;
import pt.ipp.isep.dei.g312.repository.Repositories;
import pt.ipp.isep.dei.g312.repository.VehicleRepository;

import java.util.*;

public class CheckUpController {

    private VehicleRepository vehicleRepository;

    /** Constructor of a new instance of CheckUpController. This constructor initializes the
     * VehicleRepository by retrieving it from the central repository manager. It ensures that
     * the controller has access to the vehicle data needed for its operations.
     */
    public CheckUpController() {
        this.vehicleRepository = getVehicleRepository();
    }

    //method to get the vehicle repository
    private VehicleRepository getVehicleRepository() {
        return Repositories.getInstance().getVehicleRepository();
    }

    /** Registers a check-up for a vehicle identified by its registration plate. This method verifies that
     * the kilometers at the last check-up are non-negative, retrieves the vehicle from the repository,
     * and records a new check-up date and current km at the check-up if the vehicle is found.
     *
     * @param registrationPlate the registration plate of the vehicle to register the check-up for.
     * @param checkUpDate the date when the check-up was performed.
     * @param kmAtLastCheckUp the kilometers at the time of the check-up, must not be negative.
     * @return true if the check-up was successfully registered.
     *         false if the vehicle does not exist or the check-up could not be registered.
     * @throws IllegalArgumentException if the kmAtLastCheckUp parameter is negative.
     */
    public boolean registerCheckUp(String registrationPlate, Date checkUpDate, double kmAtLastCheckUp) {
        if (kmAtLastCheckUp < 0) {
            throw new IllegalArgumentException("Km at last checkup cannot be negative");
        }
        try {
            Vehicle vehicle = vehicleRepository.getVehicle(registrationPlate);
            if (vehicle != null) {
                // if vehicle is found,register a new checkUp
                vehicle.registerCheckUp(kmAtLastCheckUp, checkUpDate);

                vehicleRepository.updateVehicle(vehicle);
                return true;
            }
        } catch (NoSuchElementException e) {
            return false;
        }
        return false;
    }

}
