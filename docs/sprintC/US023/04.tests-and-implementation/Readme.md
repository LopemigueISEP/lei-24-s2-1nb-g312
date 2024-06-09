# US23 - As a GSM, I want to assign a Team to an entry in the Agenda

## 4. Tests

**Test 1:** Check that the email service is valid.

    @Test
    void loadValidEmailServices() {
    String[] emailServices = {"gmail.com", "dei.isep.ipp.pt", "this.app"};
    int counter = 0;

        for (String emailService : emailService.emailServices) {
            assertTrue(emailService.equals(emailServices[counter]));
            counter++;
        }

    }

**Test 2:** Check that the email provided is valid

    @Test
    void validEmail(){
        assertTrue(emailService.validEmail("test@gmail.com"));

        assertFalse(emailService.validEmail("test@hotmail.com"));
    }

**Test 3:** Check that a team is assigned to Task

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


## 5. Construction (Implementation)

### Class AssignTeamEntryAgendaUI

```java
public AssignTeamEntryAgendaUI() {
        controller = new AssignTeamEntryAgendaController();
        }
```

### Class AssignTeamEntryAgendaController

```java
public AssignTeamEntryAgendaController() {
        this.taskRepository = getTaskRepository();
        this.teamRepository = getTeamRepository();
        this.greenSpaceRepository = getGreenSpaceRepository();
        this.emailService = new Email();
        }
```

### Class Team

```java
public Team() {
        teamEmployees = new ArrayList<>();
        }
```

### Class Task

```java
public Task( GreenSpace greenSpace, String title, String description, TaskUrgency urgency, int taskExpectedDuration, TaskPosition taskPosition,int taskId) {
        if (taskPosition.equals(TaskPosition.TODOLIST)) {
        this.greenSpace = greenSpace;
        this.title = title;
        this.description = description;
        this.taskExpectedDuration = taskExpectedDuration;
        this.urgency = urgency;
        this.taskPosition = taskPosition;
        this.assignedVehicles = new ArrayList<>();
        this.taskID=taskId;
        }

        }

public void assignTeam(Team team) {
        this.assignedTeam = team;
        }
```




## 6. Observations

n/a