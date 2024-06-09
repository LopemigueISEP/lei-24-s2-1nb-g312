# US025 - As a GSM, I want to Cancel an entry in the Agenda

## 4. Tests

**Test 1:** Check that the task is really canceled

	@Test
    public void testCancel() {
        task.cancel();
        assertEquals(TaskStatus.CANCELED, task.getStatus());
    }

**Test 2:** Check that the repository correctly returns all cancelable tasks.

    @Test
    public void testGetTasksCancelable() {
        List<Task> cancelableTasks = taskRepository.getTasksCancelable();
        assertEquals(4, cancelableTasks.size());
        assertTrue(cancelableTasks.contains(task1));
    }

**Test 3:** Check that canceling a task updates its status to CANCELED

    @Test
    public void testCancelTask() {
        taskRepository.addTask(task1);
        taskRepository.cancelTask(task1);
        assertEquals(TaskStatus.CANCELED, task1.getStatus());
    }



## 5. Construction (Implementation)

### Class AddEntryToDoListUI

```java
public CancelEntryAgendaUI(){
        cancelEntryAgendaController=new CancelEntryAgendaController();
        }
```

### Class AddEntryToDoListController

```java
public CancelEntryAgendaController(){
        getTaskRepository();
        }
```

### Class Task

```java
public Task(String title,String description,TaskUrgency urgency,int taskExpectedDuration,GreenSpace greenSpace,TaskPosition taskPosition){
        this.title=title;
        this.description=description;
        this.taskExpectedDuration=taskExpectedDuration;
        this.greenSpace=greenSpace;
        this.urgency=urgency;
        this.assignedVehicles=new ArrayList<>();
        this.taskPosition=taskPosition;


        }
```

## 6. Integration and Demo

* A new option on the Employee menu options was added.


## 7. Observations

n/a