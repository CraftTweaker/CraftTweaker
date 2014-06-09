/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript.expression;

import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.util.MethodOutput;
import stanhebben.zenscript.util.ZenPosition;

/**
 *
 * @author Stanneke
 */
public class ExpressionJavaCallStaticGenerated extends Expression {
	private final String target;
	private final String name;
	private final String descriptor;
	
	private final ZenType returnZType;
	private final Expression[] arguments;
	
	public ExpressionJavaCallStaticGenerated(
			ZenPosition position,
			String target, String name, String descriptor,
			ZenType returnZType,
			Expression... arguments) {
		super(position);
		
		this.target = target;
		this.name = name;
		this.descriptor = descriptor;
		
		this.returnZType = returnZType;
		this.arguments = arguments;
	}

	@Override
	public ZenType getType() {
		return returnZType;
	}

	@Override
	public void compile(boolean result, IEnvironmentMethod environment) {
		MethodOutput output = environment.getOutput();
		
		for (Expression argument : arguments) {
			argument.compile(true, environment);
		}
		
		output.invokeStatic(target, name, descriptor);
		if (!descriptor.endsWith("V") && !result) {
			output.pop();
		}
	}
}
