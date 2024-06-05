# US006 - Create a Task 

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
### Class AddEntryToDoListUI
```java
public AddEntryToDoListUI(){
    addEntryToDoListController= new AddEntryToDoListController();
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
public GreenSpace(String name, String address, double area, String typology, String greenSpaceManager){
    this.name = name;
    this.address = address;
    this.area = area;
    this.typology = typology;
    this.greenSpaceManager = greenSpaceManager;
}

public GreenSpace(GreenSpace greenSpace) {
    this.name = greenSpace.getName();
    this.address = greenSpace.getAddress();
    this.area = greenSpace.getArea();
    this.typology = greenSpace.getTypology();
    this.greenSpaceManager = greenSpace.getGreenSpaceManager();

}

```

### Class Task

```java
public Task(String title, String description, TaskUrgency urgency, int taskExpectedDuration, GreenSpace greenSpace, TaskPosition taskPosition) {
    this.title = title;
    this.description = description;
    this.taskExpectedDuration = taskExpectedDuration;
    this.greenSpace = greenSpace;
    this.urgency = urgency;
    this.assignedVehicles = new ArrayList<>();
    this.taskPosition = taskPosition;


}
```



## 6. Integration and Demo 

* A new option on the Employee menu options was added.

* For demo purposes some tasks are bootstrapped while system starts.


## 7. Observations

n/a