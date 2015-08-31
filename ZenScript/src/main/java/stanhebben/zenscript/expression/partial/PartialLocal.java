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
import stanhebben.zenscript.expression.ExpressionLocalGet;
import stanhebben.zenscript.expression.ExpressionLocalSet;
import stanhebben.zenscript.symbols.IZenSymbol;
import stanhebben.zenscript.symbols.SymbolLocal;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.util.ZenPosition;

/**
 *
 * @author Stanneke
 */
public class PartialLocal implements IPartialExpression {
	private final ZenPosition position;
	private final SymbolLocal variable;

	public PartialLocal(ZenPosition position, SymbolLocal variable) {
		this.position = position;
		this.variable = variable;
	}

	@Override
	public Expression eval(IEnvironmentGlobal environment) {
		return new ExpressionLocalGet(position, variable);
	}

	@Override
	public IPartialExpression getMember(ZenPosition position, IEnvironmentGlobal environment, String name) {
		return variable.getType().getMember(position, environment, this, name);
	}

	@Override
	public Expression assign(ZenPosition position, IEnvironmentGlobal environment, Expression other) {
		if (variable.isFinal()) {
			environment.error(position, "value cannot be changed");
			return new ExpressionInvalid(position);
		} else {
			return new ExpressionLocalSet(position, variable, other);
		}
	}

	@Override
	public Expression call(ZenPosition position, IEnvironmentMethod environment, Expression... values) {
		return variable.getType().call(position, environment, eval(environment), values);
	}

	@Override
	public ZenType[] predictCallTypes(int numArguments) {
		return variable.getType().predictCallTypes(numArguments);
	}

	@Override
	public IZenSymbol toSymbol() {
		return variable;
	}

	@Override
	public ZenType getType() {
		return variable.getType();
	}

	@Override
	public ZenType toType(IEnvironmentGlobal environment) {
		environment.error(position, "not a valid type");
		return ZenType.ANY;
	}
}
