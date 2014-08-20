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
public class ParsedExpressionValue extends ParsedExpression {
	private final IPartialExpression value;
	
	public ParsedExpressionValue(ZenPosition position, IPartialExpression value) {
		super(position);
		
		if (value == null) throw new IllegalArgumentException("value cannot be null");
		
		this.value = value;
	}

	@Override
	public IPartialExpression compile(IEnvironmentMethod environment, ZenType predictedType) {
		return value;
	}
}
