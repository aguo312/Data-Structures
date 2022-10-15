/**
 *
 * Name: Andrew Guo
 * SBU ID: 113517303
 * Recitation: R03
 *
 * This class is a program that simulates packets traveling through routers
 * across the network to a specific destination.
 *
 **/

import java.util.*;

public class Simulator {

    public static final int MAX_PACKETS = 3;

    private Router dispatcher;
    private Collection<Router> routers;
    private int totalServiceTime;
    private int totalPacketsArrived;
    private int packetsDropped;

    private double arrivalProb;
    private int numIntRouters;
    private int maxBufferSize;
    private int minPacketSize;
    private int maxPacketSize;
    private int bandWidth;
    private int duration;

    /**
     * Creates a new Simulation object with values 0
     *
     * @postcondition
     *  This Simulation object has been initialized with values 0.
     */
    public Simulator() {

        numIntRouters = 0;
        arrivalProb = 0;
        maxBufferSize = 0;
        minPacketSize = 0;
        maxPacketSize = 0;
        bandWidth = 0;
        duration = 0;

    }

    /**
     * Creates a new Simulator object with the supplied values
     *
     * @param numIntRouters
     *  The int to set numIntRouters to.
     * @param arrivalProb
     *  The double to set arrivalProb to.
     * @param maxBufferSize
     *  The int to set maxBufferSize to.
     * @param minPacketSize
     *  The int to set minPacketSize to.
     * @param maxPacketSize
     *  The int to set maxPacketSize to.
     * @param bandWidth
     *  The int to set bandWidth to.
     * @param duration
     *  The int to set duration to.
     * @postcondition
     *  This Simulator has been initialized.
     */
    public Simulator(int numIntRouters, double arrivalProb, int maxBufferSize,
      int minPacketSize, int maxPacketSize, int bandWidth, int duration) {

        this.numIntRouters = numIntRouters;
        this.arrivalProb = arrivalProb;
        this.maxBufferSize = maxBufferSize;
        this.minPacketSize = minPacketSize;
        this.maxPacketSize = maxPacketSize;
        this.bandWidth = bandWidth;
        this.duration = duration;

    }

    /**
     * Determines the average amount of time a Packet spends in the network
     *
     * @return
     *  The double representing the average amount of time a Packet spends in
     *  the network.
     */
    public double simulate() {

        // Initializes the intermediate routers.
        addIntermediateRouters();

        // Runs one simulation unit per iteration
        for (int i = 1; i <= duration; i++) {

            System.out.println("\nTime: " + i);

            // Router to hold the Packets that reached timeToDest 0
            Router toDestination = new Router();

            // Generates packets arriving at the dispatcher.
            arriveAtDispatcher(i);

            // Decrements timeToDest and queues up priority Packets to be
            // sent to destination.
            toDestination = decrementPackets(toDestination);

            // Sends packets from dispatcher to intermediate routers.
            dispatchPackets();

            // Removes Packets that reached timeToDest 0 and queues them to
            // be sent to destination.
            toDestination = forwardDestination(toDestination);

            // Sends queued up Packets to destination.
            sendDestination(i, toDestination);

            // Prints the state of the intermediate routers.
            printIntermediateRouters();

        }

        return totalServiceTime * 1.0 / totalPacketsArrived;

    }

    /**
     * Helper method to generate a random int between the given values
     *
     * @param minVal
     *  The minimum value of the generated number.
     * @param maxVal
     *  The maximum value of the generated number.
     * @return
     *  A random integer between the given values.
     */
    private int randInt(int minVal, int maxVal) {

        return (int) ((Math.random() * (maxVal - minVal + 1)) + minVal);

    }

    /**
     * Initializes the intermediate routers
     *
     * @postcondition
     *  The Collection<Router> object is initialized.
     */
    public void addIntermediateRouters() {

        routers = new LinkedList<Router>();

        // Adds numIntRouters amount of intermediate routers.
        for (int i = 0; i < numIntRouters; i++) {

            Router r = new Router(maxBufferSize);
            routers.add(r);

        }

    }

    /**
     * Generates packets arriving at the dispatcher
     *
     * @param time
     *  The int representing the simulation time.
     * @postcondition
     *  Generated packets are enqueued to the dispatcher.
     */
    public void arriveAtDispatcher(int time) {

        // Initializes dispatcher as an empty router
        dispatcher = new Router();

        // Tries to generate a Packet up to MAX_PACKETS times
        for (int i = 0; i < MAX_PACKETS; i++) {

            // Generate a packet and enqueues it if successful
            if (Math.random() < arrivalProb) {

                int packetSize = randInt(minPacketSize, maxPacketSize);
                Packet p = new Packet(packetSize, time);
                dispatcher.enqueue(p);
                System.out.println("Packet " + p.getId() + " arrives at " +
                  "dispatcher with size " + packetSize);

            }

        }

        // Prints if no Packets were generated
        if (dispatcher.isEmpty()) {

            System.out.println("No packets arrived.");

        }

    }

    /**
     * Decrements timeToDest and queues up priority Packets to be sent to
     * destination.
     *
     * @param toDestination
     *  The router to store the priority Packets to send to destination.
     * @return
     *  The router that stores the Packets to send to destination.
     * @postcondition
     *  The timeToDest of the first Packet of every intermediate router is
     *  decremented by one and priority Packets are queued to be sent to the
     *  destination.
     */
    public Router decrementPackets(Router toDestination) {

        // Goes through each intermediate router
        for (Object obj : routers) {

            Router r = (Router) obj;

            if (!r.isEmpty()) {

                // Enqueues Packets that already reached 0 timeToDest
                if (toDestination.size() < bandWidth &&
                  r.peek().getTimeToDest() == 0)
                    toDestination.enqueue(r.peek());

                // Decrements timeToDest of other Packets
                else
                    r.peek().setTimeToDest(r.peek().getTimeToDest() - 1);

            }

        }

        return toDestination;

    }

    /**
     * Sends packets from dispatcher to intermediate routers
     *
     * @postcondition
     *  All Packets in dispatcher is sent to an intermediate router or is
     *  dropped.
     */
    public void dispatchPackets() {

        // Runs until dispatcher is empty.
        while (!dispatcher.isEmpty()) {

            Packet p = dispatcher.dequeue();

            // Determines the router to sent Packet to.
            int intRouter = Router.sendPacketTo(routers);

            // Enqueues Packet to one of the intermediate routers.
            if (intRouter > 0) {

                Iterator iterator = routers.iterator();

                for (int i = 1; i <= numIntRouters; i++) {

                    Router r = (Router) iterator.next();

                    if (i == intRouter) {

                        r.enqueue(p);
                        System.out.println("Packet " + p.getId() + " sent to "
                          + "Router " + i + ".");

                    }

                }

            }

            // Drops the Packet if all intermediate routers are full.
            else {

                packetsDropped++;
                System.out.println("Network is congested. Packet " +
                  p.getId() + " is dropped.");

            }

        }

    }

    /**
     * Removes Packets that reached timeToDest 0 and queues them to be sent to
     * destination
     *
     * @param toDestination
     *  The router to store the Packets to send to destination.
     * @return
     *  The router that stores the Packets to send to destination.
     * @postcondition
     *  Removes Packets with 0 timeToDest and enqueues any new Packets with 0
     *  timeToDest.
     */
    public Router forwardDestination(Router toDestination) {

        // Iterates through the intermediate routers and dequeues any Packets
        // already queued to be sent to destination.
        Iterator iterator = routers.iterator();

        for (int i = 1; i <= numIntRouters; i++) {

            Router r = (Router) iterator.next();

            if (!r.isEmpty()) {

                for (int j = 0; j < toDestination.size(); j++) {

                    if (r.peek().getId() == toDestination.get(j).getId()) {

                        r.dequeue();
                        break;

                    }

                }

            }

        }

        // Iterates through the intermediate routers, dequeues any Packets
        // with 0 timeToDest, and enqueues them to be sent to destination.
        Iterator iterator2 = routers.iterator();

        for (int i = 1; i <= numIntRouters; i++) {

            Router r = (Router) iterator2.next();

            if (!r.isEmpty()) {

                if (toDestination.size() < bandWidth &&
                  r.peek().getTimeToDest() == 0)
                    toDestination.enqueue(r.dequeue());

            }

        }

        return toDestination;

    }

    /**
     * Sends queued up Packets to destination
     *
     * @param time
     *  The int representing the simulation time.
     * @param toDestination
     *  The router to store the Packets to send to destination.
     * @postCondition
     *  Sends all queued Packets to destination, and increments
     *  totalPacketsArrived and totalServiceTime.
     */
    public void sendDestination(int time, Router toDestination) {

        // Runs until the Router is empty
        while (!toDestination.isEmpty()) {

            Packet p = toDestination.dequeue();
            int timeSpent = time - p.getTimeArrive();
            totalPacketsArrived++;
            totalServiceTime += timeSpent;
            System.out.println("Packet " + p.getId() + " has successfully " +
              "reached its destination: +" + timeSpent);

        }

    }

    /**
     * Prints the state of the intermediate routers
     *
     * @postcondition
     *  All intermediate routers are formatted and printed.
     */
    public void printIntermediateRouters() {

        Iterator iterator = routers.iterator();

        for (int i = 1; i <= numIntRouters; i++) {

            Router r = (Router) iterator.next();
            System.out.println("R" + i + ": " + r);

        }

    }

    /**
     * Runs the program to run a simulation of packets traveling through
     * routers across the network to a specific destination
     *
     * @param args
     *  Command line arguments
     */
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        System.out.println("Starting simulator...");

        // Asks for inputs to start a simulation.
        while (true) {

            System.out.print("\nEnter the number of Intermediate routers: ");
            int intRouters = checkInt(input.nextLine());

            // Continues if intRouters is not negative or 0.
            if (intRouters > 0) {

                System.out.print("\nEnter the arrival possibility of a " +
                  "packet: ");
                double prob = checkDouble(input.nextLine());

                // Continues if prob is not negative or 0.
                if (prob > 0) {

                    System.out.print("\nEnter the maximum buffer size of a " +
                      "router: ");
                    int maxBuffer = checkInt(input.nextLine());

                    // Continues if maxBuffer is not negative or 0.
                    if (maxBuffer > 0) {

                        System.out.print("\nEnter the minimum size of a " +
                          "packet: ");
                        int minPacket = checkInt(input.nextLine());

                        // Continues if minPacket is not negative or 0.
                        if (minPacket > 0) {

                            System.out.print("\nEnter the maximum size of a " +
                              "packet: ");
                            int maxPacket = checkSize(input.nextLine(),
                              minPacket);

                            // Continues if maxPacket > minPacket.
                            if (maxPacket > 0) {

                                System.out.print("\nEnter the bandwidth " +
                                  "size: ");
                                int band = checkInt(input.nextLine());

                                // Continues if band is not negative or 0.
                                if (band > 0) {

                                    System.out.print("\nEnter the " +
                                      "simulation duration: ");
                                    int simTime = checkInt(input.nextLine());

                                    // Continues if simTime is not negative
                                    // or 0.
                                    if (simTime > 0) {

                                        // Creates a new Simulator with the
                                        // input values.
                                        Simulator trial =
                                          new Simulator(intRouters, prob,
                                            maxBuffer, minPacket, maxPacket,
                                            band, simTime);

                                        // Runs the simulation.
                                        Packet.packetCount = 0;
                                        double avgServiceTime =
                                          trial.simulate();

                                        System.out.println("\nSimulation " +
                                          "ending...");
                                        System.out.println("Total service " +
                                          "time: " + trial.totalServiceTime);
                                        System.out.println("Total packets " +
                                          "served: " +
                                          trial.totalPacketsArrived);
                                        System.out.println("Average service " +
                                          "time per packet: " +
                                          avgServiceTime);
                                        System.out.println("Total packets " +
                                          "dropped: " + trial.packetsDropped);

                                        // Prompts another simulation.
                                        String selection = endSequence();

                                        // Ends the program.
                                        if (selection.equals("n")) {

                                            System.out.println("\nProgram " +
                                              "terminating successfully...");
                                            break;

                                        }

                                    }

                                }

                            }

                        }

                    }

                }

            }

        }

    }

    /**
     * Verifies if the input is not 0 or negative
     *
     * @param s
     *  The string representation of the input to verify.
     * @return
     *  The int representation of String s if s is verified, -1 otherwise.
     * @throws IllegalArgumentException
     *  Thrown if s is negative or 0.
     */
    public static int checkInt(String s) throws IllegalArgumentException {

        try {

            if (s.substring(0, 1).equals("-") || s.equals("0"))
                throw new IllegalArgumentException();

            return Integer.parseInt(s);

        }

        catch (IllegalArgumentException e) {

            System.out.println("\nArgument cannot be negative or zero.");
            return -1;

        }

    }

    /**
     * Verifies if the input is not 0 or negative
     *
     * @param s
     *  The string representation of the input to verify.
     * @return
     *  The double representation of String s if s is verified, -1 otherwise.
     * @throws IllegalArgumentException
     *  Thrown if s is negative or 0.
     */
    public static double checkDouble(String s) throws
      IllegalArgumentException {

        try {

            if (s.substring(0, 1).equals("-") || s.equals("0") ||
              s.equals("0.0"))
                throw new IllegalArgumentException();

            return Double.parseDouble(s);

        }

        catch (IllegalArgumentException e) {

            System.out.println("\nArgument cannot be negative or zero.");
            return -1;

        }

    }

    /**
     * Verifies if the input is greater than minPacket
     *
     * @param s
     *  The string representation of the input to verify.
     * @return
     *  The int representation of String s if s is verified, -1 otherwise.
     * @throws IllegalArgumentException
     *  Thrown if s less than minPacket.
     */
    public static int checkSize(String s, int minPacket) throws
      IllegalArgumentException {

        int size = Integer.parseInt(s);

        try {

            if (size < minPacket)
                throw new IllegalArgumentException();

            return size;

        }

        catch (IllegalArgumentException e) {

            System.out.println("\nArgument cannot be less than minPacket.");
            return -1;

        }

    }

    /**
     * Prompts to start another simulation
     *
     * @return
     *  A string of choice "n" or "y"
     */
    public static String endSequence() {

        Scanner input = new Scanner(System.in);

        System.out.print("\nDo you want to try another simulation? (y/n):" +
          " ");
        String selection = input.nextLine();

        if (selection.equals("n") || selection.equals("y")) {
            return selection;
        }

        return endSequence();

    }

}
