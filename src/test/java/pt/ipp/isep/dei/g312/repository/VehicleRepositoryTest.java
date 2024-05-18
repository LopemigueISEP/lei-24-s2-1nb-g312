package pt.ipp.isep.dei.g312.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.g312.domain.Vehicle;

import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class VehicleRepositoryTest {
    private VehicleRepository vehicleRepo;
    private Vehicle vehicle1;
    private Vehicle vehicle2;

    private Date registerDate;
    private Date acquisitionDate;

    @BeforeEach
    public void setUp() {
        vehicleRepo = new VehicleRepository();
        registerDate = new Date();
        acquisitionDate = new Date();
        vehicle1 = new Vehicle("11-AA-11", "Toyota", "Yaris", "Car", 1000, 1500, 20000, registerDate, acquisitionDate, 10000);
        vehicle2 = new Vehicle("22-AA-22", "Toyota", "Yaris", "Car", 1000, 1500, 20000, registerDate, acquisitionDate, 10000);
    }

    @Test
    public void testAddVehicleSuccess() {
        Optional<Vehicle> result = vehicleRepo.add(vehicle1);
        assertTrue(result.isPresent(), "Vehicle should be added successfully.");
        assertEquals(vehicle1.getRegistrationPlate(), result.get().getRegistrationPlate(), "Returned vehicle should match the added vehicle.");
    }

    @Test
    public void testAddDuplicateVehicleFails() {
        vehicleRepo.add(vehicle1);
        Optional<Vehicle> result = vehicleRepo.add(vehicle1);
        assertFalse(result.isPresent(), "Duplicate vehicle should not be added.");
    }

    @Test
    public void testGetVehicleSuccess() {
        vehicleRepo.add(vehicle1);
        Vehicle foundVehicle = vehicleRepo.getVehicle("11-AA-11");
        assertEquals(vehicle1.getRegistrationPlate(), foundVehicle.getRegistrationPlate(), "The vehicle retrieved should match the one added.");
    }

    @Test
    public void testGetVehicleNotFound() {
        assertThrows(NoSuchElementException.class, () -> vehicleRepo.getVehicle("22-AA-22"), "Should throw an exception if the vehicle is not found.");
    }

    @Test
    public void testExistsVehicleByRegistrationPlate() {
        vehicleRepo.add(vehicle1);
        assertTrue(vehicleRepo.existsVehicle("11-AA-11"), "Should return true if the vehicle exists with the given registration plate.");
        assertFalse(vehicleRepo.existsVehicle("22-AA-22"), "Should return false if the vehicle does not exist.");
    }

    @Test
    public void testUpdateVehicle() {
        vehicleRepo.add(vehicle1);
        vehicle1.setCurrentKm(30000);
        vehicleRepo.updateVehicle(vehicle1);

        assertEquals(30000, vehicleRepo.getVehicle("11-AA-11").getCurrentKm(), "The vehicle should be updated successfully.");
    }
}