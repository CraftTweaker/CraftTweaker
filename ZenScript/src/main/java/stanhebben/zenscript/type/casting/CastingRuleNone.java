/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript.type.casting;

import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.type.ZenType;

/**
 *
 * @author Stan
 */
public class CastingRuleNone implements ICastingRule {
	private final ZenType fromType;
	private final ZenType toType;
	
	public CastingRuleNone(ZenType fromType, ZenType toType) {
		this.fromType = fromType;
		this.toType = toType;
	}

	@Override
	public void compile(IEnvironmentMethod method) {
		// nothing to do
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
