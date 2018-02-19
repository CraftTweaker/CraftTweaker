package stanhebben.zenscript.type.casting;

import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.type.ZenType;

/**
 * @author Stan
 */
public class CastingRuleI2L extends BaseCastingRule {

    public CastingRuleI2L(ICastingRule baseRule) {
        super(baseRule);
    }

    @Override
    public void compileInner(IEnvironmentMethod method) {
        method.getOutput().i2l();
    }

    @Override
    public ZenType getInnerInputType() {
        return ZenType.INT;
    }

    @Override
    public ZenType getResultingType() {
        return ZenType.LONG;
    }
}
