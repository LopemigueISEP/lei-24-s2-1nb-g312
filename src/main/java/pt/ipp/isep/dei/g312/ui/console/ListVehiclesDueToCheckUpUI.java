package pt.ipp.isep.dei.g312.ui.console;

import pt.ipp.isep.dei.g312.application.controller.CheckUpController;
import pt.ipp.isep.dei.g312.application.controller.ListVehiclesDueToCheckUpController;
import pt.ipp.isep.dei.g312.domain.Vehicle;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class ListVehiclesDueToCheckUpUI implements Runnable {

    private final ListVehiclesDueToCheckUpController controller;


    /** Constructs a new instance of ListVehiclesDueToCheckUpUI. This constructor initializes the controller
     * responsible for accessing vehicle data related to check-ups.
     */
    public ListVehiclesDueToCheckUpUI(){

        controller = new ListVehiclesDueToCheckUpController();
    }

    /** The main method for listing vehicles due for a check-up. It calls the method to display the list
     * of due vehicles to the user.
     */
    @Override
    public void run() {
        System.out.println("\n\n--- List Vehicles due to Check-up ------------------------\n\n");
        listVehiclesDueToCheckUp();
    }

    /** Retrieves and displays a list of vehicles due to a check-up. It uses the controller to get the
     * list and then formats it for display. The list includes vehicle information such as registration
     * plate, brand, model, and Current Km, Check-up Km Frequency, Km at Last Check-up, and Km at Next Check-up.
     *
     * The output is formatted to display each vehicle's details in a table format.
     */
    public void listVehiclesDueToCheckUp() {
        List<Vehicle> vehiclesDue = controller.vehiclesDueToCheckUp();
        if (vehiclesDue.isEmpty()) {
            System.out.println("There are no vehicles due for a check-up at this time.");
        } else {
            System.out.printf("%-22s %-10s %-10s %-15s %-25s %-22s %-20s%n",
                    "Registration Plate ", "Brand", "Model", "Current Km", "Check-up Km Frequency", "Km at Last Check-up", "Km at Next Check-up");
            System.out.println("---------------------------------------------------------------------------------------------------------------------------------");

            for (Vehicle vehicle : vehiclesDue) {
                System.out.printf("%-22s %-10s %-10s %-15s %-25s %-22s %-20s%n",
                        vehicle.getRegistrationPlate(),
                        vehicle.getBrand(),
                        vehicle.getModel(),
                        String.format("%.1f", vehicle.getCurrentKm()),
                        String.format("%.1f", vehicle.getCheckUpKmFrequency()),
                        String.format("%.1f", vehicle.getKmAtLastCheckUp()),
                        String.format("%.1f", vehicle.getKmAtLastCheckUp() + vehicle.getCheckUpKmFrequency()));
            }
        }
    }


}
