package stanhebben.zenscript.type.casting;

import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.type.ZenType;

/**
 * @author Stan
 */
public class CastingRuleD2I extends BaseCastingRule {

    public CastingRuleD2I(ICastingRule baseRule) {
        super(baseRule);
    }

    @Override
    public void compileInner(IEnvironmentMethod method) {
        method.getOutput().d2i();
    }

    @Override
    public ZenType getInnerInputType() {
        return ZenType.DOUBLE;
    }

    @Override
    public ZenType getResultingType() {
        return ZenType.INT;
    }
}
