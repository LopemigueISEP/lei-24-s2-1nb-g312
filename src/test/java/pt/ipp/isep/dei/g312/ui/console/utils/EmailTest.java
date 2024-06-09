package pt.ipp.isep.dei.g312.ui.console.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class EmailTest {
    private Email emailService;

    @BeforeEach
    void setUp() {
        emailService = new Email();


    }

    @Test
    void loadValidEmailServices() {
        String[] emailServices = {"gmail.com", "dei.isep.ipp.pt", "this.app"};
        int counter = 0;

        for (String emailService : emailService.emailServices) {
            assertTrue(emailService.equals(emailServices[counter]));
            counter++;
        }

    }

    @Test
    void validEmail(){
        assertTrue(emailService.validEmail("test@gmail.com"));

        assertFalse(emailService.validEmail("test@hotmail.com"));
    }



}


