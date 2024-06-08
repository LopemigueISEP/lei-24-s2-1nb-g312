# US20 - As a Green Space Manager (GSM), I want to register a greenspace (garden, medium-sized park or large-sized park) and its respectivearea.

## 4. Tests 


**Test 1:** This test ensure that the new registered green space is correctly added to the repository.

**Test 2:** This test ensure that the new registered green space is validated.

**Test 3:** This test ensure that the green space name already exists.

**Test 4:** This test ensure that the green space address already exists.

**Test 5:** This test tests the constructor.

**Test 6:** This tests verifies if the clone is a different object and if primitive field values are copied correctly




	 @Test
    public void testGetGreenSpace() {
    private GreenSpaceRepository repository;
    private GreenSpace greenSpace1;
    private GreenSpace greenSpace2;

    @BeforeEach
    public void setUp() {
        repository = new GreenSpaceRepository();
        greenSpace1 = new GreenSpace("Park A", "Address A", 1000.0, GreenSpaceTypology.MEDIUM, "GSM");
        greenSpace2 = new GreenSpace("Garden B", "Address B", 2000.0, GreenSpaceTypology.GARDEN, "Admin");
    }

        //Test 1
       Optional<GreenSpace> addedGreenSpace = repository.addGreenSpace(greenSpace1);
        assertTrue(addedGreenSpace.isPresent());
        assertTrue(repository.getGreenSpaceList().contains(greenSpace1));

        // Test adding an invalid green space (already exists)
        Optional<GreenSpace> addedGreenSpace2 = repository.addGreenSpace(greenSpace1);
        assertFalse(addedGreenSpace2.isPresent());
    
    

        //Test 2
        assertTrue(repository.validateGreenSpace(greenSpace1));
        repository.addGreenSpace(greenSpace1);
        assertFalse(repository.validateGreenSpace(greenSpace1));


        //Test 3
        repository.addGreenSpace(greenSpace1);
        repository.addGreenSpace(greenSpace2);

        assertTrue(repository.existsWithName("Park A"));

        assertFalse(repository.existsWithName("Square C"));

         //Test 4

        repository.addGreenSpace(greenSpace1);
        repository.addGreenSpace(greenSpace2);

        assertTrue(repository.existsWithAddress("Address A"));

        assertFalse(repository.existsWithAddress("Address C"));


        //Test 5
        GreenSpace clonedGreenSpace = greenSpace.clone();
        assertEquals(greenSpace, clonedGreenSpace);
        assertNotSame(greenSpace, clonedGreenSpace);

        //Test 6

        String name = "Park A";
        String address = "Address A";
        double area = 1000.0;
        GreenSpaceTypology typology = GreenSpaceTypology.MEDIUM;
        String greenSpaceManager = "Manager A";

     
        GreenSpace greenSpace = new GreenSpace(name, address, area, typology, greenSpaceManager);
        /assertEquals(name, greenSpace.getName());
        assertEquals(address, greenSpace.getAddress());
        assertEquals(area, greenSpace.getArea(), 0.001); // Use delta for double comparison
        assertEquals(typology, greenSpace.getTypology());
        assertEquals(greenSpaceManager, greenSpace.getGreenSpaceManager());

       }


## 5. Construction (Implementation)

### Class RegisterGreenSpaceUI

```java
public RegisterGreenSpaceUI(){
    registerGreenSpaceController= new RegisterGreenSpaceController();

}

```

### Class RegisterGreenSpaceController 

```java
    public RegisterGreenSpaceController(){
    getGreenSpaceRepository();
    getAuthRepository();
    getEmployeeRepository();
}
public static Optional<GreenSpace> registerGreenSpace(String name, String address, double area, GreenSpaceTypology typology, String greenSpaceManager) {
    try {
        GreenSpace greenSpace = new GreenSpace(name, address, area, typology, greenSpaceManager);
        return greenSpaceRepository.addGreenSpace(greenSpace);
    } catch (Exception e) {
        System.err.println("Error occurred while registering a green space: " + e.getMessage());
        return Optional.empty();
    }
}

public GreenSpaceTypology[] getGreenSpaceTypologies() {
    return GreenSpaceTypology.values();
}

```

### Class Employee

```java
public static Optional<GreenSpace> registerGreenSpace(String name, String address, double area, GreenSpaceTypology typology, String greenSpaceManager, boolean userValidation) {
    try {
        if (userValidation) {
            GreenSpace greenSpace = new GreenSpace(name, address, area, typology, greenSpaceManager);
            Optional<GreenSpace> addedGreenSpace = Repositories.getInstance().getGreenSpaceRepository().addGreenSpace(greenSpace);
            return addedGreenSpace;
        } else {
            System.out.println("This user doesn't have permissions to register green spaces");
            return Optional.empty();
        }
    } catch (Exception e) {
        System.err.println("Error occurred while registering a green space: " + e.getMessage());
        return Optional.empty();
    }
}
```

### Class GreenSpace

```java
public GreenSpace(GreenSpace greenSpace) {
    this.name = greenSpace.getName();
    this.address = greenSpace.getAddress();
    this.area = greenSpace.getArea();
    this.typology = greenSpace.getTypology();
    this.greenSpaceManager = greenSpace.getGreenSpaceManager();

}
```


## 6. Integration and Demo 

* A new option on the Admin menu and GSM menu options was added - show list of registered green spaces.


## 7. Observations

n/a