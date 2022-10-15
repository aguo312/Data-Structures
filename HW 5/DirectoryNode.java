/**
 *
 * Name: Andrew Guo
 * SBU ID: 113517303
 * Recitation: R03
 *
 * This class represents a DirectoryNode. A DirectoryNode has a name, a left,
 * a middle, a right, an isFile, and a parent.
 *
 **/

public class DirectoryNode {

    private String name;
    private DirectoryNode left;
    private DirectoryNode middle;
    private DirectoryNode right;
    private boolean isFile;
    private DirectoryNode parent;

    /**
     * Creates a new DirectoryNode object with null values and isFile set to
     * false.
     *
     * @postcondition
     *  This DirectoryNode has been initialized.
     */
    public DirectoryNode() {

        this.name = null;
        this.left = null;
        this.middle = null;
        this.right = null;
        this.isFile = false;
        this.parent = null;

    }

    /**
     * Creates a new DirectoryNode object with the supplied values.
     *
     * @param name
     *  The string to set name to.
     * @param isFile
     *  The boolean to set isFile to.
     * @param parent
     *  The DirectoryNode to set parent to.
     * @postcondition
     *  This DirectoryNode has been initialized.
     */
    public DirectoryNode(String name, boolean isFile, DirectoryNode parent) {

        this.name = name;
        this.left = null;
        this.middle = null;
        this.right = null;
        this.isFile = isFile;
        this.parent = parent;

    }

    // Getter method for name.
    public String getName() {

        return name;

    }

    // Setter method for name.
    public void setName(String name) {

        this.name = name;

    }

    // Getter method for left.
    public DirectoryNode getLeft() {

        return left;

    }

    // Getter method for middle.
    public DirectoryNode getMiddle() {

        return middle;

    }

    // Getter method for right.
    public DirectoryNode getRight() {

        return right;

    }

    // Getter method for isFile.
    public boolean getIsFile() {

        return isFile;

    }

    // Getter method for parent.
    public DirectoryNode getParent() {

        return parent;

    }

    /**
     * Adds a new node to any open child positions of the DirectoryTree.
     *
     * @param newChild
     *  The DirectoryNode to add to the DirectoryTree.
     * @throws NotADirectoryException
     *  Thrown if the current node is a file.
     * @throws FullDirectoryException
     *  Thrown if the current directory has no open child positions.
     * @postcondition
     *  A new DirectoryNode has been added as a child of node on the cursor.
     */
    public void addChild(DirectoryNode newChild) throws NotADirectoryException,
      FullDirectoryException {

        // Checks if the current node is a file.
        try {

            if (isFile) {

                throw new NotADirectoryException();

            }

        }

        // Catches if the current node is a file.
        catch (NotADirectoryException e) {

            System.out.println("ERROR: Cursor is not on a directory.");

        }

        // Checks if the current node has an opened child position.
        try {

            if (left != null && middle != null && right != null) {

                throw new FullDirectoryException();

            }

        }

        // Catches if the current node does not have an open child position.
        catch (FullDirectoryException e) {

            System.out.println("ERROR: Present directory is full.");
            return;

        }

        // Assigns newChild to the next open position (from left to right).
        if (left == null) {

            left = newChild;

        }

        else if (middle == null) {

            middle = newChild;

        }

        else {

            right = newChild;

        }

    }

}


