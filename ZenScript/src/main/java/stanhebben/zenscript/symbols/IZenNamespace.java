/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript.symbols;

import stanhebben.zenscript.expression.partial.IPartialExpression;
import stanhebben.zenscript.util.ZenPosition;

/**
 *
 * @author Stanneke
 */
public interface IZenNamespace {
	public IZenCompileEnvironment getEnvironment();
	
	public IPartialExpression getValue(String name, ZenPosition position);
	
	public void putValue(String name, IZenSymbol value);
}
