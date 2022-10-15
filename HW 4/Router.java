/**
 *
 * Name: Andrew Guo
 * SBU ID: 113517303
 * Recitation: R03
 *
 * This class represents a Router. A Router has a capacity.
 *
 **/

import java.nio.BufferOverflowException;
import java.util.Collection;
import java.util.LinkedList;

public class Router extends LinkedList<Packet> {

    private static int capacity;

    /**
     * Creates a new empty Router object
     *
     * @postcondition
     *  This Router has been initialized as an empty Packet linked list.
     */
    public Router() {

    }

    /**
     * Creates a new empty Router object
     *
     * @param capacity
     *  The int to set capacity of intermediate router to.
     * @postcondition
     *  This Router has been initialized as an empty Packet linked list and
     *  capacity is set to the given value.
     */
    public Router(int capacity) {

        Router.capacity = capacity;

    }

    /**
     * Adds the supplied value to the linked list
     *
     * @param p
     *  The Packet to add to the linked list of this Router.
     * @postcondition
     *  Packet is added to the end of the linked list.
     */
    public void enqueue(Packet p) {

        add(p);

    }

    /**
     * Removes the Packet on the front of the linked list
     *
     * @return
     *  The Packet object that is in front of the linked list.
     * @postcondition
     *  The Packet object that is on top of the stack of this Packet linked
     *  list is removed.
     */
    public Packet dequeue() {

        return remove();

    }

    // Peek is in LinkedList
    // Size is in LinkedList

    /**
     * Checks if the Router is empty
     *
     * @return
     *  True if the Router is empty, false otherwise.
     */
    public boolean isEmpty() {

        return size() == 0;

    }

    /**
     * Formats this Router object as a string
     *
     * @return
     *  The String representation of this Router object.
     */
    public String toString() {

        String s = "{";

        for (int i = 0; i < size(); i++) {

            s = s + get(i);

            if (i != size() - 1)
                s += ", ";

        }

        s += "}";

        return s;

    }

    /**
     * Determines which Router to send the Packet to
     *
     * @param routers
     *  The routers that the Packet can be sent to.
     * @return
     *  The number of the router with the least amount of packets buffered.
     * @throws BufferOverflowException
     *  Thrown if all routers are full.
     */
    public static int sendPacketTo(Collection routers) throws
      BufferOverflowException {

        int mostFreeRouter = 0;
        int smallestSize = 0;
        int currentRouter = 0;

        // Looks for the router with the least amount of packets.
        for (Object obj : routers) {

            currentRouter++;
            Router r = (Router) obj;

            if (currentRouter == 1) {

                mostFreeRouter = currentRouter;
                smallestSize = r.size();

            }

            else if (r.size() < smallestSize) {

                mostFreeRouter = currentRouter;
                smallestSize = r.size();

            }

        }

        // Tests if the most free Router is full.
        try {

            if (smallestSize == capacity)
                throw new BufferOverflowException();

            return mostFreeRouter;

        }

        // Catches if the most free Router is full.
        catch (BufferOverflowException e) {

            return -1;

        }

    }

}
