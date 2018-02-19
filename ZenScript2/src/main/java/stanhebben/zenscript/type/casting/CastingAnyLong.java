package stanhebben.zenscript.type.casting;

import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.value.IAny;

/**
 * @author Stan
 */
public class CastingAnyLong implements ICastingRule {

    public static final CastingAnyLong INSTANCE = new CastingAnyLong();

    private CastingAnyLong() {
    }

    @Override
    public void compile(IEnvironmentMethod method) {
        method.getOutput().invokeInterface(IAny.class, "asLong", long.class);
    }

    @Override
    public ZenType getInputType() {
        return ZenType.ANY;
    }

    @Override
    public ZenType getResultingType() {
        return ZenType.LONG;
    }
}
