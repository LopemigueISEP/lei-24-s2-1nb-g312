# US021 - As a GSM, I want to add a new entry to the To-Do List

## 4. Tests

**Test 1:** Check that everytime we create a new task the id is incremented

	@Test
    void testGetNextTaskId() {
        Task task = new Task( greenSpace, "Title", "Descr", TaskUrgency.HIGH, 2, TaskPosition.TODOLIST,taskRepository.getNextTaskId());
        taskRepository.addTask(task);
        int nextId = taskRepository.getNextTaskId();
        assertEquals(8, nextId);
    }

**Test 2:** Check that cannot create tasks with the same id that already exists

    @Test
    void testAddTaskExistingId(){
        Task task = new Task( greenSpace, "Title", "Descr", TaskUrgency.HIGH, 2, TaskPosition.TODOLIST,1);
        Optional<Task> taskSameID=taskRepository.addTask(task);
        assertFalse(taskSameID.isPresent());

    }

## 5. Construction (Implementation)

### Class AddEntryToDoListUI

```java
public AddEntryToDoListUI(){
        addEntryToDoListController=new AddEntryToDoListController();
        }
```

### Class AddEntryToDoListController

```java
public AddEntryToDoListController(){
        getGreenSpaceRepository();
        getTaskRepository();
        }
```

### Class GreenSpace

```java
public GreenSpace(String name,String address,double area,String typology,String greenSpaceManager){
        this.name=name;
        this.address=address;
        this.area=area;
        this.typology=typology;
        this.greenSpaceManager=greenSpaceManager;
        }

public GreenSpace(GreenSpace greenSpace){
        this.name=greenSpace.getName();
        this.address=greenSpace.getAddress();
        this.area=greenSpace.getArea();
        this.typology=greenSpace.getTypology();
        this.greenSpaceManager=greenSpace.getGreenSpaceManager();

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

* A new option on the GSM menu options was added.

* For demo purposes some tasks are bootstrapped while system starts.

## 7. Observations

n/a