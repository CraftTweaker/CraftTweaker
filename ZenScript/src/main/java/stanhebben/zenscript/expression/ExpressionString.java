/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript.expression;

import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.type.ZenTypeString;
import stanhebben.zenscript.util.ZenPosition;

/**
 *
 * @author Stanneke
 */
public class ExpressionString extends Expression {
	private final String value;
	
	public ExpressionString(ZenPosition position, String value) {
		super(position);
		
		this.value = value;
	}

	@Override
	public ZenType getType() {
		return ZenTypeString.INSTANCE;
	}

	@Override
	public void compile(boolean result, IEnvironmentMethod environment) {
		if (result) {
			environment.getOutput().constant(value);
		}
	}
}
