/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript.parser.expression;

import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.expression.ExpressionNull;
import stanhebben.zenscript.expression.partial.IPartialExpression;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.util.ZenPosition;

/**
 *
 * @author Stanneke
 */
public class ParsedExpressionNull extends ParsedExpression {
	public ParsedExpressionNull(ZenPosition position) {
		super(position);
	}

	@Override
	public IPartialExpression compile(IEnvironmentMethod environment, ZenType predictedType) {
		return new ExpressionNull(getPosition());
	}
}
