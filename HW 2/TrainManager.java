/**
*
* Name: Andrew Guo
* SBU ID: 113517303
* Recitation: R03
* 
* This class is a program that runs operations prompted by the user
* on a TrainLinkedList.
* 
**/

import java.util.Scanner;

public class TrainManager {

    /**
     * Runs a menu that first creates an empty TrainLinkedList object and
     * allows the user to execute an operation from the menu.
     *
     * @param args
     *   Command line arguments.
     */
    public static void main(String[] args) {

        TrainLinkedList trainManager = new TrainLinkedList();
        Scanner input = new Scanner(System.in);

        // Prints the menu in the beginning and when an operation is done.
        while (true) {

            System.out.println("(F) Cursor Forward");
            System.out.println("(B) Cursor Backward");
            System.out.println("(I) Insert Car After Cursor");
            System.out.println("(R) Remove Car At Cursor");
            System.out.println("(L) Set Product Load");
            System.out.println("(S) Search For Product");
            System.out.println("(T) Display Train");
            System.out.println("(M) Display Manifest");
            System.out.println("(D) Remove Dangerous Cars");
            System.out.println("(Q) Quit\n");
            System.out.print("Enter a selection: ");
            String selection = input.nextLine();

            // Makes input case insensitive.
            selection = selection.toUpperCase();

            // Runs the operation to move the cursor forward.
            if (selection.equals("F")) {

                System.out.println();

                // Prints out a message if the cursor is at the tail.
                if (trainManager.getCursor().equals(trainManager.getTail()))
                    System.out.println(
                      "No next car, cannot move cursor forward.");

                else {

                    trainManager.cursorForward();
                    System.out.println("Cursor moved forward");

                }

                System.out.println();

            }

            // Runs the operation to move the cursor backward.
            if (selection.equals("B")) {

                System.out.println();

                // Prints out a message if the cursor is at the head.
                if (trainManager.getCursor().equals(trainManager.getHead()))
                    System.out.println(
                      "No previous car, cannot move cursor backward.");

                else {

                    trainManager.cursorBackward();
                    System.out.println("Cursor moved backward");

                }

                System.out.println();

            }

            // Runs the operation to insert a new car after the cursor.
            if (selection.equals("I")) {

                System.out.println();

                System.out.print("Enter car length in meters: ");
                double carLength = verifyLength(input.nextLine());

                // Continues operation if the car length is valid.
                if (carLength != -1) {

                    System.out.print("Enter car weight in tons: ");
                    double carWeight = verifyWeight(input.nextLine());

                    // Continues the operation if car weight is valid.
                    if (carWeight != -1) {

                        System.out.println();

                        TrainCar newCar = new TrainCar(carWeight, carLength);
                        trainManager.insertAfterCursor(newCar);

                        // Prints out message if adding car is successful.
                        System.out.print("New train car " + carLength);
                        if (carLength == 1.0)
                            System.out.print(" meter " + carWeight);

                        else
                            System.out.print(" meters " + carWeight);

                        if (carWeight == 1.0)
                            System.out.println(" ton inserted into train.");

                        else
                            System.out.println(" tons inserted into train.");

                    }

                }

                System.out.println();

            }

            // Runs the operation to remove the car at the cursor.
            if (selection.equals("R")) {

                System.out.println();

                TrainCar removed = trainManager.removeCursor();

                // Prints message if there is no car to remove.
                if (removed == null)
                    System.out.println("Empty Train Exception: cursor is null");

                // Prints message if removing car is successful.
                else {

                    System.out.println("Car successfully unlinked. The "
                      + "following load has been removed from the train: ");

                    System.out.println();
                    System.out.printf("%8s%16s%14s%12s", "Name", "Weight (t)",
                          "Value ($)", "Dangerous\n");
                    System.out.println("====================================="
                      + "=============");

                    // Prints out load information if removed car is not empty.
                    if (!removed.isEmpty()) {

                        String removedName = removed.getLoad().getName();
                        double removedWeight = removed.getLoad().getWeight();
                        double removedValue = removed.getLoad().getValue();
                        boolean removedDanger =
                          removed.getLoad().getIsDangerous();

                        System.out.printf("%10s%14s%14s", removedName, 
                          "" + removedWeight, formatValue("" + removedValue));

                        if (removedDanger)
                            System.out.printf("%12s", "YES\n");

                        else
                            System.out.printf("%12s", "NO\n");

                    }

                }

                System.out.println();

            }

            // Runs the operation to load the car at the cursor.
            if (selection.equals("L")) {

                System.out.println();

                System.out.print("Enter produce name: ");
                String loadName = input.nextLine();

                System.out.print("Enter product weight in tons: ");
                double loadWeight = verifyWeight(input.nextLine());

                // Continues operation if load weight is valid.
                if (loadWeight != -1) {

                    System.out.print("Enter product value in dollars: ");
                    double loadValue = verifyValue(input.nextLine());

                        // Continues operation if load value is valid.
                        if (loadValue != -1) {

                            System.out.print("Enter is product dangerous? "
                              + "(y/n): ");
                            String loadDanger = verifyDanger(input.nextLine());

                            // Continues operation if load danger is valid.
                            if (loadDanger.equals("y") ||
                                loadDanger.equals("n")) {

                                ProductLoad newLoad = null;

                                if (loadDanger.equals("y"))
                                    newLoad = new ProductLoad(loadName,
                                      loadWeight, loadValue, true);

                                if (loadDanger.equals("n"))
                                    newLoad = new ProductLoad(loadName,
                                      loadWeight, loadValue, false);

                                // Tests if train has cars.
                                try {

                                    if (trainManager.size() == 0)
                                        throw new EmptyTrainException();

                                    TrainCar c = trainManager.getCursorData();
                                    c.setLoad(newLoad);

                                    trainManager.changeWeight(loadWeight);
                                    trainManager.changeValue(loadValue);

                                    if (loadDanger.equals("y"))
                                        trainManager.changeDangerCounter(1);

                                    System.out.println();
                                    System.out.print(loadWeight);

                                    if (loadWeight == 1.0)
                                        System.out.println(" ton of "
                                          + loadName
                                          + " added to the current car.");

                                    else
                                        System.out.println(" tons of "
                                          + loadName
                                          + " added to the current car.");

                                }

                                // Catches if there is no cars on the train.
                                catch (EmptyTrainException e) {

                                    System.out.println("\nEmpty Train "
                                      + "Exception: Train has no cars.\n");

                                }

                            }

                        }

                    }

                System.out.println();

            }

            // Runs the operation to search for a product name.
            if (selection.equals("S")) {

                System.out.println();

                System.out.print("Enter product name: ");
                String searchName = input.nextLine();

                // All data of specified product name
                int carWithSearchCounter = 0;
                double searchWeight = 0;
                double searchValue = 0;
                boolean searchDanger = false;

                trainManager.resetCursor();

                // Searches for all the loads with the specified name.
                for (int i = 1; i <= trainManager.size(); i++) {

                    ProductLoad searchLoad =
                      trainManager.getCursorData().getLoad();

                    if (searchLoad.getName().equals(searchName)) {

                        carWithSearchCounter++;
                        searchWeight += searchLoad.getWeight();
                        searchValue += searchLoad.getValue();

                        if (searchLoad.getIsDangerous())
                            searchDanger = true;

                    }

                    trainManager.cursorForward();

                }

                System.out.println();

                // Prints message if loads with specified name is found.
                if (carWithSearchCounter != 0) {

                    if (carWithSearchCounter == 1)
                        System.out.println("The following products were found "
                          + "on " + carWithSearchCounter + " car:");

                    else
                        System.out.println("The following products were found "
                          + "on " + carWithSearchCounter + " cars:");

                    // needs value $ fixed
                    System.out.println();
                    System.out.printf("%8s%16s%14s%12s", "Name", "Weight (t)",
                      "Value ($)", "Dangerous\n");
                    System.out.println("====================================="
                      + "=============");
                    System.out.printf("%10s%14s%14s", searchName,
                      "" + searchWeight, formatValue("" + searchValue));

                    if (searchDanger)
                        System.out.printf("%12s", "YES\n");

                    else
                        System.out.printf("%12s", "NO\n");

                }

                // Prints message if no load were found.
                else
                    System.out.println("No record of " + searchName
                      + " on board train.");

                System.out.println();

            }

            // Runs the operation to print the string value of the train.
            if (selection.equals("T")) {

                System.out.println();
                System.out.println(trainManager.toString());
                System.out.println();

            }

            // Runs the operation to print each car of the train.
            if (selection.equals("M")) {

                System.out.println();
                trainManager.printManifest();
                System.out.println();

            }

            // Runs the operation to remove all cars with a dangerous load
            if (selection.equals("D")) {

                System.out.println();
                trainManager.removeDangerousCars();
                System.out.println("Dangerous cars successfully removed "
                  + "from the train.");
                System.out.println();

            }

            // Runs the operation to quit the program.
            if (selection.equals("Q")) {

                System.out.println("\nProgram terminating successfully...");
                break;

            }

        }

    }

    /**
     * Verifies if the length is not negative
     *
     * @param s
     *   String that is being verified.
     * @return
     *   String s up to one digit after the decimal as a double
     *   if s is verified, -1 otherwise.
     * @throws IllegalArgumentException
     *   Thrown if s is negative.
     */
    public static double verifyLength(String s) {

        String l = "";
        int decimalIndex = 0; // holds the index of the decimal.

        // Searches for the decimal in String s.
        for (int i = 0; i < s.length(); i++) {

            Character c = s.charAt(i);

            // Tests if s has a negative.
            try {

                if (c.equals('-'))
                    throw new IllegalArgumentException();

            }

            // Catches if s has a negative.
            catch (IllegalArgumentException e) {

                System.out.println("\nIllegal Argument Exception: Length is "
                  + "negative.");
                return -1;

            }

            if (c.equals('.'))
                decimalIndex = i;

        }

        // Takes only the string up to one digit after the decimal point.
        if (decimalIndex != 0 && s.substring(decimalIndex + 1).length() > 1)
            l += s.substring(0, decimalIndex + 2);

        else
            l += s;

        return Double.parseDouble(l);
    }

    /**
     * Verifies if the weight is not negative
     *
     * @param s
     *   String that is being verified.
     * @return
     *   String s up to one digit after the decimal as a double
     *   if s is verified, -1 otherwise.
     * @throws IllegalArgumentException
     *   Thrown if s is negative.
     */
    public static double verifyWeight(String s) {

        String w = "";
        int decimalIndex = 0; // holds the index of the decimal.

        // Searches for the decimal in String s.
        for (int i = 0; i < s.length(); i++) {

            Character c = s.charAt(i);

            // Tests if s has a negative.
            try {

                if (c.equals('-'))
                    throw new IllegalArgumentException();

            }

            // Catches if s has a negative.
            catch (IllegalArgumentException e) {

                System.out.println("\nIllegal Argument Exception: Weight is "
                  + "negative.");
                return -1;

            }

            if (c.equals('.'))
                decimalIndex = i;

        }

        // Takes only the string up to one digit after the decimal point.
        if (decimalIndex != 0 && s.substring(decimalIndex + 1).length() > 1)
            w += s.substring(0, decimalIndex + 2);

        else
            w += s;

        return Double.parseDouble(w);
    }

    /**
     * Verifies if the value is not negative
     *
     * @param s
     *   String that is being verified.
     * @return
     *   String s up to two digits after the decimal as a double
     *   if s is verified, -1 otherwise.
     * @throws IllegalArgumentException
     *   Thrown if s is negative.
     */
    public static double verifyValue(String s) {

        String v = "";
        int decimalIndex = 0; // holds the index of the decimal.

        // Searches for the decimal in String s.
        for (int i = 0; i < s.length(); i++) {

            Character c = s.charAt(i);

            // Tests if s has a negative.
            try {

                if (c.equals('-'))
                    throw new IllegalArgumentException();

            }

            // Catches if s has a negative.
            catch (IllegalArgumentException e) {

                System.out.println("\nIllegal Argument Exception: Value is "
                  + "negative.");
                return -1;

            }

            if (c.equals('.'))
                decimalIndex = i;

        }

        // Takes only the string up to two digits after the decimal point.
        if (decimalIndex != 0) {

            if (s.substring(decimalIndex + 1).length() == 1)
                v += s + "0";

            else
                v += s.substring(0, decimalIndex + 3);

        }

        else
            v += s;

        return Double.parseDouble(v);
    }

    /**
     * Verifies if the danger string "y" or "n"
     *
     * @param s
     *   String that is being verified.
     * @return
     *   String s if it is "y" or "n", "" otherwise.
     * @throws IllegalArgumentException
     *   Thrown if s is not "y" or "n".
     */
    public static String verifyDanger(String s) {

        // Tests if s is "y" or "n".
        try {

            if (!s.equals("y") && !s.equals("n"))
                throw new IllegalArgumentException();

        }

        // Catches if s is not "y" or "n".
        catch (IllegalArgumentException e) {

            System.out.println("\nIllegal Argument Exception: Did not answer"
              + " with y or n.");
            return "";

        }

        return s;

    }

    /**
     * Changes value into dollar format.
     *
     * @param s
     *   The String that is being formatted.
     * @return
     *   s as a String in dollar format.
     */
    public static String formatValue(String s) {

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

// Exception for when train is empty.
class EmptyTrainException extends Exception {

    public EmptyTrainException() {   
    }

}