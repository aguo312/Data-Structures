/**
 *
 * Name: Andrew Guo
 * SBU ID: 113517303
 * Recitation: R03
 *
 * This class is a helper class to sort WebPages by their rank.
 *
 **/

import java.util.*;

public class RankComparator implements Comparator {

    /**
     * Compares each object by their rank.
     *
     * @param o1
     *  The first object to be compared.
     * @param o2
     *  The second object to be compared.
     * @return
     *  0 if their index is the same, 1 if the first object has a smaller
     *  index, -1 otherwise.
     */
    public int compare(Object o1, Object o2) {


        WebPage w1 = (WebPage) o1;
        WebPage w2 = (WebPage) o2;

        if (w1.getRank() == w2.getRank())
            return 0;

        else if (w1.getRank() < w2.getRank())
            return 1;

        else
            return -1;

    }

}
