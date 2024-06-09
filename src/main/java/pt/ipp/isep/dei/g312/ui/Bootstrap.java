package pt.ipp.isep.dei.g312.ui;

import pt.ipp.isep.dei.g312.domain.*;
import pt.ipp.isep.dei.g312.repository.*;
import pt.ipp.isep.dei.g312.application.controller.authorization.AuthenticationController;
import pt.ipp.isep.dei.g312.ui.console.utils.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;




public class Bootstrap implements Runnable {


    public void run() {


        addOrganization();
        addUsers();
        addSkills();
        addJobs();
        addGreenSpaces();

        try {

            addVehicles();
            addEmployes();
            addTasks();
            addTeams();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * TODO:Adicionar aqui todos os repositórios
     */
    public void saveSeralization(){
        Repositories.getInstance().getSkillRepository().serializateData();
        Repositories.getInstance().getGreenSpaceRepository().serializateData();
        Repositories.getInstance().getJobRepository().serializateData();
        Repositories.getInstance().getTaskRepository().serializateData();
        Repositories.getInstance().getVehicleRepository().serializateData();
        Repositories.getInstance().getEmployeeRepository().serializateData();
        Repositories.getInstance().getTeamRepository().serializateData();
    }

    private void addEmployes() {
        EmployeeRepository employeeRepository = Repositories.getInstance().getEmployeeRepository();
        employeeRepository.getSeralizatedData();


//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//        try {
//            employeeRepository.addEmployee(new Employee("Main Administrator", dateFormat.parse("01/01/1950"), "admin@this.app", 919017113,  dateFormat.parse("01/01/1968"), "246597859", "Porto", "12345678", "ADMIN"));
//            employeeRepository.addEmployee(new Employee("Employee",  dateFormat.parse("01/01/1950"), "employee@this.app", 919017113,  dateFormat.parse("01/01/1968"), "246597858", "Porto", "12345678", "EMPLOYEE"));
//            employeeRepository.addEmployee(new Employee("Collaborator",  dateFormat.parse("01/01/1950"), "colab@this.app", 919017113,  dateFormat.parse("01/01/1968"), "246597860", "Porto", "12345678", "COLLABORATOR"));
//            employeeRepository.addEmployee(new Employee("Human Resources Manager",  dateFormat.parse("01/01/1950"), "HRM@this.app", 919017113,  dateFormat.parse("01/01/1968"), "246597857", "Porto", "12345678", "HRM"));
//            employeeRepository.addEmployee(new Employee("Vehicle and Equipment Fleet Manager", dateFormat.parse("01/01/1950"), "VFM@this.app", 919017113,  dateFormat.parse("01/01/1968"), "246597856", "Porto", "12345678", "VFM"));
//            employeeRepository.addEmployee(new Employee("Green Space Manager",  dateFormat.parse("01/01/1950"), "GSM@this.app", 919017113,  dateFormat.parse("01/01/1968"), "246597855", "Porto", "12345678", "GSM"));
//
//        } catch (ParseException e) {
//            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, "Incorrect date Format", e);
//        }
    }

    private void addSkills() {
        SkillRepository skillRepository = Repositories.getInstance().getSkillRepository();
        skillRepository.getSeralizatedData();
//        skillRepository.addSkillRep(new Skill("Prune", "Prune trees, shrubs and plants"));
//        skillRepository.addSkillRep(new Skill("Light Vehicle license", "Driver able to drive vehicles up to 1500 tare weight"));
//        skillRepository.addSkillRep(new Skill("Heavy Vehicle license", "Driver able to drive vehicles up to 3500 tare weight"));
    }

    private void addJobs() {
        JobRepository jobRepository = Repositories.getInstance().getJobRepository();
        jobRepository.getSeralizatedData();
//        jobRepository.addJobRep(new Job("Pruner", "Who can all types of plants"));
//        jobRepository.addJobRep(new Job("Light Vehicle Driver", "Who drives vehicles up to 1500 tare weight"));
//        jobRepository.addJobRep(new Job("Heavy Vehicle Driver", "Who drives vehicles up to 3500 tare weight"));

    }

    private void addTeams() {
        TeamRepository teamRepository = Repositories.getInstance().getTeamRepository();
        teamRepository.getSeralizatedData();

//        EmployeeRepository employeeRepository = Repositories.getInstance().getEmployeeRepository();
//
//        // Get employees to form teams
//        List<Employee> allEmployees = employeeRepository.getEmployees();
//
//        // Create a sample team with some employees
//        List<Employee> teamMembers = new ArrayList<>();
//        teamMembers.add(allEmployees.get(0)); // Add first employee
//        teamMembers.add(allEmployees.get(1)); // Add second employee
//
//        Team team = new Team(teamMembers);
//
//        // Add the team to the repository
//        Optional<Team> addedTeam = teamRepository.add(team);
    }

    private void addTasks() {
        TaskRepository taskRepository = Repositories.getInstance().getTaskRepository();
        taskRepository.getSeralizatedData();
    }

    private void addVehicles() throws ParseException {
        VehicleRepository vehicleRepository = Repositories.getInstance().getVehicleRepository();
        vehicleRepository.getSeralizatedData();

//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//        Vehicle vehicle = new Vehicle("80-PH-70", "BMW", "320D", "Passageiros", 1900, 2000, 19000, dateFormat.parse("08/03/2011"), dateFormat.parse("01/09/2020"), 20000);
//        vehicleRepository.add(vehicle);
//
//        Vehicle vehicleDrivenWithin = new Vehicle("81-PH-70", "BMW", "320D", "Passageiros", 1900, 2000, 19000, dateFormat.parse("08/03/2011"), dateFormat.parse("01/09/2020"), 20000);
//        vehicleRepository.add(vehicleDrivenWithin);
//        vehicleDrivenWithin.setCurrentKm(38500.0);
//        vehicleRepository.updateVehicle(vehicleDrivenWithin);
//
//        Vehicle vehicleDrivenAbove = new Vehicle("82-PH-70", "BMW", "320D", "Passageiros", 1900, 2000, 19000, dateFormat.parse("08/03/2011"), dateFormat.parse("01/09/2020"), 20000);
//        vehicleRepository.add(vehicleDrivenAbove);
//        vehicleDrivenAbove.setCurrentKm(42000.0);
//        vehicleRepository.updateVehicle(vehicleDrivenAbove);
    }
    private void addGreenSpaces() {
        GreenSpaceRepository greenSpaceRepository = Repositories.getInstance().getGreenSpaceRepository();
        greenSpaceRepository.getSeralizatedData();
//        greenSpaceRepository.addGreenSpace(new GreenSpace("Parque da Cidade do Porto", "Porto", 99.6, "Large-sized park", "Green Space Manager"));
//        greenSpaceRepository.addGreenSpace(new GreenSpace("Avioso", "Maia", 43.4, "Medium-sized park", "Main Administrator"));
//        greenSpaceRepository.addGreenSpace(new GreenSpace("Rabada", "Santo Tirso", 15.3, "Garden", "Green Space Manager"));
//        greenSpaceRepository.addGreenSpace(new GreenSpace("Bela Vista", "Lisboa", 259.6, "Large-sized park", "Main Administrator"));
//        greenSpaceRepository.addGreenSpace(new GreenSpace("Azibo", "Bragança", 493.2, "Large-sized park", "Green Space Manager"));
//        greenSpaceRepository.addGreenSpace(new GreenSpace("Oriental", "Campanhã", 35.9, "Medium-sized park", "Green Space Manager"));
//        greenSpaceRepository.addGreenSpace(new GreenSpace("Parque Eduardo VII", "Lisboa", 26.0, "Large-sized park", "Green Space Manager"));
//        greenSpaceRepository.addGreenSpace(new GreenSpace("Parque da Cidade do Porto", "Porto", 83.0, "Large-sized park", "Main Administrator"));
//        greenSpaceRepository.addGreenSpace(new GreenSpace("Parque Florestal de Monsanto", "Lisboa", 900.0, "Large-sized park", "Main Administrator"));
    }

    private void addOrganization() {
        //TODO: add organizations bootstrap here
        //get organization repository
        OrganizationRepository organizationRepository = Repositories.getInstance().getOrganizationRepository();
        Organization organization = new Organization("This Company");
        organizationRepository.add(organization);
    }

    private void addUsers() {
        //TODO: add Authentication users here: should be created for each user in the organization
        AuthenticationRepository authenticationRepository = Repositories.getInstance().getAuthenticationRepository();
        authenticationRepository.addUserRole(AuthenticationController.ROLE_ADMIN, AuthenticationController.ROLE_ADMIN);
        authenticationRepository.addUserRole(AuthenticationController.ROLE_EMPLOYEE,
                AuthenticationController.ROLE_EMPLOYEE);
        authenticationRepository.addUserRole(AuthenticationController.ROLE_HRM,
                AuthenticationController.ROLE_HRM);
        authenticationRepository.addUserRole(AuthenticationController.ROLE_VFM,
                AuthenticationController.ROLE_VFM);
        authenticationRepository.addUserRole(AuthenticationController.ROLE_COLLABORATOR,
                AuthenticationController.ROLE_COLLABORATOR);
        authenticationRepository.addUserRole(AuthenticationController.ROLE_GSM,
                AuthenticationController.ROLE_GSM);


        authenticationRepository.addUserWithRole("Main Administrator", "admin@this.app", "admin",
                AuthenticationController.ROLE_ADMIN);
        authenticationRepository.addUserWithRole("Human Resources Manager", "hrm@this.app", "hrm",
                AuthenticationController.ROLE_HRM);
        authenticationRepository.addUserWithRole("Vehicle and Equipment Fleet Manager", "vfm@this.app", "vfm",
                AuthenticationController.ROLE_VFM);
        authenticationRepository.addUserWithRole("Green Space Manager", "gsm@this.app", "gsm",
                AuthenticationController.ROLE_GSM);


        authenticationRepository.addUserWithRole("Employee", "employee@this.app", "pwd",
                AuthenticationController.ROLE_EMPLOYEE);
        authenticationRepository.addUserWithRole("Collaborator", "colab@this.app", "colab",
                AuthenticationController.ROLE_COLLABORATOR);
    }

}