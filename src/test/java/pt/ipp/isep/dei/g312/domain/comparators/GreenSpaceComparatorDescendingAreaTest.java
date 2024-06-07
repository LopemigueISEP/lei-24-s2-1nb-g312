package pt.ipp.isep.dei.g312.domain.comparators;

import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.g312.domain.GreenSpace;
import pt.ipp.isep.dei.g312.domain.GreenSpaceTypology;

import static org.junit.jupiter.api.Assertions.*;

class GreenSpaceComparatorDescendingAreaTest {

    @Test
    void compare() {

        GreenSpace g1 = new GreenSpace("greenSpace1","asd",500, GreenSpaceTypology.GARDEN,"admin");
        GreenSpace g2 = new GreenSpace("greenSpace2","asd",1000,GreenSpaceTypology.GARDEN,"admin");
        GreenSpace g3 = new GreenSpace("greenSpace3","asd",1500,GreenSpaceTypology.GARDEN,"admin");
        GreenSpace g4 = new GreenSpace("greenSpace4","asd",1500,GreenSpaceTypology.GARDEN,"admin");
        GreenSpace g5 = new GreenSpace("greenSpace5","asd",2000,GreenSpaceTypology.GARDEN,"admin");
        GreenSpaceComparatorDescendingArea comparator = new GreenSpaceComparatorDescendingArea();

        // area g2 maior que g1
        int result1 = comparator.compare(g2, g1);
        assertEquals(-1, result1);

        // area g3 igual g4
        int result2 = comparator.compare(g3, g4);
        assertEquals(0, result2);

        // area g4 menor g5
        int result3 = comparator.compare(g4, g5);
        assertEquals(1, result3);



    }
}