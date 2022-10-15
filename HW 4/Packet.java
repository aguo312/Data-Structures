/**
 *
 * Name: Andrew Guo
 * SBU ID: 113517303
 * Recitation: R03
 *
 * This class represents a Packet. A Packet has an id, a packetSize, a
 * timeArrive, and a timeToDest.
 *
 **/

public class Packet {

    public static int packetCount = 0;

    private int id;
    private int packetSize;
    private int timeArrive;
    private int timeToDest;

    /**
     * Creates a new Packet object with 0 values
     *
     * @postcondition
     *  This Packet has been initialized.
     */
    public Packet() {

        this.id = ++packetCount;
        this.packetSize = 0;
        this.timeArrive = 0;
        this.timeToDest = packetSize / 100;

    }

    /**
     * Creates a new Packet object with the supplied values
     *
     * @param packetSize
     *  The int to set packetSize to.
     * @param timeArrive
     *  The int to set timeArrive to.
     * @postcondition
     *  This Packet has been initialized.
     */
    public Packet(int packetSize, int timeArrive) {

        this.id = ++packetCount;
        this.packetSize = packetSize;
        this.timeArrive = timeArrive;
        this.timeToDest = packetSize / 100;

    }

    // Getter method for id
    public int getId() {

        return id;

    }

    // Setter method for id
    public void setId(int id) {

        this.id = id;

    }

    // Getter method for packetSize
    public int getPacketSize() {

        return packetSize;

    }

    // Setter method for packetSize
    public void setPacketSize(int packetSize) {

        this.packetSize = packetSize;

    }

    // Getter method for timeArrive
    public int getTimeArrive() {

        return timeArrive;

    }

    // Setter method for timeArrive
    public void setTimeArrive(int timeArrive) {

        this.timeArrive = timeArrive;

    }

    // Getter method for timeToDest
    public int getTimeToDest() {

        return timeToDest;

    }

    // Setter method for timeToDest
    public void setTimeToDest(int timeToDest) {

        this.timeToDest = timeToDest;

    }

    /**
     * Formats this Packet object as a string
     *
     * @return
     *  The String representation of this Packet object.
     */
    public String toString() {

        return "[" + id + ", " + timeArrive + ", " + timeToDest + "]";

    }

}
