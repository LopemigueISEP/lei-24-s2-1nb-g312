package pt.ipp.isep.dei.g312.repository;


import pt.ipp.isep.dei.g312.domain.Employee;
import pt.ipp.isep.dei.g312.domain.Skill;


import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

import java.util.Optional;




/**
 * This class represents a repository for managing Employee objects. It provides methods
 * for adding, retrieving, updating, and searching for employees.
 */
public class EmployeeRepository implements Serializable {
    /**
     * List to store all registered employees.
     */
    private List<Employee> employeeList = new ArrayList<>();
    /**
     * Retrieves an `Optional` containing the employee with the specified taxpayer number.
     * If no employee is found, an empty `Optional` is returned.
     * @param taxpayerNumber The taxpayer number of the employee to search for.
     * @return An `Optional` containing the employee or empty if not found.
     */
    public Optional<Employee> getEmployees(String taxpayerNumber) {
        for (Employee employee : employeeList) {
            if (employee.getTaxpayerNumber().equals(taxpayerNumber)) {
                return Optional.of(employee);
            }
        }
        return Optional.empty();
    }

    /**
     * Returns a copy of the list containing all registered employees.
     * Modifying the returned list will not affect the internal employee list.
     * @return A copy of the employee list.
     */
    public List<Employee> getEmployees() {
        return new ArrayList<>(employeeList) ;
    }

    /**
     * Attempts to add a new employee to the repository.
     * @param employee The employee object to be added.
     * @return An `Optional` containing the added employee if successful, or empty if the operation fails
     */
    public Optional<Employee> addEmployee(Employee employee) {
        Optional<Employee> newEmployee = Optional.empty();
        boolean operationSuccess = false;

        if (validateEmployee(employee)) {
            newEmployee = Optional.of(employee.clone());
            operationSuccess = employeeList.add(newEmployee.get());
        }
        if (!operationSuccess) {
            newEmployee = Optional.empty();
        }
        return newEmployee;
    }
    /**
     * Validates if the provided employee can be added to the repository.
     * Currently, it checks for duplicate employees based on object equality.
     * @param employee The employee to be validated.
     * @return True if the employee is valid to add, false otherwise.
     */
    public boolean validateEmployee(Employee employee) {
        boolean isValid = !employeeList.contains(employee);

        return isValid;
    }

    /**
     * Prints information about all registered employees, including their names and jobs.
     */
    public void printRegisteredEmployees() {

        for (Employee emp : employeeList) {
            System.out.printf("%25s - Job: %s\n", emp.getName(), emp.getJob());
        }
        System.out.println("---------------------------------------------------");
    }
    /**
     * Prints a formatted list of all registered employees and their associated skills.
     * If the employee has no skills, "No skills assigned" is displayed.
     * Otherwise, a comma-separated list of the employee's skill names is printed.
     */
    public void printRegisteredEmployeesAndHisSkills() {

        for (Employee emp : employeeList) {
            List<Skill> skills = getEmployeeSkills(emp);

            System.out.printf("%25s - ", emp.getName());

            if (skills.isEmpty()) {
                System.out.print("No skills assigned");
            } else {
                System.out.print("Skills: ");
                int index = 0;
                for (Skill skl : skills) {
                    if (index == 0) {
                        System.out.printf("%s", skl.getSkillName());
                        index++;
                    } else {
                        System.out.printf("; %s", skl.getSkillName());
                    }
                }
            }
            System.out.println();
        }
        System.out.println("----------------------------------------------------------");
    }
    /**
     * Attempts to add a list of skills to an existing employee's profile by finding
     * the employee in the repository and then updates their skills
     * @param collaborator The employee object whose skills should be updated.
     * @param skillsToAdd The list of skills to be added to the employee's profile.

     * @return An `Optional` containing the updated employee if successful, or empty if the collaborator
     *         is not found.
     */
    public Optional<Employee> addSkillsToCollaboratorProfile(Employee collaborator, List<Skill> skillsToAdd) {
        // Attempt to find the collaborator in the employee list
        Optional<Employee> updatedEmployee = findEmployee(collaborator);

        // Update skills if collaborator found
        if (updatedEmployee.isPresent()) {
            Employee employeeToUpdate = updatedEmployee.get();
            // Update collaborator's skills (specific logic depends on your data model)
            employeeToUpdate.addSkills(skillsToAdd); // Assuming an addSkills method in Employee
            return Optional.of(employeeToUpdate); // Return updated employee
        } else {
            // Collaborator not found, return empty optional
            return Optional.empty();
        }
    }

    /**
     * Method to find an employee by any attribute.
     * Currently, it searches based on object equality with the provided string.
     *
     * @param collaborator A string representing the employee attribute to search for.
     * @return An `Optional` containing the employee if found, or empty if not found.
     */
    public Optional<Employee> findEmployee(Employee collaborator) {
        for (Employee employee : employeeList) {

            if (employee.equals(collaborator)) { //compares taxpayernumber
                return Optional.of(employee);
            }
        }
        return Optional.empty();
    }

    /**
     * This method iterates through the employee list and searches for an employee whose job title
     * matches the provided string. If a match is found, the employee object is returned. Otherwise,
     * null is returned.
     *
     * @param rl The job title of the employee to search for.
     * @return The employee object with the matching job title, or null if not found.
     * @deprecated This method does not use `Optional` for null safety. Consider using `findEmployee`
     *             with a search criteria based on job title instead.
     */

    public Employee getEmployFromJob(String rl) {
        try {
            for (Employee emp : employeeList) {
                if (rl.equals(emp.getJob())) {
                    return emp;
                }
            }
        }catch (Exception e){
            System.out.println("Error in getting employee from the current user Job");
        }
     return null;
    }

    /**
     * Retrieves a copy of the list containing the employee's associated skills..
     * @param employee The employee whose skills should be retrieved.
     * @return A copy of the employee's skill list.
     */

    public List<Skill> getEmployeeSkills(Employee employee){

        return new ArrayList<>(employee.getEmployeeSkillList());
    }
    /**
     * Retrieves a list of employees sorted by the number of skills they possess.
     *
     * @return A list of employees sorted in descending order based on the number of skills they possess.
     */
    public List<Employee> getEmployeeSortedByNumberOfSkill() {
        List<Employee> employeesSortedSkills = new ArrayList<>(employeeList);
        employeesSortedSkills.sort(Collections.reverseOrder());
        return employeesSortedSkills;

    }

    public Optional<Employee> getEmployeeByEmail(String userEmail) {
        Optional<Employee> responsible=Optional.empty();
        for (Employee e :
                employeeList) {
            if (e.getEmail().equals(userEmail)){
                responsible=Optional.of(e);
            }
        }
        return responsible;
    }


    /**
     * Serializes the EmployeeRepository object to a file.
     */
    public void serializateData() {

        String filename = this.getClass().getSimpleName()+".bin";

        // Serialization
        try {

            // Saving of object in a file
            FileOutputStream file = new FileOutputStream
                    (filename);
            ObjectOutputStream out = new ObjectOutputStream
                    (file);

            // Method for serialization of object
            out.writeObject(this);


            out.close();
            file.close();

            System.out.println(this.getClass().getSimpleName()+" Has Been Serialized successfully! ");
        } catch (FileNotFoundException ex) {
            System.out.println("IOException is caught");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Deserializes the EmployeeRepository object from a file and adds the skills to the current repository.
     */
    public void getSeralizatedData() {
        String filename = this.getClass().getSimpleName()+".bin";

        try {

            // Reading the object from a file
            FileInputStream file = new FileInputStream
                    (filename);
            ObjectInputStream in = new ObjectInputStream
                    (file);

            // Method for deserialization of object
            EmployeeRepository employeeRepository = (EmployeeRepository) in.readObject();

            for (Employee employee :
                    employeeRepository.getEmployees()) {
                this.addEmployee(employee);
            }

            in.close();
            file.close();

        }

        catch (IOException ex) {
            System.out.println("IOException is caught");
        }

        catch (ClassNotFoundException ex) {
            System.out.println("ClassNotFoundException" +
                    " is caught");
        }
    }
}