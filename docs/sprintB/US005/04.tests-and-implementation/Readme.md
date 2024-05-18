# US005 - Generate a Team proposal automatically 

## 4. Tests 

**Test 1:** Ensures that adding an employee to the team increases the team's size by one and ensures the employee is indeed part of the team 

	@Test
    void testAddEmployee() {
        team.add(employee1);
        assertEquals(1, team.size());
        assertTrue(team.getTeamEmployees().contains(employee1));
    }
	

**Test 2:** Checks that a new team is empty at initialization 

	@Test
    void testTeamIsEmptyInitially() {
        assertTrue(team.isEmpty());
    }

**Test 3:** Verifies that the team is not empty after adding an employee, confirming the update in team status 

	@Test
    void testTeamIsNotEmptyAfterAdd() {
        team.add(employee1);
        assertFalse(team.isEmpty());
    }0

**Test 4:** Ensures that retrieving the list of team members returns all added employees and that modifications to the retrieved list do not affect the original team list, demonstrating its immutability 

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

**Test 5:** Confirms that setting a new list of employees updates the team's internal list correctly and reflects the correct team size and membership. 

	@Test
    void testSetTeamEmployees() {
        List<Employee> newEmployees = Arrays.asList(employee1, employee2);
        team.setTeamEmployees(newEmployees);
        assertEquals(2, team.size());
        assertTrue(team.getTeamEmployees().containsAll(newEmployees));
    }

**Test 6:** Tests that cloning a team produces a new team object with the same size and containing the same members as the original.

	@Test
    void testClone() {
        team.add(employee1);
        Team clone = team.clone();
        assertEquals(team.size(), clone.size());
        assertTrue(clone.getTeamEmployees().contains(employee1));
    }

**Test 7:** Validates that the team's size method correctly reports the number of team members initially and adjusts accurately as new members are added.

	@Test
    void testClone() {
        team.add(employee1);
        Team clone = team.clone();
        assertEquals(team.size(), clone.size());
        assertTrue(clone.getTeamEmployees().contains(employee1));
    }

_It is also recommended to organize this content by subsections._ 


## 5. Construction (Implementation)

### Class GenerateTeamUI 

```java
public class GenerateTeamUI implements Runnable {

    private final String ACCEPT = "Accept";
    private final String ACCEPT_MODIFY = "Accept & Modify";
    private final String DECLINE = "Decline";
    private final String MODIFY_ADD = "Add Collaborator(s)";
    private final String MODIFY_REMOVE = "Remove Collaborator(s)";

    private GenerateTeamController controller;
    private int minNumberOfEmployees;
    private int maxNumberOfEmployees;
    private Team teamEmployees;
    private List<Skill> skillSet;

    private List<String> optionMenu;
    private List<String> optionModifyMenu;

    public GenerateTeamUI() {
        this.skillSet = new ArrayList<>();
        setNewOptionMenu();
        setNewOptionModifyMenu();
        controller = new GenerateTeamController();
    }
}
```
### Class GenerateTeamController

```java
public class GenerateTeamController {

    private EmployeeRepository employeeRepository;
    private SkillRepository skillRepository;
    private TeamRepository teamRepository;


    public GenerateTeamController() {
        getEmployeeRepository();
        getSkillRepository();
        getTeamRepository();
    }
}
```

### Class Team

```java
public class Team {

    private List<Employee> teamEmployees;

    public Team() {
        teamEmployees = new ArrayList<>();
    }

    public Team(List<Employee> teamEmployees) {
        this.teamEmployees = new ArrayList<>(teamEmployees);
    }
}
```


## 6. Integration and Demo 

* A new option on the HRM and Admin menu options was added.
