/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript.expression;

import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.type.natives.IJavaMethod;
import stanhebben.zenscript.type.natives.JavaMethod;
import stanhebben.zenscript.util.ZenPosition;

/**
 *
 * @author Stanneke
 */
public class ExpressionCallVirtual extends Expression {
	private final IJavaMethod method;
	
	private final Expression receiver;
	private final Expression[] arguments;
	
	public ExpressionCallVirtual(
			ZenPosition position,
			IEnvironmentGlobal environment,
			IJavaMethod method,
			Expression receiver,
			Expression... arguments) {
		super(position);
		
		this.method = method;
		
		this.receiver = receiver;
		this.arguments = JavaMethod.rematch(position, method, environment, arguments);
	}

	@Override
	public ZenType getType() {
		return method.getReturnType();
	}

	@Override
	public void compile(boolean result, IEnvironmentMethod environment) {
		receiver.compile(true, environment);
		
		for (Expression argument : arguments) {
			argument.compile(true, environment);
		}
		
		method.invokeVirtual(environment.getOutput());
		if (method.getReturnType() != ZenType.VOID && !result) {
			environment.getOutput().pop(method.getReturnType().isLarge());
		}
	}
}
