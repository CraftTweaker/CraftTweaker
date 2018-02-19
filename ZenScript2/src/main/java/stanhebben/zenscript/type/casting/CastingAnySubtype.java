package stanhebben.zenscript.type.casting;

import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.type.ZenType;

import static stanhebben.zenscript.util.ZenTypeUtil.internal;

public class CastingAnySubtype implements ICastingRule {

    private final ZenType fromType;
    private final ZenType toType;

    public CastingAnySubtype(ZenType fromType, ZenType toType) {
        this.fromType = fromType;
        this.toType = toType;
    }

    @Override
    public void compile(IEnvironmentMethod method) {
        method.getOutput().checkCast(internal(toType.toJavaClass()));
    }

    @Override
    public ZenType getInputType() {
        return fromType;
    }

    @Override
    public ZenType getResultingType() {
        return toType;
    }
}
