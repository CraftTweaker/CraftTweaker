/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript.symbols;

import stanhebben.zenscript.expression.ExpressionArgument;
import stanhebben.zenscript.expression.partial.IPartialExpression;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.util.ZenPosition;

/**
 *
 * @author Stanneke
 */
public class SymbolArgument implements IZenSymbol {
	private final int id;
	private final ZenType type;
	
	public SymbolArgument(int id, ZenType type) {
		this.id = id;
		this.type = type;
	}

	@Override
	public IPartialExpression instance(ZenPosition position) {
		return new ExpressionArgument(position, id, type);
	}
}
