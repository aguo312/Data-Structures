/**
 *
 * Name: Andrew Guo
 * SBU ID: 113517303
 * Recitation: R03
 *
 * This class represents an AuctionTable. An AuctionTable has a table of
 * Auctions.
 *
 **/

import java.io.Serializable;
import java.util.*;
import big.data.*;

public class AuctionTable implements Serializable {

    private Hashtable<String, Auction> table;

    /**
     * Creates a new AuctionTable object with table initialized to a
     * hashtable using String as keys and Auction as values.
     *
     * @postcondition
     *  This AuctionTable has been initialized.
     */
    public AuctionTable() {

        this.table = new Hashtable<String, Auction>();

    }

    /**
     * Manually posts an auction and adds it to the table.
     *
     * @param auctionID
     *  The String to set the key to.
     * @param auction
     *  The Auction to set value to.
     * @throws IllegalArgumentException
     *  Thrown if the given auctionID is already stored in the table.
     * @postcondition
     *  A new auction would be added to the table if all given parameters are
     *  correct.
     */
    public void putAuction(String auctionID, Auction auction) throws
      IllegalArgumentException {

        // Tests if the given auctionID is already in the table.
        try {

            if (table.containsKey(auctionID))
                throw new IllegalArgumentException();

        }

        // Catches if the given auctionID is already in the table.
        catch (IllegalArgumentException e) {

            System.out.println("\nERROR: auctionID already exists in the " +
              "auction.");
            return;

        }

        // Adds this auctionID and auction to the table.
        table.put(auctionID, auction);

    }

    /**
     * Gets the information of an Auction that contains the given auctionID
     * as its key.
     *
     * @param auctionID
     *  The String of the key to look for.
     * @return
     *  An Auction object with the given key, null otherwise.
     */
    public Auction getAuction(String auctionID) {

        if (table.containsKey(auctionID))
            return table.get(auctionID);

        else
            return null;

    }

    /**
     * Simulates the passing of time.
     *
     * @param numHours
     *  The number of hours to decrement the timeRemaining values by.
     * @throws IllegalArgumentException
     *  Thrown if numHours is not positive.
     * @postcondition
     *  All Auctions in the table have their timeRemaining decreased by the
     *  specified amount, or is set to zero if the new timeRemaining would be
     *  negative.
     */
    public void letTimePass(int numHours) throws IllegalArgumentException {

        // Tests if numHours is positive.
        try {

            if (numHours <= 0)
                throw new IllegalArgumentException();

        }

        // Catches if numHours is not positive.
        catch (IllegalArgumentException e) {

            System.out.println("ERROR: Hours must be positive.");
            return;

        }

        // Creates a set of the keys in the table.
        Set<String> keys = table.keySet();

        // Iterates through the keys in the table.
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()) {

            // Decrements the timeRemaining each auction.
            String s = iterator.next();
            table.get(s).decrementTimeRemaining(numHours);

        }

        System.out.println("\nTime passing...");
        System.out.println("Auction times updated.");

    }

    /**
     * Iterates over all Auction objects in the table and removes them if
     * they are expired.
     *
     * @postcondition
     *  Only open Auctions remain in the table.
     */
    public void removeExpiredAuctions() {

        // Creates a set of the keys in the table.
        Set<String> keys = table.keySet();

        // Iterates through the keys in the table.
        Iterator<String> iterator = keys.iterator();

        // String array to store each key of the table.
        String[] k = new String[table.size()];
        int j = 0;

        while (iterator.hasNext()) {

            String s = iterator.next();
            k[j] = s;
            j++;

        }

        // Loops through the string array and removes the Auctions with zero
        // timeRemaining.
        for (int i = 0; i < k.length; i++) {

            if (table.get(k[i]).getTimeRemaining() == 0)
                table.remove(k[i]);

        }

    }

    /**
     * Prints the AuctionTable in tabular form.
     */
    public void printTable() {

        // Prints out the heading block.
        System.out.printf("%13s%13s%24s%26s%12s%12s", "Auction ID |",
          "Bid   |", "Seller         |", "Buyer          |", "Time   |",
          "Item Info\n");

        String line = "";

        for (int i = 0; i < 120; i++) {

            line += "=";

        }

        System.out.println(line);

        // Creates a set of the keys in the table.
        Set<String> keys = table.keySet();

        // Iterates through the keys in the table.
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()) {

            // Gets the information of each Auction.
            String s = iterator.next();
            String id = table.get(s).getAuctionID();
            double bid = table.get(s).getCurrentBid();
            String seller = table.get(s).getSellerName();
            String buyer = table.get(s).getBuyerName();
            int time = table.get(s).getTimeRemaining();
            String itemInfo = table.get(s).getItemInfo();

            // Prints out each Auction in tabular form.
            if (bid != 0)
                System.out.printf("%11s%4s%9.2f%2s%-23s%1s%-24s%2s%4d%8s%5s",
                  id, "| $", bid, "|", " " + seller, "|", "  " + buyer, "|",
                  time, "hours |", " " + itemInfo + "\n");

            else
                System.out.printf("%11s%4s%9s%2s%-23s%1s%-24s%2s%4d%8s%5s",
                  id, "|  ", "", "|", " " + seller, "|", "  " + buyer, "|",
                  time, "hours |", " " + itemInfo + "\n");

        }

    }

    /**
     * Uses the BigData library to construct an AuctionTable from a remote
     * data source.
     *
     * @param URL
     *  The String representing the URL of the remote data source.
     * @return
     *  The AuctionTable constructed from the remote data source.
     * @throws IllegalArgumentException
     *  Thrown if the URL does not represent a valid datasource.
     */
    public static AuctionTable buildFromURL(String URL) throws
      IllegalArgumentException {

        // Tests if the URL represents a valid datasource.
        try {

            DataSource ds = DataSource.connect(URL).load();

            // Takes the information from the datasource and stores it in
            // string arrays.
            String[] seller = ds.fetchStringArray("listing/seller_info" +
              "/seller_name");
            String[] bid1 = ds.fetchStringArray("listing/auction_info" +
              "/current_bid");
            String[] time = ds.fetchStringArray("listing/auction_info" +
              "/time_left");
            String[] buyer = ds.fetchStringArray("listing/auction_info" +
              "/high_bidder/bidder_name");
            String[] id = ds.fetchStringArray("listing/auction_info" +
              "/id_num");
            String[] info1 = ds.fetchStringArray("listing/item_info/cpu");
            String[] info2 = ds.fetchStringArray("listing/item_info" +
              "/memory");
            String[] info3 = ds.fetchStringArray("listing/item_info" +
              "/hard_drive");

            // Constructs an int array of the time left represented in hours.
            int[] timeHours = new int[time.length];
            for (int i = 0; i < timeHours.length; i++) {

                String[] x = time[i].split(" ");
                int time1 = Integer.parseInt(x[0]);
                int time2 = 0;
                if (x.length > 2)
                    time2 = Integer.parseInt(x[2]);
                int totalHours = 24 * time1 + time2;
                timeHours[i] = totalHours;

            }

            // Constructs a double array of the current bid.
            double[] bid = new double[bid1.length];
            for (int i = 0; i < bid.length; i++) {

                bid[i] = bidToDouble(bid1[i]);

            }

            // Constructs a string array by combining the information of the
            // cpu, hard drive and memory.
            String[] info = new String[info1.length];
            for (int i = 0; i < info.length; i++) {

                info[i] = info1[i] + " - " + info2[i] + " - " + info3[i];

            }

            // Initializes a new AuctionTable and stores each auction in it.
            AuctionTable a = new AuctionTable();
            for (int i = 0; i < id.length; i++) {

                Auction auction = new Auction(timeHours[i], bid[i], id[i],
                  seller[i], buyer[i], info[i]);
                a.table.put(id[i], auction);

            }

            return a;

        }

        // Catches if the URL does not represent a valid datasource.
        catch (Exception e) {

            try {

                throw new IllegalArgumentException();

            }

            catch (IllegalArgumentException i) {

                System.out.println("ERROR: Invalid URL.");
                return null;

            }

        }

    }

    /**
     * Changes the string of the bids to a double.
     *
     * @param bid
     *  The String of the bid to convert.
     * @return
     *  A double representing the bid amount.
     */
    public static double bidToDouble(String bid) {

        // Constructs a string of only the numbers and decimals in the string.
        String s = "";

        for (int i = 0; i < bid.length(); i++) {

            if (Character.isDigit(bid.charAt(i)) || bid.charAt(i) == '.')
                s += bid.charAt(i);

        }

        return Double.parseDouble(s);

    }

}
