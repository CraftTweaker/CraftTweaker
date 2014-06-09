/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript.expression;

import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.type.natives.JavaMethod;
import stanhebben.zenscript.util.MethodOutput;
import stanhebben.zenscript.util.ZenPosition;

/**
 *
 * @author Stanneke
 */
public class ExpressionJavaCallStatic extends Expression {
	private final JavaMethod method;
	private final Expression[] arguments;
	
	public ExpressionJavaCallStatic(
			ZenPosition position,
			JavaMethod method,
			Expression... arguments) {
		super(position);
		
		this.method = method;
		this.arguments = arguments;
	}

	@Override
	public ZenType getType() {
		return method.getReturnType();
	}

	@Override
	public void compile(boolean result, IEnvironmentMethod environment) {
		MethodOutput output = environment.getOutput(); 
		
		for (Expression argument : arguments) {
			argument.compile(true, environment);
		}
		
		output.invokeStatic(method);
		
		if (method.getReturnType() != ZenType.VOID && !result) {
			output.pop(method.getReturnType().isLarge());
		}
	}
}
