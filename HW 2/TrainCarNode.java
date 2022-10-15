/**
*
* Name: Andrew Guo
* SBU ID: 113517303
* Recitation: R03
* 
* This class represents a train car node. A TrainCarNode has a previous
* node, a next node, and a TrainCar. 
* 
**/

public class TrainCarNode {

    private TrainCarNode prev;
    private TrainCarNode next;
    private TrainCar car;

    /**
     * Creates a new empty TrainCarNode object
     * 
     * @postcondition
     *  This TrainCarNode has been initialized with all values at null.
     */
    public TrainCarNode() {

        prev = null;
        next = null;
        car = null;

    }

    /**
     * Creates a new TrainCarNode object with the supplied value
     * 
     * @param car
     *  The TrainCar to set car to.
     * @postcondition
     *  This Train has been initialized and prev and next are null.
     */
    public TrainCarNode(TrainCar car) {

        prev = null;
        next = null;
        this.car = car;

    }

    // Getter method for prev.
    public TrainCarNode getPrev() {

        return prev;

    }

    /**
     * Sets the prev of this TrainCarNode object.
     *
     * @param prev
     *  The TrainCarNode to set the prev of this TrainCarNode object to.
     * @postcondition
     *  prev is set to the supplied value.
     */
    public void setPrev(TrainCarNode prev) {

        this.prev = prev;

    }

    // Getter method for next.
    public TrainCarNode getNext() {

        return next;

    }

    /**
     * Sets the next of this TrainCarNode object.
     *
     * @param next
     *  The TrainCarNode to set the next of this TrainCarNode object to.
     * @postcondition
     *  next is set to the supplied value.
     */
    public void setNext(TrainCarNode next) {

        this.next = next;

    }

    // Getter method for car.
    public TrainCar getCar() {

        return car;

    }

    /**
     * Sets the car of this TrainCarNode object.
     *
     * @param prev
     *  The TrainCar to set the car of this TrainCarNode object to.
     * @postcondition
     *  car is set to the supplied value.
     */
    public void setCar(TrainCar car) {

        this.car = car;

    }

    /**
     * Puts this TrainCarNode object in a formatted String that displays
     * all of the information in this TrainCarNode object.
     *
     * @return
     *   The String representation of this TrainCarNode object
     */
    public String toString() {

        String c = "";

        // Adds information about the TrainCar.
        c += String.format("%14s%14s%3s", "" + car.getCarLength(),
          "" + car.getCarWeight(), "|");

        // Adds information for an empty load.
        if (car.getLoad() == null)
            c += String.format("%10s%14s%14s%11s", "Empty", "0.0", "0.00",
              "NO");

        // Adds information about the load of the TrainCar.
        else {

            c += String.format("%10s%14s%14s", car.getLoad().getName(),
              "" + car.getLoad().getWeight(),
              formatValue("" + car.getLoad().getValue()));

            if (car.getLoad().getIsDangerous())
                c += String.format("%11s", "YES");

            else
                c += String.format("%11s", "NO");

        }

        return c + "\n";

    }

    /**
     * Changes value into dollar format.
     *
     * @param s
     *   The String that is being formatted.
     * @return
     *   s as a String in dollar format.
     */
    public String formatValue(String s) {

        int decimalIndex = 0; // holds the index of the decimal.

        // Searches for the decimal in String s.
        for (int i = 0; i < s.length(); i++) {

            Character c = s.charAt(i);
            if (c.equals('.'))
                decimalIndex = i;

        }

        // Takes the dollar part of s.
        String dollar = s.substring(0, decimalIndex);
        String newDollar = "";
        int j = dollar.length();
        int i = j - 3;

        // Adds commas between every 3 values.
        while (true) {

            if (i <= 0) {
                newDollar = dollar.substring(0, j) + newDollar;
                break;
            }

            newDollar = "," + dollar.substring(i, j) + newDollar;

            i = i - 3;
            j = j - 3;

        }

        // Takes the cents part of s.
        String cents = s.substring(decimalIndex + 1);

        // Appends a 0 if there is only one digit in cents
        if (cents.length() == 1)
            cents += "0";

        // Takes only the first two digits from cents
        else
            cents = cents.substring(0, 2);

        // Combines the formatted dollar and cents.
        return newDollar + "." + cents;

    }

}