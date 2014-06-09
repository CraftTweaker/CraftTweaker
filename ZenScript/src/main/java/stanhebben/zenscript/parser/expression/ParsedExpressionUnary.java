/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript.parser.expression;

import stanhebben.zenscript.annotations.OperatorType;
import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.expression.Expression;
import stanhebben.zenscript.expression.partial.IPartialExpression;
import stanhebben.zenscript.util.ZenPosition;

/**
 *
 * @author Stanneke
 */
public class ParsedExpressionUnary extends ParsedExpression {
	private final ParsedExpression value;
	private final OperatorType operator;
	
	public ParsedExpressionUnary(ZenPosition position, ParsedExpression value, OperatorType operator) {
		super(position);
		
		this.value = value;
		this.operator = operator;
	}

	@Override
	public IPartialExpression compile(IEnvironmentMethod environment) {
		Expression cValue = value.compile(environment).eval(environment);
		return cValue.getType().unary(getPosition(), environment, cValue, operator);
	}
}
