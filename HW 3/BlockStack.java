/**
 *
 * Name: Andrew Guo
 * SBU ID: 113517303
 * Recitation: R03
 *
 * This class represents a stack. A BlockStack has a blockStack and a
 * blockStackSize.
 *
 **/

import java.util.Stack;

public class BlockStack {

    private Stack<CodeBlock> blockStack;
    private int blockStackSize;

    /**
     * Creates a new empty BlockStack object
     *
     * @postcondition
     *  This BlockStack has been initialized as an empty CodeBlock Stack and
     *  blockStackSize is 0.
     */
    public BlockStack() {

        blockStack = new Stack<CodeBlock>();
        blockStackSize = 0;

    }

    /**
     * Pushes the supplied value on the stack.
     *
     * @param block
     *  The CodeBlock to push on the stack of this BlockStack object.
     * @postcondition
     *  CodeBlock is on the top of the stack and blockStackSize increases by 1.
     */
    public void push(CodeBlock block) {

        blockStackSize++;
        blockStack.push(block);

    }

    /**
     * Pops the CodeBlock on the top of the stack.
     *
     * @return
     *  The CodeBlock object that is on the top of the stack of this
     *  BlockStack object.
     * @postcondtion
     *  The CodeBlock object on top of the stack of this BlockStack object is
     *  removed and blockStackSize decreases by 1.
     */
    public CodeBlock pop() {

        blockStackSize--;
        return blockStack.pop();

    }

    /**
     * Looks at the CodeBlock on the top of the stack.
     *
     * @return
     *  The CodeBlock object that is on the top of the stack of this
     *  BlockStack object.
     * @postcondition
     *  The stack of this BlockStack object stays the same.
     */
    public CodeBlock peek() {

        return blockStack.peek();

    }

    /**
     * Gets the size of the stack of this BlockStack object.
     *
     * @return
     *  The int that represents the number of CodeBlock objects on the stack
     *  of this BlockStack object.
     * @postcondition
     *  The size of the stack of this BlockStack object is returned.
     */
    public int stackSize() {

        return blockStackSize;

    }

}
