package stanhebben.zenscript.type.casting;

import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.type.ZenType;

/**
 * @author Stan
 */
public class CastingRuleF2I extends BaseCastingRule {

    public CastingRuleF2I(ICastingRule baseRule) {
        super(baseRule);
    }

    @Override
    public void compileInner(IEnvironmentMethod method) {
        method.getOutput().f2i();
    }

    @Override
    public ZenType getInnerInputType() {
        return ZenType.FLOAT;
    }

    @Override
    public ZenType getResultingType() {
        return ZenType.INT;
    }
}
