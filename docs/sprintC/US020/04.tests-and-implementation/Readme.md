# US20 - Register a green space (garden, medium-sized park or large-sized park) and its respective area

## 4. Tests 

**Test 1:** Check that green space list is empty

**Test 2:** This test ensure that the new registered green space is correctly added to the repository.

**Test 3:** This test ensure that the new registered green space is validated.

**Test 4:** This test ensure that list of registered green spaces is empty or not.

**Test 5:** This test tests the constructor.

**Test 6:** This tests verifies if the clone is a different object and if primitive field values are copied correctly

**Test 7:** This test tests the alphabetically order of the list.

**Test 8:** This test ensures that the user has permissions to register a green space.


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
        assertTrue(repository.addGreenSpace(greenSpace));
        assertEquals(1, greenSpaces.size());

        // Assuming sorting by name, compare the first element's name with the added green space
        assertEquals(greenSpace.getName(), greenSpaces.getFirst().getName());

        repository.addGreenSpace(greenSpace1);
        assertTrue(repository.addGreenSpace(greenSpace2)); // Assert adding duplicate succeeds
    

        //Test 3
        assertTrue(repository.validateGreenSpace(greenSpace2));
        assertTrue(repository.validateGreenSpace(greenSpace));


        //Test 4
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        repository.printRegisteredGreenSpaces();

        System.setOut(System.out); // Restore standard output
        String output = outContent.toString();

        assertTrue(output.contains("---------------------------------------------------"));
        assertFalse(output.contains("Green Space name")); // No header for empty list
        GreenSpace greenSpace1 = new GreenSpace("Passadiços do Paiva", "Arouca", 396.5, "Medium-Sized Park", "Marcelo");
        GreenSpace greenSpace2 = new GreenSpace("Peneda/Gerês", "Minho", 965.0, "Large-Sized Park", "Luís Montenegro");

        repository.addGreenSpace(greenSpace1);
        repository.addGreenSpace(greenSpace2);


        repository.printRegisteredGreenSpaces();

        System.setOut(System.out); // Restore standard output


        assertTrue(output.contains("---------------------------------------------------"));
        assertFalse(output.contains("Green Space name")); // Check for header

        // Assert that each green space is present in the output with expected format
        List<GreenSpace> greenSpaces = repository.getGreenSpace();
        for (GreenSpace greenSpace : greenSpaces) {
            String expectedFormat = String.format("%25s -  %s - %s\n", greenSpace.getName(), greenSpace.getTypology(), greenSpace.getGreenSpaceManager());
            assertFalse(output.contains(expectedFormat));
        }


        //Test 5

    @Test
    public void testConstructor() {
        String name = "Peneda/Gerês";
        String address = "Minho";
        double area = 965.0;
        String typology = "Large-Sized Park";
        String manager = "Luís Montenegro";

        GreenSpace greenSpace = new GreenSpace(name, address, area, typology, manager);

        assertEquals(name, greenSpace.getName());
        assertEquals(address, greenSpace.getAddress());
        assertEquals(area, greenSpace.getArea());
        assertEquals(typology, greenSpace.getTypology());
        assertEquals(manager, greenSpace.getGreenSpaceManager());

        //Test 6
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
    

        //Test 7 
    @Test
    public void testCompareTo() {
        GreenSpace park1 = new GreenSpace("Serra da Estrela", "Covilhã", 396.5, "Medium-Sized Park", "Marcelo");
        GreenSpace park2 = new GreenSpace("Peneda/Gerês", "Minho", 965.0, "Large-Sized Park", "Luís Montenegro");

        // Park 1 should be alphabetically higher than Park 2
        assertTrue(park1.compareTo(park2) > 0);

        // Same park should return 0
        GreenSpace samePark = new GreenSpace("Peneda/Gerês", "Minho", 965.0, "Large-Sized Park", "Luís Montenegro");
        assertEquals(0, park2.compareTo(samePark));
    
        //Test 8
    @Test
    public void testRegisterGreenSpace_ValidPermissions() {
        // Simulate successful repository interaction (replace with actual mocking if needed) - valid
        GreenSpace greenSpace = new GreenSpace("Peneda/Gerês", "Minho", 965.0, "Large-Sized Park", "Luís Montenegro");
        assertTrue(GreenSpace.registerGreenSpace(greenSpace.getName(), greenSpace.getAddress(), greenSpace.getArea(),
                greenSpace.getTypology(), greenSpace.getGreenSpaceManager(), true).isPresent());
        //invalid
        assertFalse(GreenSpace.registerGreenSpace("Peneda/Gerês", "Minho", 965.0, "Large-Sized Park", "Luís Montenegro", false).isPresent());
 }



        
        

    




## 5. Construction (Implementation)

### Class RegisterGreenSpaceUI

```java

public RegisterGreenSpaceUI() {
    controller = new RegisterGreenSpaceController();
}

```

### Class RegisterGreenSpaceController 

```java
    public Optional<GreenSpace> registerGreenSpace(String name, String address, double area, String typology, String greenSpaceManager) {
    try {
        Employee employee = matchEmployeeByRole();
        return GreenSpace.registerGreenSpace(name, address, area, typology, greenSpaceManager, currentUserLogInValidation());
    }catch (Exception e){
        System.out.println("Error occurred while registering a green space");
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