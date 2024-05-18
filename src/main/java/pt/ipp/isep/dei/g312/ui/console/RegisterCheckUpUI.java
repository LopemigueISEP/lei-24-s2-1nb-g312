package pt.ipp.isep.dei.g312.ui.console;

import pt.ipp.isep.dei.g312.application.controller.CheckUpController;
import pt.ipp.isep.dei.g312.domain.Vehicle;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.Scanner;

public class RegisterCheckUpUI implements Runnable {

    private final CheckUpController controller;


    /** Constructs a new RegisterCheckUpUI instance and initializes the controller responsible
     * for managing vehicle check-up registrations.
     */
    public RegisterCheckUpUI(){

        controller = new CheckUpController();
    }

    /** The main method for the check-up registration process. It prompts the user for the vehicle
     * registration plate, check-up date, and kilometers at last check-up, then attempts to register
     * the check-up.
     *
     * If any input format is incorrect, particularly the date, it catches the exception and notifies
     * the user of an unexpected input format.
     */

    @Override
    public void run() {
        System.out.println("\n\n--- Register Check-up ------------------------");

        try {
            String registrationPlate = requestRegistrationPlate();
            Date checkUpDate = requestCheckUpDate();
            double kmAtLastCheckUp = requestKmAtLastCheckUp();


            submitData(registrationPlate, checkUpDate, kmAtLastCheckUp);
        } catch (ParseException e) {
            System.out.println("Unexpected input format");
        }

    }

    /** Submits the check-up data to the controller for registration.
     *
     * @param registrationPlate The vehicle's registration plate.
     * @param checkUpDate The date when the check-up was performed.
     * @param kmAtLastCheckUp The vehicle current kilometers at the last check-up.
     */
    private void submitData(String registrationPlate, Date checkUpDate, double kmAtLastCheckUp) {
        boolean checkUpRegistered = controller.registerCheckUp(registrationPlate, checkUpDate, kmAtLastCheckUp);
        if (checkUpRegistered) {
            System.out.println("Check-up registered successfully");
        } else {
            System.out.println("Vehicle not found. Please register the vehicle first.");
        }
    }

    /** Requests and returns the registration plate from the user.
     *
     * @return The vehicle's registration plate entered by the user.
     */
    private String requestRegistrationPlate() {
        Scanner input = new Scanner(System.in);
        System.out.print("Registration Plate: ");
        return input.nextLine();
    }

    /** Requests and returns the check-up date from the user. The date must be entered in the format "dd/MM/yyyy".
     *
     * @return Date object representing the date of the check-up.
     * @throws ParseException If the user's input does not match the "dd/MM/yyyy" format.
     */
    private Date requestCheckUpDate() throws ParseException {
        Scanner input = new Scanner(System.in);
        SimpleDateFormat registerDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        System.out.print("Check-up Date (dd/MM/yyyy): ");
        return registerDateFormat.parse(input.nextLine());
    }

    /** Requests and returns the vehicle current kilometers at the last check-up from the user.
     *
     * @return The vehicle current kilometers at the last check-up as a double.
     */
    private double requestKmAtLastCheckUp() {
        Scanner input = new Scanner(System.in);
        System.out.print("Km at Last Check-up: ");
        return Double.parseDouble(input.nextLine());
    }

}
