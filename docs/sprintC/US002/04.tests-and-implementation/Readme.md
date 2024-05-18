# US002 - Register a job

## 4. Tests 

**Test 1:** This test ensures that the job was successfully registered and the returned Optional is not empty.

**Test 2:** This test verifies if the name of the registered job is the same as the one provided.

**Test 3:** This test verifies if the description of the registered job is the same as the one provided.

**Test 4:** This test verifies if it's possible to register an already existing job.

**Test 5:** This test verifies if user has permissions to register a job.




	@Test
    void registerJob() throws ParseException {
        Employee employee = new Employee("Gervasio Silva", dateFormat.parse("01/01/1950"), "aaasa@ass.pt", 913456789, dateFormat.parse("01/01/1980"), "123456789", "avenida de cima, andar de baixo, numero ao lado", "87654321", "cozinheiro");

        String jobName = "Condutor";
        String jobDescription = "Triciclos";
        boolean userValidation =true;


        Optional<Job> registeredJob = employee.registerJob(jobName, jobDescription, userValidation);


        // Verify successful
        assertTrue(registeredJob.isPresent()); // Test 1
        assertEquals(jobName, registeredJob.get().getJobName()); // Test 2
        assertEquals(jobDescription, registeredJob.get().getJobDescription()); // Test 3

        //already existing job - Test 4
        Optional<Job> registeredJob2 = employee.registerJob(jobName, jobDescription, userValidation);
        assertFalse(registeredJob2.isPresent());

        jobName = "Cozinheiro";
        jobDescription = "Couves";
        userValidation =false;

        //user without permission - Test 5
        Optional<Job> registeredJob3 = employee.registerJob(jobName, jobDescription, userValidation);
        assertTrue(registeredJob3.isEmpty());

    }


## 5. Construction (Implementation)

### Class RegisterJobUI

```java
 public RegisterJobUI() {
        controller = new RegisterJobController();
    }
```

### Class RegisterJobController 

```java
public Optional<Job> registerJob(String jobName, String jobDescription){
    try {
        Employee employee = matchEmployeeByRole();
        return employee.registerJob(jobName,jobDescription, currentUserLogInValidation());
    }catch (Exception e){
        System.out.println("Error occurred while registering a job");
        return Optional.empty();
    }

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


#### Method that registers the job in employee class:
```java
public Optional<Job> registerJob(String jobName, String jobDescription, boolean userValidation) {
    boolean validateAddedRepository;
    try {
        if (userValidation) {
            Job job = new Job(jobName, jobDescription);
            validateAddedRepository = Repositories.getInstance().getJobRepository().addJobRep(job);

            if (validateAddedRepository) {
                return Optional.of(job);
            } else {
                return Optional.empty();
            }
        } else {
            System.out.println("This user don't have permissions to register jobs");
            return Optional.empty();
        }

    } catch (Exception e) {
        return Optional.empty();
    }
    
}
```
### Class Job
```java
public Job(String jobName, String jobDescription){
        this.jobName = jobName;
        this.jobDescription = jobDescription;

    }
```



## 6. Integration and Demo

* For demo purposes some tasks are bootstrapped while system starts.


## 7. Observations

n/a