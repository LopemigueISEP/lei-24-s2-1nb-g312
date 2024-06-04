# US026 - Assign one or more vehicles to an entry in the Agenda

## 4. Tests 

**Test 1:** Check if done or canceled tasks cannot be selected to assign vehicles.

**Test 2:** Check if a pending status task can select a vehicle. 

**Test 3:** Check if a vehicle not assigned to any tasks, in the same working hours, can be assigned.

**Test 4:** Check if there is any vehicle to assign after assign all vehicles to tasks in the same working hours.


	void getAllAgendaTasksExceptDoneCanceled() throws ParseException {


        List<Task> result = taskRepository.getAllAgendaTasksExceptDoneCanceled();

        // in setup 5 tasks 1 is done and 1 is canceled
        assertEquals(3, result.size());

        Task taskTeste6 = new Task(dateFormat.parse("16/06/2024 - 14"),TaskStatus.Done);
        taskRepository.addTask(taskTeste6);
        List<Task> result1 = taskRepository.getAllAgendaTasksExceptDoneCanceled();

        assertNotEquals(4, result1.size());
        assertEquals(taskTeste6.getStatus(),TaskStatus.Done);
        assertFalse(result1.contains(taskTeste6)); //check if not contains a done status task
        assertTrue(result1.contains(task1)); // check if contains a pending status task

    }

    

    @Test
    void getVehicleAvaiability() {

        List<Vehicle> availableVehiclesForTask1 = taskRepository.getVehicleAvaiability(task1, vehicles);
        List<Vehicle> availableVehiclesForTask5 = taskRepository.getVehicleAvaiability(task5, vehicles);

        assertEquals(1, availableVehiclesForTask1.size()); // Only one vehicle is available for task1
        assertTrue(availableVehiclesForTask1.contains(vehicle2)); // Vehicle2 is available for task1
        assertEquals(0, availableVehiclesForTask5.size()); // No vehicle available for task5
    }
}



## 5. Construction (Implementation)

### Class AssignVehicleToAgendaEntryController 

```java
public List<Task> getAvailableTasks(){
    List<Task> listAvailableTasks;
    try{
        listAvailableTasks = taskRepository.getAllAgendaTasksExceptDoneCanceled();

    }catch (NullPointerException nullPointerException) {
        listAvailableTasks = new ArrayList<>();
        throw new RuntimeException("no data in tasksExceptDoneCanceled in AssignVehicleToAgendaEntryController",nullPointerException);
    }catch (Exception e){
        throw new RuntimeException("error in getAvailableTasks in AssignVehicleToAgendaEntryController",e);
    }

    return listAvailableTasks;
}
```

```java
public List<Vehicle> getAvailableVehicles(Task taskSelected){

        List<Vehicle> listAvailableVehicles;
        try {
            listAvailableVehicles = taskRepository.getVehicleAvaiability(taskSelected, vehicleRepository.getVehicles());
        }catch (NullPointerException nullPointerException) {
            listAvailableVehicles = new ArrayList<>();
            throw new RuntimeException("no data in getVehicleAvaiability in AssignVehicleToAgendaEntryController",nullPointerException);
        }catch (Exception e) {
            throw new RuntimeException("error in getAvailableVehicles in AssignVehicleToAgendaEntryController",e);
        }
        return listAvailableVehicles;
    }

```
```java
public void assignVehicleToTask(Vehicle vehicle, Task task){
try {
task.assignVehicle(vehicle);
}catch (NullPointerException nullPointerException){
throw  new RuntimeException("doesn't found the vehicle list in task",nullPointerException);
}catch (Exception e){
throw new RuntimeException("error in assigneVehicleToTask in AssignVehicleToAgendaEntryController", e);
}
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
### Class Vehicle

```java
public Vehicle(String registrationPlate, String brand, String model
            , String type, double tare, double grossWeight
            , double currentKm, Date registerDate, Date acquisitionDate
            , double checkUpKmFrequency) {
        validateVehicle(registrationPlate);
        this.registrationPlate = registrationPlate;
        this.brand = brand;
        this.model = model;
        this.type = type;
        this.tare = tare;
        this.grossWeight = grossWeight;
        this.currentKm = currentKm;
        this.registerDate = registerDate;
        this.acquisitionDate = acquisitionDate;
        this.checkUpKmFrequency = checkUpKmFrequency;
        this.checkUps.add(new CheckUp(currentKm, new Date())); 
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

## 6. Integration and Demo 

n/a

## 7. Observations

n/a