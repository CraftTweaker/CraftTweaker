/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript.type.casting;

import java.util.Map;
import stanhebben.zenscript.type.ZenType;

/**
 *
 * @author Stan
 */
public class CastingRuleDelegateMap implements ICastingRuleDelegate {
	private final Map<ZenType, ICastingRule> target;
	
	public CastingRuleDelegateMap(Map<ZenType, ICastingRule> target) {
		this.target = target;
	}

	@Override
	public void registerCastingRule(ZenType type, ICastingRule rule) {
		target.put(type, rule);
	}
}
