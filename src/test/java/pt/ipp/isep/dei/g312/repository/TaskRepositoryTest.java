package pt.ipp.isep.dei.g312.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.g312.domain.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class TaskRepositoryTest {


    private TaskRepository taskRepository;
    private SimpleDateFormat dateFormat;

    private GreenSpace greenSpace;
    private Task task1,task2, task5,canceledTask;
    private Vehicle vehicle1, vehicle2;
    private List<Vehicle> vehicles = new ArrayList<>();
    private Team team1;

    @BeforeEach
    void setUp() throws ParseException {
        taskRepository = new TaskRepository();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy - HH");


        vehicle1 = new Vehicle("81-PH-70", "BMW", "320D", "Passageiros", 1900, 2000, 19000, dateFormat.parse("08/03/2011 - 14"), dateFormat.parse("01/09/2020 - 14"), 20000);
        vehicle2 = new Vehicle("82-PH-70", "BMW", "320D", "Passageiros", 1900, 2000, 19000, dateFormat.parse("08/03/2011 - 14"), dateFormat.parse("01/09/2020 - 14"), 20000);
        vehicles.add(vehicle1);
        vehicles.add(vehicle2);


        Date startDate = dateFormat.parse("01/06/2024 - 14");
        Date endDate = dateFormat.parse("02/06/2024 - 13");
        greenSpace = new GreenSpace("greenTeste", "casota", 200, "cenas", "GSM@this.app");

        task1 = new Task("Sample Task", "This is a sample task description.", 8, "Type A", greenSpace,
                TaskUrgency.HIGH, TaskStatus.PENDING, null, new ArrayList<>(), 1, startDate, endDate, TaskPosition.AGENDA);
        task1.assignVehicle(vehicle1);
        taskRepository.addTask(task1);

        task2 = new Task("Sample Task 2 for testing", "This is a sample task description.", 8, "Type A", greenSpace,
                TaskUrgency.HIGH, TaskStatus.CANCELED, null, new ArrayList<>(), 2, startDate, endDate, TaskPosition.AGENDA);
        task2.assignVehicle(vehicle2);
        taskRepository.addTask(task2);

        canceledTask = new Task("Sample Task", "This is a sample task description.", 8, "Type A", greenSpace,
                TaskUrgency.HIGH, TaskStatus.CANCELED, null, new ArrayList<>(), 1, startDate, endDate, TaskPosition.AGENDA);
        taskRepository.addTask(canceledTask);

        Task task3 = new Task("Tascão", "descrição do tasco lá do sitio", 3, "Type A", greenSpace, TaskUrgency.MEDIUM, TaskStatus.DONE,
                null, new ArrayList<>(), 3, startDate, endDate, TaskPosition.AGENDA);

        taskRepository.addTask(task3);

        Task task4 = new Task("GrandaTask", "O Miguel já pagava uns finos ao pessoal", 3, "Type A", greenSpace,
                TaskUrgency.MEDIUM, TaskStatus.POSTPONED, null, new ArrayList<>(), 4, startDate, endDate, TaskPosition.AGENDA);

        taskRepository.addTask(task4);

        Date task5startDate = dateFormat.parse("16/06/2024 - 14");
        Date task5EndDate = dateFormat.parse("17/06/2024 - 13");

        task5 = new Task("TaskSemNomeDeJeito", "Vou-me despedir para ficar a dormir o dia todo", 5, "Type A", greenSpace,
                TaskUrgency.LOW, TaskStatus.PENDING, null, new ArrayList<>(), 5, task5startDate, task5EndDate, TaskPosition.AGENDA);
        task5.assignVehicle(vehicle1);
        task5.assignVehicle(vehicle2);
        taskRepository.addTask(task5);
        team1 = new Team();


    }

    @Test
    void getAllAgendaTasksExceptDoneCanceled() throws ParseException {


        List<Task> result = taskRepository.getAllAgendaTasksExceptDoneCanceled();

        // in setup 5 tasks 1 is done and 1 is canceled
        assertEquals(3, result.size());

        Task taskTeste6 = new Task("TASK_TESTE","DESCRICAO",8,"",greenSpace,TaskUrgency.HIGH,TaskStatus.DONE,team1,new ArrayList<Vehicle>(),1012,dateFormat.parse("16/06/2024 - 14"),dateFormat.parse("19/06/2024 - 14"),TaskPosition.AGENDA);

        taskRepository.addTask(taskTeste6);
        List<Task> result1 = taskRepository.getAllAgendaTasksExceptDoneCanceled();

        assertNotEquals(4, result1.size());
        assertEquals(taskTeste6.getStatus(),TaskStatus.DONE);
        assertFalse(result1.contains(taskTeste6)); //check if not contains a done status task
        assertTrue(result1.contains(task1)); // check if contains a pending status task

    }

    @Test
    void getTasksByGreenSpace() throws ParseException {

        Date task5startDate = dateFormat.parse("16/06/2024 - 14");
        Date task5EndDate = dateFormat.parse("17/06/2024 - 13");
        GreenSpace greenSpace12 = new GreenSpace("ABC","asd",1000,"asd","asd");

        Task task12 = new Task("TaskSemNomeDeJeito", "Vou-me despedir para ficar a dormir o dia todo", 5, "Type A", greenSpace12,
                TaskUrgency.LOW, TaskStatus.PENDING, null, new ArrayList<>(), 5, task5startDate, task5EndDate, TaskPosition.AGENDA);

        taskRepository.addTask(task12);
        List<Task> result = taskRepository.getTasksByGreenSpace();


        assertEquals(5, result.size()); //All 5 starting tasks are assigned to this greenspace, the number 6 task12 is in another greenspace
        assertTrue(result.contains(task1)); //contains a task of the selected greenspace
        assertFalse(result.contains(task12)); // didn't contain a task of another greenspace


    }

    @Test
    void getVehicleAvaiability() {

        List<Vehicle> availableVehiclesForTask1 = taskRepository.getVehicleAvaiability(task1, vehicles);
        List<Vehicle> availableVehiclesForTask5 = taskRepository.getVehicleAvaiability(task5, vehicles);

        assertEquals(1, availableVehiclesForTask1.size()); // Only one vehicle is available for task1
        assertTrue(availableVehiclesForTask1.contains(vehicle2)); // Vehicle2 is available for task1
        assertEquals(0, availableVehiclesForTask5.size()); // No vehicle available for task5
    }

    @Test
    public void testGetTasksCancelable() {
        List<Task> cancelableTasks = taskRepository.getTasksCancelable();
        assertEquals(4, cancelableTasks.size());
        assertTrue(cancelableTasks.contains(task1));
    }

    @Test
    public void testCancelTask() {
        taskRepository.addTask(task1);
        taskRepository.cancelTask(task1);
        assertEquals(TaskStatus.CANCELED, task1.getStatus());
    }
}