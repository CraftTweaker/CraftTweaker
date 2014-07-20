/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript.expression;

import java.util.Map;
import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.type.ZenTypeBool;
import stanhebben.zenscript.util.ZenPosition;
import static stanhebben.zenscript.util.ZenTypeUtil.internal;

/**
 *
 * @author Stanneke
 */
public class ExpressionMapContains extends Expression {
	private final Expression map;
	private final Expression key;
	
	public ExpressionMapContains(ZenPosition position, Expression map, Expression key) {
		super(position);
		
		this.map = map;
		this.key = key;
	}

	@Override
	public ZenType getType() {
		return ZenType.BOOL;
	}

	@Override
	public void compile(boolean result, IEnvironmentMethod environment) {
		if (result) {
			map.compile(result, environment);
			key.compile(result, environment);
			
			environment.getOutput().invokeInterface(internal(Map.class), "containsKey", "()Ljava/lang/Object;");
		}
	}
}
