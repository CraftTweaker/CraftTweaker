package stanhebben.zenscript.type.casting;

import stanhebben.zenscript.type.*;

/**
 * @author Stan
 */
public class CastingRuleDelegateList implements ICastingRuleDelegate {

    private final ICastingRuleDelegate base;
    private final ZenTypeArrayList from;

    public CastingRuleDelegateList(ICastingRuleDelegate base, ZenTypeArrayList from) {
        this.base = base;
        this.from = from;
    }

    @Override
    public void registerCastingRule(ZenType type, ICastingRule rule) {
        base.registerCastingRule(new ZenTypeArrayBasic(type), new CastingRuleListArray(rule, from, new ZenTypeArrayBasic(type)));
        base.registerCastingRule(new ZenTypeArrayList(type), new CastingRuleListList(rule, from, new ZenTypeArrayList(type)));
    }
}
