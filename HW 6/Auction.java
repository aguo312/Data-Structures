/**
 *
 * Name: Andrew Guo
 * SBU ID: 113517303
 * Recitation: R03
 *
 * This class represents an Auction. An Auction has a timeRemaining, a
 * currentBid, an auctionID, a sellerName, a buyerName, and an itemInfo.
 *
 **/

import java.io.Serializable;

public class Auction implements Serializable {

    private int timeRemaining;
    private double currentBid;
    private String auctionID;
    private String sellerName;
    private String buyerName;
    private String itemInfo;

    /**
     * Creates a new Auction object with timeRemaining and currentBid set to
     * 0 and the other values set to an empty string.
     *
     * @postcondition
     *  This Auction has been initialized.
     */
    public Auction() {
        this.timeRemaining = 0;
        this.currentBid = 0;
        this.auctionID = "";
        this.sellerName = "";
        this.buyerName = "";
        this.itemInfo = "";
    }

    /**
     * Creates a new Auction object with the supplied values.
     *
     * @param timeRemaining
     *  The int to set timeRemaining to.
     * @param currentBid
     *  The double to set currentBid to.
     * @param auctionID
     *  The String to set auctionID to.
     * @param sellerName
     *  The String to set sellerName to.
     * @param buyerName
     *  The String to set buyerName to.
     * @param itemInfo
     *  The String to set itemInfo to.
     * @postcondition
     *  This Auction object has been initialized.
     */
    public Auction(int timeRemaining, double currentBid, String auctionID,
                   String sellerName, String buyerName, String itemInfo) {

        this.timeRemaining = timeRemaining;
        this.currentBid = currentBid;
        this.auctionID = auctionID;
        this.sellerName = sellerName;
        this.buyerName = buyerName;
        this.itemInfo = itemInfo;

    }

    // Getter method for timeRemaining.
    public int getTimeRemaining() {

        return timeRemaining;

    }

    // Getter method for currentBid.
    public double getCurrentBid() {

        return currentBid;

    }

    // Getter method for auctionID.
    public String getAuctionID() {

        return auctionID;

    }

    // Getter method for sellerName.
    public String getSellerName() {

        return sellerName;

    }

    // Getter method for buyerName.
    public String getBuyerName() {

        return buyerName;

    }

    // Getter method for itemInfo.
    public String getItemInfo() {

        return itemInfo;

    }

    /**
     * Decreases the time remaining for this auction by the specified amount.
     *
     * @param time
     *  The int to decrement timeRemaining by.
     * @postcondition
     *  timeRemaining has been decremented by time and is greater than or
     *  equal to zero.
     */
    public void decrementTimeRemaining(int time) {

        if (time >= timeRemaining)
            timeRemaining = 0;

        else
            timeRemaining -= time;

    }

    /**
     * Makes a new bid on this auction.
     *
     * @param bidderName
     *  The String to set buyerName to.
     * @param bidAmt
     *  The double to set currentBid to.
     * @throws ClosedAuctionException
     *  Thrown if the timeRemaining for this auction is zero.
     * @postcondition
     *  currentBid reflects the largest bid placed on the object.
     */
    public void newBid(String bidderName, double bidAmt) throws
      ClosedAuctionException {

        // Tests if there is time remaining on this auction.
        try {

            if (!(timeRemaining > 0))
                throw new ClosedAuctionException();

        }

        // Catches if the time remaining on this auction has hit zero.
        catch (ClosedAuctionException e) {

            System.out.println("ERROR: Auction is closed.");
            return;

        }

        // Changes currentBid and buyerName if the new bid is higher.
        if (bidAmt > currentBid) {

            currentBid = bidAmt;
            buyerName = bidderName;

        }

    }

    /**
     * Puts the information of this auction into a string.
     *
     * @return
     *  The string of the data members of this auction in tabular form.
     */
    public String toString() {

        String s = "";
        s += "Auction " + auctionID + ":\n";
        s += "\tSeller: " + sellerName + "\n";
        s += "\tBuyer: " + buyerName + "\n";
        s += "\tTime: " + timeRemaining + " hours\n";
        s += "\tInfo: " + itemInfo;
        return s;

    }

    /**
     * Tests if this Auction object is equal to the supplied object by
     * teesting the corresponding fields of each against each other.
     *
     * @param obj
     *  The object being compared to this Auction.
     * @return
     *  True if the two objects are the same, false otherwise.
     */
    public boolean equals(Object obj) {

        // Checks if obj is an Auction.
        if (obj instanceof Auction) {

            // Type casts obj to Auction.
            Auction a = (Auction) obj;

            // Checks each field of this auction and a.
            if (this.getTimeRemaining() == a.getTimeRemaining() &&
              this.getCurrentBid() == a.getCurrentBid() &&
              this.getAuctionID().equals(a.getAuctionID()) &&
              this.getSellerName().equals(a.getSellerName()) &&
              this.getBuyerName().equals(a.getSellerName()) &&
              this.getItemInfo().equals(a.getItemInfo())) {
                return true;

            }

        }

        return false;

    }

}
