package pt.ipp.isep.dei.g312.domain.comparators;

import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.g312.domain.GreenSpace;
import pt.ipp.isep.dei.g312.domain.GreenSpaceTypology;

import static org.junit.jupiter.api.Assertions.*;

class GreenSpaceComparatorNameAlphabeticalTest {

    @Test
    void compare() {

        GreenSpace g1 = new GreenSpace("AgreenSpace1","asd",500, GreenSpaceTypology.GARDEN,"admin");
        GreenSpace g2 = new GreenSpace("BgreenSpace2","asd",1000,GreenSpaceTypology.GARDEN,"admin");
        GreenSpace g3 = new GreenSpace("CgreenSpace3","asd",1500,GreenSpaceTypology.GARDEN,"admin");
        GreenSpace g4 = new GreenSpace("EgreenSpace5","asd",1500,GreenSpaceTypology.GARDEN,"admin");
        GreenSpace g5 = new GreenSpace("EgreenSpace5","asd",2000,GreenSpaceTypology.GARDEN,"admin");
        GreenSpaceComparatorNameAlphabetical comparator = new GreenSpaceComparatorNameAlphabetical();


        //A vem antes do B
        int result1 = comparator.compare(g1,g2);
        assertEquals(-1,result1);

        //A vem antes do C

        int result2 = comparator.compare(g1,g3);
        assertEquals(-1,result2);

        //E não pode vir antes do C
        int result3 = comparator.compare(g5,g3);
        assertNotEquals(-1,result3);

        //iguais
        int result4 = comparator.compare(g5,g4);
        assertEquals(0,result4);

    }

}