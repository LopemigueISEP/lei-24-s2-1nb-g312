package pt.ipp.isep.dei.g312.ui.console;

import pt.ipp.isep.dei.g312.application.controller.ListVehiclesDueToCheckUpController;
import pt.ipp.isep.dei.g312.domain.Vehicle;

import java.util.List;


public class ShowVehicleListUI implements Runnable {

    private final ListVehiclesDueToCheckUpController controller;


    public ShowVehicleListUI() {
        controller = new ListVehiclesDueToCheckUpController();
    }


    @Override
    public void run() {
        System.out.println("\n\n------------------ Vehicle List ------------------");
        try {
            registeredVehicleList();
        } catch (Exception e) {
            System.out.println("Impression not possible");
        }
    }

    public void registeredVehicleList() {
        System.out.printf("%-22s %-10s %-10s %-15s %-25s %-22s %-20s%n",
                "Registration Plate ", "Brand", "Model", "Current Km", "Check-up Km Frequency", "Km at Last Check-up", "Km at Next Check-up");
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------");

        for (Vehicle vehicle : controller.registeredVehicleList()) {
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



