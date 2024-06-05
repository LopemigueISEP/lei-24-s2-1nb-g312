# US22 - Add a new entry in the Agenda

## 4. Tests 

**Test 1:** Check that it is not possible to create an instance of the Task class with null values. 

	@Test(expected = IllegalArgumentException.class)
		public void ensureNullIsNotAllowed() {
		Task instance = new Task(null, null, null, null, null, null, null);
	}
	

**Test 2:** Check that it is not possible to create an instance of the Task class with a reference containing less than five chars - AC2. 

	@Test(expected = IllegalArgumentException.class)
		public void ensureReferenceMeetsAC2() {
		Category cat = new Category(10, "Category 10");
		
		Task instance = new Task("Ab1", "Task Description", "Informal Data", "Technical Data", 3, 3780, cat);
	}

_It is also recommended to organize this content by subsections._ 


## 5. Construction (Implementation)

### Class AddEntryToAgendaUI

```java
public void start(Stage primaryStage) {
    Platform.setImplicitExit(false);
    FXMLLoader fxmlLoader = new FXMLLoader(AddEntryToDoListUI.class.getResource("AddEntryToAgendaUI.fxml"));
    Scene scene;
    try {
        scene = new Scene(fxmlLoader.load());
        primaryStage.setTitle("MusgoSublime");
        primaryStage.setScene(scene);

        primaryStage.show();

    } catch (IOException e) {
        throw new RuntimeException(e);
    }
}
@Override
public void initialize(URL url, ResourceBundle resourceBundle) {
    String userEmail = addEntryAgendaController.getUserEmail();
    initializeComboBoxGreenSpaces(userEmail);
    GreenSpace selectedGreenSpace = (GreenSpace) cmbGreenSpace.getSelectionModel().getSelectedItem();
    initializeTaskComboBox(selectedGreenSpace);
    initializeDatePicker();

}

```

### Class AddEntryAgendaController

```java
    public AddEntryAgendaController() {
    getGreenSpaceRepository();
    getTaskRepository();
    getAuthRepository();
}
    public Optional<Task> addTaskToAgenda(GreenSpace selectedGreenSpace, Task selectedTask, LocalDate startDate, int startTime) {
    Optional<Task> newTaskAgenda = Optional.empty();
    newTaskAgenda=Employee.addTaskAgenda(selectedGreenSpace,selectedTask,startDate,startTime,TaskPosition.AGENDA,true);
    if (newTaskAgenda.isPresent()){
        return  newTaskAgenda;
    }
    else {
        return Optional.empty();
    }
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
 public Task(GreenSpace selectedGreenSpace, Task selectedTask, LocalDate selectedDate, int startTime, TaskPosition taskPosition) {
    if (taskPosition.equals(TaskPosition.AGENDA)) {
        this.greenSpace = selectedGreenSpace;
        this.selectedTask = selectedTask;
        this.selectedDate = selectedDate;
        this.startTime = startTime;
        this.taskPosition = taskPosition;
    }

}
```
## 6. Integration and Demo

* A new option on the Admin menu and GSM menu options was added - show list of Agenda.


## 7. Observations

n/a