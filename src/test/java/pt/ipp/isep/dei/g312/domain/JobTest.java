package pt.ipp.isep.dei.g312.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JobTest {

    @Test
    void testClone() {
        Job jb = new Job("CONDUTOR","Triciclos");
        Job jbClone = jb.clone();

        boolean diferentMemoryAdress = !(jb == jbClone);
        boolean sameName = jb.getJobName().equals(jbClone.getJobName());
        assertTrue(diferentMemoryAdress&&sameName);

        boolean diferentDescription = !(jbClone.getJobDescription().equals(jb.getJobDescription()));
        assertFalse(diferentDescription);
    }

    @Test
    void testToString() {
        Job jb = new Job("CONDUTOR","Triciclos");
        final String EXP_RESULT = "CONDUTOR";
        String result = jb.toString();
        assertEquals(EXP_RESULT,result);

        final String NOT_EXP_RESULT = "";
        assertNotEquals(NOT_EXP_RESULT,result);
    }

    @Test
    void getTitle() {
        Job jb = new Job("CONDUTOR","Triciclos");
        final String EXP_RESULT = "CONDUTOR";
        String result = jb.getTitle();
        assertEquals(EXP_RESULT,result);

        final String NOT_EXP_RESULT = "";
        assertNotEquals(NOT_EXP_RESULT,result);
    }

    @Test
    void getJobName() {
        Job jb = new Job("CONDUTOR","Triciclos");
        final String EXP_RESULT = "CONDUTOR";
        String result = jb.getJobName();
        assertEquals(EXP_RESULT,result);

        final String NOT_EXP_RESULT = "";
        assertNotEquals(NOT_EXP_RESULT,result);
    }

    @Test
    void setJobName() {
        Job jb = new Job("CONDUTOR","Triciclos");
        final String EXP_RESULT = "ADMIN";
        jb.setJobName("ADMIN");
        String result = jb.getJobName();
        assertEquals(EXP_RESULT,result);

        final String NOT_EXP_RESULT = "";
        assertNotEquals(NOT_EXP_RESULT,result);
    }

    @Test
    void getJobDescription() {
        Job jb = new Job("CONDUTOR","Triciclos");
        final String EXP_RESULT = "Triciclos";
        String result = jb.getJobDescription();
        assertEquals(EXP_RESULT,result);

        final String NOT_EXP_RESULT = "";
        assertNotEquals(NOT_EXP_RESULT,result);

    }

    @Test
    void setJobDescription() {
        Job jb = new Job("CONDUTOR","Triciclos");
        final String EXP_RESULT = "Bicicletas";
        jb.setJobDescription("Bicicletas");
        String result = jb.getJobDescription();
        assertEquals(EXP_RESULT,result);

        final String NOT_EXP_RESULT = "";
        assertNotEquals(NOT_EXP_RESULT,result);

    }

    @Test
    void compareTo() {
        Job job1 = new Job("CONDUTOR", "Bicicletas");
        Job job2 = new Job("PADEIRO", "Folhados");
        assertTrue(job1.compareTo(job2)<0);

        Job job3 = new Job("CONDUTOR", "Bicicletas");
        assertEquals(0,job1.compareTo(job3));

    }
}