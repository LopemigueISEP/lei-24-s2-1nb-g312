package pt.ipp.isep.dei.g312.ui.console;


import pt.ipp.isep.dei.g312.application.controller.CreateVehicleController;
import pt.ipp.isep.dei.g312.domain.Vehicle;
import pt.ipp.isep.dei.g312.ui.console.utils.Result;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.Scanner;

import static pt.ipp.isep.dei.g312.ui.console.utils.Utils.*;
import static pt.ipp.isep.dei.g312.ui.console.utils.Utils.readLineFromConsole;

//When we implement Runnable we must override the method run() so it works
public class CreateVehicleUI implements Runnable {

    private final CreateVehicleController controller;
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

    /**
     * This constructor will create a new controller of the model we are working on private  on this case
     * it will create a CreateVehicleController
     **/
    public CreateVehicleUI() {
        controller = new CreateVehicleController();
    }

    //The method works like a "main" where the aplication will begin to this model
    @Override
    public void run() {

        Result result = new Result();

        System.out.println("\n\n--- Create Vehicle ------------------------");


        result = requestData();
        if (!result.hasError) {
            submitData();
        } else {
            raiseAlertMessage(result.message);
        }


    }


    private Result requestData() {
        registrationPlate = requestRegistrationPlate();

        if (!(controller.existsVehicle(registrationPlate))) {

            brand = requestBrand();
            model = requestModel();
            type = requestType();
            tare = requestTare();
            grossWeight = requestGrossWeight();
            currentKm = requestCurrentKm();
            registerDate = requestRegisterDate();
            acquisitionDate = requestAcquisitionDate();
            checkUpKmFrequency = requestCheckUpKmFrequency();
            return new Result();
        } else {
            return new Result("Vehicle already exists.", true);
        }

    }

    private void submitData() {
        Optional<Vehicle> vehicle = getController().createVehicle(registrationPlate, brand, model
                , type, tare, grossWeight
                , currentKm, registerDate, acquisitionDate
                , checkUpKmFrequency, calculateCheckUpKm(currentKm, checkUpKmFrequency));

        if (vehicle.isPresent()) {
            System.out.println("Vehicle successfully created!");
        }

    }


    private String requestRegistrationPlate() {
        return readLineFromConsole("Registration Plate: ");
    }

    private String requestBrand() {
        return readLineFromConsole("Brand: ");
    }

    private String requestModel() {
        return readLineFromConsole("Model: ");
    }

    private String requestType() {
        return readLineFromConsole("Type: ");
    }

    private double requestTare() {
        return readDoubleFromConsole("Tare: ");
    }

    private double requestGrossWeight() {
        return readDoubleFromConsole("Gross Weight: ");
    }


    private double requestCurrentKm() {
        return readDoubleFromConsole("Current Kilometers: ");
    }

    private Date requestRegisterDate() {
        return readDateFromConsole("Register Date (dd-MM-yyyy): ");

    }


    private Date requestAcquisitionDate() {
        return readDateFromConsole("Acquisition Date (dd-MM-yyyy): ");
    }

    private double requestCheckupKm() {
        return readDoubleFromConsole("Checkup Kilometers: ");
    }

    private double requestCheckUpKmFrequency() {
        return readDoubleFromConsole("Checkup Kilometers Frequency: ");
    }

    private double calculateCheckUpKm(double currentKm, double checkUpKmFrequency) {
        return currentKm + checkUpKmFrequency;
    }

    public CreateVehicleController getController() {
        return controller;
    }
}
