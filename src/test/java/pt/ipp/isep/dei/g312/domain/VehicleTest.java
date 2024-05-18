package pt.ipp.isep.dei.g312.domain;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.g312.domain.Vehicle;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class VehicleTest {

    private Vehicle vehicle;
    private Date registerDate;
    private Date acquisitionDate;

    @BeforeEach
    public void setUp() {
        // Set up dates
        registerDate = new Date();
        acquisitionDate = new Date();
        // Initialize a new Vehicle object before each test
        vehicle = new Vehicle("11-AA-11", "Toyota", "Yaris", "Car", 1000, 1500, 20000, registerDate, acquisitionDate, 10000);
    }

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

    @Test
    public void testSetAndGetRegistrationPlate() {
        vehicle.setRegistrationPlate("22-BB-22");
        assertEquals("22-BB-22", vehicle.getRegistrationPlate());
    }


    @Test
    public void testRegisterCheckUp() {
        Date checkUpDate = new Date();
        vehicle.registerCheckUp(25000, checkUpDate);
        assertEquals(2, vehicle.getCheckUpList().size()); // Assuming one check-up was already added in the constructor
        assertEquals(25000, vehicle.getCheckUpList().get(1).getKmAtLastCheckUp());
        assertEquals(checkUpDate, vehicle.getCheckUpList().get(1).getCheckUpDate());
    }

    @Test
    public void testIsCheckUpDue() {
        vehicle.setCurrentKm(29000); // Close to the next check-up frequency of 30000 (10000 + 20000 initial)
        assertTrue(vehicle.isCheckUpDue());
        vehicle.setCurrentKm(27000); // Not close enough for a check-up
        assertFalse(vehicle.isCheckUpDue());
    }

    @Test
    public void testCompareTo() {
        Vehicle otherVehicle = new Vehicle("33-CC-33", "Ford", "Fiesta", "Car", 1000, 1500, 0, new Date(System.currentTimeMillis() + 1000), new Date(), 10000);
        assertTrue(vehicle.compareTo(otherVehicle) < 0);
        assertTrue(otherVehicle.compareTo(vehicle) > 0);
    }

    @Test
    public void testClone() {
        Vehicle clonedVehicle = vehicle.clone();
        assertNotSame(vehicle, clonedVehicle);
        assertEquals(vehicle.getRegistrationPlate(), clonedVehicle.getRegistrationPlate());
        assertEquals(vehicle.getBrand(), clonedVehicle.getBrand());
    }
}



