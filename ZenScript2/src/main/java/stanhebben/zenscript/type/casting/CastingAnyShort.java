package stanhebben.zenscript.type.casting;

import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.value.IAny;

/**
 * @author Stan
 */
public class CastingAnyShort implements ICastingRule {

    public static final CastingAnyShort INSTANCE = new CastingAnyShort();

    private CastingAnyShort() {
    }

    @Override
    public void compile(IEnvironmentMethod method) {
        method.getOutput().invokeInterface(IAny.class, "asShort", short.class);
    }

    @Override
    public ZenType getInputType() {
        return ZenType.ANY;
    }

    @Override
    public ZenType getResultingType() {
        return ZenType.SHORT;
    }
}
