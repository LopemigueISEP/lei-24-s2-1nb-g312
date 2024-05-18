package pt.ipp.isep.dei.g312.repository;

import pt.ipp.isep.dei.g312.domain.Employee;
import pt.ipp.isep.dei.g312.domain.Skill;
import pt.ipp.isep.dei.g312.domain.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static pt.ipp.isep.dei.g312.ui.console.utils.Utils.showList;

public class TeamRepository {
    public List<Team> teamList = new ArrayList<>();

    public Optional<Team> add(Team team) {
        Optional<Team> newTeam = Optional.empty();
        boolean operationSuccess = false;

        if (validateTeam(team)) {
            newTeam = Optional.of(team.clone());
            operationSuccess = teamList.add(newTeam.get());
        }
        if (!operationSuccess) {
            newTeam = Optional.empty();
        }
        return newTeam;
    }

    private boolean validateTeam(Team team) {

        return !teamList.contains(team);
    }

    public void printRegisteredTeams() {
        int index = 1;
        for (Team team : teamList) {
            int indexEmployee = 0;
            System.out.print(index + " - ");
            for (Employee employee :
                    team.getTeamEmployees()) {

                List<Skill> skills = employee.getSkills();
                if (indexEmployee == 0) {
                    System.out.printf("%s - ", employee.getName());
                    indexEmployee++;
                } else {
                    System.out.printf(" | %s - ", employee.getName());
                }


                if (skills.isEmpty()) {
                    System.out.print("No skills assigned");
                } else {
                    System.out.print("Skills: ");
                    int indexSkills = 0;
                    for (Skill skl : skills) {
                        if (indexSkills == 0) {
                            System.out.printf("%s", skl.getSkillName());
                            indexSkills++;
                        } else {
                            System.out.printf("; %s", skl.getSkillName());
                        }
                    }
                }
            }
            index++;
            System.out.println();

        }
        System.out.println("----------------------------------------------------------");
    }
}
