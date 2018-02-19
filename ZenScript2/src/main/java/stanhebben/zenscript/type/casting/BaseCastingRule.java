package stanhebben.zenscript.type.casting;

import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.type.ZenType;

/**
 * @author Stan
 */
public abstract class BaseCastingRule implements ICastingRule {

    private final ICastingRule baseRule;

    public BaseCastingRule(ICastingRule baseRule) {
        this.baseRule = baseRule;
    }

    @Override
    public final void compile(IEnvironmentMethod method) {
        if(baseRule != null)
            baseRule.compile(method);

        compileInner(method);
    }

    @Override
    public final ZenType getInputType() {
        return baseRule == null ? getInnerInputType() : baseRule.getInputType();
    }

    public abstract ZenType getInnerInputType();

    public abstract void compileInner(IEnvironmentMethod method);
}
