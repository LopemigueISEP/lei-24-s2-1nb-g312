package pt.ipp.isep.dei.g312.domain;



import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
/**
 * This class represents an organization and contains details such as its VAT number,
 * name, website, phone number, email, and a list of employees.
 */
public class Organization {
    private final String vatNumber;
    private final List<Employee> employees;
    private String name;
    private String website;
    private String phone;
    private String email;

    /**
     * This method is the constructor of the organization.
     *
     * @param vatNumber The vat number of the organization. This is the identity of the organization, therefore it
     *                  cannot be changed.
     */
    public Organization(String vatNumber) {
        this.vatNumber = vatNumber;
        employees = new ArrayList<>();
//        tasks = new ArrayList<>();
    }

    /**
     * This method checks if an employee works for the organization.
     *
     * @param employee The employee to be checked.
     * @return True if the employee works for the organization.
     */
    public boolean employees(Employee employee) {
        return employees.contains(employee);
    }

    /**
     * This methos checks if the organization has an employee with the given email.
     *
     * @param email The email to be checked.
     * @return True if the organization has an employee with the given email.
     */
    public boolean anyEmployeeHasEmail(String email) {
        boolean result = false;
        for (Employee employee : employees) {
            if (employee.hasEmail(email)) {
                result = true;
            }
        }
        return result;
    }
    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param o The reference object with which to compare.
     * @return True if this object is the same as the obj argument, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Organization)) {
            return false;
        }
        Organization that = (Organization) o;
        return vatNumber.equals(that.vatNumber);
    }
    /**
     * Returns a hash code value for the object.
     *
     * @return A hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(vatNumber);
    }

    /**
     * Adds an employee to the organization.
     *
     * @param employee The employee to be added.
     * @return True if the employee is successfully added, false otherwise.
     */
    public boolean addEmployee(Employee employee) {
        boolean success = false;
        if (validateEmployee(employee)) {
            success = employees.add(employee);
        }
        return success;
    }
    /**
     * Validates an employee.
     *
     * @param employee The employee to be validated.
     * @return True if the employee is valid, false otherwise.
     */
    private boolean validateEmployee(Employee employee) {
        return employeesDoNotContain(employee);
    }
    /**
     * Checks if the employees list does not contain the specified employee.
     *
     * @param employee The employee to be checked.
     * @return True if the employees list does not contain the employee, false otherwise.
     */
    private boolean employeesDoNotContain(Employee employee) {
        return !employees.contains(employee);
    }

    /**
     * Creates a deep copy of the current Organization object.
     *
     * @return A new Organization object that is a copy of the current instance.
     */
    public Organization clone() {
        Organization clone = new Organization(this.vatNumber);
        clone.name = (this.name);
        clone.website = (this.website);
        clone.phone = (this.phone);
        clone.email = (this.email);

        for (Employee in : this.employees) {
            clone.employees.add(in.clone());
        }

        return clone;
    }
}