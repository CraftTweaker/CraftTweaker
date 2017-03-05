package stanhebben.zenscript.type.casting;

import stanhebben.zenscript.type.*;

/**
 * @author Stan
 */
public class CastingRuleDelegateArray implements ICastingRuleDelegate {

    private final ICastingRuleDelegate base;
    private final ZenTypeArrayBasic from;

    public CastingRuleDelegateArray(ICastingRuleDelegate base, ZenTypeArrayBasic from) {
        this.base = base;
        this.from = from;
    }

    @Override
    public void registerCastingRule(ZenType type, ICastingRule rule) {
        base.registerCastingRule(new ZenTypeArrayBasic(type), new CastingRuleArrayArray(rule, from, new ZenTypeArrayBasic(type)));
        base.registerCastingRule(new ZenTypeArrayList(type), new CastingRuleArrayList(rule, from, new ZenTypeArrayList(type)));
    }
}
