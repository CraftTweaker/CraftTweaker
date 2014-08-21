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
	private final ZenType forType;
	private final Map<ZenType, ICastingRule> target;
	
	public CastingRuleDelegateMap(ZenType forType, Map<ZenType, ICastingRule> target) {
		this.forType = forType;
		this.target = target;
	}

	@Override
	public void registerCastingRule(ZenType type, ICastingRule rule) {
		// XXX: remove before release
		//System.out.println("Registering casting rule to convert " + forType + " to " + type + ": " + rule);
		
		target.put(type, rule);
	}
}
