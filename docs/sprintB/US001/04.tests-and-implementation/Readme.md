# US001 - Register a skill

## 4. Tests 

**Test 1:** This test ensures that the skill was successfully registered and the returned Optional is not empty.

**Test 2:** This test verifies if the name of the registered skill is the same as the one provided.

**Test 3:** This test verifies if the description of the registered skill is the same as the one provided.

**Test 4:** This test verifies if it's possible to register an already existing skill.

**Test 5:** This test verifies if user has permissions to register a skill.


	void registerSkill() throws ParseException {
        Employee employee = new Employee("Gervasio Silva", dateFormat.parse("01/01/1950"), "aaasa@ass.pt", 913456789, dateFormat.parse("01/01/1980"), "123456789", "avenida de cima, andar de baixo, numero ao lado", "87654321", "cozinheiro");

        String skillName = "Bananas";
        String skillDescription = "Estragadas";
        boolean userValidation = true;


        Optional<Skill> registeredSkill = employee.registerSkill(skillName, skillDescription, userValidation);


        assertTrue(registeredSkill.isPresent()); //Test 1
        assertEquals(skillName, registeredSkill.get().getSkillName()); //Test 2
        assertEquals(skillDescription, registeredSkill.get().getSkillDescription()); //Test 3

        // already existing skill - Test 4
        Optional<Skill> registeredSkill3 = employee.registerSkill(skillName, skillDescription, userValidation);
        assertTrue(registeredSkill3.isEmpty());

        skillName = "Pessegos";
        skillDescription = "Estragados";
        userValidation = false;


        //user without permission - Test 5
        Optional<Skill> registeredSkill2 = employee.registerSkill(skillName, skillDescription, userValidation);
        assertFalse(registeredSkill2.isPresent());

    }
	


## 5. Construction (Implementation)

### Class RegisterSkillUI
```java
public RegisterSkillUI() {
        controller = new RegisterSkillController();
    }
```

### Class RegisterSkillController 

```java
public Optional<Skill> registerSkill(String skillName, String skillDescription) {
    try {
        Employee employee = matchEmployeeByRole();
        return employee.registerSkill(skillName, skillDescription, currentUserLogInValidation());
    }catch (Exception e){
        System.out.println("Error occurred while registering a skill");
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

#### Method that registers the skill in employee class:
```java
public Optional<Skill> registerSkill(String skillName, String skillDescription, boolean userValidation) {
    boolean validateAddedRepository;
    try {
        if (userValidation) {
            Skill skill = new Skill(skillName, skillDescription);
            validateAddedRepository = Repositories.getInstance().getSkillRepository().addSkillRep(skill);
            if (validateAddedRepository) {
                return Optional.of(skill);
            } else {
                return Optional.empty();
            }
        } else {
            System.out.println("This user don't have permissions to register skills");
            return Optional.empty();
        }
    } catch (Exception e) {
        return Optional.empty();
    }
}
```
### Class Skill

```java
public Skill(String skillName, String skillDescription){
        this.skillName = skillName;
        this.skillDescription = skillDescription;
    }
```


## 6. Integration and Demo

* For demo purposes some skills are bootstrapped while system starts.


## 7. Observations

n/a