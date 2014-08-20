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
public class ParsedExpressionMember extends ParsedExpression {
	private final ParsedExpression value;
	private final String member;
	
	public ParsedExpressionMember(ZenPosition position, ParsedExpression value, String member) {
		super(position);
		
		this.value = value;
		this.member = member;
	}

	@Override
	public IPartialExpression compile(IEnvironmentMethod environment, ZenType predictedType) {
		return value.compile(environment, null).getMember(getPosition(), environment, member);
	}
}
