# US004 - Assign one or more skills to a collaborator

## 4. Tests 

**Test 1:** This test ensures the process of adding skills to an employee object and then verifies that those skills have been correctly retrieved using a getter method.

**Test 2:** This test ensures the functionality of adding skills to a employee's profile.

**Test 3:** This test ensures if the method can accurately retrieve the skills currently available in an employee's profile.

**Test 4:** This test verifies that the  method successfully retrieves the skills associated with an employee previously registered. It checks both the size and the content of the retrieved skill list.

	void assignSkillsCollaborator()  {
    try{
        Employee employee = new Employee("Gervasio Silva", dateFormat.parse("01/01/1950"), "aaasa@ass.pt", 913456789, dateFormat.parse("01/01/1980"), "123456789", "avenida de cima, andar de baixo, numero ao lado", "87654321", "cozinheiro");
        EmployeeRepository employeeRepository = new EmployeeRepository();
        Skill skill1 = new Skill("Cozinhar", "Saber cozinhar");
        Skill skill2 = new Skill("Limpar", "Saber limpar");
        Skill skill3 = new Skill("Varrer", "Saber varrer");
        List<Skill> addSkills = new ArrayList<>();

        // Test 1
        addSkills.add(skill1);
        addSkills.add(skill2);
        employee.addSkills(addSkills);
        List<Skill> employeeSkills = employee.getSkills();
        assertEquals(2, employeeSkills.size()); // Verify number of skills
        assertEquals(skill1, employeeSkills.get(0)); // Verify first skill
        assertEquals(skill2, employeeSkills.get(1)); // Verify second skill


        //Test 2
        List<Skill> skillsToAdd = List.of(new Skill("Cozinhar", "Sabe cozinhar"));

        Optional<Employee> updatedEmployee = employeeRepository.addSkillsToCollaboratorProfile(employee1, skillsToAdd);
        assertFalse(updatedEmployee.isPresent());

        employeeRepository.addEmployee(employee1);
        updatedEmployee = employeeRepository.addSkillsToCollaboratorProfile(employee1, skillsToAdd);
        assertTrue(updatedEmployee.isPresent());
        Employee updatedEmp = updatedEmployee.get();
        assertEquals(employee1.getName(), updatedEmp.getName());
        assertTrue(updatedEmp.getSkills().contains(skillsToAdd.getFirst()));

        List<Skill> emptySkills = Collections.emptyList();
        updatedEmployee = employeeRepository.addSkillsToCollaboratorProfile(employee1, emptySkills);
        assertTrue(updatedEmployee.isPresent());
        updatedEmp = updatedEmployee.get();
        assertEquals(employee1, updatedEmp);
    
        //Test 3

        addSkills.add(skill1);
        addSkills.add(skill2);
        addSkills.add(skill3);
        employee.addSkills(addSkills);
        List<Skill> availableSkills = employee.getEmployeeSkillList();

        assertEquals(addSkills.size(), availableSkills.size());
        assertTrue(availableSkills.containsAll(addSkills));

        //Test 4

        addSkills.add(skill1);
        addSkills.add(skill2);
        employee1.addSkills(addSkills);
        employeeRepository.addEmployee(employee1);

        List<Skill> employeeSkills = employeeRepository.getEmployeeSkills(employee1);

        List<Skill> originalSkills = employee1.getEmployeeSkillList();
        assertEquals(2, originalSkills.size());

        assertEquals("Cozinhar", employeeSkills.getFirst().getSkillName());
        assertEquals("Saber cozinhar", employeeSkills.getFirst().getSkillDescription());
            } catch (ParseException e) {
            raiseInvalidInput();
        }
}


## 5. Construction (Implementation)

### Class AssignSkillCollaboratorUI

```java
   public AssignSkillsCollaboratorUI() {
            controller = new AssignSkillCollaboratorController();
        }
```

### Class AssignSkillCollaboratorController 

```java
public List<Employee> getCollaboratorListAndSkillsAdded() {
    try {
        List<Employee> employeeList = employeeRepository.getEmployee();
        return employeeList; // Return all employees
    } catch (NullPointerException e) {
        System.out.println("Returning empty collaborator list.");

        return Collections.emptyList();
    }
}

public List<Skill> getSkillListPossibleToBeAdded(Employee selectedCollaborator) {
    if (selectedCollaborator == null) {
        System.out.println("Error: Cannot retrieve available skills.");
        return Collections.emptyList();
    }
    return selectedCollaborator.getAvailableSkillsToAddToCollaborator(skillRepository);
}

public void addSkillToCollaboratorProfile(Employee collaborator, List<Skill> skillsToAdd) {
    employeeRepository.addSkillsToCollaboratorProfile(collaborator, skillsToAdd); // Delegate to service
}
```

### Class Employee

```java
 public List<Skill> getSkills() {
    return Repositories.getInstance().getEmployeeRepository().getEmployeeSkills(this);
}

public void addSkills(List<Skill> skillsToAdd) {
    skills.addAll(skillsToAdd); // Add all skills from the provided list
}

public List<Skill> getAvailableSkillsToAddToCollaborator(SkillRepository skillRepository) {
    List<Skill> allSkills = skillRepository.getSkills().get();

    List<Skill> availableSkills = new ArrayList<>();

    // Check for existing skills in the selected collaborator
    List<Skill> collaboratorSkills = this.getSkills(); // Assuming you have a getSkills method in Employee
    for (Skill skill : allSkills) {
        boolean skillAlreadyPresent = false;
        for (Skill collaboratorSkill : collaboratorSkills) {
            if (skill.getSkillName().equals(collaboratorSkill.getSkillName())) {
                skillAlreadyPresent = true;
                break;
            }
        }
        if (!skillAlreadyPresent) {
            availableSkills.add(skill);
        }
    }

    return availableSkills;
}
```


## 6. Integration and Demo 

* A new option on the Admin menu and HRM menu options was added - show list of skills of registered employees..

* For demo purposes some tasks are bootstrapped while system starts.


## 7. Observations

n/a