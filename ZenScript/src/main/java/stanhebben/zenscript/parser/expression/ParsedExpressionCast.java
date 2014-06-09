/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript.parser.expression;

import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.expression.partial.IPartialExpression;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.util.ZenPosition;

/**
 *
 * @author Stanneke
 */
public class ParsedExpressionCast extends ParsedExpression {
	private final ParsedExpression value;
	private final ZenType type;
	
	public ParsedExpressionCast(ZenPosition position, ParsedExpression value, ZenType type) {
		super(position);
		
		this.value = value;
		this.type = type;
	}

	@Override
	public IPartialExpression compile(IEnvironmentMethod environment) {
		return value
				.compile(environment)
				.eval(environment)
				.cast(getPosition(), environment, type);
	}
}
