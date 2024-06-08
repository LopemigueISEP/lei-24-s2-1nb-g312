# US028 - As a Collaborator, I wish to consult the tasks assigned to me between two dates

## 4. Tests 

**Test 1:** Check if retrieves tasks within the date range.

**Test 2:** Check if not retrieves tasks outside the data range.

**Test 3:** Check if returns tasks without team assigned.

**Test 4:** Check if returns tasks without matching employee.

	// Tasks within the date range
    LocalDate startDate = LocalDate.of(2024, 6, 1);
    LocalDate endDate = LocalDate.of(2024, 6, 7);
    List<Task> result = employee.getTasksAssignedToMeBetweenToDates(startDate, endDate);
    assertEquals(2, result.size());
    assertTrue(result.contains(task1));
    assertTrue(result.contains(task2));

    // Tasks outside the date range
    startDate = LocalDate.of(2024, 6, 10);
    endDate = LocalDate.of(2024, 6, 15);
    result = employee.getTasksAssignedToMeBetweenToDates(startDate, endDate);
    assertEquals(0, result.size());

    // Tasks with no assigned team
    startDate = LocalDate.of(2024, 6, 1);
    endDate = LocalDate.of(2024, 6, 7);
    result = employee.getTasksAssignedToMeBetweenToDates(startDate, endDate);
    assertEquals(2, result.size());
    assertFalse(result.contains(taskNoTeam));

    // Tasks with no matching employee email
    startDate = LocalDate.of(2024, 6, 1);
    endDate = LocalDate.of(2024, 6, 7);
    result = employee.getTasksAssignedToMeBetweenToDates(startDate, endDate);
    assertEquals(0, result.size());
	



## 5. Construction (Implementation)

### Class TasksAssignedToMeBetweenToDatesController 

```java
 public List<Task> getTasksAssignedToMeBetweenToDates(LocalDate startDate, LocalDate endDate) {

    List<Task> taskList = new ArrayList<>();
    try {
        taskList = taskRepository.getTasksAssignedToMeBetweenToDates(getLoggedInUserEmail(), startDate, endDate);
    }catch (Exception e){
        throw new RuntimeException("error in getTasksAssignedToMeBetweenToDates",e);
    }
    return taskList;
}
```

### Class Employee

```java
 public Employee(String name, Date birthDate, String email, int phoneNumber, Date admissionDate, String taxpayerNumber, String address, String docNumber, String job,List<Skill> skills) {

    this.name = name;
    this.birthDate = birthDate;
    this.phoneNumber = phoneNumber;
    this.taxpayerNumber = taxpayerNumber;
    this.address = address;
    this.docNumber = docNumber;
    this.admissionDate = admissionDate;
    this.email = email;
    this.job = job;
    this.skills = new ArrayList<>(skills);

}
```

### Class Task

```java
public Task(String title, String description, int taskExpectedDuration, String type, GreenSpace greenSpace, TaskUrgency urgency, TaskStatus status, Team assignedTeam, ArrayList<Vehicle> assignedVehicles, int taskID, Date startDate, Date endDate, TaskPosition taskPosition) {
        this.title = title;
        this.description = description;
        this.taskExpectedDuration = taskExpectedDuration;
        this.type = type;
        this.greenSpace = greenSpace;
        this.urgency = urgency;
        this.status = status;
        this.assignedTeam = assignedTeam;
        this.assignedVehicles = assignedVehicles;
        this.taskID = taskID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.taskPosition = taskPosition;
    }

```

#### getter method

```java
public List<Task> getTasksAssignedToMeBetweenToDates(String userEmail, LocalDate startDate, LocalDate endDate) {

    // Converte LocalDate para ZonedDateTime usando o fuso horário do sistema
    ZonedDateTime zonedStartDate = startDate.atStartOfDay(ZoneId.systemDefault());
    ZonedDateTime zonedEndDate = endDate.atStartOfDay(ZoneId.systemDefault());

    // Converte ZonedDateTime para Date
    Date startDateDate = Date.from(zonedStartDate.toInstant());
    Date endDateDate = Date.from(zonedEndDate.toInstant());

    List<Task> employeeTasksBetweenDates = new ArrayList<>();
    List<Employee> taskEmployees = new ArrayList<>();

    for(Task task : getAgenda()) {
        try {
            if (task != null) {
                if ((startDateDate.before(task.getEndDate()) || startDateDate.equals(task.getEndDate())) && (endDateDate.after(task.getStartDate()) || endDateDate.equals(task.getStartDate()))) {
                    if (task.getAssignedTeam() != null && task.getAssignedTeam().getTeamEmployees() != null) {
                        taskEmployees = task.getAssignedTeam().getTeamEmployees();
                        for (Employee emp : taskEmployees) {
                            if (emp != null && emp.getEmail().equals(userEmail)) {
                                employeeTasksBetweenDates.add(task);
                            }
                        }
                    }
                }
            }
        } catch (NullPointerException e) {
            System.err.println("Error: Null value encountered - " + e.getMessage());
            throw new RuntimeException("Null value encountered during task processing", e);
        } catch (Exception e) {
            System.err.println("An unexpected error occurred - " + e.getMessage());
            throw new RuntimeException("An unexpected error occurred during task processing", e);
        }
    }

    return employeeTasksBetweenDates;
}

```



## 6. Integration and Demo 

n/a


## 7. Observations

n/a