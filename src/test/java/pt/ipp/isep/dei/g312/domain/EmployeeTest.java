package pt.ipp.isep.dei.g312.domain;

import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.g312.repository.GreenSpaceRepository;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static pt.ipp.isep.dei.g312.ui.console.utils.Utils.raiseInvalidInput;

class EmployeeTest {
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
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
    void registerSkill()  {
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
    void getSkills()  {
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
    void getAvailableSkillsToAddToCollaborator()  {
        try{
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
        try{
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
        try{
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
        try{
        Employee employee = new Employee("Gervasio Silva", dateFormat.parse("01/01/1950"), "aaasa@ass.pt", 913456789, dateFormat.parse("01/01/1980"), "123456789", "avenida de cima, andar de baixo, numero ao lado", "87654321", "cozinheiro");
        assertTrue(employee.hasEmail("aaasa@ass.pt"));
        assertFalse(employee.hasEmail("dsdadsads@sas.com"));
        } catch (ParseException e) {
            raiseInvalidInput();
        }
    }

    @Test
    void testToString()  {
        try{
        Employee employee = new Employee("Gervasio Silva", dateFormat.parse("01/01/1950"), "aaasa@ass.pt", 913456789, dateFormat.parse("01/01/1980"), "123456789", "avenida de cima, andar de baixo, numero ao lado", "87654321", "cozinheiro");
        String expectedString = "Gervasio Silva";
        assertEquals(expectedString, employee.toString());
        } catch (ParseException e) {
            raiseInvalidInput();
        }
    }



}