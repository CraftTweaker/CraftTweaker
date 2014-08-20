/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript.parser.expression;

import stanhebben.zenscript.annotations.CompareType;
import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.expression.Expression;
import stanhebben.zenscript.expression.partial.IPartialExpression;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.util.ZenPosition;

/**
 *
 * @author Stanneke
 */
public class ParsedExpressionCompare extends ParsedExpression {
	private final ParsedExpression left;
	private final ParsedExpression right;
	private final CompareType type;
	
	public ParsedExpressionCompare(
			ZenPosition position,
			ParsedExpression left,
			ParsedExpression right,
			CompareType type) {
		super(position);
		
		this.left = left;
		this.right = right;
		this.type = type;
	}

	@Override
	public IPartialExpression compile(IEnvironmentMethod environment, ZenType predictedType) {
		Expression cLeft = left.compile(environment, null).eval(environment);
		Expression cRight = right.compile(environment, null).eval(environment);
		return cLeft.getType().compare(getPosition(), environment, cLeft, cRight, type);
	}
}
