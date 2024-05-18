package pt.ipp.isep.dei.g312.application.controller;

import pt.ipp.isep.dei.g312.domain.Employee;
import pt.ipp.isep.dei.g312.domain.Skill;
import pt.ipp.isep.dei.g312.domain.Team;
import pt.ipp.isep.dei.g312.repository.EmployeeRepository;
import pt.ipp.isep.dei.g312.repository.Repositories;
import pt.ipp.isep.dei.g312.repository.SkillRepository;
import pt.ipp.isep.dei.g312.repository.TeamRepository;
import pt.ipp.isep.dei.g312.ui.console.utils.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GenerateTeamController {

    private EmployeeRepository employeeRepository;
    private SkillRepository skillRepository;
    private TeamRepository teamRepository;



    public GenerateTeamController(){
        getEmployeeRepository();
        getSkillRepository();
        getTeamRepository();
    }


    private EmployeeRepository getEmployeeRepository() {
        if (employeeRepository == null) {
            Repositories repositories = Repositories.getInstance();
            employeeRepository = repositories.getEmployeeRepository();

        }
        return employeeRepository;

    }

    private SkillRepository getSkillRepository() {
        if (skillRepository == null) {
            Repositories repositories = Repositories.getInstance();
            skillRepository = repositories.getSkillRepository();

        }
        return skillRepository;

    }

    private TeamRepository getTeamRepository() {
        if (teamRepository == null) {
            Repositories repositories = Repositories.getInstance();
            teamRepository = repositories.getTeamRepository();

        }
        return teamRepository;
    }
    public Optional<List<Skill>> getSkills(){
        Optional<List<Skill>> skills = Optional.empty();
        skills=skillRepository.getSkills();

        return skills;

    }

    /**
     * Attempts to assemble a team of employees based on the specified skill requirements
     * and constraints on the minimum and maximum number of team members.
     * <p>
     * This method searches for employees who can fulfill the required skills and tries to form
     * a team that meets the specified size constraints. It processes each suitable employee
     * and adjusts the remaining skill needs accordingly. The team is considered valid if it
     * contains at least the minimum number of employees required and does not exceed the maximum
     * limit, while also covering all the specified skills.
     *
     * @param minNumberOfEmployees The minimum number of employees required in the team.
     * @param maxNumberOfEmployees The maximum number of employees that the team can have.
     * @param skillSet A list of {@link Skill} objects representing the skills required for the team.
     * @param teamEmployees An instance of {@link Team} where the selected employees will be added.
     * @return A {@link Result} object containing the outcome of the team generation process.
     *         The Result includes a success flag and a message indicating the outcome. If the
     *         team cannot be formed due to insufficient employees, exceeding size limits, or
     *         unmet skill requirements, the result will indicate failure with an appropriate message.
     *         If no employees are available to form a team, this will also result in a failure message.
     */
    public Result getTeamWithSkillSet(int minNumberOfEmployees,int maxNumberOfEmployees, List<Skill> skillSet, Team teamEmployees) {
        Optional<List<Employee>> availableEmployees = Optional.empty();
        List<Skill> skillSetNeeded = new ArrayList<>(skillSet);
        availableEmployees=Optional.of(employeeRepository.getEmployeeSortedByNumberOfSkill());
        if (availableEmployees.isPresent()){
            for (Employee employee :
                    availableEmployees.get()) {

                if (employee.addEmployeeIfSuitable(skillSetNeeded,teamEmployees.getTeamEmployees())){
                    teamEmployees.add(employee);

                    if (teamEmployees.size()>=minNumberOfEmployees){

                        for (Employee teamEmployee:
                             teamEmployees.getTeamEmployees()) {
                            for (Skill teamEmployeeSkill :
                                    teamEmployee.getSkills()) {
                                skillSetNeeded.remove(teamEmployeeSkill);
                                }
                        }

                    }
                }
            }
            if (teamEmployees.isEmpty() || teamEmployees.size()<minNumberOfEmployees){
                return new Result("There are no Collaborators available to generate a team",true);
            } else if (teamEmployees.size()>maxNumberOfEmployees) {
                return new Result("There are no possible team with the number of maximum Collaborators", true);
            } else if (!skillSetNeeded.isEmpty()){
                return new Result("There are no Collaborators with all the skills you need available to generate a team",true);
            }else {
                return new Result("", false);
            }
        }
        else{
            return new Result("There are no employees available to generate a team",true);
        }

    }
    /**
     * Creates a new team based on the given team details and adds it to the repository.
     * This method copies the employees from the provided team object into a new team instance,
     * and then attempts to add this new team to the repository. The success of the operation
     * depends on the repository's behavior and constraints.
     *
     * @param teamEmployees The {@link Team} object containing the list of employees to be included
     *                      in the new team. The team should have its employees properly initialized.
     *                      This parameter should not be null.
     * @return An {@link Optional} containing the newly created team if the operation was successful,
     *         or an empty Optional if the addition to the repository failed for any reason.
     */
    public Optional<Team> createTeam(Team teamEmployees) {
        Optional<Team> newTeam = Optional.empty();
        Team team = new Team(teamEmployees.getTeamEmployees());

        newTeam=teamRepository.add(team);

        return newTeam;
    }

    /**
     * Retrieves a list of employees who are not currently part of the specified team.
     * This method queries all available employees and filters out those who are already
     * members of the team provided as a parameter.
     *
     * @param teamList The team whose non-members are to be determined. This parameter
     *                 should not be null.
     * @return A list of {@link Employee} objects representing employees not in the specified
     *         team. The list will be empty if all employees are part of the team or if there
     *         are no employees to check.
     */
    public List<Employee> getEmployeesNotInTeam(Team teamList){
        Optional<List<Employee>> employees = Optional.empty();
        List<Employee> employeesAdd= new ArrayList<>();
        employees=Optional.of(employeeRepository.getEmployees());
        for (Employee employee :
                employees.get()) {
            if (employee.validateAddToTeam(teamList.getTeamEmployees())){
                employeesAdd.add(employee);
            }
        }

        return employeesAdd;
    }

    public void printAllTeams(){
        teamRepository.printRegisteredTeams();
    }


    public void addTeamRemoveToAdd(Team teamEmployees, List<Employee> employeesToAdd, List<String> employeesToAddNames, int currentIndex) {
        teamEmployees.add(employeesToAdd.get(currentIndex));
        employeesToAdd.remove(currentIndex);
        employeesToAddNames.remove(currentIndex);
    }
}
