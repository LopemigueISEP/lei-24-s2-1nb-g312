package pt.ipp.isep.dei.g312.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.g312.domain.Employee;
import pt.ipp.isep.dei.g312.domain.Skill;
import pt.ipp.isep.dei.g312.domain.Team;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TeamRepositoryTest {
    private TeamRepository teamRepo;
    private Team team1;
    private Team team2;
    @BeforeEach
    public void setup() {
        teamRepo = new TeamRepository();
        team1 = new Team();
        team2 = new Team();
        // Assuming Employee and Skill are defined with appropriate constructors and methods
        team1.addEmployee(new Employee("John Doe", Arrays.asList(new Skill("Java","Java.js"), new Skill("SQL","SQL Server"))));
        team2.addEmployee(new Employee("Jane Doe", Arrays.asList(new Skill("Python","Snake"))));
    }

    @Test
    public void testAddNewTeamSuccess() {
        Optional<Team> result = teamRepo.add(team1);
        assertTrue(result.isPresent(), "The team should be added successfully.");
        assertEquals(1, teamRepo.teamList.size(), "The team list should contain one team.");
    }

    @Test
    public void testTeamCloningOnAdd() {
        teamRepo.add(team1);
        Team addedTeam = teamRepo.teamList.get(0);
        assertFalse(team1 == addedTeam, "The team stored should be a clone, not the original instance.");
    }

}