package pt.ipp.isep.dei.g312.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SkillTest {

    @Test
    void getSkillName() {
        Skill teste = new Skill("BANANAS","Estragadas");
        final String EXP_RESULT = "BANANAS";
        String result = teste.getSkillName();
        assertEquals(EXP_RESULT,result);

        final String NOT_EXP_RESULT = "";
        assertNotEquals(NOT_EXP_RESULT,result);
    }

    @Test
    void setSkillName() {
        Skill teste = new Skill("BANANAS","Estragadas");
        final String EXP_RESULT = "PESSEGOS";
        teste.setSkillName("PESSEGOS");
        String result = teste.getSkillName();
        assertEquals(EXP_RESULT,result);

        final String NOT_EXP_RESULT = "";
        assertNotEquals(NOT_EXP_RESULT,result);
    }

    @Test
    void getSkillDescription() {
        Skill teste = new Skill("BANANAS","Estragadas");
        final String EXP_RESULT = "Estragadas";
        String result = teste.getSkillDescription();
        assertEquals(EXP_RESULT,result);

        final String NOT_EXP_RESULT = "";
        assertNotEquals(NOT_EXP_RESULT,result);
    }

    @Test
    void setSkillDescription() {
        Skill teste = new Skill("BANANAS","Estragadas");
        final String EXP_RESULT = "Boas";
        teste.setSkillDescription("Boas");
        String result = teste.getSkillDescription();
        assertEquals(EXP_RESULT,result);

        final String NOT_EXP_RESULT = "";
        assertNotEquals(NOT_EXP_RESULT,result);
    }

    @Test
    void testClone() {
        Skill teste = new Skill("BANANAS","Estragadas");
        Skill cloneTeste = teste.clone();

        boolean diferentMemoryAdress = !(teste == cloneTeste);
        boolean sameName = teste.getSkillName().equals(cloneTeste.getSkillName());

        assertTrue(diferentMemoryAdress&&sameName);

        boolean diferentDescription = !(teste.getSkillDescription().equals(cloneTeste.getSkillDescription()));
        assertFalse(diferentDescription);
    }


    @Test
    void compareTo() {
        Skill skill1 = new Skill("BANANAS","Estragadas");
        Skill skill2 = new Skill("PESSEGOS","Bons");

        assertTrue(skill1.compareTo(skill2)<0);

        Skill skill3 = new Skill("BANANAS","Estragadas");

        assertEquals(0,skill1.compareTo(skill3));
    }
}