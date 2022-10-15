/**
 *
 * Name: Andrew Guo
 * SBU ID: 113517303
 * Recitation: R03
 *
 * This class represents a block of code. A CodeBlock has a name, a
 * blockComplexity, a highestSubComplexity, and a loopVariable.
 *
 **/

public class CodeBlock {

    public static final String[] BLOCK_TYPES = { "def", "for", "while", "if",
     "elif", "else"};
    public static final int DEF = 0;
    public static final int FOR = 1;
    public static final int WHILE = 2;
    public static final int IF = 3;
    public static final int ELIF = 4;
    public static final int ELSE = 5;

    private String name;
    private Complexity blockComplexity;
    private Complexity highestSubComplexity;
    private String loopVariable;

    /**
     * Creates a new CodeBlock object with the supplied values
     *
     * @param name
     *  The String to set name to.
     * @param blockComplexity
     *  The Complexity to set blockComplexity to.
     * @param highestSubComplexity
     *  The Complexity to set highestSubComplexity to.
     * @postcondition
     *  This CodeBlock has been initialized with loopVariable as null.
     */
    public CodeBlock(String name, Complexity blockComplexity,
                     Complexity highestSubComplexity) {

        this.name = name;
        this.blockComplexity = blockComplexity;
        this.highestSubComplexity = highestSubComplexity;
        loopVariable = null;

    }

    /**
     * Creates a new CodeBlock object with the supplied values
     *
     * @param name
     *  The String to set name to.
     * @param blockComplexity
     *  The Complexity to set blockComplexity to.
     * @param highestSubComplexity
     *  The Complexity to set highestSubComplexity to.
     * @param loopVariable
     *  The String to set loopVariable to.
     * @postcondition
     *  This CodeBlock has been initialized.
     */
    public CodeBlock(String name, Complexity blockComplexity,
                     Complexity highestSubComplexity, String loopVariable) {

        this.name = name;
        this.blockComplexity = blockComplexity;
        this.highestSubComplexity = highestSubComplexity;
        this.loopVariable = loopVariable;

    }

    // Getter method for name.
    public String getName() {

        return name;

    }

    /**
     * Sets the name of this CodeBlock object.
     *
     * @param name
     *  The String to set the name of this CodeBlock object to.
     * @postcondition
     *  name is set to the supplied value.
     */
    public void setName(String name) {

        this.name = name;

    }

    // Getter method for blockComplexity
    public Complexity getBlockComplexity() {

        return blockComplexity;

    }

    /**
     * Sets the blockComplexity of this CodeBlock object.
     *
     * @param blockComplexity
     *  The Complexity to set the blockComplexity of this CodeBlock object to.
     * @postcondition
     *  blockComplexity is set to the supplied value.
     */
    public void setBlockComplexity(Complexity blockComplexity) {

        this.blockComplexity = blockComplexity;

    }

    // Getter method for highestSubComplexity
    public Complexity getHighestSubComplexity() {

        return highestSubComplexity;

    }

    /**
     * Sets the highestSubComplexity of this CodeBlock object.
     *
     * @param highestSubComplexity
     *  The Complexity to set the highestSubComplexity of this CodeBlock object
     *  to.
     * @postcondition
     *  highestSubComplexity is set to the supplied value.
     */
    public void setHighestSubComplexity(Complexity highestSubComplexity) {

        this.highestSubComplexity = highestSubComplexity;

    }

    // Getter method for loopVariable.
    public String getLoopVariable() {

        return loopVariable;

    }

    /**
     * Sets the loopVariable of this CodeBlock object.
     *
     * @param loopVariable
     *  loopVariable is set to the supplied value.
     */
    public void setLoopVariable(String loopVariable) {

        this.loopVariable = loopVariable;

    }

}
