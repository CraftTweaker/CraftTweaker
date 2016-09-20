/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript.compiler;

import org.objectweb.asm.ClassWriter;

/**
 *
 * @author Stan
 */
public interface IEnvironmentClass extends IEnvironmentGlobal {
	public ClassWriter getClassOutput();
}
