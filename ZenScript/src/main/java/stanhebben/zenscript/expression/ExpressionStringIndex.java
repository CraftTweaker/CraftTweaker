/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript.expression;

import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.util.ZenPosition;

/**
 *
 * @author Stan
 */
public class ExpressionStringIndex extends Expression {
	private final Expression source;
	private final Expression index;
	
	public ExpressionStringIndex(ZenPosition position, Expression source, Expression index) {
		super(position);
		
		this.source = source;
		this.index = index;
	}
	
	@Override
	public ZenType getType() {
		return ZenType.STRING;
	}

	@Override
	public void compile(boolean result, IEnvironmentMethod environment) {
		source.compile(result, environment);
		index.compile(result, environment);
		
		if (result) {
			environment.getOutput().dup();
			environment.getOutput().iConst1();
			environment.getOutput().iAdd();
			environment.getOutput().invokeVirtual(String.class, "substring", String.class, int.class, int.class);
		}
	}
}
