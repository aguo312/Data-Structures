/**
 *
 * Name: Andrew Guo
 * SBU ID: 113517303
 * Recitation: R03
 *
 * This class is a program that models a file system using a ternary
 * (3-child) tree data structure.
 *
 **/

import java.util.Scanner;

public class BashTerminal {

    public static void main(String[] args) throws NotADirectoryException,
      FullDirectoryException {

        DirectoryTree directory = new DirectoryTree();
        Scanner input = new Scanner(System.in);

        System.out.println("Starting bash terminal.");

        // Allows the user to input commands to interact with the file system.
        while (true) {

            // Prompts a command.
            System.out.print("[113517303@host]: $ ");
            String command = input.nextLine();

            // Runs the command to print the present working directory of the
            // cursor node.
            if (command.equals("pwd")) {

                System.out.println(directory.presentWorkingDirectory());

            }

            // Runs the command to list the names of all child directories or
            // files of the cursor node.
            if (command.equals("ls")) {

                System.out.println(directory.listDirectory());

            }

            // Runs the command to print the entire tree starting from the
            // cursor in pre-order traversal.
            if (command.equals("ls -R")) {

                directory.printDirectoryTree();

            }

            // Runs the command to change directory.
            if (command.length() > 3 && command.startsWith("cd ")) {

                // Moves the cursor to the root of the tree.
                if (command.substring(3).equals("/")) {

                    directory.resetCursor();

                }

                // Moves the cursor to the child directory with the indicated
                // name.
                else {

                    directory.changeDirectory(command.substring(3));

                }

            }

            // Runs the command to create a new directory with the indicated
            // name as a child of the cursor node.
            if (command.length() > 6 && command.startsWith("mkdir ")) {

                directory.makeDirectory(command.substring(6));

            }

            // Runs the command to create a new file with the indicated name
            // as a child of the cursor node.
            if (command.length() > 6 && command.startsWith("touch ")) {

                directory.makeFile(command.substring(6));

            }

            // Runs the command to terminate the program.
            if (command.equals("exit")) {

                System.out.println("Program terminating normally");
                break;

            }

        }

    }

}

// Exception for when adding a node to a full directory.
class FullDirectoryException extends Exception {

    public FullDirectoryException() {

    }

}

// Exception for when the node is not a directory.
class NotADirectoryException extends Exception {

    public NotADirectoryException() {

    }

}