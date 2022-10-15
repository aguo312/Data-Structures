/**
 *
 * Name: Andrew Guo
 * SBU ID: 113517303
 * Recitation: R03
 *
 * This class represents a WebGraph. A WebGraph has pages and links.
 *
 **/

import java.io.*;
import java.util.*;

public class WebGraph {

    public static final int MAX_PAGES = 40;

    private ArrayList<WebPage> pages;
    private int[][] links;

    /**
     * Creates a new WebGraph object with values null.
     *
     * @postcondition
     *  This WebGraph has been initialized.
     */
    public WebGraph() {

        this.pages = null;
        this.links = null;

    }

    /**
     * Creates a new WebGraph object with the supplied values.
     *
     * @param pages
     *  The ArrayList<WebPage> to set pages to.
     * @param links
     *  The int[][] to set links to.
     */
    public WebGraph(ArrayList<WebPage> pages, int[][] links) {

        this.pages = pages;
        this.links = links;

    }

    // Getter method for pages.
    public ArrayList<WebPage> getPages() {

        return this.pages;

    }

    /**
     * Adds a page to the WebGraph
     *
     * @param url
     *  The url of the new WebPage.
     * @param keywords
     *  The keywords of the new WebPage.
     * @throws IllegalArgumentException
     *  Thrown if either argument is null or if the url already exists in the
     *  graph.
     * @postcondition
     *  The page has been added to pages.
     */
    public void addPage(String url, String[] keywords)
      throws IllegalArgumentException {

        // Tests if either arguments are null.
        try {

            if (url == null || keywords == null) {

                throw new IllegalArgumentException();

            }

        }

        // Catches if either arguments are null.
        catch (IllegalArgumentException e) {

            System.out.println("Error: One or more arguments are null.");
            return;

        }

        // Tests if the url already exists in the graph.
        try {

            for (int i = 0; i < pages.size(); i++) {

                if (pages.get(i).getUrl().equals(url)) {

                    throw new IllegalArgumentException();

                }

            }

        }

        // Catches if the url already exists in the graph.
        catch (IllegalArgumentException e) {

            System.out.println("\nError: " + url + " already exists in the " +
              "WebGraph. Could not add new WebPage.");
            return;

        }

        WebPage newPage = new WebPage(url, pages.size(), 0, keywords);
        pages.add(newPage);
        updatePageRanks();

        System.out.println();
        System.out.println(url + " successfully added to the WebGraph!");

    }

    /**
     * Adds a link from the source WebPage to the destination WebPage.
     *
     * @param source
     *  The url of the source WebPage.
     * @param destination
     *  The url of the destination WebPage.
     * @throws IllegalArgumentException
     *  Thrown if either url are null or cannot be found in pages.
     * @postconditon
     *  A link is added from source to destination.
     */
    public void addLink(String source, String destination)
      throws IllegalArgumentException {

        // Tests if either arguments are null.
        try {

            if (source == null || destination == null)
                throw new IllegalArgumentException();

        }

        // Catches if either arguments are null.
        catch (IllegalArgumentException e) {

            System.out.println("Error: One or more arguments are null.");
            return;

        }

        // Variables to hold the index of the source and destination.
        int s = -1;
        int d = -1;

        for (int i = 0; i < pages.size(); i++) {

            if (pages.get(i).getUrl().equals(source))
                s = pages.get(i).getIndex();

            if (pages.get(i).getUrl().equals(destination))
                d = pages.get(i).getIndex();

        }

        // Tests if the source and destination are in pages.
        try {

            if (s < 0 || d < 0)
                throw new IllegalArgumentException();

        }

        // Catches if either the source or destination are not in pages.
        catch (IllegalArgumentException e) {

            if (s < 0 && d < 0)
                System.out.println("Error: " + source + " and " + destination +
                  " could not be found in the WebGraph.");

            else if (s < 0)
                System.out.println("Error: " + source + " could not be found " +
                  "in the WebGraph.");

            else
                System.out.println("Error: " + destination + " could not be " +
                  "found in the WebGraph.");

            return;

        }

        links[s][d] = 1;
        updatePageRanks();

        System.out.println("Link successfully added from " + source + " to "
          + destination + "!");

    }

    /**
     * Removes the WebPage from the graph with the given URL.
     *
     * @param url
     *  The url of the page to remove from the graph.
     * @postcondition
     *  The WebPage with the specified url is removed from the graph.
     */
    public void removePage(String url) {

        // Continues if the url is not null.
        if (url != null) {

            // Variable to hold the index of the WebPage with the specified
            // url.
            int index = -1;

            for (int i = 0; i < pages.size(); i++) {

                if (pages.get(i).getUrl().equals(url))
                    index = pages.get(i).getIndex();

            }

            System.out.println();

            // Continues if the WebPage with the specified url is found.
            if (index > -1) {

                pages.remove(index);

                // Decrements the indexes of webpages with higher index of
                // the removed WebPage.
                for (int i = 0; i < pages.size(); i++) {

                    if (pages.get(i).getIndex() > index)
                        pages.get(i).setIndex(pages.get(i).getIndex() - 1);

                }

                // Adjusts the adjacency matrix for the removed WebPage.
                for (int i = 0; i < pages.size(); i++) {

                    for (int j = index; j < pages.size(); j++) {

                        if (j == MAX_PAGES - 1)
                            links[i][j] = 0;

                        links[i][j] = links[i][j + 1];

                    }

                }

                for (int i = index; i < pages.size(); i++) {

                    for (int j = 0; j < pages.size(); j++) {

                        if (i == MAX_PAGES - 1)
                            links[i][j] = 0;

                        links[i][j] = links[i + 1][j];

                    }

                }

                updatePageRanks();
                System.out.println(url + " has been removed from the graph!");

            }

        }

    }

    /**
     * Removes the link from source WebPage from the destination WebPage.
     *
     * @param source
     *  The url of the WebPage to remove the link.
     * @param destination
     *  The url of the link to be removed.
     * @postcondition
     *  The entry in links for the specified hyperlink is set to 0.
     */
    public void removeLink(String source, String destination) {

        // Continues if neither of the arguments are null.
        if (source != null && destination != null) {

            // Variables to hold the index of the source and destination.
            int s = -1;
            int d = -1;

            for (int i = 0; i < pages.size(); i++) {

                if (pages.get(i).getUrl().equals(source))
                    s = pages.get(i).getIndex();

                if (pages.get(i).getUrl().equals(destination))
                    d = pages.get(i).getIndex();

            }

            // Continues if both source and destination are found.
            if (s > -1 && d > -1) {

                links[s][d] = 0;
                updatePageRanks();

                System.out.println("\nLink removed from " + source + " to " +
                  destination + "!");

            }

        }

    }

    /**
     * Calculates and assigns the PageRank for every Page in the WebGraph.
     *
     * @postcondition
     *  All WebPages in the graph have been assigned their proper PageRank.
     */
    public void updatePageRanks() {

        for (int j = 0; j < pages.size(); j++) {

            // Counter for the PageRank.
            int count = 0;

            for (int i = 0; i < pages.size(); i++) {

                if (links[i][j] == 1)
                    count++;

                // Sets the PageRank at the end of the column.
                if (i == pages.size() - 1)
                    pages.get(j).setRank(count);

            }

        }

    }

    /**
     * Prints the WebGraph in tabular form.
     *
     * @postcondition
     *  The WebGraph and all its pages are printed.
     */
    public void printTable() {

        // Header of the table.
        System.out.printf("%5s%8s%23s%7s%24s", "\nIndex", "URL", "PageRank",
          "Links", "Keywords\n");
        String line = "";

        for (int i = 0; i < 100; i++) {

            line += "-";

        }

        System.out.println(line);

        // Array to store the information for the links to a page.
        String[] link = new String [pages.size()];

        for (int i = 0; i < pages.size(); i++) {

            String l = "";

            for (int j = 0; j < pages.size(); j++) {

                    if (links[i][j] == 1) {

                        l += j + ", ";

                    }

            }

            if (l.length() != 0)
                link[i] = l.substring(0, l.length() - 2);

        }

        // Formats the links in the array.
        for (int i = 0; i < link.length; i++) {

            if (link[i] == null) {

                link[i] = "";

            }

            while (link[i].length() < 18) {

                link[i] += " ";

            }

        }

        // Replaces the * with the links of the page and prints it.
        for (int i = 0; i < pages.size(); i++) {

            String s = pages.get(i).toString();
            s = s.replace("******************",
              link[pages.get(i).getIndex()]);
            System.out.println(s);

        }

    }

    /**
     * Searches for the WebPages with the specified keyword.
     *
     * @param keyword
     *  The keyword of a WebPage to look for.
     * @postcondition
     *  Prints a table of each url of the WebPage with the specified keyword
     *  sorted by rank.
     */
    public void search(String keyword) {

        // Arraylist to store the WebPages with the specified keyword.
        ArrayList<WebPage> search = new ArrayList<WebPage>();

        for (int i = 0; i < pages.size(); i++) {

            String[] keywords = pages.get(i).getKeywords();

            for (String k : keywords) {

                if (k.equals(keyword)) {

                    search.add(pages.get(i));

                }

            }

        }

        // Continues if WebPages with the specified keyword are found.
        if (search.size() != 0) {

            // Sorts the searched WebPages by rank.
            Collections.sort(search, new RankComparator());

            // Header of the table.
            System.out.printf("%4s%11s%7s", "\nRank", "PageRank", "URL\n");

            String l = "";

            for (int i = 0; i < 45; i++) {

                l += "-";

            }

            System.out.println(l);

            // Prints each of the searched WebPages.
            for (int i = 0; i < search.size(); i++) {

                System.out.printf("%3d%3s%5d%7s%-1s", i + 1, "|",
                  search.get(i).getRank(), "| ", search.get(i).getUrl()
                    + "\n");

            }

        }

        // Runs if no WebPages has the specified keyword.
        else
            System.out.println("\nNo search results found for the keyword " +
              keyword + ".");

    }

    // Sort method by index.
    public void sortIndex() {

        Collections.sort(pages, new IndexComparator());

    }

    // Sort method by URL.
    public void sortURL() {

        sortIndex();
        Collections.sort(pages, new URLComparator());

    }

    // Sort method by rank.
    public void sortRank() {

        sortIndex();
        Collections.sort(pages, new RankComparator());

    }

    /**
     * Constructs a WebGraph object using the indicated files as the source
     * for pages and links.
     *
     * @param pagesFile
     *  String of the file containing the page information.
     * @param linksFile
     *  String of the file containing the link information.
     * @return
     *  The WebGraph constructed from text files.
     * @throws IllegalArgumentException
     *  Thrown if the files do not reference a valid file, or the files are
     *  not formatted correctly.
     * @postcondition
     *  A WebGraph has been constructed and initialized based on the text
     *  files.
     */
    public static WebGraph buildFromFiles(String pagesFile, String linksFile)
      throws IllegalArgumentException {

        FileInputStream fis = null;

        // Tests if the pages file is valid.
        try {

            fis = new FileInputStream(pagesFile);

        }

        // Catches if the file is invalid.
        catch (Exception e) {

            try {

                throw new IllegalArgumentException();

            }

            catch (IllegalArgumentException f) {

                System.out.println("Error: Invalid File.");

            }

        }

        Scanner reader = new Scanner(fis);

        // Arraylist to hold the information from the pages file.
        ArrayList<WebPage> p = new ArrayList<WebPage>();

        // Reads and formats the information from the pages file and creates a
        // new page to add to the arraylist.
        while (reader.hasNextLine()) {

            String[] s = reader.nextLine().trim().split(" ");
            String[] k = new String[s.length - 1];

            for (int i = 1; i < s.length; i++) {

                k[i - 1] = s[i];

            }

            WebPage newPage = new WebPage(s[0], p.size(), 0, k);
            p.add(newPage);

        }

        // Tests if the links file is valid.
        try {

            fis = new FileInputStream(linksFile);

        }

        // Catches if the links file is invalid.
        catch (Exception e) {

            try {

                throw new IllegalArgumentException();

            }

            catch (IllegalArgumentException f) {

                System.out.println("Error: Invalid File.");

            }

        }

        reader = new Scanner(fis);

        // 2-D array to hold the information from the links file.
        int[][] e = new int[MAX_PAGES][MAX_PAGES];

        // Reads the information from the links file and puts it in the 2-D
        // array.
        while (reader.hasNextLine()) {

            String[] s = reader.nextLine().trim().split(" ");
            int from = 0;
            int to = 0;

            for (int i = 0; i < p.size(); i++) {

                if (p.get(i).getUrl().equals(s[0]))
                    from = i;

                if (p.get(i).getUrl().equals(s[1]))
                    to = i;

            }

            e[from][to]++;

        }

        WebGraph newGraph = new WebGraph(p, e);
        newGraph.updatePageRanks();

        System.out.println("Loading WebGraph data...\nSuccess!");

        return newGraph;

    }

}
