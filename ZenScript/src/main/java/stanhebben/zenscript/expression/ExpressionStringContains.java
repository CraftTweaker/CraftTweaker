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
public class ExpressionStringContains extends Expression {
	private final Expression haystack;
	private final Expression needle;
	
	public ExpressionStringContains(ZenPosition position, Expression haystack, Expression needle) {
		super(position);
		
		this.haystack = haystack;
		this.needle = needle;
	}
	
	@Override
	public ZenType getType() {
		return ZenType.STRING;
	}

	@Override
	public void compile(boolean result, IEnvironmentMethod environment) {
		haystack.compile(result, environment);
		needle.compile(result, environment);
		
		if (result) {
			environment.getOutput().invokeVirtual(String.class, "contains", CharSequence.class);
		}
	}
}
