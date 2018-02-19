package stanhebben.zenscript.type.casting;

import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.type.ZenType;

/**
 * @author Stan
 */
public class CastingRuleL2I extends BaseCastingRule {

    public CastingRuleL2I(ICastingRule baseRule) {
        super(baseRule);
    }

    @Override
    public void compileInner(IEnvironmentMethod method) {
        method.getOutput().l2i();
    }

    @Override
    public ZenType getInnerInputType() {
        return ZenType.LONG;
    }

    @Override
    public ZenType getResultingType() {
        return ZenType.INT;
    }
}
