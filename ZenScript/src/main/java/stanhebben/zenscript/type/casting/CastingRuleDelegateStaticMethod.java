/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript.type.casting;

import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.type.natives.IJavaMethod;

/**
 *
 * @author Stan
 */
public class CastingRuleDelegateStaticMethod implements ICastingRuleDelegate {
	private final ICastingRuleDelegate target;
	private final IJavaMethod method;

	public CastingRuleDelegateStaticMethod(ICastingRuleDelegate target, IJavaMethod method) {
		this.target = target;
		this.method = method;
	}

	@Override
	public void registerCastingRule(ZenType type, ICastingRule rule) {
		target.registerCastingRule(type, new CastingRuleStaticMethod(method, rule));
	}
}
