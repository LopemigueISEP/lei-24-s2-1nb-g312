package pt.ipp.isep.dei.g312.ui.console;

import pt.ipp.isep.dei.g312.application.controller.GenerateTeamController;
import pt.ipp.isep.dei.g312.domain.Employee;
import pt.ipp.isep.dei.g312.domain.Skill;
import pt.ipp.isep.dei.g312.domain.Team;
import pt.ipp.isep.dei.g312.ui.console.utils.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static pt.ipp.isep.dei.g312.ui.console.utils.Utils.*;

public class GenerateTeamUI implements Runnable {

    private final String ACCEPT = "Accept";
    private final String ACCEPT_MODIFY = "Accept & Modify";
    private final String DECLINE = "Decline";
    private final String MODIFY_ADD = "Add Collaborator(s)";
    private final String MODIFY_REMOVE = "Remove Collaborator(s)";

    private GenerateTeamController controller;
    private int minNumberOfEmployees;
    private int maxNumberOfEmployees;
    private Team teamEmployees;
    private List<Skill> skillSet;

    private List<String> optionMenu;
    private List<String> optionModifyMenu;

    public GenerateTeamUI() {
        this.skillSet = new ArrayList<>();
        setNewOptionMenu();
        setNewOptionModifyMenu();
        controller = new GenerateTeamController();
    }


    @Override
    public void run() {

        this.teamEmployees = new Team();
        this.skillSet.clear();
        boolean hasErrors = false;

        System.out.println("\n\n--- Generate Team ------------------------");

        Result result = new Result();
        result = requestData();
        if (result.hasError) {
            raiseAlertMessage(result.message);
        } else {
            result = getTeamWithSkillSet(this.minNumberOfEmployees, this.maxNumberOfEmployees, this.skillSet, this.teamEmployees);
            if (result.hasError) {
                raiseAlertMessage(result.message);
            } else {
                showChosenSkills();
                showChosenEmployees();
                result =dataConfirmation();
                if (result.hasError){
                    raiseAlertMessage(result.message);
                }
                else {
                    submitData();
                }

            }
        }

    }

    private Result dataConfirmation() {
        int currentIndex;

        do {

            currentIndex = showAndSelectIndex(optionMenu, "\n\n--- Data Confirmation ------------------------",false);
            if (currentIndex > optionMenu.size()) {
                raiseInvalidInput();
            }
        } while (currentIndex > optionMenu.size());

        switch (optionMenu.get(currentIndex)) {
            case ACCEPT:
                return new Result("",false);
            case ACCEPT_MODIFY:
                return modifySubmitData();
            case DECLINE:
                return new Result("Operation aborted by the user",true);
            default:
                return new Result("Operation aborted by the user",true);
        }
    }

    private Result getTeamWithSkillSet(int minNumberOfEmployees, int maxNumberOfEmployees, List<Skill> skillSet, Team teamEmployees) {
        return controller.getTeamWithSkillSet(minNumberOfEmployees,maxNumberOfEmployees,skillSet,teamEmployees);
    }

    private Result modifySubmitData() {
        int currentIndex;

        do {

            currentIndex = showAndSelectIndex(optionModifyMenu, "\n\n--- Data Modify ------------------------",false);
            if (currentIndex > optionModifyMenu.size()) {
                raiseInvalidInput();
            }
        } while (currentIndex > optionModifyMenu.size());

        switch (optionModifyMenu.get(currentIndex)) {
            case MODIFY_ADD:
                return modifyAddData();
            case MODIFY_REMOVE:
                return modifyRemoveData();

            default:
                return new Result("No valid action selected", false);
        }
    }


    private Result modifyAddData() {
        List<Employee> employeesToAdd = new ArrayList<>();
        return addTeamEmployees(employeesToAdd);
    }
    private Result modifyRemoveData() {
        List<Employee> employeesToRemove = new ArrayList<>();
        return removeTeamEmployees(employeesToRemove);
    }

    private Result addTeamEmployees(List<Employee> employeesToAdd) {

        employeesToAdd = controller.getEmployeesNotInTeam(teamEmployees);
        if (employeesToAdd.isEmpty()){
            return new Result("There are no Collaborators available to add",true);
        }
        List<String> employeesToAddNames = new ArrayList<>();
        showAndSelectCollaboratorsNotInTeam(employeesToAdd,employeesToAddNames);
        return  new Result("",false);
    }

    private Result removeTeamEmployees(List<Employee> employeesToRemove) {
        employeesToRemove=teamEmployees.getTeamEmployees();
        showAndSelectCollaboratorsToRemove(employeesToRemove);
        if (teamEmployees.isEmpty()){
            return  new Result("All Collaborators have been removed",false);
        }
        teamEmployees.setTeamEmployees(employeesToRemove);
        return  new Result("",false);
    }

    private void showAndSelectCollaboratorsToRemove(List<Employee> employeesToRemove) {
        int currentIndex;
        do {

            currentIndex = showAndSelectIndexDone(employeesToRemove, "\n--- Remove Collaborators from team  ------------------------");
            if (currentIndex > employeesToRemove.size() || currentIndex < 0) {
                raiseInvalidInput();
            } else {
                employeesToRemove.remove(employeesToRemove.get(currentIndex));
            }
        } while (currentIndex !=-1);
    }

    private void showAndSelectCollaboratorsNotInTeam(List<Employee> employeesToAdd, List<String> employeesToAddNames) {
        for (Employee employee :
                employeesToAdd) {
            StringBuilder emplyoeeAddName;
            int skillsAssigned = 0;
            emplyoeeAddName = new StringBuilder(employee.getName() + "- Skills: ");
            for (Skill employeeSkill :
                    employee.getSkills()) {
                if (skillsAssigned==0){
                    emplyoeeAddName.append(employeeSkill.getSkillName());
                }
                else {
                    emplyoeeAddName.append("; "+employeeSkill.getSkillName());
                }
                skillsAssigned++;
            }
            if (skillsAssigned==0){
                emplyoeeAddName.append("- No skills assigned");
            }
            employeesToAddNames.add(emplyoeeAddName.toString());

        }
        int currentIndex;

        do {

            currentIndex = showAndSelectIndexDone(employeesToAddNames, "\n--- All Collaborators not in team ------------------------");
            if (currentIndex > employeesToAddNames.size() || currentIndex < 0) {
                raiseInvalidInput();
            } else {
                controller.addTeamRemoveToAdd(teamEmployees,employeesToAdd,employeesToAddNames,currentIndex);
            }
        } while (currentIndex !=-1);
    }

    private void submitData() {
        Optional<Team> teamEmployee = getController().createTeam(this.teamEmployees);
        if (teamEmployee.isPresent()) {
            System.out.println("Team successfully created!");
        }
    }

    private Result requestData() {
        this.minNumberOfEmployees = requestMinNumberOfCollaborators();
        this.maxNumberOfEmployees = requestMaxNumberOfCollaborators();
        this.skillSet = requestSkillSet();
        if (this.skillSet.isEmpty()) {
            return new Result("No Skills Selected", true);
        }
        return new Result("", false);
    }

    private List<Skill> requestSkillSet() {
        List<Skill> newSkillSet = new ArrayList<>();
        List<Skill> skillSetRepo = new ArrayList<>();
        List<String> skillSetNames = new ArrayList<>();
        Optional<List<Skill>> existingSkillSet = Optional.empty();

        existingSkillSet = controller.getSkills();
        if (existingSkillSet.isPresent()) {
            skillSetRepo = existingSkillSet.get();
        }
        int currentIndex = 0;
        for (Skill skill :
                skillSetRepo) {
            skillSetNames.add(skill.getSkillName());
        }


        while (currentIndex != -1 && !skillSetRepo.isEmpty()) {
            currentIndex = showAndSelectIndexDone(skillSetNames, "\n\n--- Skill Set ------------------------");
            if (currentIndex != -1 && currentIndex<=skillSetRepo.size()) {
                newSkillSet.add(skillSetRepo.get(currentIndex));
                skillSetRepo.remove(currentIndex);
                skillSetNames.remove(currentIndex);
            }
            else {
                raiseInvalidInput();
            }
        }

        if (skillSetRepo.isEmpty()) {
            System.out.println("All skills selected!");
        }

        return newSkillSet;

    }

    private int requestMinNumberOfCollaborators() {
        return readIntegerFromConsole("Number of Minium Collaborators: ");
    }

    private int requestMaxNumberOfCollaborators() {
        int output;
        do {
            output = readIntegerFromConsole("Number of Maximum Collaborators: ");
            if (output < minNumberOfEmployees) {
                raiseInvalidInput();
            }
        } while (output < minNumberOfEmployees);
        return output;
    }

    public GenerateTeamController getController() {
        return controller;
    }

    private void setNewOptionMenu() {
        optionMenu = new ArrayList<>();
        optionMenu.add(ACCEPT);
        optionMenu.add(ACCEPT_MODIFY);
        optionMenu.add(DECLINE);

    }

    private void setNewOptionModifyMenu() {
        optionModifyMenu = new ArrayList<>();
        optionModifyMenu.add(MODIFY_ADD);
        optionModifyMenu.add(MODIFY_REMOVE);

    }

    private void showChosenSkills() {
        List<String> chosenSkills = new ArrayList<>();
        for (Skill skill :
                skillSet) {
            chosenSkills.add(skill.getSkillName());
        }
        showOnlyList(chosenSkills, "\nSkill(s) Selected: ");
    }

    private void showChosenEmployees() {
        List<String> chosenEmployees = new ArrayList<>();
        for (Employee employee :
                teamEmployees.getTeamEmployees()) {
            chosenEmployees.add(employee.getName());
        }
        showOnlyList(chosenEmployees, "\nCollaborator(s) Selected: ");
    }


}
