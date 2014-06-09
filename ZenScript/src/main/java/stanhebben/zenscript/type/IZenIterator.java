/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript.type;

import org.objectweb.asm.Label;

/**
 *
 * @author Stanneke
 */
public interface IZenIterator {
	/**
	 * Compiles the header before the iteration. The list is on the top of the stack.
	 * 
	 * @param locals
	 */
	public void compileStart(int[] locals);
	
	/**
	 * Compiles the start of an iteration. The stack is unmodified from the 
	 * previous iteration and from the iteration start.
	 * 
	 * @param locals
	 * @param exit 
	 */
	public void compilePreIterate(int[] locals, Label exit);
	
	/**
	 * Compiles the end of an iteration. The stack is the same as it was after
	 * preIterate.
	 * 
	 * @param locals
	 * @param exit
	 * @param repeat 
	 */
	public void compilePostIterate(int[] locals, Label exit, Label repeat);
	
	/**
	 * Compiles the end of the whole iteration.
	 */
	public void compileEnd();
	
	public ZenType getType(int i);
}
