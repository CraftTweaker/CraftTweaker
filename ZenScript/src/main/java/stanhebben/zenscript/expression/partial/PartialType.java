/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript.expression.partial;

import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.expression.Expression;
import stanhebben.zenscript.expression.ExpressionInvalid;
import stanhebben.zenscript.symbols.IZenSymbol;
import stanhebben.zenscript.symbols.SymbolType;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.util.ZenPosition;

/**
 *
 * @author Stan
 */
public class PartialType implements IPartialExpression {
	private final ZenPosition position;
	private final ZenType type;
	
	public PartialType(ZenPosition position, ZenType type) {
		this.position = position;
		this.type = type;
	}

	@Override
	public Expression eval(IEnvironmentGlobal environment) {
		environment.error(position, "cannot use type as expression");
		return new ExpressionInvalid(position, type);
	}

	@Override
	public Expression assign(ZenPosition position, IEnvironmentGlobal environment, Expression other) {
		environment.error(position, "cannot assign to a type");
		return new ExpressionInvalid(position, type);
	}

	@Override
	public IPartialExpression getMember(ZenPosition position, IEnvironmentGlobal environment, String name) {
		return type.getStaticMember(position, environment, name);
	}

	@Override
	public Expression call(ZenPosition position, IEnvironmentMethod environment, Expression... values) {
		environment.error(position, "cannot call a type");
		return new ExpressionInvalid(position, type);
	}

	@Override
	public IZenSymbol toSymbol() {
		return new SymbolType(type);
	}

	@Override
	public ZenType toType(IEnvironmentGlobal environment) {
		return type;
	}
}
