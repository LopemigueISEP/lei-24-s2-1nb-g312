package pt.ipp.isep.dei.g312.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.g312.ui.console.utils.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

public class TeamTest {
    private Team team;
    private Employee employee1;
    private Employee employee2;

    @BeforeEach
    void setUp(){
        team = new Team();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            employee1 = new Employee("Employee", (Date) dateFormat.parse("01/01/1950"), "employee@this.app", 919017113, (Date) dateFormat.parse("01/01/1968"),"246597858","Porto","12345678","EMPLOYEE");
            employee2 = new Employee("Human Resources Manager", (Date) dateFormat.parse("01/01/1950"), "HRM@this.app", 919017113, (Date) dateFormat.parse("01/01/1968"),"246597857","Porto","12345678","HRM");
        }catch (ParseException e){
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, "Incorrect date Format", e);
        }

    }

    @Test
    void testAddEmployee() {
        team.add(employee1);
        assertEquals(1, team.size());
        assertTrue(team.getTeamEmployees().contains(employee1));
    }

    @Test
    void testTeamIsEmptyInitially() {
        assertTrue(team.isEmpty());
    }

    @Test
    void testTeamIsNotEmptyAfterAdd() {
        team.add(employee1);
        assertFalse(team.isEmpty());
    }

    @Test
    void testGetTeamEmployees() {
        team.add(employee1);
        team.add(employee2);
        List<Employee> employees = team.getTeamEmployees();
        assertEquals(2, employees.size());
        assertTrue(employees.contains(employee1));
        assertTrue(employees.contains(employee2));

        // Test immutability of getTeamEmployees
        employees.remove(employee1);
        assertTrue(team.getTeamEmployees().contains(employee1));
    }

    @Test
    void testSetTeamEmployees() {
        List<Employee> newEmployees = Arrays.asList(employee1, employee2);
        team.setTeamEmployees(newEmployees);
        assertEquals(2, team.size());
        assertTrue(team.getTeamEmployees().containsAll(newEmployees));
    }

    @Test
    void testClone() {
        team.add(employee1);
        Team clone = team.clone();
        assertEquals(team.size(), clone.size());
        assertTrue(clone.getTeamEmployees().contains(employee1));
    }

    @Test
    void testSize() {
        assertEquals(0, team.size());
        team.add(employee1);
        assertEquals(1, team.size());
        team.add(employee2);
        assertEquals(2, team.size());
    }
}
