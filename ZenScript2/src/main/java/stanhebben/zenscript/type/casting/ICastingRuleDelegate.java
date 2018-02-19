package stanhebben.zenscript.type.casting;

import stanhebben.zenscript.type.ZenType;

/**
 * @author Stan
 */
public interface ICastingRuleDelegate {
    
    void registerCastingRule(ZenType type, ICastingRule rule);
}
