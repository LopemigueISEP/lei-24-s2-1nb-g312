# US20 - Register a green space (garden, medium-sized park or large-sized park) and its respective area

## 4. Tests 

**Test 1:** Check that green space list is empty

**Test 2:** This test ensure that the new registered green space is correctly added to the repository.

**Test 3:** This test ensure that the new registered green space is validated.

**Test 4:** This test ensure that the green space name already exists.

**Test 5:** This test ensure that the green space address already exists.

**Test 6:** This test tests the constructor.

**Test 6:** This tests verifies if the clone is a different object and if primitive field values are copied correctly

**Test 7:** This test tests the alphabetically order of the list.



	 @Test
    public void testGetGreenSpace() {
        GreenSpaceRepository repository = new GreenSpaceRepository();
        GreenSpace greenSpace = new GreenSpace("Passadiços do Paiva", "Arouca", 396.5, "Medium-Sized Park", "Marcelo");
        GreenSpace greenSpace1 = new GreenSpace("Passadiços do Paiva", "Arouca", 396.5, "Medium-Sized Park", "Marcelo");
        GreenSpace greenSpace2 = new GreenSpace("Passadiços do Paiva", "Arouca", 396.5, "Medium-Sized Park", "Marcelo"); // Duplicate
        List<GreenSpace> greenSpaces = repository.getGreenSpace();


        //Test 1
        assertEquals(0, greenSpaces.size());

        //Test 2
        assertTrue(addedGreenSpace.isPresent());
        assertNotEquals(greenSpace, addedGreenSpace.get());

        assertTrue(repository.addGreenSpace(greenSpace).isPresent()); 
        List<GreenSpace> greenSpaceList = repository.getGreenSpaceList();
        assertNotEquals(1, greenSpaceList.size()); 
        assertNotEquals(greenSpace, greenSpaceList.getFirst());
        assertEquals(greenSpaceList, repository.getGreenSpaceList());

        assertEquals(greenSpace.getName(), greenSpaces.getFirst().getName());

        repository.addGreenSpace(greenSpace1);
        assertTrue(repository.addGreenSpace(greenSpace2)); // Assert adding duplicate succeeds
    

        //Test 3
        assertTrue(repository.validateGreenSpace(greenSpace2));
        assertTrue(repository.validateGreenSpace(greenSpace));


        //Test 4
        assertTrue(repository.existsWithName("Passadiços do Paiva"));
        assertFalse(repository.existsWithName("Peneda/Gerês"));

         //Test 5

        assertTrue(repository.existsWithAddress("Arouca"));
        assertFalse(repository.existsWithAddress("Minho"));

        //Test 6

            public void testConstructor() {
        // Arrange
        String name = "Peneda/Gerês";
        String address = "Minho";
        double area = 965.0;
        String typology = "Large-Sized Park";
        String greenSpaceManager = "Luís Montenegro";

        // Act
        GreenSpace greenSpace = new GreenSpace(name, address, area, typology, greenSpaceManager);

        // Assert
        assertEquals(name, greenSpace.getName());
        assertEquals(address, greenSpace.getAddress());
        assertEquals(area, greenSpace.getArea());
        assertEquals(typology, greenSpace.getTypology());
        assertEquals(greenSpaceManager, greenSpace.getGreenSpaceManager());
    }

        //Test 7
    @Test
    public void testClone() {
        GreenSpace original = new GreenSpace("Passadiços do Paiva", "Arouca", 396.5, "Medium-Sized Park", "Marcelo");
        GreenSpace clone = original.clone();

        // Verify that the clone has the same attributes as the original
        assertEquals(original.getName(), clone.getName());
        assertEquals(original.getAddress(), clone.getAddress());
        assertEquals(original.getArea(), clone.getArea());
        assertEquals(original.getTypology(), clone.getTypology());
        assertEquals(original.getGreenSpaceManager(), clone.getGreenSpaceManager());

        // Modify the clone and verify that the original is not affected
        clone.setName("Passadiços de Vizela");
        clone.setArea(29.0);

        assertNotEquals(original.getName(), clone.getName());
        assertNotEquals(original.getArea(), clone.getArea());

       }


        
        

    




## 5. Construction (Implementation)

### Class RegisterGreenSpaceUI

```java

public void start(Stage primaryStage) {
    Platform.setImplicitExit(false);
    FXMLLoader fxmlLoader = new FXMLLoader(RegisterGreenSpaceUI.class.getResource("RegisterGreenSpace.fxml"));
    Scene scene;
    try {
        scene = new Scene(fxmlLoader.load());
        primaryStage.setTitle("MusgoSublime");
        primaryStage.setScene(scene);
        primaryStage.show();
        RegisterGreenSpaceController registerGreenSpaceController = fxmlLoader.getController();
        registerGreenSpaceController.initialize(true);


    } catch (IOException e) {
        throw new RuntimeException(e);
    }
}

```

### Class RegisterGreenSpaceController 

```java
    public Optional<GreenSpace> registerGreenSpace(String name, String address, double area, String typology, String greenSpaceManager) {
    try {
        GreenSpace greenSpace = new GreenSpace(name, address, area, typology, greenSpaceManager);
        return greenSpaceRepository.addGreenSpace(greenSpace);
    } catch (Exception e) {
        System.err.println("Error occurred while registering a green space: " + e.getMessage());
        return Optional.empty();
    }
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
```


## 6. Integration and Demo 

* A new option on the Admin menu and GSM menu options was added - show list of registered green spaces.
* For demo purposes some green spaces are bootstrapped while system starts.


## 7. Observations

n/a