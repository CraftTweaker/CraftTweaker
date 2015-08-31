/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript.symbols;

import stanhebben.zenscript.expression.partial.IPartialExpression;
import stanhebben.zenscript.expression.partial.PartialLocal;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.util.ZenPosition;

/**
 *
 * @author Stanneke
 */
public class SymbolLocal implements IZenSymbol {
	private final ZenType type;
	private final boolean isFinal;

	public SymbolLocal(ZenType type, boolean isFinal) {
		this.type = type;
		this.isFinal = isFinal;
	}

	public ZenType getType() {
		return type;
	}

	public boolean isFinal() {
		return isFinal;
	}

	@Override
	public IPartialExpression instance(ZenPosition position) {
		return new PartialLocal(position, this);
	}
}
