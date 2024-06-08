package pt.ipp.isep.dei.g312.repository;

import pt.ipp.isep.dei.g312.domain.Employee;
import pt.ipp.isep.dei.g312.domain.Skill;
import pt.ipp.isep.dei.g312.domain.Team;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static pt.ipp.isep.dei.g312.ui.console.utils.Utils.showList;

public class TeamRepository implements Serializable {
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

    public List<Team> getAllTeams() {
        return new ArrayList<>(teamList);
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


    /**
     * Serializes the TeamRepository object to a file.
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
     * Deserializes the TeamRepository object from a file and adds the skills to the current repository.
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
            TeamRepository teamRepository = (TeamRepository) in.readObject();

            for (Team team :
                    teamRepository.getAllTeams()) {
                this.add(team);
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
