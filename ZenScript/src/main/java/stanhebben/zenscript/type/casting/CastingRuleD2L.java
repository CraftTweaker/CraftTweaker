package stanhebben.zenscript.type.casting;

import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.type.ZenType;

/**
 * @author Stan
 */
public class CastingRuleD2L extends BaseCastingRule {

    public CastingRuleD2L(ICastingRule baseRule) {
        super(baseRule);
    }

    @Override
    public void compileInner(IEnvironmentMethod method) {
        method.getOutput().d2l();
    }

    @Override
    public ZenType getInnerInputType() {
        return ZenType.DOUBLE;
    }

    @Override
    public ZenType getResultingType() {
        return ZenType.LONG;
    }
}
