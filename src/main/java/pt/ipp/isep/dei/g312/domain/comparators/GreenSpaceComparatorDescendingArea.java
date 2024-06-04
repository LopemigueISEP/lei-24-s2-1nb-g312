package pt.ipp.isep.dei.g312.domain.comparators;

import pt.ipp.isep.dei.g312.domain.GreenSpace;

import java.util.Comparator;

public class GreenSpaceComparatorDescendingArea implements Comparator<GreenSpace> {
    @Override
    public int compare(GreenSpace g1, GreenSpace g2) {

        return Double.compare(g1.getArea(),g2.getArea());
    }
}
