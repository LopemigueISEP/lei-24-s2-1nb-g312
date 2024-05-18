# US003 - Register a collaborator with a job and fundamental characteristics

## 4. Tests 

**Test 1:** This test ensures that the collaborator was successfully registered and the returned Optional is not empty.

**Test 2:** This test ensure that the new registered collaborator is correctly added to the repository

**Test 3:** This test verifies if it's possible to register an already existing collaborator.

**Test 4:** This tests verifies if the clone is a different object and if primitive field values are copied correctly

	 @Test
    void registerCollaborator()  {
    try{
    Employee employee1 = new Employee("Gervasio Silva", dateFormat.parse("01/01/1950"), "aaasa@ass.pt", 913456789, dateFormat.parse("01/01/1980"), "123456789", "avenida de cima, andar de baixo, numero ao lado", "87654321", "cozinheiro");
    Employee employee2 = new Employee("Rute Carmo", dateFormat.parse("12/06/1980"), "aaa5a@ass.pt", 912345678, dateFormat.parse("12/06/2000"), "987654321", "avenida de baixo, andar de cima, numero a direita", "12345678", "padeiro");
    EmployeeRepository employeeRepository = new EmployeeRepository();
        // Test 1

        List<Employee> originalList = new ArrayList<>();
       
        employeeRepository.addEmployee(employee1);
        employeeRepository.addEmployee(employee2);
        originalList.add(employee1);
        originalList.add(employee2);

        Optional<Employee> result = employeeRepository.getEmployee("123456789");
        List<Employee> returnedList = employeeRepository.getEmployee();

        assertTrue(result.isPresent());
        assertEquals(employee1, result.get());

        // returned list is a copy
        assertNotSame(originalList, returnedList);
        assertEquals(originalList.size(), returnedList.size());

        assertEquals(Optional.empty(),employeeRepository.getEmployee("456327894"));
    
        // Test2
        Optional<Employee> addedEmployee = employeeRepository.addEmployee(employee1);
        assertTrue(addedEmployee.isPresent());
        assertFalse(employeeRepository.employeeList.isEmpty());
        assertEquals(Optional.empty(), employeeRepository.addEmployee(employee1));
    
        // Test 3
        boolean validateNotExist = employeeRepository.validateEmployee(employee1);
        assertTrue(validateNotExist);

        employeeRepository.addEmployee(employee1);
        boolean validateEmployeeExists = employeeRepository.validateEmployee(employee1);
        assertFalse(validateEmployeeExists);
    
        // Test4
        Employee clonedEmployee = employee.clone();
        assertNotSame(employee, clonedEmployee);

        assertEquals(employee.getName(), clonedEmployee.getName());
        assertEquals(employee.getBirthDate(), clonedEmployee.getBirthDate());
        assertEquals(employee.getEmail(), clonedEmployee.getEmail());
        assertEquals(employee.getPhoneNumber(), clonedEmployee.getPhoneNumber());
        assertEquals(employee.getAdmissionDate(), clonedEmployee.getAdmissionDate());
        assertEquals(employee.getTaxpayerNumber(), clonedEmployee.getTaxpayerNumber());
        assertEquals(employee.getAddress(), clonedEmployee.getAddress());
        assertEquals(employee.getDocNumber(), clonedEmployee.getDocNumber());
        assertEquals(employee.getJob(), clonedEmployee.getJob());
        } catch (ParseException e) {
            raiseInvalidInput();
        }
    }



## 5. Construction (Implementation)

### Class RegisterCollaboratorUI

```java

    public RegisterCollaboratorUI() {
        controller = new RegisterCollaboratorController();
    }

```

### Class RegisterCollaboratorController 

```java
    public Optional<Employee> registerEmployee(String name, Date birthDate, String email, int phoneNumber, Date admissionDate,
                                               String taxpayerNumber, String address, String docNumber, String jobTitle) {
    Optional<Employee> newEmployee;
    Employee employee = new Employee(name, birthDate, email, phoneNumber, admissionDate, taxpayerNumber, address, docNumber, jobTitle);


    Optional<Job> selectedJob = jobRepository.getJobTitle(jobTitle);


    employee.setJob(String.valueOf(selectedJob.get()));
    newEmployee = employeeRepository.addEmployee(employee);

    return newEmployee;
}
```

### Class Employee

```java
    public Employee(String name, Date birthDate, String email, int phoneNumber, Date admissionDate, String taxpayerNumber, String address, String docNumber, String job) {

    this.name = name;
    this.birthDate = birthDate;
    this.phoneNumber = phoneNumber;
    this.taxpayerNumber = taxpayerNumber;
    this.address = address;
    this.docNumber = docNumber;
    this.admissionDate = admissionDate;
    this.email = email;
    this.job = job;
    this.skills = new ArrayList<>();
}
```


## 6. Integration and Demo 

* A new option on the Admin menu and HRM menu options was added - show list of registered employees.

* For demo purposes some employees are bootstrapped while system starts.


## 7. Observations

n/a