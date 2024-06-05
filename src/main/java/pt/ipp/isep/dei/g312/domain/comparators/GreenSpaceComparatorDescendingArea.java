package pt.ipp.isep.dei.g312.domain.comparators;

import pt.ipp.isep.dei.g312.domain.GreenSpace;

import java.util.Comparator;

/**
 * Comparator class for comparing two GreenSpace objects by their area in descending order.
 */
public class GreenSpaceComparatorDescendingArea implements Comparator<GreenSpace> {


    /**
     * Compares two GreenSpace objects by their area in descending order.
     *
     * @param g1 the first GreenSpace object to be compared
     * @param g2 the second GreenSpace object to be compared
     * @return a negative integer, zero, or a positive integer as the first argument is greater than, equal to, or less than the second
     */
    @Override
    public int compare(GreenSpace g1, GreenSpace g2) {

        return Double.compare(g2.getArea(),g1.getArea());
    }
}
