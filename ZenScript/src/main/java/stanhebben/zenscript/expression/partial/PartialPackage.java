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
import stanhebben.zenscript.symbols.SymbolPackage;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.util.ZenPosition;

/**
 *
 * @author Stanneke
 */
public class PartialPackage implements IPartialExpression {
	private final ZenPosition position;
	private final SymbolPackage contents;
	
	public PartialPackage(ZenPosition position, SymbolPackage contents) {
		this.position = position;
		this.contents = contents;
	}

	@Override
	public Expression eval(IEnvironmentGlobal environment) {
		environment.error(position, "Cannot use package name as expression");
		return new ExpressionInvalid(position);
	}

	@Override
	public Expression assign(ZenPosition position, IEnvironmentGlobal environment, Expression other) {
		environment.error(position, "Cannot assign to a package");
		return new ExpressionInvalid(position);
	}

	@Override
	public IPartialExpression getMember(ZenPosition position, IEnvironmentGlobal environment, String name) {
		IZenSymbol member = contents.get(name);
		if (member == null) {
			environment.error(position, "No such member: " + name);
			return new ExpressionInvalid(position);
		} else {
			return member.instance(position);
		}
	}

	@Override
	public Expression call(ZenPosition position, IEnvironmentMethod environment, Expression... values) {
		environment.error(position, "cannot call a package");
		return new ExpressionInvalid(position);
	}

	@Override
	public IZenSymbol toSymbol() {
		return null; // not supposed to be used as symbol
	}

	@Override
	public ZenType toType(IEnvironmentGlobal environment) {
		environment.error(position, "not a valid type");
		return ZenType.ANY;
	}
}
