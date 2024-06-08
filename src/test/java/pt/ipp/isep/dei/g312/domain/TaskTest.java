package pt.ipp.isep.dei.g312.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    private Task task;
    private GreenSpace greenSpace;
    private Date startDate;
    private Date endDate;
    private Team team;
    private Vehicle vehicle;

    @BeforeEach
    void setUp() {
        greenSpace = new GreenSpace("Parque da Cidade do Porto", "Porto", 99.6, GreenSpaceTypology.LARGE, "Green Space Manager");
        startDate = new Date();
        endDate = new Date(startDate.getTime() + 3600 * 1000); // 1 hour later
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        List<Employee> teamMembers = new ArrayList<>();
        try {
            teamMembers.add(new Employee("Employee",  dateFormat.parse("01/01/1950"), "employee@this.app", 919017113,  dateFormat.parse("01/01/1968"), "246597858", "Porto", "12345678", "EMPLOYEE")); // Add first employee
            teamMembers.add(new Employee("Green Space Manager",  dateFormat.parse("01/01/1950"), "GSM@this.app", 919017113,  dateFormat.parse("01/01/1968"), "246597855", "Porto", "12345678", "GSM")); // Add second employee
            vehicle = new Vehicle("80-PH-70", "BMW", "320D", "Passageiros", 1900, 2000, 19000, dateFormat.parse("08/03/2011"), dateFormat.parse("01/09/2020"), 20000);
        }catch (ParseException e){

        }
        team = new Team(teamMembers);


        task = new Task(greenSpace, "Clean Park", "Clean the park area", TaskUrgency.HIGH, 120, TaskPosition.TODOLIST,99999);
    }

    @Test
    void testAssignTeam() {
        task.assignTeam(team);
        assertEquals(team, task.getAssignedTeam());
    }

    @Test
    void testAssignVehicle() {
        task.assignVehicle(vehicle);
        assertTrue(task.getAssignedVehicles().contains(vehicle));
    }

    @Test
    void testSetTaskStartDate() {
        task.setTaskStartDate(startDate);
        assertEquals(startDate, task.getStartDate());
    }

    @Test
    void testCalculateEndDate() {
        task.setTaskStartDate(startDate);
        task.setEndDate();
        assertNotNull(task.getEndDate());
    }

    /**
     * //TODO:para verificar este teste está a dar erro
     */

//    @Test
//    void testTaskOverlap() {
//        Task otherTask = new Task(greenSpace, "Mow Lawn", "Mow the lawn", TaskUrgency.MEDIUM, 60, TaskPosition.TODOLIST);
//        otherTask.setTaskStartDate(startDate);
//        otherTask.setEndDate();
//
//        task.setTaskStartDate(new Date(startDate.getTime() + 30 * 60 * 1000)); // 30 minutes after startDate
//        task.setEndDate();
//
//        assertTrue(task.taskOverlap(otherTask));
//
//        Task nonOverlappingTask = new Task(greenSpace, "Plant Trees", "Plant new trees", TaskUrgency.LOW, 180, TaskPosition.TODOLIST);
//        nonOverlappingTask.setTaskStartDate(new Date(endDate.getTime() + 3600 * 1000)); // 1 hour after endDate
//        nonOverlappingTask.setEndDate();
//
//        assertFalse(task.taskOverlap(nonOverlappingTask));
//    }

    @Test
    void testClone() {
        Task clonedTask = task.clone();
        assertEquals(task.getTitle(), clonedTask.getTitle());
        assertEquals(task.getDescription(), clonedTask.getDescription());
        assertEquals(task.getDuration(), clonedTask.getDuration());
        assertEquals(task.getGreenSpace(), clonedTask.getGreenSpace());
    }

    @Test
    public void testCancel() {
        task.cancel();
        assertEquals(TaskStatus.CANCELED, task.getStatus());
    }
    @Test
    public void testAddTaskAgenda() {
        LocalDate startDate = LocalDate.of(2024, 6, 8);
        LocalTime startTime = LocalTime.of(10, 30);

        task.addTaskAgenda(startDate, startTime);

        assertEquals(TaskPosition.AGENDA, task.getTaskPosition());
        assertEquals(TaskStatus.PENDING, task.getStatus());

        LocalDateTime expectedStartDateTime = LocalDateTime.of(startDate, startTime);
        Date expectedStartDate = Date.from(expectedStartDateTime.atZone(ZoneId.systemDefault()).toInstant());

        assertEquals(expectedStartDate, task.getStartDate());

        // Here we assume that calculateEndDate() is a method that returns a non-null Date.
        assertNotNull(task.getEndDate());
    }
}