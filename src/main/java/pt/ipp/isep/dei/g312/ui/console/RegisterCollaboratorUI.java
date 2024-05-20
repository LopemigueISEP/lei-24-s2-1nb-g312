package pt.ipp.isep.dei.g312.ui.console;

import pt.ipp.isep.dei.g312.application.controller.RegisterCollaboratorController;
import pt.ipp.isep.dei.g312.domain.Employee;

import pt.ipp.isep.dei.g312.ui.console.utils.Result;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static pt.ipp.isep.dei.g312.ui.console.utils.Utils.raiseAlertMessage;

/**
 * This class implements the user interface for registering collaborators.
 * It interacts with the `RegisterCollaboratorController` to collect user input,
 * validate data, and submit employee information for registration.

 * This class implements the `Runnable` interface, indicating it can be executed
 * as a thread.

When we implement Runnable we must override the method run() so it works
 */
public class RegisterCollaboratorUI implements Runnable {

    private final RegisterCollaboratorController controller;
    private String name;
    private Date birthDate;
    private int phoneNumber;
    private String email;
    private Date admissionDate;
    private String taxpayerNumber;
    private String address;
    private String docNumber;
    private String job;



    /**
     * Creates a new instance of `RegisterCollaboratorUI`.
     * Internally, it creates a new instance of `RegisterCollaboratorController`
     * to interact with the application logic.
     */
    public RegisterCollaboratorUI() {
        controller = new RegisterCollaboratorController();
    }
    /**
     * Getter method for the `RegisterCollaboratorController` instance.
     *
     * @return The associated `RegisterCollaboratorController` instance
     */
    public RegisterCollaboratorController getController() {
        return controller;
    }

    /**
     * This method defines the tasks to be executed by a separate thread for registering a collaborator.

     * The method creates a new Result object to store the outcome of the operation an prints a message to the console indicating the start of the collaborator registration process.
     * Then calls the `requestData` method to retrieve the collaborator's data and checks the `hasError` field of the `Result` object:
     *      - If there are no errors (`hasError` is false), the method calls the `submitData` method to submit the data.
     *      - If there are errors (`hasError` is true), the method calls the `raiseAlertMessage` method to display an alert message with the error details.
     */

    @Override
    public void run() {
        Result result = new Result();

        System.out.println("\n\n--- Register Collaborator ---");
        System.out.println();

        result = requestData();
        if (!result.hasError) {
            submitData();
        } else {
            raiseAlertMessage(result.message);
        }
    }
    /**
     * This method collects user input for all employee attributes:
     * name, birthdate, email, phone number, admission date, taxpayer number,
     * address, ID document number, and job title. It also verifies if the
     * provided taxpayer number already exists in the system. If it does,
     * it prompts the user to enter a new one or exit the registration process.
     */

    private void submitData() {
        Optional<Employee> employee = getController().registerEmployee(name, birthDate, email
                , phoneNumber, admissionDate, taxpayerNumber
                , address, docNumber, job);
        //controller.printEmployes();
        if (employee.isPresent()) {
            System.out.println("Collaborator successfully registered");
        } else {
            System.out.println("Failed to register collaborator due to an error.");
        }

    }


       /**
     * This method retrieves information required to register a collaborator.
     * The method requests the taxpayer number of the collaborator using the `requestTaxpayerNumber` method and checks if a collaborator with the provided taxpayer number already exists in the system using the `controller.existsCollaborator` method.
     *     - If a collaborator exists it prints an error message indicating a duplicate taxpayer number, prompts the user to enter a new taxpayer number or exit the registration process using a loop, validates user input (1 - enter new number, 0 - exit) andreturns a `Result` object with an error message if the user chooses to exit.
     *     - If a collaborator doesn't exist with the provided taxpayer number it requests additional collaborator information using dedicated methods for each field (name, birth date, email, etc.) and returns a successful `Result` object.
     * @return A `Result` object containing either collaborator information or an error message if the registration fails.
     */
    private Result requestData() {
         taxpayerNumber = requestTaxpayerNumber();
        // confirm that the collaborator is not registered using taxpayer number that is different for each collaborator
        if (controller.existsCollaborator(taxpayerNumber)) {
            System.out.println("Collaborator with taxpayer number " + taxpayerNumber + " already exists in the system, insert a different one.");
            System.out.println();



           while (true) {

                System.out.println("Enter your choice:");
                System.out.println("1. Enter a new taxpayer number");
                System.out.println("0. Exit");
                System.out.print("Type your option: ");
                Scanner scanner = new Scanner(System.in);
                String choice = scanner.nextLine();

                if (choice.equals("1")) {
                    requestData();
                    return new Result();

                } else if (choice.equals("0")) {
                    return new Result("Collaborator already exists.", true);
                } else {
                    System.out.println("Invalid choice. Please enter 1 or 0.");
                }
            }
        }else{
            name = requestName();
            birthDate = requestBirthDate();
            email = requestEmail();
            phoneNumber = Integer.parseInt(requestPhoneNumber());
            admissionDate = requestAdmissionDate(birthDate);
            address = requestAddress();
            docNumber = requestDocNumber();
            job = requestJobList();
            showsDataRequestsValidation();
            }
        return new Result();
    }
    /**
     * Requests the user to enter the collaborator's name. It ensures the name
     * is not empty and has a maximum of 6 words.
     *
     * @return The collaborator's name entered by the user
     */
    private String requestName() {
        Scanner input = new Scanner(System.in);

        while (true) {
            System.out.print("Name: ");
            String name = input.nextLine();

            if (!name.isEmpty()) {
                String[] words = name.split(" "); // verify number of words

                if (words.length <= 6) {
                    return name; // name is valid - less or equal to 6 words
                } else {
                    System.out.println("Error: Name must be maximum 6 words");
                }
            }
        }
    }
    /**
     * Requests the user to enter the collaborator's birthdate in the format DD/MM/YYYY. It ensures the user
     * is at least 18 years old by checking against the current date.
     *
     * @return The collaborator's birthdate
     */
    private Date requestBirthDate() {
        boolean validDate = false;
        Date birthDate = null;

        while (!validDate) {
            try {
                System.out.print("Birth date: ");
                birthDate = getDate();
                Calendar now = Calendar.getInstance();


                // Check if birthDate is 18 years before today (not only year, but also the day)
                now.add(Calendar.YEAR, -18);
                if (birthDate.before(now.getTime())) {
                    validDate = true;
                } else {
                    System.out.println("Invalid date. User must be at least 18 years old.");
                }
            } catch (Exception e) {
                raiseInvalidInput();
            }
        }
        return birthDate;
    }
    /**
     * Requests the user to enter the collaborator's email address. It validates the email format using a
     * regular expression to ensure it includes an "@" symbol and a domain name.
     *
     * @return The collaborator's email
     */
    private String requestEmail() {
        Scanner input = new Scanner(System.in);
        String email;

        while (true) {
            System.out.print("Enter email: ");
            email = input.nextLine();

            if (isEmailValid(email)) { // verify is mail is valid
                return email;
            } else {
                System.out.println("Invalid email format. Please enter a valid email");
            }
        }
    }
    /**
     * Validates the email format using a regular expression that checks for the presence of "@" and a domain name.
     *
     * @param email The email address to validate
     * @return True if the email format is valid, false otherwise
     */
    private boolean isEmailValid(String email) {
        String regex = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return email.matches(regex);
    }
 /** Requests the user to enter the collaborator's phone number.
  * It ensures the phone number has exactly 9 digits.
  * @return The collaborator's phone number as an integer
  **/
    private String requestPhoneNumber() {
        Scanner input = new Scanner(System.in);
        String phoneNumber;

        while (true) {
            System.out.print("Phone Number: ");
            phoneNumber = input.nextLine();

            if (phoneNumber.matches("^[0-9]{9}$") && phoneNumber.length() == 9) { //verify 9 digits
                return phoneNumber;
            } else {
                System.out.println("Error: Invalid phone number format. Please enter 9 digits.");
            }
        }
    }
    /**
     * Requests the user to enter the collaborator's taxpayer number. It ensures the taxpayer number
     * is not empty and has exactly 9 digits.
     * @return The collaborator's taxpayer number entered by the user
     */
    private String requestTaxpayerNumber() {
        Scanner input = new Scanner(System.in);
        String taxpayerNumber;

        while (true) {
            System.out.print("Taxpayer Number: ");
            taxpayerNumber = input.nextLine();

            // Check if empty or doesn't have exactly 9 digits
            if (taxpayerNumber.length() != 9 || !taxpayerNumber.matches("^[0-9]+$")) {
                System.out.println("Error: Taxpayer number cannot be empty and must have exactly 9 digits.");
            } else {
                return taxpayerNumber;
            }
        }
    }
    /**
     * Requests the user to enter the collaborator's admission date in the format DD/MM/YYYY. It ensures the user
     * is at least 18 years old by checking against the birth date.
     * @param birthDate The collaborator's birth date (used to calculate minimum valid admission date)
     * @return The collaborator's admission date entered by the user (after validation)
     */
    private Date requestAdmissionDate(Date birthDate) {
        boolean parseSuccess = false;
        Date admissionDate = null;
        Calendar minAdmissionDate = Calendar.getInstance();
        minAdmissionDate.setTime(birthDate);
        minAdmissionDate.add(Calendar.YEAR, 18);

        Calendar birthCalendar = Calendar.getInstance();
        birthCalendar.setTime(birthDate);
        int birthYear = birthCalendar.get(Calendar.YEAR);

        while (!parseSuccess) {
            System.out.print("Admission Date: ");
            try {
                admissionDate = getDate();
                Calendar admissionCalendar = Calendar.getInstance();
                admissionCalendar.setTime(admissionDate);

                if (admissionCalendar.get(Calendar.YEAR) >= (birthYear + 18)) {

                        parseSuccess = true;
                    } else{
                        System.out.println("Invalid admission date. Must be at least 18 years after birth date.");
                    }
                } catch(Exception e){
                    raiseInvalidInput();
                }

        }
        return admissionDate;
    }
    /**
     * Requests the user to enter the collaborator's address. There are no specific format restrictions
     * on the address input.
     * @return The collaborator's address entered by the user
     */
    private String requestAddress() {
        Scanner input = new Scanner(System.in);
        System.out.print("Address: ");
        return input.nextLine();
    }
    /**
     * Prompts the user to enter a document number by repeatedly asking for the document type (CC or Passport)
     * and the corresponding document number until a valid format is provided.
     * @return The valid document number entered by the user.
     * @throws InputMismatchException if the user enters an invalid document type (not 'CC' or 'Passport').
     */
    private String requestDocNumber() {
        Scanner input = new Scanner(System.in);
        String docNumber;
        // asks if it is CC or passport
        while (true) {
            System.out.print("ID document type (CC/Passport): ");
            String docType = input.nextLine().toUpperCase();

            if (docType.equals("CC")) { // if it is CC
                System.out.print("Citizen card number: ");
                docNumber = input.nextLine();
                if (docNumber.matches("^[0-9]{8}$")) {  // Check if format is valid (8 digits)
                    return docNumber;
                } else {
                    System.out.println("Invalid citizen card number format (must be 8 digits).");
                }
            } else if (docType.equals("PASSPORT")) {  // if it is PASSPORT
                System.out.print("Passport number: ");
                docNumber = input.nextLine();
                if (docNumber.matches("^[A-Za-z]{2}[0-9]{6}$")) {  // Check if format is valid: 2 letters followed by 6 digits
                    return docNumber;
                } else {
                    System.out.println("Invalid passport number format (must be 2 letters followed by 6 digits).");
                }
            } else {
                System.out.println("Error: Invalid document type. Please enter 'CC' or 'Passport'.");
            }
        }
    }
    /**
     * This method retrieves the list of available jobs from the controller and displays them to the user.
     * The user is then prompted to enter a number corresponding to the desired job. The method validates the user input
     * and ensures the selected number is within the valid range of available jobs. If a valid selection is made,
     * the corresponding job title is returned. Otherwise, an error message is displayed, and the user is prompted
     * to re-enter a valid selection.
     * @return The selected job title (String) or an empty string if no valid selection is made.
     * @throws NullPointerException if the retrieved list of jobs is null.
     */
    public String requestJobList() {
        String selectedJob ="";
        List<String> jobs = controller.getJobsList(); //asks controller to acesss jobList

        if (jobs.isEmpty()) {
            System.out.println("No jobs available.");
        } else {
            System.out.println("Available Jobs:");
            for (int i = 0; i < jobs.size(); i++) {
                System.out.printf("%d. %s\n", i + 1, jobs.get(i)); // Display numbered titles with jobs registered
            }

            Scanner input = new Scanner(System.in);
            int selection = -1;
            boolean validSelection = false;

            while (!validSelection) {
                System.out.print("Select Job (by number): ");

                try {
                    selection = input.nextInt();
                    validSelection = selection >= 1 && selection <= jobs.size(); // Check for valid range within loop
                    if (!validSelection) {
                        System.out.println("Invalid job selection. Please choose a number between 1 and " + jobs.size() + ".");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a number.");
                    selection = -1; // Reset selection on invalid input
                    input.nextLine(); // Clear the scanner buffer to avoid infinite loop
                }
            }

            selectedJob = jobs.get(selection - 1);
        }
        return selectedJob;
    }

    /**
     Prints a simple error message "Invalid Input!" to the console. This method likely serves as a helper method
     used within the UI class to provide basic feedback to the user when input validation fails.
     */
    private void raiseInvalidInput() {
        System.out.println("Invalid Input!");
    }

    /**
     Prompts the user to enter a date in the format DD/MM/YYYY and validates the input. It uses a loop to continuously
     request input until a valid date is entered.
     @return The parsed Date object representing the entered date

     */
    private Date getDate() {
        Scanner input = new Scanner(System.in);
        SimpleDateFormat registerDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        while (true) {

            String dateString = input.nextLine();

            // Validate date format using regular expression
            if (!dateString.matches("^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/[0-9]{4}$")) {
                System.out.println("Error: Invalid date format. Please use DD/MM/YYYY.");
                continue;
            }

            try {
                // Parse the date string if format is valid
                return registerDateFormat.parse(dateString);
            } catch (ParseException e) {
                System.out.println("Please enter a valid date."); // More specific error message
            }
        }
    }
    /**
     Presents a summary of the collaborator's information to the user for confirmation before creating
     the collaborator in the system. This method formats the date attributes (birth date and admission date)
     using a SimpleDateFormat object.
     @return True if the user confirms the information is correct, false otherwise. */
    private boolean showsDataRequestsValidation() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        System.out.printf("\nName: %s\nBirth date: %s\nEmail: %s\nPhone Number: %s\nAdmission Date: %s\nTaxpayer Number: %s\nAddress: %s\nID number: %s\nJob: %s\n", name, simpleDateFormat.format(birthDate), email, phoneNumber, simpleDateFormat.format(admissionDate), taxpayerNumber, address, docNumber, job);
        return requestConfirmation();
    }

    /**
     Prompts the user to confirm the collaborator's information by entering 'Y' (yes) or 'N' (no). It validates
     the user input to ensure it's either 'Y' or 'N' (case-insensitive).
     @return True if the user confirms the information (enters 'Y'), false otherwise.
     */
    private boolean requestConfirmation() {
        String dados ="";
        final String CONFIRMAR = "y";
        final String REJEITAR = "n";
        boolean resposta = false;

        do {
            try {
                Scanner input = new Scanner(System.in);
                System.out.print("\nThe data is correct? [Y/N]");
                dados = input.nextLine().toLowerCase();
                if(!dados.matches("[YyNn]+")){
                    System.out.print("Inserted character is incorrect");
                }
            }catch (Exception e){
                System.out.println("Error reading Y/N in UI");
            }
        }while (!dados.equals(CONFIRMAR) && !dados.equals(REJEITAR));



        if(dados.equals(CONFIRMAR)){
            resposta = true;
        }

        return resposta;
    }

}
