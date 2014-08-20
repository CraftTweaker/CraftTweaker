/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript.type.casting;

import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.type.ZenTypeAssociative;

/**
 *
 * @author Stan
 */
public class CastingRuleMap implements ICastingRule {
	private final ICastingRule keyRule;
	private final ICastingRule valueRule;
	private final ZenTypeAssociative fromType;
	private final ZenTypeAssociative toType;
	
	public CastingRuleMap(ICastingRule keyRule, ICastingRule valueRule, ZenTypeAssociative fromType, ZenTypeAssociative toType) {
		this.keyRule = keyRule;
		this.valueRule = valueRule;
		this.fromType = fromType;
		this.toType = toType;
	}
	
	@Override
	public void compile(IEnvironmentMethod method) {
		// TODO: implement
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
