/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript.parser.expression;

import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.expression.Expression;
import stanhebben.zenscript.expression.ExpressionOrOr;
import stanhebben.zenscript.expression.partial.IPartialExpression;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.util.ZenPosition;

/**
 *
 * @author Stanneke
 */
public class ParsedExpressionOrOr extends ParsedExpression {
	private final ParsedExpression left;
	private final ParsedExpression right;
	
	public ParsedExpressionOrOr(ZenPosition position, ParsedExpression left, ParsedExpression right) {
		super(position);
		
		this.left = left;
		this.right = right;
	}

	@Override
	public IPartialExpression compile(IEnvironmentMethod environment, ZenType predictedType) {
		Expression cLeft = left.compile(environment, predictedType).eval(environment);
		Expression cRight = right.compile(environment, predictedType).eval(environment);
		
		ZenType type;
		if (cRight.getType().canCastImplicit(cLeft.getType(), environment)) {
			type = cLeft.getType();
		} else if (cLeft.getType().canCastImplicit(cRight.getType(), environment)) {
			type = cRight.getType();
		} else {
			environment.error(getPosition(), "These types could not be unified: " + cLeft.getType() + " and " + cRight.getType());
			type = ZenType.ANY;
		}
		
		return new ExpressionOrOr(
				getPosition(),
				cLeft.cast(getPosition(), environment, type),
				cRight.cast(getPosition(), environment, type));
	}
}
