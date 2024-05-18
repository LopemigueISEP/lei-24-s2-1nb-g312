# US007 - Create a Task 

## 4. Tests 

**Test 1:** Check if getVehicle method return the correct vehicle in the vehicle class.

	@Test
    public void testGetVehicleSuccess() {
        vehicleRepo.add(vehicle1);
        Vehicle foundVehicle = vehicleRepo.getVehicle("11-AA-11");
        assertEquals(vehicle1.getRegistrationPlate(), foundVehicle.getRegistrationPlate(), "The vehicle retrieved should match the one added.");
    }

**Test 2:** Test if updateVehicle method adds edits the vehicle in the vehicleRepository class.

    @Test
    public void testUpdateVehicle() {
        vehicleRepo.add(vehicle1);
        vehicle1.setCurrentKm(30000);
        vehicleRepo.updateVehicle(vehicle1);

        assertEquals(30000, vehicleRepo.getVehicle("11-AA-11").getCurrentKm(), "The vehicle should be updated successfully.");
    }



**Test 3:** Test if registerCheckUp method adds a new check-up to the vehicle in the vehicle class.

	@Test
    public void testRegisterCheckUp() {
        Date checkUpDate = new Date();
        vehicle.registerCheckUp(25000, checkUpDate);
        assertEquals(2, vehicle.getCheckUpList().size()); // Assuming one check-up was already added in the constructor
        assertEquals(25000, vehicle.getCheckUpList().get(1).getKmAtLastCheckUp());
        assertEquals(checkUpDate, vehicle.getCheckUpList().get(1).getCheckUpDate());
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



