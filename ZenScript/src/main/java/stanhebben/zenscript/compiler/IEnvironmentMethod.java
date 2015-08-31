/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript.compiler;

import stanhebben.zenscript.symbols.SymbolLocal;
import stanhebben.zenscript.util.MethodOutput;

/**
 *
 * @author Stan
 */
public interface IEnvironmentMethod extends IEnvironmentClass {
	public MethodOutput getOutput();

	public int getLocal(SymbolLocal variable);
}
