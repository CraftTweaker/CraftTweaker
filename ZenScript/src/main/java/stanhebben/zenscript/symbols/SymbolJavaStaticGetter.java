/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript.symbols;

import stanhebben.zenscript.expression.partial.IPartialExpression;
import stanhebben.zenscript.expression.partial.PartialStaticGetter;
import stanhebben.zenscript.type.natives.JavaMethod;
import stanhebben.zenscript.util.ZenPosition;

/**
 *
 * @author Stanneke
 */
public class SymbolJavaStaticGetter implements IZenSymbol {
	private final JavaMethod method;
	
	public SymbolJavaStaticGetter(JavaMethod method) {
		this.method = method;
	}

	@Override
	public IPartialExpression instance(ZenPosition position) {
		return new PartialStaticGetter(position, method);
	}
}
