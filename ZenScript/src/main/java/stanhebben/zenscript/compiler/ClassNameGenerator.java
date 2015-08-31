/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript.compiler;

/**
 *
 * @author Stanneke
 */
public class ClassNameGenerator {
	private int counter = 0;

	public ClassNameGenerator() {

	}

	public String generate() {
		return "ZenClass" + counter++;
	}

	public String generate(String prefix) {
		return prefix + (counter++);
	}
}
