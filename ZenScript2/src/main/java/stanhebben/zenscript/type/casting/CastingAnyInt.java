package stanhebben.zenscript.type.casting;

import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.value.IAny;

/**
 * @author Stan
 */
public class CastingAnyInt implements ICastingRule {

    public static final CastingAnyInt INSTANCE = new CastingAnyInt();

    private CastingAnyInt() {
    }

    @Override
    public void compile(IEnvironmentMethod method) {
        method.getOutput().invokeInterface(IAny.class, "asInt", int.class);
    }

    @Override
    public ZenType getInputType() {
        return ZenType.ANY;
    }

    @Override
    public ZenType getResultingType() {
        return ZenType.INT;
    }
}
