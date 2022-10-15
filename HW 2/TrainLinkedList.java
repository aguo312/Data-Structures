/**
*
* Name: Andrew Guo
* SBU ID: 113517303
* Recitation: R03
* 
* This TrainLinkedList class implements an abstract data type for a
* doubly linked list of a train as well as operations on this list.
* 
**/

public class TrainLinkedList {

    // Data of linked nodes and cursor.
    private TrainCarNode head;
    private TrainCarNode tail;
    private TrainCarNode cursor;

    // Data of the entire train.
    private int trainSize;
    private double trainLength;
    private double trainWeight;
    private double trainValue;
    private int trainDangerCounter;

    /**
     * Creates a new empty TrainLinkedList object.
     *
     * @postcondition
     *   This TrainLinkedList has been initialized as an empty train.
     */
    public TrainLinkedList() {

        // Head, tail, and cursor data.
        head = null;
        tail = null;
        cursor = null;

        // Data of the train.
        trainSize = 0;
        trainLength = 0;
        trainWeight = 0;
        trainValue = 0;
        trainDangerCounter = 0;

    }

    // Getter method for the car of the current TrainCarNode.
    public TrainCar getCursorData() {

        if (cursor != null)
            return cursor.getCar();

        return null;

    }

    /**
     * Sets the car of the TrainCarNode that the cursor is on
     *
     * @param car
     *  The TrainCar to set the car of the current TrainCarNode object to.
     * @precondition
     *   The list is not empty.
     * @postcondition
     *  The car of the current TrainCarNode is set to the supplied value.
     */
    public void setCursorData(TrainCar car) {

        if (cursor != null)
            cursor.setCar(car);

    }

    /**
     * Moves the cursor to the next TrainCarNode
     *
     * @precondition
     *   The list is not empty and cursor is not on the tail.
     * @postcondition
     *   The cursor is moved forward or remained at the tail.
     */
    public void cursorForward() {

        if (cursor != null && !cursor.equals(tail))
            cursor = cursor.getNext();

    }

    /**
     * Moves the cursor to the previous TrainCarNode
     *
     * @precondition
     *   The list is not empty and cursor is not on the head.
     * @postcondition
     *   The cursor is moved backward or remained at the head.
     */
    public void cursorBackward() {

        if (cursor != null && !cursor.equals(head))
            cursor = cursor.getPrev();

    }

    /**
     * Inserts a new TrainCar after the current TrainCarNode
     *
     * @param newCar
     *   The new TrainCar to be inserted into the train.
     * @precondition
     *   This TrainCar object has been instantiated.
     * @postcondition
     *   The new TrainCar is inserted into the train after the position of the
     *   cursor, all TrainCar objects already on the train are in the same
     *   order, and cursor points to the inserted car.
     * @throws IllegalArgumentException
     *   Thrown if newCar is null.
     */
    public void insertAfterCursor(TrainCar newCar) {

        // Tests if newCar is null.
        try {

            if (newCar == null)
                throw new IllegalArgumentException();

        }

        // Catches if newCar is null.
        catch (IllegalArgumentException e) {

            System.out.println("IllegalArgumentException: newCar is null.");

        }

        // Adding new TrainCar to the train.
        TrainCarNode newNode = new TrainCarNode(newCar);

        if (cursor == null)
            head = newNode;

        else {

            newNode.setNext(cursor.getNext());
            newNode.setPrev(cursor);
            cursor.setNext(newNode);

            if (newNode.getNext() != null)
                newNode.getNext().setPrev(newNode);

        }

        // Adjusts cursor and train data.
        cursor = newNode;
        trainSize++;
        trainLength += newCar.getCarLength();
        trainWeight += newCar.getCarWeight();

        if (cursor.getNext() == null)
            tail = cursor;

    }

    /**
     * Removes the TrainCarNode of the cursor and returns the TrainCar in that
     * node.
     *
     * @precondition
     *   The cursor is not null.
     * @postcondition
     *   The TrainCarNode of the cursor is removed and the cursor references
     *   the next node, or the previous node if there is no next node.
     */
    public TrainCar removeCursor() {

        TrainCar removed = null;

        if (cursor != null) {

            // Adjusts links, head, and tail depending on location of the
            // cursor.
            if (cursor.equals(head) && cursor.equals(tail)) {

                head = null;
                tail = null;

            }

            else if (cursor.equals(head)) {

                head = cursor.getNext();
                cursor.getNext().setPrev(null);

            }

            else if (cursor.equals(tail)) {

                tail = cursor.getPrev();
                cursor.getPrev().setNext(null);

            }

            else {

                cursor.getPrev().setNext(cursor.getNext());
                cursor.getNext().setPrev(cursor.getPrev());

            }

            // Stores data of the removed car.
            removed = cursor.getCar();

            // Adjusts train data.
            trainSize--;
            trainLength -= removed.getCarLength();
            trainWeight -= removed.getCarWeight();

            if (!removed.isEmpty()) {

                trainWeight -= removed.getLoad().getWeight();
                trainValue -= removed.getLoad().getValue();

                if (removed.getLoad().getIsDangerous())
                    trainDangerCounter--;

            }

            // Adjusts cursor.
            if (head == null && tail == null)
                cursor = null;

            else if (cursor.getPrev().equals(tail))
                cursorBackward();

            else
                cursorForward();

        }

        return removed;

    }

    /**
     * Determines the number of TrainCar objects in the train.
     *
     * @return
     *   The number of TrainCar objects on this train.
     */
    public int size() {

        return trainSize;

    }

    // Getter method for the head of the train.
    public TrainCarNode getHead() {

        return head;

    }

    // Getter method for the tail of the train.
    public TrainCarNode getTail() {

        return tail;

    }

    // Getter method for the TrainCarNode of the cursor.
    public TrainCarNode getCursor() {

        return cursor;

    }

    // Getter method for trainLength.
    public double getLength() {

        return trainLength;

    }

    // Getter method for trainWeight.
    public double getWeight() {

        return trainWeight;

    }

    /**
     * Adjusts the weight of the train
     *
     * @param weight
     *   The double that is the change in weight of the train.
     * @postcondition
     *   trainWeight is changed according to weight.
     */
    public void changeWeight(double weight) {

        trainWeight += weight;

    }

    // Getter method for trainValue.
    public double getValue() {

        return trainValue;

    }

    /**
     * Adjusts the value of the train
     *
     * @param value
     *   The double that is the change in weight of the train.
     * @postcondition
     *   trainValue is changed according to value.
     */
    public void changeValue(double value) {

        trainValue += value;

    }

    /**
     * Determines if there is a dangerous product on one of the TrainCar objects
     * on the train.
     *
     * @return
     *   True if there is at least one TrainCar carrying a dangerous
     *   ProductLoad, false otherwise.
     */
    public boolean isDangerous() {

        return trainDangerCounter != 0;

    }

    /**
     * Adjusts the trainDangerCounter of the train
     *
     * @param count
     *   The int that is the change in dangerous TrainCars on the train.
     * @postcondition
     *   trainDangerCounter is changed according to count.
     */
    public void changeDangerCounter(int count) {

        trainDangerCounter += count;

    }

    /**
     * Searches the list for all ProductLoad objects with the given name and
     * prints their sum weight and value as well as whether the product is
     * dangerous or not as a formatted string.
     *
     * @param name
     *   The name of the ProductLoad to find on the train.
     */
    public void findProduct(String name) {

        TrainCarNode nodePtr = head;

        double totalWeight = 0;
        double totalValue = 0;
        boolean productDanger = false;

        // Searches for ProductLoad with specified name and sums the weight
        // and value for ProductLoads found.
        while (nodePtr != null) {

            ProductLoad currentLoad = nodePtr.getCar().getLoad();

            if (currentLoad.getName().equals(name)) {

                totalWeight += currentLoad.getWeight();
                totalValue += currentLoad.getValue();
                if (!productDanger && currentLoad.getIsDangerous())
                    productDanger = true;

            }

            nodePtr = nodePtr.getNext();

        }

        System.out.println("Weight: " + totalWeight + " Value: " + totalValue
          + " Danger: " + productDanger);

    }

    /**
     * Prints a formatted table of car number, car length, car weight,
     * load name, load weight, load value, and load dangerousness for all of
     * the cars on the train.
     *
     * @postcondition
     *   The train is printed as a formatted table.
     */
    public void printManifest() {

        System.out.printf("%7s%37s", "CAR:", "LOAD:\n");
        System.out.printf("%8s%13s%14s%3s%8s%16s%14s%12s", "Num", "Length (m)",
          "Weight (t)", "|", "Name", "Weight (t)", "Value ($)", "Dangerous\n");
        System.out.println("   =================================="
          + "+==================================================");
        System.out.print(trainString());

    }

    /**
     * Removes all dangerous cars on the train and maintains the order of the
     * other cars on the train.
     *
     * @postcondition
     *   All dangerous cars are removed and the other cars are in the same
     *   order.
     */
    public void removeDangerousCars() {

        TrainCarNode nodePtr = head;

        // Removes the car on the cursor if it has a dangerous load.
        while (nodePtr != null) {

            if (nodePtr.getCar().getLoad().getIsDangerous()) {

                cursor = nodePtr;
                removeCursor();

            }

            nodePtr = nodePtr.getNext();

        }

        // Resets the dangerous car counter to 0.
        trainDangerCounter = 0;

    }

    /**
     * Returns a formatted String representation of each TrainCar on the train.
     *
     * @return
     *  A formatted string representation containing information from each
     *  TrainCar on the train including its length, weight, and the load's
     *  weight, value, and whether or not it is dangerous.
     */
    public String trainString() {

        String t = "";
        TrainCarNode nodePtr = head;

        for (int i = 1; i <= trainSize; i++) {

            if (nodePtr == null)
                break;

            if (nodePtr.equals(cursor))
                t += String.format("%2s%5d", "->", i) + nodePtr.toString();

            else
                t += String.format("%7d", i) + nodePtr.toString();

            nodePtr = nodePtr.getNext();

        }

        return t;

    }

    public String toString() {

        String s = "";
        if (size() == 1)
            s += "Train: 1 car, ";
        else
            s += "Train: " + size() + " cars, ";
        s += roundNumber("" + getLength()) + " meters, "
               + roundNumber("" + getWeight()) + " tons, $"
               + formatValue("" + getValue()) + " value, ";
        if (isDangerous())
            s += "DANGEROUS.";
        else
            s += "not dangerous.";
        return s;
    }

    /**
     * Rounds the double value of string s to the nearest tenth.
     *
     * @param s
     *   The string representation of a double that is being rounded.
     * @return
     *   The rounded string representation of a double to the nearest tenth.
     */
    public String roundNumber(String s) {

        int decimalIndex = 0; // holds the index of the decimal.

        // Searches for the decimal in String s.
        for (int i = 0; i < s.length(); i++) {

            Character c = s.charAt(i);
            if (c.equals('.'))
                decimalIndex = i;

        }

        // Takes the decimal part of s.
        String decimal = s.substring(decimalIndex + 1);
        String round = "";

        // Determines if the string needs to be rounded and if so how should it
        // be rounded.
        if (decimal.length() > 1) {

            if (decimal.substring(0, 2).equals("99")) {

                round += s.substring(0, decimalIndex);
                int d = Integer.parseInt(round);
                d++;
                round = d + ".0";

            }

            else if (decimal.substring(1, 2).equals("9")) {

                round += s.substring(decimalIndex + 1, decimalIndex + 2);
                int d = Integer.parseInt(round);
                d++;
                round = s.substring(0, decimalIndex + 1);

            }

            else {

                round += s.substring(0, decimalIndex + 2);

            }

        }

        else
            round = s;

        return round;

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

    /**
     * Resets the cursor to the beginning of the train
     *
     * @postcondition
     *   The cursor now references the head of the TrainLinkedList.
     */
    public void resetCursor() {

        cursor = head;

    }

}