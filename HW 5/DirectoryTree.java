/**
 *
 * Name: Andrew Guo
 * SBU ID: 113517303
 * Recitation: R03
 *
 * This class represents a DirectoryTree. A DirectoryTree has a root and a
 * cursor.
 *
 **/

public class DirectoryTree {

    private DirectoryNode root;
    private DirectoryNode cursor;

    /**
     * Creates a new DirectoryTree object with a DirectoryNode object named
     * "root" that represents the root of the directory tree.
     *
     * @postcondition
     *  This DirectoryTree has been initialized. Both cursor and root
     *  references this DirectoryNode.
     */
    public DirectoryTree() {

        this.root = new DirectoryNode("root", false, null);
        this.cursor = root;

    }

    /**
     * Moves the cursor to the root node of the tree.
     *
     * @postcondition
     *  The cursor now references the root node of the tree.
     */
    public void resetCursor() {

        cursor = root;

    }

    /**
     * Moves the cursor to the directory with the given name.
     *
     * @param name
     *  The name of the DirectoryNode to move cursor to.
     * @throws NotADirectoryException
     *  Thrown if the indicated name references a node that is a file.
     * @postcondition
     *  The cursor now references the directory with the indicated name.
     */
    public void changeDirectory(String name) throws NotADirectoryException {

        DirectoryNode node = null;

        // Checks if each of the child nodes have the indicated name.
        if (cursor.getLeft() != null &&
          cursor.getLeft().getName().equals(name)) {

            node = cursor.getLeft();

        }

        else if (cursor.getMiddle() != null &&
          cursor.getMiddle().getName().equals(name)) {

            node = cursor.getMiddle();

        }

        else if (cursor.getRight() != null &&
          cursor.getRight().getName().equals(name)) {

            node = cursor.getRight();

        }

        // Prints if the node with the specified name does not exist.
        if (node == null) {

            System.out.println("ERROR: No such directory named '" + name
              + "'.");
            return;

        }

        else {

            // Checks if the node with the specified name is a file.
            try {

                if (node.getIsFile()) {

                    throw new NotADirectoryException();

                }

            }

            // Catches if the node with the specified name is a file.
            catch (NotADirectoryException e) {

                System.out.println("ERROR: Cannot change directory into a " +
                  "file.");
                return;

            }

        }

        // Sets cursor to the node with the specified name.
        cursor = node;

    }

    /**
     * Returns a String containing the path of directory names from the root
     * node of the tree to the cursor, each separated by a forward slash.
     *
     * @return
     *  A string with the names of the directories from root to the cursor.
     * @postcondition
     *  The cursor remains at the same DirectoryNode.
     */
    public String presentWorkingDirectory() {

        String pwd = "";

        // Create a temporary pointer.
        DirectoryNode nodePtr = cursor;

        // Iterates the temporary backwards to the root and records the names
        // of the directories.
        while (nodePtr.getParent() != null) {

            pwd = "/" + nodePtr.getName() + pwd;
            nodePtr = nodePtr.getParent();

        }

        // Adds the name of the root directory to the beginning of the string.
        pwd = "root" + pwd;

        return pwd;

    }

    /**
     * Returns a String containing the space-separated list of names of all
     * child directories or files of the cursor.
     *
     * @return
     *  A formatted String of the DirectoryNode names.
     * @postcondition
     *  The cursor remains at the same DirectoryNode.
     */
    public String listDirectory() {

        String ld = "";

        // Records the name of existing child nodes.
        if (cursor.getLeft() != null) {

            ld += cursor.getLeft().getName() + " ";

        }

        if (cursor.getMiddle() != null) {

            ld += cursor.getMiddle().getName() + " ";

        }

        if (cursor.getRight() != null) {

            ld += cursor.getRight().getName() + " ";

        }

        return ld;

    }

    /**
     * Prints a formatted nested list of names of all the nodes in the
     * directory tree starting from the cursor.
     *
     * @postcondition
     *  The cursor remains at the same DirectoryNode.
     */
    public void printDirectoryTree() {

        String dt = "";
        dt = directoryTreeHelper(cursor, 0);
        System.out.println(dt);

    }

    /**
     * A helper method to create the nested list of the names of all nodes in
     * the directory tree starting from the cursor.
     *
     * @param node
     *  The DirectoryNode to add to the nested list of nodes in the directory
     *  tree.
     * @param depth
     *  The depth of node in the directory tree.
     * @return
     *  The nested list of names of all nodes starting from the cursor.
     */
    public String directoryTreeHelper(DirectoryNode node, int depth) {

        // Base case when reached the end of the directory tree.
        if (node == null) {

            return "";

        }

        // Nests the name of the node depending on its depth
        String tab = "";
        for (int i = 0; i < depth; i++) {
            tab += "\t";
        }

        // Records the name of the file and recursively calls on each child
        // node.
        if (node.getIsFile()) {

            return tab + "- " + node.getName() + "\n" +
              directoryTreeHelper(node.getLeft(), depth + 1) +
              directoryTreeHelper(node.getMiddle(), depth + 1) +
              directoryTreeHelper(node.getRight(), depth + 1);

        }

        // Records the name of the directory and recursively calls on each
        // child node.
        else {

            return tab + "|- " + node.getName() + "\n" +
              directoryTreeHelper(node.getLeft(), depth + 1) +
              directoryTreeHelper(node.getMiddle(), depth + 1) +
              directoryTreeHelper(node.getRight(), depth + 1);

        }

    }

    /**
     * Creates a directory with the indicated name and adds it to the
     * children of the cursor node.
     *
     * @param name
     *  The name of the DirectoryNode to add.
     * @throws FullDirectoryException
     *  Thrown if all child references of this directory are occupied.
     * @throws IllegalArgumentException
     *  Thrown if the name argument is invalid.
     */
    public void makeDirectory(String name) throws FullDirectoryException,
      IllegalArgumentException {

        // Tests if the name argument is valid.
        try {

            if (checkIllegalName(name)) {

                throw new IllegalArgumentException();

            }

        }

        // Thrown if the name argument is invalid.
        catch (IllegalArgumentException e) {

            System.out.println("ERROR: Illegal name argument.");
            return;

        }

        DirectoryNode node = new DirectoryNode(name, false, cursor);

        // Adds the new node to the directory tree.
        try {

            cursor.addChild(node);

        }

        // Catches the exception thrown from addChild if the cursor is on a
        // file.
        catch (NotADirectoryException e) {

            System.out.println("ERROR: Cursor is not on a directory.");
            return;

        }

    }

    /**
     * Creates a file with the indicated name and adds it to the children of
     * the cursor node.
     *
     * @param name
     *  The name of the file to add.
     * @throws FullDirectoryException
     *  Thrown if all child references of this directory are occupied.
     * @throws IllegalArgumentException
     *  Thrown if the name argument is invalid.
     */
    public void makeFile(String name) throws FullDirectoryException,
      IllegalArgumentException {

        // Tests if the name argument is valid.
        try {

            if (checkIllegalName(name)) {

                throw new IllegalArgumentException();

            }

        }

        // Catches if the name argument is invalid.
        catch (IllegalArgumentException e) {

            System.out.println("ERROR: Illegal name argument.");
            return;

        }

        DirectoryNode node = new DirectoryNode(name, true, cursor);

        // Adds the new node to the directory tree.
        try {

            cursor.addChild(node);

        }

        // Catches the exception thrown from addChild if the cursor is on a
        // file.
        catch (NotADirectoryException e) {

            System.out.println("Error: Cursor is not on a directory.");
            return;

        }

    }

    /**
     * Verifies if the name is a legal argument.
     *
     * @param name
     *  The name to verify.
     * @return
     *  True if the name does not contain spaces or forward slashes, false
     *  otherwise.
     */
    public boolean checkIllegalName(String name) {

        return name.contains(" ") || name.contains("/");

    }

}
