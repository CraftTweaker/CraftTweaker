package stanhebben.zenscript.type.casting;

import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.type.ZenType;

/**
 * @author Stan
 */
public class CastingRuleL2F extends BaseCastingRule {

    public CastingRuleL2F(ICastingRule baseRule) {
        super(baseRule);
    }

    @Override
    public void compileInner(IEnvironmentMethod method) {
        method.getOutput().l2f();
    }

    @Override
    public ZenType getInnerInputType() {
        return ZenType.LONG;
    }

    @Override
    public ZenType getResultingType() {
        return ZenType.FLOAT;
    }
}
