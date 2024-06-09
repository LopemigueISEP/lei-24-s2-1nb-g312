package pt.ipp.isep.dei.g312.domain;

import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.g312.repository.GreenSpaceRepository;
import pt.ipp.isep.dei.g312.repository.TaskRepository;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static pt.ipp.isep.dei.g312.ui.console.utils.Utils.raiseInvalidInput;

class EmployeeTest {


    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private TaskRepository mockTaskRepository;
    private GreenSpaceRepository mockGreenSpaceRepository;


    @Test
    void testClone() {
        try {
            Employee employee = new Employee("Gervasio Silva", dateFormat.parse("01/01/1950"), "aaasa@ass.pt", 913456789, dateFormat.parse("01/01/1980"), "123456789", "avenida de cima, andar de baixo, numero ao lado", "87654321", "cozinheiro");
            Employee clonedEmployee = employee.clone();

            // Verify the clone is a different object
            assertNotSame(employee, clonedEmployee);

            // Verify primitive field values are copied correctly
            assertEquals(employee.getName(), clonedEmployee.getName());
            assertEquals(employee.getBirthDate(), clonedEmployee.getBirthDate());
            assertEquals(employee.getEmail(), clonedEmployee.getEmail());
            assertEquals(employee.getPhoneNumber(), clonedEmployee.getPhoneNumber());
            assertEquals(employee.getAdmissionDate(), clonedEmployee.getAdmissionDate());
            assertEquals(employee.getTaxpayerNumber(), clonedEmployee.getTaxpayerNumber());
            assertEquals(employee.getAddress(), clonedEmployee.getAddress());
            assertEquals(employee.getDocNumber(), clonedEmployee.getDocNumber());
            assertEquals(employee.getJob(), clonedEmployee.getJob());
        } catch (ParseException e) {
            raiseInvalidInput();
        }
    }

    @Test
    void registerSkill() {
        try {
            Employee employee = new Employee("Gervasio Silva", dateFormat.parse("01/01/1950"), "aaasa@ass.pt", 913456789, dateFormat.parse("01/01/1980"), "123456789", "avenida de cima, andar de baixo, numero ao lado", "87654321", "cozinheiro");

            String skillName = "Bananas";
            String skillDescription = "Estragadas";
            boolean userValidation = true;


            Optional<Skill> registeredSkill = employee.registerSkill(skillName, skillDescription, userValidation);


            assertTrue(registeredSkill.isPresent());
            assertEquals(skillName, registeredSkill.get().getSkillName());
            assertEquals(skillDescription, registeredSkill.get().getSkillDescription());

            // already existing skill
            Optional<Skill> registeredSkill3 = employee.registerSkill(skillName, skillDescription, userValidation);
            assertTrue(registeredSkill3.isEmpty());

            skillName = "Pessegos";
            skillDescription = "Estragados";
            userValidation = false;


            //user without permission
            Optional<Skill> registeredSkill2 = employee.registerSkill(skillName, skillDescription, userValidation);
            assertFalse(registeredSkill2.isPresent());
        } catch (ParseException e) {
            raiseInvalidInput();
        }
    }


    @Test
    void registerJob() {
        try {
            Employee employee = new Employee("Gervasio Silva", dateFormat.parse("01/01/1950"), "aaasa@ass.pt", 913456789, dateFormat.parse("01/01/1980"), "123456789", "avenida de cima, andar de baixo, numero ao lado", "87654321", "cozinheiro");

            String jobName = "Condutor";
            String jobDescription = "Triciclos";
            boolean userValidation = true;


            Optional<Job> registeredJob = employee.registerJob(jobName, jobDescription, userValidation);


            // Verify successful
            assertTrue(registeredJob.isPresent());
            assertEquals(jobName, registeredJob.get().getJobName());
            assertEquals(jobDescription, registeredJob.get().getJobDescription());

            //already existing job
            Optional<Job> registeredJob2 = employee.registerJob(jobName, jobDescription, userValidation);
            assertFalse(registeredJob2.isPresent());

            jobName = "Cozinheiro";
            jobDescription = "Couves";
            userValidation = false;

            //user without permission
            Optional<Job> registeredJob3 = employee.registerJob(jobName, jobDescription, userValidation);
            assertTrue(registeredJob3.isEmpty());

        } catch (ParseException e) {
            raiseInvalidInput();
        }
    }


    @Test
    void getSkills() {
        try {
            Employee employee = new Employee("Gervasio Silva", dateFormat.parse("01/01/1950"), "aaasa@ass.pt", 913456789, dateFormat.parse("01/01/1980"), "123456789", "avenida de cima, andar de baixo, numero ao lado", "87654321", "cozinheiro");

            //add skill
            Skill skill1 = new Skill("Cozinhar", "Saber cozinhar");
            Skill skill2 = new Skill("Limpar", "Saber limpar");
            List<Skill> addSkills = new ArrayList<>();
            addSkills.add(skill1);
            addSkills.add(skill2);
            employee.addSkills(addSkills);

            //call method
            List<Skill> employeeSkills = employee.getSkills();
            //verify skills
            assertEquals(2, employeeSkills.size()); // Verify number of skills
            assertEquals(skill1, employeeSkills.get(0)); // Verify first skill
            assertEquals(skill2, employeeSkills.get(1)); // Verify second skill
        } catch (ParseException e) {
            raiseInvalidInput();
        }
    }

    @Test
    void addSkills() {
        try {
            Employee employee = new Employee("Gervasio Silva", dateFormat.parse("01/01/1950"), "aaasa@ass.pt", 913456789, dateFormat.parse("01/01/1980"), "123456789", "avenida de cima, andar de baixo, numero ao lado", "87654321", "cozinheiro");

            //add skill
            Skill skill1 = new Skill("Cozinhar", "Saber cozinhar");
            Skill skill2 = new Skill("Limpar", "Saber limpar");
            List<Skill> addSkills = new ArrayList<>();
            addSkills.add(skill1);
            addSkills.add(skill2);
            employee.addSkills(addSkills);
            // Get the initial skill count
            int initialSkillCount = employee.getEmployeeSkillList().size(); // Use the getter method

            // Call addSkills
            employee.addSkills(addSkills);

            // Verify the skills are added
            assertEquals(initialSkillCount + addSkills.size(), employee.getEmployeeSkillList().size());
        } catch (ParseException e) {
            raiseInvalidInput();
        }
    }


    @Test
    void getAvailableSkillsToAddToCollaborator() {
        try {
            // Create an employee with no skills
            Employee employee = new Employee("Gervasio Silva", dateFormat.parse("01/01/1950"), "aaasa@ass.pt", 913456789, dateFormat.parse("01/01/1980"), "123456789", "avenida de cima, andar de baixo, numero ao lado", "87654321", "cozinheiro");

            // Create a list of all available skills
            Skill skill1 = new Skill("Cozinhar", "Saber cozinhar");
            Skill skill2 = new Skill("Limpar", "Saber limpar");
            Skill skill3 = new Skill("Varrer", "Saber varrer");
            List<Skill> addSkills = new ArrayList<>();
            addSkills.add(skill1);
            addSkills.add(skill2);
            addSkills.add(skill3);
            employee.addSkills(addSkills);
            // Get the available skills for the employee

            List<Skill> availableSkills = employee.getEmployeeSkillList();

            // Verify that all skills are available
            assertEquals(addSkills.size(), availableSkills.size());
            assertTrue(availableSkills.containsAll(addSkills));
        } catch (ParseException e) {
            raiseInvalidInput();
        }
    }


    @Test
    void testEquals() {
        try {
            Employee employee1 = new Employee("Gervasio Silva", dateFormat.parse("01/01/1950"), "aaasa@ass.pt", 913456789, dateFormat.parse("01/01/1980"), "123456789", "avenida de cima, andar de baixo, numero ao lado", "87654321", "cozinheiro");
            Employee employee2 = new Employee("Rute Carmo", dateFormat.parse("12/06/1980"), "aaa5a@ass.pt", 912345678, dateFormat.parse("12/06/2000"), "987654321", "avenida de baixo, andar de cima, numero a direita", "12345678", "padeiro");
            Employee employee3 = new Employee("Rute Carmo", dateFormat.parse("12/06/1980"), "aaasa@ass.pt", 912345678, dateFormat.parse("12/06/2000"), "987654321", "avenida de baixo, andar de cima, numero a direita", "12345678", "padeiro");

            assertEquals(employee1, employee1); //equal
            assertNotEquals(null, employee1); // null
            assertNotEquals(employee1, employee2); //different
            assertEquals(employee1, employee3); //same mail
        } catch (ParseException e) {
            raiseInvalidInput();
        }
    }

    @Test
    void testHashCode() {
        try {
            Employee employee1 = new Employee("Gervasio Silva", dateFormat.parse("01/01/1950"), "aaasa@ass.pt", 913456789, dateFormat.parse("01/01/1980"), "123456789", "avenida de cima, andar de baixo, numero ao lado", "87654321", "cozinheiro");
            Employee employee2 = new Employee("Gervasio Silva", dateFormat.parse("01/01/1950"), "aaasa@ass.pt", 913456789, dateFormat.parse("01/01/1980"), "123456789", "avenida de cima, andar de baixo, numero ao lado", "87654321", "cozinheiro");
            Employee employee3 = new Employee("Rute Carmo", dateFormat.parse("12/06/1980"), "aaa5a@ass.pt", 912345678, dateFormat.parse("12/06/2000"), "987654321", "avenida de baixo, andar de cima, numero a direita", "12345678", "padeiro");

            assertEquals(employee1, employee2);
            assertEquals(employee1.hashCode(), employee2.hashCode());
            assertNotEquals(employee1, employee3);
            assertNotEquals(employee1.hashCode(), employee3.hashCode());
        } catch (ParseException e) {
            raiseInvalidInput();
        }
    }

    @Test
    void hasEmail() {
        try {
            Employee employee = new Employee("Gervasio Silva", dateFormat.parse("01/01/1950"), "aaasa@ass.pt", 913456789, dateFormat.parse("01/01/1980"), "123456789", "avenida de cima, andar de baixo, numero ao lado", "87654321", "cozinheiro");
            assertTrue(employee.hasEmail("aaasa@ass.pt"));
            assertFalse(employee.hasEmail("dsdadsads@sas.com"));
        } catch (ParseException e) {
            raiseInvalidInput();
        }
    }

    @Test
    void testToString() {
        try {
            Employee employee = new Employee("Gervasio Silva", dateFormat.parse("01/01/1950"), "aaasa@ass.pt", 913456789, dateFormat.parse("01/01/1980"), "123456789", "avenida de cima, andar de baixo, numero ao lado", "87654321", "cozinheiro");
            String expectedString = "Gervasio Silva";
            assertEquals(expectedString, employee.toString());
        } catch (ParseException e) {
            raiseInvalidInput();
        }
    }

    @Test
    void testRegisterTask() {
        try {
            Employee employee = new Employee("Gervasio Silva", dateFormat.parse("01/01/1950"), "aaasa@ass.pt", 913456789, dateFormat.parse("01/01/1980"), "123456789", "avenida de cima, andar de baixo, numero ao lado", "87654321", "cozinheiro");

            GreenSpace greenSpace = new GreenSpace("Parque da Cidade do Porto", "Porto", 99.6, GreenSpaceTypology.LARGE, "Green Space Manager");
            String taskTitle = "Clean Park";
            String taskDescr = "Clean the park area";
            TaskUrgency taskUrgency = TaskUrgency.HIGH;
            int expectedDuration = 120;
            TaskPosition taskPosition = TaskPosition.TODOLIST;
            int taskId = 99999;

            Optional<Task> registeredTask = employee.registerTask(greenSpace, taskTitle, taskDescr, taskUrgency, expectedDuration, taskPosition, taskId);

            assertTrue(registeredTask.isPresent());
            assertEquals(taskTitle, registeredTask.get().getTitle());
            assertEquals(taskDescr, registeredTask.get().getDescription());
            assertEquals(taskUrgency, registeredTask.get().getUrgency());
            assertEquals(expectedDuration, registeredTask.get().getDuration());
            assertEquals(taskPosition, registeredTask.get().getTaskPosition());
            assertEquals(taskId, registeredTask.get().getTaskID());
            assertEquals(greenSpace, registeredTask.get().getGreenSpace());
        } catch (ParseException e) {
            raiseInvalidInput();
        }
    }

    @Test
    void testAssignTeamToTask() {
        try {
            GreenSpace greenSpace = new GreenSpace("Parque da Cidade do Porto", "Porto", 99.6, GreenSpaceTypology.LARGE, "Green Space Manager");
            Task task = new Task(greenSpace, "Clean Park", "Clean the park area", TaskUrgency.HIGH, 120, TaskPosition.TODOLIST, 99999);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            List<Employee> teamMembers = new ArrayList<>();
            teamMembers.add(new Employee("Employee", dateFormat.parse("01/01/1950"), "employee@this.app", 919017113, dateFormat.parse("01/01/1968"), "246597858", "Porto", "12345678", "EMPLOYEE"));
            Team team = new Team(teamMembers);

            Optional<Task> assignedTask = Employee.assignTeamToTask(team, task);

            assertTrue(assignedTask.isPresent());
            assertEquals(TaskStatus.PLANNED, assignedTask.get().getStatus());
            assertEquals(team, assignedTask.get().getAssignedTeam());
        } catch (ParseException e) {
            raiseInvalidInput();
        }
    }

    @Test
    void testPostponedTask() {
        try {
            GreenSpace greenSpace = new GreenSpace("Parque da Cidade do Porto", "Porto", 99.6, GreenSpaceTypology.LARGE, "Green Space Manager");
            Task task = new Task(greenSpace, "Clean Park", "Clean the park area", TaskUrgency.HIGH, 120, TaskPosition.TODOLIST, 99999);
            Date newStartDate = dateFormat.parse("01/01/2025");

            task.setTaskStartDate(newStartDate);
            task.setEndDate();
            task.setTaskStatus(TaskStatus.POSTPONED);
            task.assignTeam(null);
            task.clearVehicleList();

            assertEquals(TaskStatus.POSTPONED, task.getStatus());
            assertEquals(newStartDate, task.getStartDate());
            assertNull(task.getAssignedTeam());
            assertTrue(task.getAssignedVehicles().isEmpty());
        } catch (ParseException e) {
            raiseInvalidInput();
        }
    }
}