# US006 - Create a Vehicle

## 4. Tests 

**Test 1:** Ensures that the Vehicle constructor correctly initializes all properties 

	@Test
    public void testVehicleConstructor() {
        assertNotNull(vehicle);
        assertEquals("11-AA-11", vehicle.getRegistrationPlate());
        assertEquals("Toyota", vehicle.getBrand());
        assertEquals("Yaris", vehicle.getModel());
        assertEquals("Car", vehicle.getType());
        assertEquals(1000, vehicle.getTare());
        assertEquals(1500, vehicle.getGrossWeight());
        assertEquals(20000, vehicle.getCurrentKm());
        assertEquals(registerDate, vehicle.getRegisterDate());
        assertEquals(acquisitionDate, vehicle.getAcquisitionDate());
        assertEquals(10000, vehicle.getCheckUpKmFrequency());
    }
	

**Test 2:** Confirms the compareTo method works correctly by comparing registration dates. 

	@Test
    public void testCompareTo() {
        Vehicle otherVehicle = new Vehicle("33-CC-33", "Ford", "Fiesta", "Car", 1000, 1500, 0, new Date(System.currentTimeMillis() + 1000), new Date(), 10000);
        assertTrue(vehicle.compareTo(otherVehicle) < 0);
        assertTrue(otherVehicle.compareTo(vehicle) > 0);
    }

**Test 3:**  Checks the clone method to ensure it creates an exact copy of the vehicle
    
    @Test
    public void testClone() {
    Vehicle clonedVehicle = vehicle.clone();
    assertNotSame(vehicle, clonedVehicle);
    assertEquals(vehicle.getRegistrationPlate(), clonedVehicle.getRegistrationPlate());
    assertEquals(vehicle.getBrand(), clonedVehicle.getBrand());
    }


## 5. Construction (Implementation)

### Class CreateVehicleUI 

```java
public class CreateVehicleUI implements Runnable {
    public CreateVehicleUI() {
        controller = new CreateVehicleController();
    }
}
```

### Class CreateVehicleController

```java
public class CreateVehicleController {

    public CreateVehicleController() {
        getVehicleRepository();
    }

    public Optional<Vehicle> createVehicle(String registrationPlate, String brand, String model
            , String type, double tare, double grossWeight
            , double currentKm, Date registerDate, Date acquisitionDate
            , double checkUpKmFrequency, double checkUpKm) {

        Optional<Vehicle> newVehicle = Optional.empty();
        Vehicle vehicle = new Vehicle(registrationPlate, brand, model
                , type, tare, grossWeight
                , currentKm, registerDate, acquisitionDate
                , checkUpKmFrequency);

        newVehicle = vehicleRepository.add(vehicle);


        return newVehicle;
    }
}
```

### Class Vehicle

```java
public class Vehicle implements Comparable<Vehicle> {
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
        this.checkUps.add(new CheckUp(currentKm, new Date())); // add first checkup when car is bought
    }
}
```


## 6. Integration and Demo 

* A new option on the VFM and Admin menu options was added.

* For demo purposes some vehicles are bootstrapped while system starts.

