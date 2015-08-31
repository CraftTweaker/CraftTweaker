/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript.expression.partial;

import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.expression.Expression;
import stanhebben.zenscript.expression.ExpressionCallStatic;
import stanhebben.zenscript.expression.ExpressionInvalid;
import stanhebben.zenscript.symbols.IZenSymbol;
import stanhebben.zenscript.symbols.SymbolJavaStaticGetter;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.type.natives.IJavaMethod;
import stanhebben.zenscript.util.ZenPosition;

/**
 *
 * @author Stanneke
 */
public class PartialStaticGetter implements IPartialExpression {
	private final ZenPosition position;
	private final IJavaMethod method;

	public PartialStaticGetter(ZenPosition position, IJavaMethod method) {
		this.position = position;
		this.method = method;
	}

	@Override
	public Expression eval(IEnvironmentGlobal environment) {
		return new ExpressionCallStatic(position, environment, method);
	}

	@Override
	public Expression assign(ZenPosition position, IEnvironmentGlobal environment, Expression other) {
		environment.error(position, "cannot alter this final");
		return new ExpressionInvalid(position);
	}

	@Override
	public IPartialExpression getMember(ZenPosition position, IEnvironmentGlobal environment, String name) {
		return method.getReturnType().getMember(position, environment, this, name);
	}

	@Override
	public Expression call(ZenPosition position, IEnvironmentMethod environment, Expression... values) {
		environment.error(position, "value cannot be called");
		return new ExpressionInvalid(position);
	}

	@Override
	public ZenType[] predictCallTypes(int numArguments) {
		return new ZenType[numArguments];
	}

	@Override
	public IZenSymbol toSymbol() {
		return new SymbolJavaStaticGetter(method);
	}

	@Override
	public ZenType getType() {
		return method.getReturnType();
	}

	@Override
	public ZenType toType(IEnvironmentGlobal environment) {
		environment.error(position, "not a valid type");
		return ZenType.ANY;
	}
}
