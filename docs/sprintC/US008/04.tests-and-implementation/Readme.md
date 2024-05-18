# US006 - Create a Task 

## 4. Tests 

**Test 1:** Check if getVehicle method return the correct vehicle in the vehicle class.

	@Test
    public void testGetVehicleSuccess() {
        vehicleRepo.add(vehicle1);
        Vehicle foundVehicle = vehicleRepo.getVehicle("11-AA-11");
        assertEquals(vehicle1.getRegistrationPlate(), foundVehicle.getRegistrationPlate(), "The vehicle retrieved should match the one added.");
    }

**Test 2:** Tests isCheckUpDue method in vehicle class.
    
    @Test
    public void testIsCheckUpDue() {
        vehicle.setCurrentKm(29000); // Close to the next check-up frequency of 30000 (10000 + 20000 initial)
        assertTrue(vehicle.isCheckUpDue());
        vehicle.setCurrentKm(27000); // Not close enough for a check-up
        assertFalse(vehicle.isCheckUpDue());
    }

**Test 3:** Test if compareTo method in vehicle class is properly sorting in the vehicle class.

    @Test
    public void testCompareTo() {
        Vehicle otherVehicle = new Vehicle("33-CC-33", "Ford", "Fiesta", "Car", 1000, 1500, 0, new Date(System.currentTimeMillis() + 1000), new Date(), 10000);
        assertTrue(vehicle.compareTo(otherVehicle) < 0);
        assertTrue(otherVehicle.compareTo(vehicle) > 0);
    }

**Test 4:** Test the checkUp constructor method in checkUp class.

    @Test
    public void testCheckUpConstructor() {
        // Arrange
        double kmAtLastCheckUp = 20000;
        Date checkUpDate = new Date();

        // Act
        CheckUp checkUp = new CheckUp(kmAtLastCheckUp, checkUpDate);

        // Assert
        assertEquals(kmAtLastCheckUp, checkUp.getKmAtLastCheckUp(), "KmAtLastCheckUp should be initialized correctly");
        assertEquals(checkUpDate, checkUp.checkUpDate, "CheckUpDate should be initialized correctly");
    }