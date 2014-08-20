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
public class CastingRuleL2I extends BaseCastingRule {
	public CastingRuleL2I(ICastingRule baseRule) {
		super(baseRule);
	}

	@Override
	public void compileInner(IEnvironmentMethod method) {
		method.getOutput().l2i();
	}

	@Override
	public ZenType getInnerInputType() {
		return ZenType.LONG;
	}

	@Override
	public ZenType getResultingType() {
		return ZenType.INT;
	}
}
