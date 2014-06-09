/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript.parser.expression;

import java.util.List;
import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.expression.Expression;
import stanhebben.zenscript.expression.ExpressionArray;
import stanhebben.zenscript.expression.partial.IPartialExpression;
import stanhebben.zenscript.util.ZenPosition;

/**
 *
 * @author Stanneke
 */
public class ParsedExpressionArray extends ParsedExpression {
	private final List<ParsedExpression> contents;
	
	public ParsedExpressionArray(ZenPosition position, List<ParsedExpression> contents) {
		super(position);
		
		this.contents = contents;
	}

	@Override
	public IPartialExpression compile(IEnvironmentMethod environment) {
		Expression[] cContents = new Expression[contents.size()];
		for (int i = 0; i < contents.size(); i++) {
			cContents[i] = contents.get(i).compile(environment).eval(environment);
		}
		return new ExpressionArray(getPosition(), cContents);
	}
	
	@Override
	public Expression compileKey(IEnvironmentMethod environment) {
		if (contents.size() == 1 && contents.get(0) instanceof ParsedExpressionVariable) {
			return contents.get(0).compile(environment).eval(environment);
		} else {
			return compile(environment).eval(environment);
		}
	}
}
