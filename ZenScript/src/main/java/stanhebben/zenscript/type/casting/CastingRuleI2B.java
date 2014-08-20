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
public class CastingRuleI2B extends BaseCastingRule {
	public CastingRuleI2B(ICastingRule baseRule) {
		super(baseRule);
	}

	@Override
	public void compileInner(IEnvironmentMethod method) {
		method.getOutput().i2b();
	}

	@Override
	public ZenType getInnerInputType() {
		return ZenType.INT;
	}

	@Override
	public ZenType getResultingType() {
		return ZenType.BYTE;
	}
}
