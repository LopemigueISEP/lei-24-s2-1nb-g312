package pt.ipp.isep.dei.g312.application.controller;

import pt.ipp.isep.dei.g312.domain.Vehicle;
import pt.ipp.isep.dei.g312.repository.Repositories;
import pt.ipp.isep.dei.g312.repository.VehicleRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class CreateVehicleController {

    private VehicleRepository vehicleRepository;

    public CreateVehicleController() {
        getVehicleRepository();
    }

    public Optional<Vehicle> createVehicle(String registrationPlate, String brand, String model
            , String type, double tare, double grossWeight
            , double currentKm, Date registerDate, Date acquisitionDate
            , double checkUpKmFrequency, double checkUpKm) {

        Optional<Vehicle> newVehicle = Optional.empty();
        Vehicle vehicle = new Vehicle(registrationPlate, brand, model
                , type, tare, grossWeight
                , currentKm, registerDate, acquisitionDate
                , checkUpKmFrequency);

        newVehicle = vehicleRepository.add(vehicle);


        return newVehicle;
    }

    private VehicleRepository getVehicleRepository() {
        if (vehicleRepository == null) {
            Repositories repositories = Repositories.getInstance();
            vehicleRepository = repositories.getVehicleRepository();

        }
        return vehicleRepository;

    }

    public Boolean existsVehicle(String registrationPlate) {
        return vehicleRepository.existsVehicle(registrationPlate);
    }
}