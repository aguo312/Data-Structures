/**
 *
 * Name: Andrew Guo
 * SBU ID: 113517303
 * Recitation: R03
 *
 * This class is a program that determines the overall complexity of a single
 * python function.
 *
 **/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class PythonTracer {

    public static final int SPACE_COUNT = 4;

    /**
     * Finds the total complexity of the function in the given filename.
     *
     * @param filename
     *  The String of the name of the file to find the total complexity of.
     * @return
     *  The Complexity object that represents the total complexity of the
     *  function in this file.
     * @postcondition
     *  The BlockStack object is empty and the total complexity is returned.
     * @throws FileNotFoundException
     *  Thrown if the file with the specified name is not found.
     */
    public static Complexity traceFile(String filename) {

        BlockStack stack = new BlockStack();

        // Tests if the file with the specified name is found.
        try {

            // Tests if the file with the specific name only has one function.
            try {

                Scanner testfile = new Scanner(new File(filename));

                if (funCount(testfile) != 1)
                    throw new NotSingleFunctionException();

            }

            // Catches if the file has 0 or more than 1 function.
            catch (NotSingleFunctionException n) {

                System.out.println("File does not contain only one function" +
                  ".\n");

                return null;
            }

            Scanner reader = new Scanner(new File(filename));

            // Counts the number of unnested blocks in the function.
            int keywordCounter = 0;

            while (reader.hasNextLine()) {

                String line = reader.nextLine();

                // Checks if the line is empty or a comment after removing
                // all leading and trailing whitespaces.
                if (!line.trim().equals("") && line.trim().charAt(0) != '#') {

                    // number of whitespace before the first character of the
                    // line
                    int lineSpace = line.indexOf(line.trim());
                    int indents = lineSpace / SPACE_COUNT;

                    while (indents < stack.stackSize()) {

                        if (indents == 0) {

                            reader.close();
                            Complexity a = stack.peek().getBlockComplexity();
                            Complexity b =
                              stack.peek().getHighestSubComplexity();

                            return totalComplexity(a, b);

                        }

                        // Totals up the complexity of the CodeBlock on the
                        // top of the stack
                        else {

                            CodeBlock oldTop = stack.pop();
                            Complexity a = oldTop.getBlockComplexity();
                            Complexity b = oldTop.getHighestSubComplexity();
                            Complexity oldTopComplexity =
                              totalComplexity(a, b);
                            String oldName = oldTop.getName();

                            Complexity highestSubComplexity =
                              stack.peek().getHighestSubComplexity();

                            // replaces highestSubComplexity with
                            // oldTopComplexity if it has a higher complexity.
                            if (compareOrder(oldTopComplexity,
                              highestSubComplexity)) {
                                stack.peek().setHighestSubComplexity(
                                  oldTopComplexity);

                                System.out.println("\tLeaving block " +
                                  oldName + ", updating block " +
                                  stack.peek().getName() + ":");

                            }

                            else {
                                System.out.println("\tLeaving block " +
                                  oldName +
                                  ", nothing to update.");
                            }

                            System.out.print("\t\tBLOCK " +
                              stack.peek().getName() + ":");
                            System.out.println("\tblock complexity = "
                              + stack.peek().getBlockComplexity() +
                              "\thighest sub-complexity = " +
                              stack.peek().getHighestSubComplexity() + "\n");

                        }

                    }

                    // Checks if line has a keyword in it
                    if (hasKeyword(line)) {

                        // Increments to keep track of a new unnested block
                        // in the function.
                        if (stack.stackSize() == 1) {

                            keywordCounter++;

                        }

                        String[] lineSplit = line.trim().split(" ");
                        String keyword = "";

                        // finds the keyword that is in the line
                        for (String x : CodeBlock.BLOCK_TYPES) {

                            if (lineSplit[0].equals(x)) {

                                keyword = x;
                                break;

                            }

                        }

                        // Pushes a new CodeBlock onto the stack if the
                        // keyword for this line is "for"
                        if (keyword.equals("for")) {

                            Complexity c = null;
                            Complexity one = new Complexity(0, 0);

                            if (lineSplit[lineSplit.length - 1].equals("N:")) {
                                c = new Complexity(1,0);
                            }
                            if (lineSplit[lineSplit.length - 1].equals(
                              "log_N:")) {
                                c = new Complexity(0,1);
                            }

                            String name = nameGenerator(indents,
                              keywordCounter);
                            CodeBlock cB = new CodeBlock(name, c, one);
                            stack.push(cB);

                        }

                        // Pushes a new CodeBlock onto the stack if the
                        // keyword for this line is "while"
                        else if (keyword.equals("while")) {

                            String loopVariable = lineSplit[1];
                            Complexity c = new Complexity(0, 0);
                            String name = nameGenerator(indents,
                              keywordCounter);
                            CodeBlock cB = new CodeBlock(name, c, c,
                              loopVariable);
                            stack.push(cB);

                        }

                        // Pushes a new CodeBlock onto the stack for every
                        // other keyword
                        else {

                            Complexity c = new Complexity(0, 0);
                            String name = nameGenerator(indents,
                              keywordCounter);
                            CodeBlock cB = new CodeBlock(name, c, c);
                            stack.push(cB);

                        }

                        System.out.println("\tEntering block " +
                          stack.peek().getName() + " '" + keyword + "':");

                        System.out.println("\t\tBLOCK " +
                          stack.peek().getName() + ":\tblock complexity = " +
                          stack.peek().getBlockComplexity() + "\thighest " +
                          "sub-complexity = " +
                          stack.peek().getHighestSubComplexity() + "\n");

                    }

                    // Updates loopVariable for a while block that is on top
                    // of the stack
                    else if (stack.peek().getLoopVariable() != null &&
                      !updateLoopVariable(line).equals("")) {

                        String loopVariable = updateLoopVariable(line);
                        stack.peek().setLoopVariable(loopVariable);
                        Complexity c = null;

                        if (stack.peek().getLoopVariable().equals("-=")) {
                            c = new Complexity(1, 0);
                        }
                        if (stack.peek().getLoopVariable().equals("/=")) {
                            c = new Complexity(0, 1);
                        }

                        stack.peek().setBlockComplexity(c);

                        System.out.println("\tFound update statement, " +
                          "updating " +
                          "block " + stack.peek().getName() + ":");
                        System.out.println("\t\tBlock " +
                          stack.peek().getName() + ":\tblock complexity = " +
                          stack.peek().getBlockComplexity() + "\thighest " +
                          "sub-complexity = " +
                          stack.peek().getHighestSubComplexity() + "\n");

                    }

                }

                // Ignores lines that are empty or comments
                else {
                }

            }

            // Totals up the Complexity of the CodeBlocks leftover in the
            // stack after there are no more lines in the file
            while (stack.stackSize() > 1) {

                CodeBlock oldTop = stack.pop();
                Complexity a = oldTop.getBlockComplexity();
                Complexity b = oldTop.getHighestSubComplexity();
                Complexity oldTopComplexity = totalComplexity(a, b);

                Complexity highestSubComplexity =
                  stack.peek().getHighestSubComplexity();

                if (compareOrder(oldTopComplexity, highestSubComplexity)) {

                    stack.peek().setHighestSubComplexity(
                      oldTopComplexity);

                }

            }

            // Returns the highest complexity of this function
            CodeBlock lastBlock = stack.pop();
            Complexity lastBlockComplexity = lastBlock.getBlockComplexity();
            Complexity lastBlockSubComplexity =
              lastBlock.getHighestSubComplexity();

            System.out.println("\tLeaving block " + lastBlock.getName()
              + ".\n");

            if (compareOrder(lastBlockComplexity, lastBlockSubComplexity))
                return lastBlockComplexity;

            return lastBlockSubComplexity;

        }

        // Catches if the file cannot be found.
        catch (FileNotFoundException e) {

            System.out.println("File not found.\n");

        }

        return null;

    }

    /**
     * Runs the program to determine the Big-Oh notation of the complexity
     * of a function from a given filename
     *
     * @param args
     *  Command line arguments
     */
    public static void main(String[] args) {

        // Asks for a file name in the beginning and after the complexity of
        // a file is found
        while (true) {

            System.out.print("Please enter a file name (or 'quit' to quit): ");
            Scanner input = new Scanner(System.in);
            String filename = input.nextLine();

            // Runs the operation to quit the program
            if (filename.equals("quit")) {

                System.out.println("Program terminating successfully...");
                break;

            }

            // Prints the total complexity of the function in the file if the
            // file is found
            else if (traceFile(filename) != null) {

                System.out.println("Overall complexity of " +
                  filename.substring(0, filename.length() - 3) + ": " +
                  traceFile(filename) + "\n");

            }

        }

    }

    public static int funCount (Scanner reader) {
        int count = 0;
        while (reader.hasNextLine()) {
            String s = reader.nextLine();
            String [] d = s.trim().split(" ");
            for (String x : d) {
                if (x.equals("def")) {
                    count++;
                }
            }
        }
        return count;
    }


    /**
     * Determines if the complexity of the first Complexity object is higher
     * than the second Complexity object.
     *
     * @param oldTopComplexity
     *  The first Complexity object being compared.
     * @param highestSubComplexity
     *  The second Complexity object being compared.
     * @return
     *  True if the first Complexity object has the higher order, false
     *  otherwise.
     */
    public static boolean compareOrder(Complexity oldTopComplexity,
                                       Complexity highestSubComplexity) {

        if (oldTopComplexity.getN_power() >
          highestSubComplexity.getN_power()) {

            return true;

        }

        else if (oldTopComplexity.getN_power() ==
          highestSubComplexity.getN_power()) {

            return oldTopComplexity.getLog_power() >
              highestSubComplexity.getLog_power();

        }

        return false;

    }

    /**
     * Determines if the line contains the keyword.
     * @param line
     *  The String that is being checked for the keyword.
     * @return
     *  True if the keyword is in the line, false otherwise.
     */
    public static boolean hasKeyword(String line) {

        String[] stringSplit = line.trim().split(" ");

        for (String x : CodeBlock.BLOCK_TYPES) {

            if (stringSplit[0].equals(x)) {

                return true;

            }

        }

        return false;
    }

    /**
     * Determines the total complexity of the given values.
     *
     * @param a
     *  The first Complexity object being totaled up.
     * @param b
     *  The second Complexity object being totaled up.
     * @return
     *  The Complexity object that is the total of the given values.
     */
    public static Complexity totalComplexity(Complexity a, Complexity b) {

        Complexity total = new Complexity(0, 0);

        total.setN_power(a.getN_power() + b.getN_power());
        total.setLog_power(a.getLog_power() + b.getLog_power());

        return total;

    }

    /**
     * Determines the name of the CodeBlock.
     *
     * @param indent
     *  The int that determines the number of sub blocks inside another block.
     * @param keywordCounter
     *  The int that determines the number of unnested block inside the
     *  function.
     * @return
     *  The name of the CodeBlock determined through the given values.
     */
    public static String nameGenerator(int indent, int keywordCounter) {

        String name = "1";

        for (int i = 0; i < indent; i++) {

            if (i == 0)
                name += "." + keywordCounter;

            else
                name += ".1";

        }

        return name;

    }

    /**
     * Determines the updated loopVariable.
     *
     * @param line
     *  The String to find if the loopVariable can be updated.
     * @return
     *  The String representation of the updated loopVariable or an empty
     *  string if the loopVariable cannot be updated.
     */
    public static String updateLoopVariable(String line) {

        String[] lineSplit = line.split(" ");

        for (String x : lineSplit) {

            if (x.equals("-="))
                return "-=";

            if (x.equals("/="))
                return "/=";

        }

        return "";

    }

}

// Exception for when the file does not contain only one function
class NotSingleFunctionException extends Exception {

    public NotSingleFunctionException() {
    }

}