/**
 *
 * Name: Andrew Guo
 * SBU ID: 113517303
 * Recitation: R03
 *
 * This class is a program that represents an Auction system and runs
 * operations prompted by the user on an AuctionTable.
 *
 **/

import java.io.*;
import java.util.Scanner;

public class AuctionSystem implements Serializable {

    private AuctionTable auctionTable;
    private String username;

    /**
     * Creates a new AuctionSystem object with the given values.
     *
     * @param auctionTable
     *  The AuctionTable to set auctionTable to.
     * @param username
     *  The String to set username to.
     */
    public AuctionSystem(AuctionTable auctionTable, String username) {

        this.auctionTable = auctionTable;
        this.username = username;

    }

    /**
     * The main program for the AuctionSystem.
     * @param args
     *  Command-line arguments.
     */
    public static void main(String[] args) throws ClosedAuctionException,
      IOException, ClassNotFoundException {

        // Tests if the "auction.obj" file exists.
        try {

            FileInputStream file = new FileInputStream("auction.obj");
            System.out.println("Starting...\nLoading previous Auction Table.."
              + ".");
            ObjectInputStream inStream = new ObjectInputStream(file);

            // Creates an AuctionTable from the "auction.obj" file.
            AuctionTable auctions = (AuctionTable) inStream.readObject();

            // Runs the auction program using the AuctionTable created from
            // the file.
            auctionProgram(auctions);

        }

        // Catches if the "auction.obj" file does not exist.
        catch (FileNotFoundException e) {

            System.out.println("Starting...\nNo previous auction table " +
              "detected.\nCreating new table...");

            // Creates a new AuctionTable object.
            AuctionTable auctions = new AuctionTable();

            // Runs the auction program using the new AuctionTable object.
            auctionProgram(auctions);

        }

        // Catches an IOException.
        catch (IOException e) {

            System.out.println("ERROR: IOException");

        }

        // Catches a ClassNotFoundException.
        catch (ClassNotFoundException e) {

            System.out.println("ERROR: ClassNotFoundException");

        }

    }

    /**
     * The program of operations on the AuctionTable.
     * @param auctions
     *  The AuctionTable to set auctionTable to.
     */
    public static void auctionProgram(AuctionTable auctions) throws
      ClosedAuctionException, FileNotFoundException, ClassNotFoundException,
      IOException {

        Scanner input = new Scanner(System.in);

        System.out.print("\nPlease select a username: ");
        String user = input.nextLine();

        AuctionSystem a = new AuctionSystem(auctions, user);

        // Prints the menu in the beginning and when an operation is done.
        while (true) {

            System.out.println("\nMenu");
            System.out.println("\t(D) - Import Data from URL");
            System.out.println("\t(A) - Create a New Auction");
            System.out.println("\t(B) - Bid on an Item");
            System.out.println("\t(I) - Get Info on Auction");
            System.out.println("\t(P) - Print All Auctions");
            System.out.println("\t(R) - Remove Expired Auctions");
            System.out.println("\t(T) - Let Time Pass");
            System.out.println("\t(Q) - Quit");

            System.out.print("\nPlease select an option: ");

            // Makes input case insensitive.
            String selection = input.nextLine().toUpperCase();

            // Runs the operation to import data from URL.
            if (selection.equals("D")) {

                System.out.print("Please enter a URL: ");
                String URL = input.nextLine();

                System.out.println("\nLoading...");

                // Continues operation if URL represents a valid datasource.
                if (AuctionTable.buildFromURL(URL) != null) {

                    a.auctionTable = AuctionTable.buildFromURL(URL);
                    System.out.println("Auction data loaded successfully!");

                }

            }

            // Runs the operation to create a new auction.
            if (selection.equals("A")) {

                System.out.println("\nCreating new Auction as " + a.username);
                System.out.print("Please enter an Auction ID: ");
                String id = input.nextLine();

                System.out.print("Please enter an Auction time (hours): ");
                int time = Integer.parseInt(input.nextLine());

                System.out.print("Please enter some Item Info: ");
                String itemInfo = input.nextLine();

                // Creates a new auction object using the input information.
                Auction newAuction = new Auction(time, 0, id, a.username, "",
                  itemInfo);

                a.auctionTable.putAuction(id, newAuction);

                // Prints out message if adding newAuction was successful.
                if (a.auctionTable.getAuction(id).equals(newAuction))
                    System.out.println("\nAuction " + id + " inserted into " +
                      "table.");

            }

            // Runs the operation to bid on an item.
            if (selection.equals("B")) {

                System.out.print("Please enter an Auction ID: ");
                String id = input.nextLine();

                // Runs if the auction with specified auctionID exists.
                if (a.auctionTable.getAuction(id) != null) {

                    Auction auction = a.auctionTable.getAuction(id);

                    // Runs if the auction is still open.
                    if (auction.getTimeRemaining() != 0) {

                        System.out.println("\nAuction " + id + " is OPEN");
                        System.out.print("\tCurrent Bid: ");

                        double current = auction.getCurrentBid();

                        if (current != 0.0)
                            System.out.printf("%2s%-10.2f", "$ ", current);

                        else
                            System.out.printf("%4s", "None");

                        System.out.print("\n\nWhat would you like to bid?: ");
                        double bid = Double.parseDouble(input.nextLine());

                        // Tests if the new bid is accepted.
                        try {

                            auction.newBid(a.username, bid);

                        }

                        // Catches if the auction is closed.
                        catch (ClosedAuctionException e) {

                            System.out.println("ERROR: Auction is closed.");

                        }

                        // Prints out message depending on whether the new
                        // bid is accepted or not.
                        if (auction.getCurrentBid() == bid)
                            System.out.println("Bid accepted.");

                        else
                            System.out.println("Bid denied. Bid must be " +
                              "higher than the current bid.");

                    }

                    // Runs if the auction is closed.
                    else {

                        System.out.println("\nAuction " + id + " is CLOSED");
                        System.out.print("\tCurrent Bid: ");
                        System.out.printf("%2s%-10.2f", "$ ",
                          a.auctionTable.getAuction(id).getCurrentBid());
                        System.out.println("\n\nYou can no longer bid on this "
                          + "item.");

                    }

                }

                // Runs if the auction with the specified auctionID does not
                // exist.
                else
                    System.out.println("\nERROR: This auction ID does not " +
                      "exist in the table.");

            }

            // Runs the operation to get information on an auction.
            if (selection.equals("I")) {

                System.out.print("Please enter an Auction ID: ");
                String id = input.nextLine();

                // Runs if the auction with specified auctionID exists.
                if (a.auctionTable.getAuction(id) != null)
                    System.out.println("\n" + a.auctionTable.getAuction(id));


                // Runs if the auction with the specified auctionID does not
                // exist.
                else
                    System.out.println("\nERROR: This auction ID does not " +
                      "exist in the table.");

            }

            // Runs the operation to print all auctions.
            if (selection.equals("P")) {

                System.out.println();
                a.auctionTable.printTable();

            }

            // Runs the operation to remove expired auctions.
            if (selection.equals("R")) {

                a.auctionTable.removeExpiredAuctions();
                System.out.println("\nRemoving expired auctions...");
                System.out.println("All expired auctions removed.");

            }

            // Runs the operation to simulate letting time pass.
            if (selection.equals("T")) {

                System.out.print("How many hours should pass: ");
                int hours = Integer.parseInt(input.nextLine());
                a.auctionTable.letTimePass(hours);

            }

            // Runs the operation to quit the program.
            if (selection.equals("Q")) {

                System.out.println("\nWriting Auction Table to file...");


                FileOutputStream file = null;

                // Tries to open the file "auction.obj"
                try {

                    file = new FileOutputStream("auction.obj");

                }

                // Catches if the file "auction.obj" does not exist.
                catch (FileNotFoundException e) {

                    System.out.println("ERROR: FileNotFoundException");

                }

                ObjectOutputStream outStream = null;

                // Tries to set outStream to file.
                try {

                    outStream = new ObjectOutputStream(file);

                }

                // Catches an IO Exception.
                catch (IOException e) {

                    System.out.println("ERROR: IOException");

                }

                // Takes data of the auctionTable.
                auctions = a.auctionTable;

                // Tries to write auctions to outStream.
                try {

                    outStream.writeObject(auctions);

                }

                // Catches an IO exception.
                catch (IOException e) {

                    System.out.println("ERROR: IOException");

                }

                System.out.println("Done!");
                System.out.println("\nGoodbye.");
                break;

            }

        }

    }

}

// Exception for when the Auction is closed.
class ClosedAuctionException extends Exception {

    public ClosedAuctionException(){

    }

}