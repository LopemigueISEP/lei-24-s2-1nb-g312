package pt.ipp.isep.dei.g312.domain.comparators;

import pt.ipp.isep.dei.g312.domain.GreenSpace;

import java.util.Comparator;

/**
 * Comparator class for comparing two GreenSpace objects by their name in alphabetical order.
 */
public class GreenSpaceComparatorNameAlphabetical implements Comparator<GreenSpace> {

    /**
     * Compares two GreenSpace objects by their name in alphabetical order.
     *
     * @param g1 the first GreenSpace object to be compared
     * @param g2 the second GreenSpace object to be compared
     * @return a negative integer, zero, or a positive integer as the first argument is less than, equal to, or greater than the second
     */
    @Override
    public int compare(GreenSpace g1, GreenSpace g2) {

        return g1.getName().toUpperCase().compareTo(g2.getName().toUpperCase());
    }
}
