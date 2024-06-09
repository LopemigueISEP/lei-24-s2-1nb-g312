# US025 - As a Collaborator, I want to record the completion of a task.

## 4. Tests 

**Test 1:** Check that the task is really completed

	@Test
    void testComplete() {
        task.complete("Completed", endDate);
        assertEquals(TaskStatus.DONE, task.getStatus());
        assertEquals("Completed", task.getObservation());
        assertEquals(endDate, task.getEndDate());
    }

**Test 2:** Check that the repository correctly returns all completable tasks.

    @Test
    void testGetTasksCompletable() throws ParseException {
        Date startDate = dateFormat.parse("01/06/2024 - 14");
        Date endDate = dateFormat.parse("02/06/2024 - 13");
        List<Task> completableTasks = taskRepository.getTasksCompletable(team1.getTeamEmployees().get(0));
        assertEquals(1, completableTasks.size());
        assertEquals(task6, completableTasks.get(0));
    }

**Test 3:** Check that completing a task updates its status to DONE

    @Test
    void testCompleteTask() throws ParseException {

        taskRepository.completeTask(task6, "Completed", dateFormat.parse("02/06/2024 - 13"));
        assertEquals(TaskStatus.DONE, task6.getStatus());
        assertEquals("Completed", task6.getObservation());
        assertEquals(dateFormat.parse("02/06/2024 - 13"), task6.getEndDate());
    }


## 5. Construction (Implementation)

### Class CompleteTaskUI

```java
public CompleteTaskUI() {
        completeTaskController = new CompleteTaskController();
        }
```

### Class CompleteTaskController

```java
public CompleteTaskController(){
        getTaskRepository();
        getEmployeeRepository();
        getAuthRepository();
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

* For demo purposes some tasks are bootstrapped while system starts.


## 7. Observations

n/a