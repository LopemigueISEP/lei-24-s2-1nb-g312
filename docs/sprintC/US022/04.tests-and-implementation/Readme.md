# US22 - As a GSM, I want to add a new entry in the Agenda.

## 4. Tests 

**Test 1:** Test adding task to agenda. 

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
	

**Test 2:** Check that it is not possible to create an instance of the Task class with a reference containing less than five chars - AC2. 

	




## 5. Construction (Implementation)

### Class AddEntryToAgendaUI

```java
    public AddEntryToAgendaUI() {
    addEntryAgendaController = new AddEntryAgendaController();
}

```

### Class AddEntryAgendaController

```java
    public AddEntryAgendaController() {
    getGreenSpaceRepository();
    getTaskRepository();
    getAuthRepository();
}

public void addTaskToAgenda(Task selectedTask, LocalDate startDate, LocalTime startTime) {
    taskRepository.addTaskAgenda(selectedTask, startDate, startTime);
}
```


### Employee
```java

public static Optional<Task> addTaskAgenda(GreenSpace selectedGreenSpace, Task selectedTask, LocalDate selectedDate, int startTime, TaskPosition agenda, boolean userValidation) {
    try {
        if (userValidation) {
            Task newTaskAgenda = new Task(selectedGreenSpace, selectedTask, selectedDate, startTime, agenda);
            Optional<Task> addedTask = Repositories.getInstance().getTaskRepository().addTask(newTaskAgenda);
            return addedTask;
        } else {
            System.out.println("This user doesn't have permissions to add tasks to Agenda");
            return Optional.empty();
        }
    } catch (Exception e) {
        System.out.println("Error occurred while adding a task to Agenda: " + e.getMessage());
        return Optional.empty();
    }
}
```
### Class Task

```java
    public void addTaskAgenda(LocalDate startDate, LocalTime startTime) {
    this.taskPosition=TaskPosition.AGENDA;
    this.status=TaskStatus.PENDING;
    LocalDateTime newStartDateTime = LocalDateTime.of(startDate, startTime);
    this.startDate=Date.from(newStartDateTime.atZone(ZoneId.systemDefault()).toInstant());
    this.endDate= calculateEndDate();
}
```
## 6. Integration and Demo

* A new option on the Admin menu and GSM menu options was added - show list of Agenda.


## 7. Observations

n/a