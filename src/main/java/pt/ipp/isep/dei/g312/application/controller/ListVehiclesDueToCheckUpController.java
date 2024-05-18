package pt.ipp.isep.dei.g312.application.controller;

import pt.ipp.isep.dei.g312.domain.Vehicle;
import pt.ipp.isep.dei.g312.repository.Repositories;
import pt.ipp.isep.dei.g312.repository.VehicleRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.NoSuchElementException;

public class ListVehiclesDueToCheckUpController {

    private VehicleRepository vehicleRepository;

    //constructor for controller class, initializes the vehicleRepository
    public ListVehiclesDueToCheckUpController() {
        this.vehicleRepository = getVehicleRepository();
    }

    //method to get the vehicle repository
    private VehicleRepository getVehicleRepository() {
        return Repositories.getInstance().getVehicleRepository();
    }

    /** Returns a list of vehicles that are due for check-up. This method checks each vehicle
     * in the repository to determine if a check-up is due based on specific criteria implemented
     * in the isCheckUpDue() method.
     *
     * The list of due vehicles is then sorted by registration date before being returned.
     *
     * @return an ArrayList of Vehicle objects that are due for a check-up. The list is empty if no vehicles are due for check-up.
     */

    public ArrayList<Vehicle> vehiclesDueToCheckUp() {
        ArrayList<Vehicle> vehiclesDueToCheckUp = new ArrayList<>();
        for (Vehicle vehicle : vehicleRepository.getVehicles()) {


            if (vehicle.isCheckUpDue()) {
                vehiclesDueToCheckUp.add(vehicle);
            }

        }
        Collections.sort(vehiclesDueToCheckUp);
        return vehiclesDueToCheckUp;
    }

}
