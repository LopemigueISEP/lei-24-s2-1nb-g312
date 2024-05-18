package pt.ipp.isep.dei.g312.repository;

import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.g312.domain.Employee;
import pt.ipp.isep.dei.g312.domain.Job;
import pt.ipp.isep.dei.g312.domain.Skill;

import java.text.ParseException;

import java.util.Collections;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

import java.text.SimpleDateFormat;


import static org.junit.jupiter.api.Assertions.*;
import static pt.ipp.isep.dei.g312.ui.console.utils.Utils.raiseInvalidInput;


class EmployeeRepositoryTest {
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    @Test
    void getEmployee()  {
        try{
        // with taxpayer number already registered

        EmployeeRepository employeeRepository = new EmployeeRepository();
        List<Employee> originalList = new ArrayList<>();

        Employee employee1 = new Employee("Gervasio Silva", dateFormat.parse("01/01/1950"), "aaasa@ass.pt", 913456789, dateFormat.parse("01/01/1980"), "123456789", "avenida de cima, andar de baixo, numero ao lado", "87654321", "cozinheiro");
        Employee employee2 = new Employee("Rute Carmo", dateFormat.parse("12/06/1980"), "aaa5a@ass.pt", 912345678, dateFormat.parse("12/06/2000"), "987654321", "avenida de baixo, andar de cima, numero a direita", "12345678", "padeiro");
        employeeRepository.addEmployee(employee1);
        employeeRepository.addEmployee(employee2);

        originalList.add(employee1);
        originalList.add(employee2);

        // Call the method under test
        Optional<Employee> result = employeeRepository.getEmployees("123456789");
        List<Employee> returnedList = employeeRepository.getEmployees();

        // Assertions
        assertTrue(result.isPresent());
        assertEquals(employee1, result.get());

        // returned list is a copy
        assertNotSame(originalList, returnedList);
        assertEquals(originalList.size(), returnedList.size());

        assertEquals(Optional.empty(),employeeRepository.getEmployees("456327894"));
        } catch (ParseException e) {
            raiseInvalidInput();
        }
        }



    @Test
    void addEmployee()  {
        try{

        EmployeeRepository employeeRepository = new EmployeeRepository();
        Employee employee1 = new Employee("Gervasio Silva", dateFormat.parse("01/01/1950"), "aaasa@ass.pt", 913456789, dateFormat.parse("01/01/1980"), "123456789", "avenida de cima, andar de baixo, numero ao lado", "87654321", "cozinheiro");
        Optional<Employee> addedEmployee = employeeRepository.addEmployee(employee1);

        //Employee found
        assertTrue(addedEmployee.isPresent());

        //Employee not found
        assertFalse(employeeRepository.getEmployees().isEmpty());

        assertEquals(Optional.empty(), employeeRepository.addEmployee(employee1));
        } catch (ParseException e) {
            raiseInvalidInput();
        }
    }

    @Test
    void validateEmployee()  {
        try{
        EmployeeRepository employeeRepository = new EmployeeRepository();
        Employee employee1 = new Employee("Gervasio Silva", dateFormat.parse("01/01/1950"), "aaasa@ass.pt", 913456789, dateFormat.parse("01/01/1980"), "123456789", "avenida de cima, andar de baixo, numero ao lado", "87654321", "cozinheiro");

        //Employee found
        boolean validateNotExist = employeeRepository.validateEmployee(employee1);
        assertTrue(validateNotExist);

        //Employee not found
        employeeRepository.addEmployee(employee1);
        boolean validateEmployeeExists = employeeRepository.validateEmployee(employee1);
        assertFalse(validateEmployeeExists);
        } catch (ParseException e) {
            raiseInvalidInput();
        }
    }


    @Test
    void addSkillsToCollaboratorProfile() {
        try{
        EmployeeRepository employeeRepository = new EmployeeRepository();

        //Employee not found
        Employee employee1 = new Employee("Gervasio Silva", dateFormat.parse("01/01/1950"), "aaasa@ass.pt", 913456789, dateFormat.parse("01/01/1980"), "123456789", "avenida de cima, andar de baixo, numero ao lado", "87654321", "cozinheiro");
        List<Skill> skillsToAdd = List.of(new Skill("Cozinhar", "Sabe cozinhar"));

        Optional<Employee> updatedEmployee = employeeRepository.addSkillsToCollaboratorProfile(employee1, skillsToAdd);
        assertFalse(updatedEmployee.isPresent());

        //Employee found, skills to add
        employeeRepository.addEmployee(employee1);
        updatedEmployee = employeeRepository.addSkillsToCollaboratorProfile(employee1, skillsToAdd);
        assertTrue(updatedEmployee.isPresent());
        Employee updatedEmp = updatedEmployee.get();
        assertEquals(employee1.getName(), updatedEmp.getName());
        assertTrue(updatedEmp.getSkills().contains(skillsToAdd.getFirst()));

        // 3. Employee found, empty skills list to add (no change)
        List<Skill> emptySkills = Collections.emptyList();
        updatedEmployee = employeeRepository.addSkillsToCollaboratorProfile(employee1, emptySkills);
        assertTrue(updatedEmployee.isPresent());
        updatedEmp = updatedEmployee.get();
        assertEquals(employee1, updatedEmp);
        } catch (ParseException e) {
            raiseInvalidInput();
        }
    }

    @Test
    void findEmployee() {
        try {
            EmployeeRepository employeeRepository = new EmployeeRepository();
            Employee employee1 = new Employee("Gervasio Silva", dateFormat.parse("01/01/1950"), "aaasa@ass.pt", 913456789, dateFormat.parse("01/01/1980"), "123456789", "avenida de cima, andar de baixo, numero ao lado", "87654321", "cozinheiro");
            Optional<Employee> findEmployee = employeeRepository.findEmployee(employee1);

            // Employee not found
            assertFalse(findEmployee.isPresent());

            // Employee found - Assert employee found
            employeeRepository.addEmployee(employee1);
            findEmployee = employeeRepository.findEmployee(employee1);
            assertTrue(findEmployee.isPresent());
            Employee findEm = findEmployee.get();
            assertEquals(employee1, findEm);
        } catch (ParseException e) {
            raiseInvalidInput();
        }
    }

    @Test
    void getEmployFromJob()  {
        try{
        EmployeeRepository employeeRepository = new EmployeeRepository();

        // 1. Job not found -- Assert
        Job jb = new Job("BATEDOR","Laranjas");
        Employee employee = employeeRepository.getEmployFromJob(jb.getJobName());
        assertNull(employee);

        // 2. Job found - Assert
        Employee employee1 = new Employee("Gervasio Silva", dateFormat.parse("01/01/1950"), "aaasa@ass.pt", 913456789, dateFormat.parse("01/01/1980"), "123456789", "avenida de cima, andar de baixo, numero ao lado", "87654321", "cozinheiro");
        employeeRepository.addEmployee(employee1);
        employee = employeeRepository.getEmployFromJob(employee1.getJob());
        assertNotNull(employee);
        assertEquals(employee1, employee); // Ensure it's the same object

        // 3. Job not found - Assert
        String job = "CORTADOR";
        employee = employeeRepository.getEmployFromJob(job);
        assertNull(employee);
        } catch (ParseException e) {
            raiseInvalidInput();
        }
    }


    @Test
    void getEmployeeSkills()  {
        try{
        EmployeeRepository employeeRepository = new EmployeeRepository();

        // Add employee with skills
        Employee employee1 = new Employee("Gervasio Silva", dateFormat.parse("01/01/1950"), "aaasa@ass.pt", 913456789, dateFormat.parse("01/01/1980"), "123456789", "avenida de cima, andar de baixo, numero ao lado", "87654321", "cozinheiro");
        Skill skill1 = new Skill("Cozinhar", "Saber cozinhar");
        Skill skill2 = new Skill("Limpar", "Saber limpar");

        List<Skill> addSkills = new ArrayList<>();
        addSkills.add(skill1);
        addSkills.add(skill2);
        employee1.addSkills(addSkills);
        employeeRepository.addEmployee(employee1);

        // Get employee skills
        List<Skill> employeeSkills = employeeRepository.getEmployeeSkills(employee1);

        // Verify returned list

        List<Skill> originalSkills = employee1.getEmployeeSkillList();
        assertEquals(2, originalSkills.size());

        // Assert returned list content
        assertEquals("Cozinhar", employeeSkills.getFirst().getSkillName());
        assertEquals("Saber cozinhar", employeeSkills.getFirst().getSkillDescription());
        } catch (ParseException e) {
            raiseInvalidInput();
        }
    }
    @Test
    public void employeeList() {
        List<Employee> employeeList = new ArrayList<>();

        // Assert that the list is empty (initial size is 0)
        assertEquals(0, employeeList.size());
    }

}