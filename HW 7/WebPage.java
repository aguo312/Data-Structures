/**
 *
 * Name: Andrew Guo
 * SBU ID: 113517303
 * Recitation: R03
 *
 * This class represents a WebPage. A WebPage has a url, an index, a rank,
 * and keywords.
 *
 **/

import java.util.*;

public class WebPage {

    private String url;
    private int index;
    private int rank;
    private String[] keywords;

    /**
     * Creates a new WebPage object with index and rank set to 0 and url and
     * keywords set to null.
     *
     * @postcondition
     *  This WebPage has been initialized.
     */
    public WebPage() {

        this.url = null;
        this.index = 0;
        this.rank = 0;
        this.keywords = null;

    }

    /**
     * Creates a new WebPage object with the supplied values.
     *
     * @param url
     *  The string to set url to.
     * @param index
     *  The int to set index to.
     * @param rank
     *  The int to set rank to.
     * @param keywords
     *  The String[] to set keywords to.
     * @postcondition
     *  This WebPage object has been initialized.
     */
    public WebPage(String url, int index, int rank,
                   String[] keywords) {

        this.url = url;
        this.index = index;
        this.rank = rank;
        this.keywords = keywords;

    }

    // Getter method for url.
    public String getUrl() {

        return url;

    }

    // Getter method for index.
    public int getIndex() {

        return index;

    }

    // Setter method for index.
    public void setIndex(int index) {

        this.index = index;

    }

    // Getter method for rank.
    public int getRank() {

        return rank;

    }

    // Setter method for rank.
    public void setRank(int rank) {

        this.rank = rank;

    }

    // Getter method for keywords.
    public String[] getKeywords() {

        return keywords;

    }

    /**
     * Changes WebPage into a formatted string.
     *
     * @return
     *  The string of data members in tabular form.
     */
    public String toString() {

        // Formats the keywords correctly.
        String k = "";

        for (int i = 0; i < keywords.length; i++) {

            if (i == keywords.length - 1)
                k += keywords[i];

            else
                k += keywords[i] + ", ";

        }

        String f1 = String.format("%3d%5s%-19s%1s%5d%6s", index,
          "| ", url, "|", rank, "| ");
        String f2 = String.format("%-18s", "******************");
        String f3 = String.format("%2s%1s", "| ", k);


        return f1 + f2 + f3;

    }
/*
    public int compareTo(Object o) {

        WebPage other = (WebPage) o;
        if (index == other.index)
            return 0;
        else if (index > other.index)
            return 1;
        else
            return -1;

    }
*/
}
