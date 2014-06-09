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
	public IPartialExpression compile(IEnvironmentMethod environment) {
		IPartialExpression result = environment.getValue(name, getPosition());
		if (result == null) {
			environment.error(getPosition(), "could not find " + name);
			return new ExpressionInvalid(getPosition());
		} else {
			return result;
		}
	}
	
	@Override
	public Expression compileKey(IEnvironmentMethod environment) {
		return new ExpressionString(getPosition(), name);
	}
}
