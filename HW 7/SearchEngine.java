/**
 *
 * Name: Andrew Guo
 * SBU ID: 113517303
 * Recitation: R03
 *
 * This class is a program that represents a SearchEngine and runs
 * operations prompted by the user on a WebGraph constructed from the
 * pages and links files.
 *
 **/

import java.util.*;

public class SearchEngine {

    public static final String PAGES_FILE = "pages.txt";
    public static final String LINKS_FILE = "links.txt";

    private WebGraph web;

    /**
     * Constructs a new SearchEngine object.
     *
     * @postcondition
     *  This SearchEngine object has been initialized and web set to the
     *  WebGraph built from the pages and links files.
     */
    public SearchEngine() {

        this.web = WebGraph.buildFromFiles(PAGES_FILE, LINKS_FILE);

    }

    /**
     * The main program for the SearchEngine.
     *
     * @param args
     *  Command-line arguments.
     */
    public static void main(String [] args) {

        SearchEngine searchEngine = new SearchEngine();

        Scanner input = new Scanner(System.in);

        // Prints the menu in the beginning and when an operation is done.
        while (true) {

            System.out.println("\nMenu");
            System.out.println("\t(AP) - Add a new page to the graph.");
            System.out.println("\t(RP) - Remove a page from the graph.");
            System.out.println("\t(AL) - Add a link between pages in the " +
              "graph.");
            System.out.println("\t(RL) - Remove a link between pages in the " +
              "graph.");
            System.out.println("\t(P)  - Print the graph.");
            System.out.println("\t(S)  - Search for pages with a keyword.");
            System.out.println("\t(Q)  - Quit.");

            System.out.print("\nPlease select an option: ");

            // Makes input case insensitive.
            String selection = input.nextLine().toUpperCase();

            // Runs the operation to add a page.
            if (selection.equals("AP")) {

                System.out.print("Enter a URL: ");
                String url = input.nextLine();

                System.out.print("Enter keywords (space-separated): ");
                String k = input.nextLine();
                String[] keywords = k.trim().split(" ");

                searchEngine.web.addPage(url, keywords);

            }

            // Runs the operation to remove a page.
            if (selection.equals("RP")) {

                System.out.print("Enter a URL: ");
                String url = input.nextLine();

                searchEngine.web.removePage(url);

            }

            // Runs the operation to add a link.
            if (selection.equals("AL")) {

                System.out.print("Enter a source URL: ");
                String source = input.nextLine();

                System.out.print("Enter a destination URL: ");
                String destination = input.nextLine();

                System.out.println();
                searchEngine.web.addLink(source, destination);

            }

            // Runs the operation to remove a link.
            if (selection.equals("RL")) {

                System.out.print("Enter a source URL: ");
                String source = input.nextLine();

                System.out.print("Enter a destination URL: ");
                String destination = input.nextLine();

                searchEngine.web.removeLink(source, destination);

            }

            // Runs the operation to print the graph.
            if (selection.equals("P")) {

                // Menu to choose the type of sorting.
                System.out.println("\n\t(I) Sort based on index (ASC)");
                System.out.println("\t(U) Sort based on URL (ASC)");
                System.out.println("\t(R) Sort based on rank (DSC)");

                System.out.print("\nPlease select an option: ");
                selection = input.nextLine().toUpperCase();

                // Runs the operation to sort by index.
                if (selection.equals("I"))
                    searchEngine.web.sortIndex();

                // Runs the operation to sort by url.
                if (selection.equals("U"))
                    searchEngine.web.sortURL();

                // Runs the operation to sort by rank.
                if (selection.equals("R"))
                    searchEngine.web.sortRank();

                searchEngine.web.printTable();

            }

            // Runs the operation to search for pages with a keyword.
            if (selection.equals("S")) {

                System.out.print("Search keyword: ");
                String keyword = input.nextLine();

                searchEngine.web.search(keyword);

            }

            // Runs the operation to quit the program.
            if (selection.equals("Q")) {

                System.out.println("\nGoodbye.");
                break;

            }

        }

    }

}
