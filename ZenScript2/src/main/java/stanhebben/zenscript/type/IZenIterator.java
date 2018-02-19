package stanhebben.zenscript.type;

import org.objectweb.asm.Label;

/**
 * @author Stanneke
 */
public interface IZenIterator {
    
    /**
     * Compiles the header before the iteration. The list is on the top of the
     * stack.
     *
     * @param locals
     */
    void compileStart(int[] locals);
    
    /**
     * Compiles the start of an iteration. The stack is unmodified from the
     * previous iteration and from the iteration start.
     *
     * @param locals
     * @param exit
     */
    void compilePreIterate(int[] locals, Label exit);
    
    /**
     * Compiles the end of an iteration. The stack is the same as it was after
     * preIterate.
     *
     * @param locals
     * @param exit
     * @param repeat
     */
    void compilePostIterate(int[] locals, Label exit, Label repeat);
    
    /**
     * Compiles the end of the whole iteration.
     */
    void compileEnd();
    
    ZenType getType(int i);
}
