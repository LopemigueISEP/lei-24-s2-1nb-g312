package pt.ipp.isep.dei.g312.domain;

import java.util.Date;

/** Represents a vehicle CheckUp.
 *  This class is responsible for managing the information of a CheckUp.
 *  It stores the km at the last check-up and date of a vehicle.
 */
public class CheckUp {
    private double kmAtLastCheckUp;
    Date checkUpDate;

    /** Constructor for a new CheckUp instance with the specified kilometers at last check-up and the date of the check-up.
     * @param kmAtLastCheckUp The kilometers covered by the vehicle at the time of the last check-up.
     * @param checkUpDate The date when the last check-up was done.
     */
    // create checkUp's
    public CheckUp(double kmAtLastCheckUp, Date checkUpDate) {
        this.kmAtLastCheckUp = kmAtLastCheckUp;
        this.checkUpDate = checkUpDate;
    }

    public double getKmAtLastCheckUp() {
        return kmAtLastCheckUp;
    }

    public Date getCheckUpDate() {
        return checkUpDate;
    }
}
