/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript.expression.partial;

import java.util.Arrays;
import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.expression.Expression;
import stanhebben.zenscript.expression.ExpressionCallStatic;
import stanhebben.zenscript.expression.ExpressionInvalid;
import stanhebben.zenscript.symbols.IZenSymbol;
import stanhebben.zenscript.symbols.SymbolZenStaticMethod;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.type.natives.JavaMethod;
import stanhebben.zenscript.util.ZenPosition;

/**
 *
 * @author Stan
 */
public class PartialStaticGenerated implements IPartialExpression {
	private final ZenPosition position;
	private final String owner;
	private final String method;
	private final String signature;
	private final ZenType[] argumentTypes;
	private final ZenType returnType;

	public PartialStaticGenerated(ZenPosition position, String owner, String method, String signature, ZenType[] argumentTypes, ZenType returnType) {
		this.position = position;
		this.owner = owner;
		this.method = method;
		this.signature = signature;
		this.argumentTypes = argumentTypes;
		this.returnType = returnType;
	}

	@Override
	public Expression eval(IEnvironmentGlobal environment) {
		environment.error(position, "cannot use a function as value");
		return new ExpressionInvalid(position);
	}

	@Override
	public Expression assign(ZenPosition position, IEnvironmentGlobal environment, Expression other) {
		environment.error(position, "cannot assign to a function");
		return new ExpressionInvalid(position);
	}

	@Override
	public IPartialExpression getMember(ZenPosition position, IEnvironmentGlobal environment, String name) {
		environment.error(position, "functions don't have members");
		return new ExpressionInvalid(position);
	}

	@Override
	public Expression call(ZenPosition position, IEnvironmentMethod environment, Expression... values) {
		if (values.length != argumentTypes.length) {
			environment.error(position, "invalid number of arguments");
			return new ExpressionInvalid(position);
		}

		Expression[] arguments = new Expression[argumentTypes.length];
		for (int i = 0; i < arguments.length; i++) {
			arguments[i] = values[i].cast(position, environment, argumentTypes[i]);
		}

		return new ExpressionCallStatic(position, environment, JavaMethod.getStatic(owner, method, returnType, argumentTypes), arguments);
	}

	@Override
	public ZenType[] predictCallTypes(int numArguments) {
		return Arrays.copyOf(argumentTypes, numArguments);
	}

	@Override
	public IZenSymbol toSymbol() {
		return new SymbolZenStaticMethod(owner, method, signature, argumentTypes, returnType);
	}

	@Override
	public ZenType getType() {
		return returnType;
	}

	@Override
	public ZenType toType(IEnvironmentGlobal environment) {
		environment.error(position, "not a valid type");
		return ZenType.ANY;
	}
}
