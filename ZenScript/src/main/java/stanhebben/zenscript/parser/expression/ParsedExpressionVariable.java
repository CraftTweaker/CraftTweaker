/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript.parser.expression;

import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.expression.Expression;
import stanhebben.zenscript.expression.ExpressionInvalid;
import stanhebben.zenscript.expression.ExpressionString;
import stanhebben.zenscript.expression.partial.IPartialExpression;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.util.ZenPosition;

/**
 *
 * @author Stanneke
 */
public class ParsedExpressionVariable extends ParsedExpression {
	private final String name;
	
	public ParsedExpressionVariable(ZenPosition position, String name) {
		super(position);
		
		this.name = name;
	}

	@Override
	public IPartialExpression compile(IEnvironmentMethod environment, ZenType predictedType) {
		IPartialExpression result = environment.getValue(name, getPosition());
		if (result == null) {
			if (predictedType == null) {
				environment.error(getPosition(), "could not find " + name);
				return new ExpressionInvalid(getPosition());
			}
			
			// enable usage of static members of the same type as the predicted type (eg. enum values)
			IPartialExpression member = predictedType.getStaticMember(getPosition(), environment, name);
			if (member == null || member.getType().getCastingRule(predictedType, environment) == null) {
				environment.error(getPosition(), "could not find " + name);
				return new ExpressionInvalid(getPosition());
			} else {
				return member;
			}
		} else {
			return result;
		}
	}
	
	@Override
	public Expression compileKey(IEnvironmentMethod environment, ZenType predictedType) {
		return new ExpressionString(getPosition(), name);
	}
}
