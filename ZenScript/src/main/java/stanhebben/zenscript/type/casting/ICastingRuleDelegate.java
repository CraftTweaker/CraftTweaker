/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript.type.casting;

import stanhebben.zenscript.type.ZenType;

/**
 *
 * @author Stan
 */
public interface ICastingRuleDelegate {
	public void registerCastingRule(ZenType type, ICastingRule rule);
}
