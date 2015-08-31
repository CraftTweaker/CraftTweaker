/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript.symbols;

import java.lang.reflect.Type;
import stanhebben.zenscript.expression.partial.IPartialExpression;
import stanhebben.zenscript.expression.partial.PartialJavaClass;
import stanhebben.zenscript.util.ZenPosition;

/**
 *
 * @author Stanneke
 */
public class SymbolJavaClass implements IZenSymbol {
	private final Type cls;

	public SymbolJavaClass(Type cls) {
		this.cls = cls;
	}

	@Override
	public IPartialExpression instance(ZenPosition position) {
		return new PartialJavaClass(position, cls);
	}
}
