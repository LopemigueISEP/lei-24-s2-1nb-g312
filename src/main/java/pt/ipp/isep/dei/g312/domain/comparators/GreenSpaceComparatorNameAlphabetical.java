package pt.ipp.isep.dei.g312.domain.comparators;

import pt.ipp.isep.dei.g312.domain.GreenSpace;

import java.util.Comparator;

public class GreenSpaceComparatorNameAlphabetical implements Comparator<GreenSpace> {
    @Override
    public int compare(GreenSpace g1, GreenSpace g2) {

        return g1.getName().compareTo(g2.getName());
    }
}
