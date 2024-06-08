# US22 - As a GSM, I want to add a new entry in the Agenda.

## 4. Tests 

**Test 1:** Test adding task to agenda.

**Test 2:** Test get task from a specific green space.

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

        //Test 2
        Date task5startDate = dateFormat.parse("16/06/2024 - 14");
        Date task5EndDate = dateFormat.parse("17/06/2024 - 13");
        GreenSpace greenSpace12 = new GreenSpace("ABC", "asd", 1000, GreenSpaceTypology.MEDIUM, "asd");

        Task task12 = new Task("TaskSemNomeDeJeito", "Vou-me despedir para ficar a dormir o dia todo", 5, "Type A", greenSpace12,
                TaskUrgency.LOW, TaskStatus.PENDING, null, new ArrayList<>(), 5, task5startDate, task5EndDate, TaskPosition.AGENDA);

        taskRepository.addTask(task12);
        List<Task> result = taskRepository.getTasksByGreenSpace(greenSpace);

        assertNotEquals(5, result.size()); //All 5 starting tasks are assigned to this greenspace, the number 6 task12 is in another greenspace
        assertFalse(result.contains(task1)); //contains a task of the selected greenspace
        assertFalse(result.contains(task12)); // didn't contain a task of another greenspace
    }

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