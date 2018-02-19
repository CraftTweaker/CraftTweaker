package stanhebben.zenscript.type.casting;

import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.type.ZenType;

/**
 * @author Stan
 */
public class CastingRuleD2F extends BaseCastingRule {

    public CastingRuleD2F(ICastingRule baseRule) {
        super(baseRule);
    }

    @Override
    public void compileInner(IEnvironmentMethod method) {
        method.getOutput().d2f();
    }

    @Override
    public ZenType getInnerInputType() {
        return ZenType.DOUBLE;
    }

    @Override
    public ZenType getResultingType() {
        return ZenType.FLOAT;
    }
}
