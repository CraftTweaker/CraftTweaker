/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript.parser.expression;

import java.util.List;
import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.expression.Expression;
import stanhebben.zenscript.expression.ExpressionTable;
import stanhebben.zenscript.expression.partial.IPartialExpression;
import stanhebben.zenscript.util.ZenPosition;

/**
 *
 * @author Stanneke
 */
public class ParsedExpressionTable extends ParsedExpression {
	private final List<ParsedExpression> keys;
	private final List<ParsedExpression> values;
	
	public ParsedExpressionTable(
			ZenPosition position,
			List<ParsedExpression> keys,
			List<ParsedExpression> values) {
		super(position);
		
		this.keys = keys;
		this.values = values;
	}

	@Override
	public IPartialExpression compile(IEnvironmentMethod environment) {
		Expression[] cKeys = new Expression[keys.size()];
		Expression[] cValues = new Expression[values.size()];
		
		for (int i = 0; i < keys.size(); i++) {
			cKeys[i] = keys.get(i).compileKey(environment);
			cValues[i] = values.get(i).compile(environment).eval(environment);
		}
		
		return new ExpressionTable(getPosition(), cKeys, cValues);
	}
}
