/**
 *
 * Name: Andrew Guo
 * SBU ID: 113517303
 * Recitation: R03
 *
 * This class is a helper class to sort WebPages by their url.
 *
 **/

import java.util.*;

public class URLComparator implements Comparator {

    /**
     * Compares each object by their url.
     *
     * @param o1
     *  The first object to be compared.
     * @param o2
     *  The second object to be compared.
     * @return
     *  0 if their url is the same, 1 if the first object has a greater
     *  url value, -1 otherwise.
     */
    public int compare(Object o1, Object o2) {

        WebPage w1 = (WebPage) o1;
        WebPage w2 = (WebPage) o2;

        return w1.getUrl().compareTo(w2.getUrl());

    }

}
