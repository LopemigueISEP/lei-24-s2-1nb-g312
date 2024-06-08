package pt.ipp.isep.dei.g312.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a team of employees within an organization. This class manages a collection of
 * Employee instances that constitute a team. It provides functionality to add employees,
 * get information about team members, and manage the team's composition.
 * <p>
 * The class is designed to allow straightforward manipulation of team member lists, including
 * adding new members, retrieving the list of current members, and checking team attributes such
 * as size and whether the team is empty.
 */
public class Team implements Serializable {

    private List<Employee> teamEmployees;

    public Team() {
        teamEmployees = new ArrayList<>();
    }

    public Team(List<Employee> teamEmployees) {
        this.teamEmployees = new ArrayList<>(teamEmployees);
    }

    /**
     * Returns a list of employees in the team. Modifications to the returned list will not affect
     * the original team.
     *
     * @return A new list containing all the employees currently in the team.
     */
    public List<Employee> getTeamEmployees() {
        return new ArrayList<>(teamEmployees);
    }

    public void setTeamEmployees(List<Employee> teamEmployees) {
        this.teamEmployees = teamEmployees;
    }

    /**
     * Creates and returns a deep copy of this team.
     *
     * @return A new {@link Team} object with the same employees as this one.
     */
    public Team clone() {
        return new Team(this.teamEmployees);
    }

    /**
     * Adds an employee to the team.
     *
     * @param employee The {@link Employee} to add to the team.
     */
    public void add(Employee employee) {
        this.teamEmployees.add(employee.clone());
    }

    /**
     * Checks if the team is currently empty (i.e., has no members).
     *
     * @return {@code true} if the team has no employees, {@code false} otherwise.
     */
    public boolean isEmpty() {
        return this.teamEmployees.isEmpty();
    }

    /**
     * Returns the number of employees currently in the team.
     *
     * @return The size of the team as an integer.
     */
    public int size() {
        return this.teamEmployees.size();
    }
    /**
     * Adds an employee to the team.
     *
     * @param employee The employee to be added to the team.
     */
    public void addEmployee(Employee employee) {
        this.teamEmployees.add(employee);
    }
    /**
     * Indicates whether some other object is "equal to" this one. This method overrides the default equals method of the Object class.
     *
     * @param obj The reference object with which to compare.
     * @return True if this object is the same as the obj argument; false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
    /**
     * Returns a string representation of the team's composition, consisting of the names of the employees in the team.
     * Since the `toString` method of the Employee class doesn't include skill information, only the names of the employees are included.
     *
     * @return A string representing the composition of the team.
     */
    // because method tostring on employee didn't have skill return, i only implemented the toString of the names of the employees of the team
    @Override
    public String toString() {
        String teamComposition = "";
        for(Employee e : teamEmployees) {
            teamComposition += e.toString() + "; ";
        }
        return teamComposition;
    }
}
