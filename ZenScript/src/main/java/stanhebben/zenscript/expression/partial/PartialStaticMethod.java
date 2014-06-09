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
import stanhebben.zenscript.symbols.SymbolJavaStaticMethod;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.type.natives.JavaMethod;
import stanhebben.zenscript.util.ZenPosition;

/**
 *
 * @author Stanneke
 */
public class PartialStaticMethod implements IPartialExpression {
	private final ZenPosition position;
	private final JavaMethod method;
	
	public PartialStaticMethod(ZenPosition position, JavaMethod method) {
		this.position = position;
		this.method = method;
	}

	@Override
	public Expression eval(IEnvironmentGlobal environment) {
		environment.error(position, "not a valid expression");
		return new ExpressionInvalid(position);
	}

	@Override
	public Expression assign(ZenPosition position, IEnvironmentGlobal environment, Expression other) {
		environment.error(position, "cannot alter this final");
		return new ExpressionInvalid(position);
	}

	@Override
	public IPartialExpression getMember(ZenPosition position, IEnvironmentGlobal environment, String name) {
		environment.error(position, "methods have no members");
		return new ExpressionInvalid(position);
	}

	@Override
	public Expression call(ZenPosition position, IEnvironmentMethod environment, Expression... values) {
		if (method.accepts(environment, values)) {
			return method.callStatic(position, environment, values);
		} else {
			environment.error(position, "parameter count mismatch");
			return new ExpressionInvalid(position, method.getReturnType());
		}
	}

	@Override
	public IZenSymbol toSymbol() {
		return new SymbolJavaStaticMethod(method);
	}

	@Override
	public ZenType toType(IEnvironmentGlobal environment) {
		return ZenType.ANY;
	}
}
