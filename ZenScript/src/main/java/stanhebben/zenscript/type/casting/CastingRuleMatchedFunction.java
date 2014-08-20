/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript.type.casting;

import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.type.ZenTypeFunction;

/**
 *
 * @author Stan
 */
public class CastingRuleMatchedFunction implements ICastingRule {
	private final ZenTypeFunction fromType;
	private final ZenType toType;
	private final ICastingRule returnCastingRule;
	private final ICastingRule[] argumentCastingRules;
	
	public CastingRuleMatchedFunction(ZenTypeFunction fromType, ZenType toType, ICastingRule returnCastingRule, ICastingRule[] argumentCastingRules) {
		this.fromType = fromType;
		this.toType = toType;
		this.returnCastingRule = returnCastingRule;
		this.argumentCastingRules = argumentCastingRules;
	}

	@Override
	public void compile(IEnvironmentMethod method) {
		// nothing to do here
	}

	@Override
	public ZenType getInputType() {
		return fromType;
	}

	@Override
	public ZenType getResultingType() {
		return toType;
	}
}
