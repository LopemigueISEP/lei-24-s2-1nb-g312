package pt.ipp.isep.dei.g312.domain;

import pt.ipp.isep.dei.g312.repository.Repositories;
import pt.ipp.isep.dei.g312.repository.SkillRepository;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

import java.util.*;

/**
 * This class represents an employee (collaborator) in the system. It stores essential information
 * about the employee - fundamental characteristics - and a list of associated skills.
 * The class provides methods for accessing and modifying employee attributes, adding skills, and retrieving
 * a list of skills available to be assigned to collaborators. It also implements methods for cloning
 * and comparing employees.
 */
public class Employee implements Cloneable, Comparable<Employee> {
    private String name;
    private Date birthDate;
    private String email;
    private int phoneNumber;
    private Date admissionDate;
    private String taxpayerNumber;
    private String address;
    private String docNumber;

    private List<Skill> skills;
    private String job;
    //private List<Skill> skillsAdded;


    /**
     * Main constructor for creating an `Employee` object.
     *
     * @param name           The employee's name.
     * @param birthDate      The employee's birthdate.
     * @param email          The employee's email address.
     * @param phoneNumber    The employee's phone number.
     * @param admissionDate  The employee's admission date.
     * @param taxpayerNumber The employee's taxpayer number.
     * @param address        The employee's address.
     * @param docNumber      The employee's ID document number.
     * @param job            The employee's job title.
     */
    public Employee(String name, Date birthDate, String email, int phoneNumber, Date admissionDate, String taxpayerNumber, String address, String docNumber, String job) {

        this.name = name;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
        this.taxpayerNumber = taxpayerNumber;
        this.address = address;
        this.docNumber = docNumber;
        this.admissionDate = admissionDate;
        this.email = email;
        this.job = job;
        this.skills = new ArrayList<>();

    }

    public Employee(String name, Date birthDate, String email, int phoneNumber, Date admissionDate, String taxpayerNumber, String address, String docNumber, String job, List<Skill> skills) {

        this.name = name;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
        this.taxpayerNumber = taxpayerNumber;
        this.address = address;
        this.docNumber = docNumber;
        this.admissionDate = admissionDate;
        this.email = email;
        this.job = job;
        this.skills = new ArrayList<>(skills);

    }

    public Employee(String name, List<Skill> skills) {
        this.name = name;
        this.skills = skills;
    }

    public String getTaxpayerNumber() {
        return taxpayerNumber;
    }

    public void setTaxpayerNumber(String taxpayerNumber) {
        this.name = taxpayerNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    public Date getAdmissionDate() {
        return admissionDate;
    }

    public void setAdmissionDateDate(Date birthDate) {
        this.admissionDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setJob(String job) {
        this.job = String.valueOf(job);
    }

    public String getJob() {
        return job;
    }

    public List<Skill> getEmployeeSkillList() {
        return this.skills;
    }

    /**
     * Creates a clone of the current employee object.
     *
     * @return A new `Employee` object that is a copy of the current instance.
     */
    @Override
    public Employee clone() {
        return new Employee(this.name, this.birthDate, this.email, this.phoneNumber, this.admissionDate, this.taxpayerNumber, this.address, this.docNumber, this.job, this.skills);
    }


    /**
     * Registers a new skill.
     *
     * @param skillName        The name of the skill.
     * @param skillDescription The description of the skill.
     * @param userValidation   True if the user have permissions to register the skill, false otherwise.
     * @return An Optional containing the registered skill, or empty if the registration fails.
     */
    public Optional<Skill> registerSkill(String skillName, String skillDescription, boolean userValidation) {
        boolean validateAddedRepository;
        try {
            if (userValidation) {
                Skill skill = new Skill(skillName, skillDescription);
                validateAddedRepository = Repositories.getInstance().getSkillRepository().addSkillRep(skill);
                if (validateAddedRepository) {
                    return Optional.of(skill);
                } else {
                    return Optional.empty();
                }
            } else {
                System.out.println("This user don't have permissions to register skills");
                return Optional.empty();
            }
        } catch (Exception e) {
            return Optional.empty();
        }
    }


    /**
     * Registers a job with the provided name and description, subject to user validation.
     *
     * @param jobName        The name of the job to register.
     * @param jobDescription The description of the job to register.
     * @param userValidation True if the user's validation was successful, false otherwise.
     * @return An Optional containing the registered job if successful, otherwise empty.
     */
    public Optional<Job> registerJob(String jobName, String jobDescription, boolean userValidation) {
        boolean validateAddedRepository;
        try {
            if (userValidation) {
                Job job = new Job(jobName, jobDescription);
                validateAddedRepository = Repositories.getInstance().getJobRepository().addJobRep(job);

                if (validateAddedRepository) {
                    return Optional.of(job);
                } else {
                    return Optional.empty();
                }
            } else {
                System.out.println("This user don't have permissions to register jobs");
                return Optional.empty();
            }

        } catch (Exception e) {
            return Optional.empty();
        }

    }

    /**
     * This method overrides the default `equals` method of the Object class.
     * It checks for object identity and type equality. If the object is the same instance (`this == o`), it returns true.
     * If the object is not an instance of the `Employee` class, it returns false.
     * Otherwise, it casts the object to `Employee` and compares the email addresses for equality using the `equals` method of the String class.
     *
     * @param o The object to be compared with this Employee object.
     * @return True if the objects are equal (same email address), False otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Employee)) {
            return false;
        }
        Employee employee = (Employee) o;
        return email.equals(employee.email);
    }

    /**
     * Overrides the default {@code hashCode()} implementation to generate a hash code based on the email address.
     *
     * @return The hash code of the email address.
     */
    @Override
    public int hashCode() {
        return Objects.hash(email);
    }


    /**
     * Checks if the provided email address matches the email address associated with this object.
     *
     * @param email The email address to compare.
     * @return True if the provided email address matches the object's email address, false otherwise.
     */
    public boolean hasEmail(String email) {
        return this.email.equals(email);
    }

    /**
     * This method retrieves the list of skills associated with the current Employee object.
     * It interacts with a repository class to access and retrieve the skills.
     *
     * @return A list of Skill objects representing the skills of the current Employee.
     */
    public List<Skill> getSkills() {
        return Repositories.getInstance().getEmployeeRepository().getEmployeeSkills(this);
    }

    /**
     * Adds a list of skills to the employee's skill set.
     *
     * @param skillsToAdd The list of `Skill` objects to be added to the employee's skills.
     */
    public void addSkills(List<Skill> skillsToAdd) {
        skills.addAll(skillsToAdd); // Add all skills from the provided list
    }


    /**
     * Gets skills available to add to a collaborator, considering existing skills.
     *
     * @param skillRepository The repository to access all skills.
     * @return List of skills not yet assigned to the collaborator.
     */
    public List<Skill> getAvailableSkillsToAddToCollaborator(SkillRepository skillRepository) {
        List<Skill> allSkills = skillRepository.getSkills().get();

        List<Skill> availableSkills = new ArrayList<>();

        // Check for existing skills in the selected collaborator
        List<Skill> collaboratorSkills = this.getSkills(); // Assuming you have a getSkills method in Employee
        for (Skill skill : allSkills) {
            boolean skillAlreadyPresent = false;
            for (Skill collaboratorSkill : collaboratorSkills) {
                if (skill.getSkillName().equals(collaboratorSkill.getSkillName())) {
                    skillAlreadyPresent = true;
                    break;
                }
            }
            if (!skillAlreadyPresent) {
                availableSkills.add(skill);
            }
        }

        return availableSkills;
    }

    /**
     * This method overrides the default `toString` method of the Object class.
     * It returns a String representation of the Employee object, likely containing the employee's name.
     *
     * @return A String representation of the Employee object.
     */
    @Override
    public String toString() {

        return this.name;
    }


    /**
     * Attempts to add the employee to a team if they possess the required skills and are not already part of the team.
     *
     * @param skillSetNeeded The list of skills needed for the team.
     * @param teamEmployees  The list of employees already in the team.
     * @return True if the employee is suitable for addition to the team, false otherwise.
     */
    public boolean addEmployeeIfSuitable(List<Skill> skillSetNeeded, List<Employee> teamEmployees) {
        for (Skill employeeSkill :
                this.getSkills()) {
            for (Skill neededSkill :
                    skillSetNeeded) {
                if (neededSkill.equals(employeeSkill) && !(teamEmployees.contains(this))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Validates whether the employee can be added to the team by checking if they are already part of it.
     *
     * @param teamEmployees The list of employees already in the team.
     * @return True if the employee can be added to the team, false otherwise.
     */
    public boolean validateAddToTeam(List<Employee> teamEmployees) {
        for (Employee employee :
                teamEmployees) {
            if (teamEmployees.contains(this)) {
                return false;
            }
        }

        return true;

    }

    /**
     * Registers a new green space if the user has permission to do so.
     *
     * @param name              The name of the green space.
     * @param address           The address of the green space.
     * @param area              The area of the green space.
     * @param typology          The typology of the green space.
     * @param greenSpaceManager The manager of the green space.
     * @param userValidation    A boolean indicating whether the user has permission to register green spaces.
     * @return An Optional containing the registered GreenSpace object if registration is successful and user has permission, otherwise an empty Optional.
     */
    public static Optional<GreenSpace> registerGreenSpace(String name, String address, double area, GreenSpaceTypology typology, String greenSpaceManager, boolean userValidation) {
        try {
            if (userValidation) {
                GreenSpace greenSpace = new GreenSpace(name, address, area, typology, greenSpaceManager);
                Optional<GreenSpace> addedGreenSpace = Repositories.getInstance().getGreenSpaceRepository().addGreenSpace(greenSpace);
                return addedGreenSpace;
            } else {
                System.out.println("This user doesn't have permissions to register green spaces");
                return Optional.empty();
            }
        } catch (Exception e) {
            System.err.println("Error occurred while registering a green space: " + e.getMessage());
            return Optional.empty();
        }
    }

    // method for the employee to register a task for the todolist
    public static Optional<Task> registerTask(GreenSpace GreenSpace, String taskTitle, String taskDescr, TaskUrgency taskUrgency, int expectedDuration, TaskPosition todolist, int taskId) {
        try {

            Task newtask = new Task(GreenSpace, taskTitle, taskDescr, taskUrgency, expectedDuration, todolist,taskId);
            Optional<Task> addedTask = Repositories.getInstance().getTaskRepository().addTask(newtask);
            return addedTask;

        } catch (Exception e) {
            System.out.println("Error occurred while registering a task: " + e.getMessage());
            return Optional.empty();
        }
    }

    // method for the employee to assign a team to a task in the agenda
    public static Optional<Task> assignTeamToTask(Team team, Task task) {
        task.assignTeam(team);
        task.setTaskStatus(TaskStatus.PLANNED);
        return Repositories.getInstance().getTaskRepository().updateTask(task);
    }

    public static Optional<Task> postponedTask(Task task, Date startDate) {
        task.setTaskStartDate(startDate);
        task.setTaskStatus(TaskStatus.POSTPONED);
        task.assignTeam(null);
        task.assignVehicle(null);
        return Repositories.getInstance().getTaskRepository().updateTask(task);
    }


    /**
     * Compares this employee with another based on their skills.
     *
     * @param o The Employee object to compare with.
     * @return A negative integer if this employee has fewer skills than the specified employee,
     * zero if they have the same number of skills, or a positive integer if this employee
     * has more skills than the specified employee.
     */
    @Override
    public int compareTo(Employee o) {
        if (this.skills.size() > o.getSkills().size()) {
            return 1;
        } else if (this.skills.size() < o.getSkills().size()) {
            return -1;
        } else {
            return 0;
        }
    }


}