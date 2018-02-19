package stanhebben.zenscript.type.casting;

import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.type.ZenType;

/**
 * @author Stan
 */
public class CastingRuleF2L extends BaseCastingRule {

    public CastingRuleF2L(ICastingRule baseRule) {
        super(baseRule);
    }

    @Override
    public void compileInner(IEnvironmentMethod method) {
        method.getOutput().f2l();
    }

    @Override
    public ZenType getInnerInputType() {
        return ZenType.FLOAT;
    }

    @Override
    public ZenType getResultingType() {
        return ZenType.LONG;
    }
}
