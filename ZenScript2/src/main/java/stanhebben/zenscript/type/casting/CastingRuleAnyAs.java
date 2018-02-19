package stanhebben.zenscript.type.casting;

import org.objectweb.asm.Type;
import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.value.IAny;

/**
 * @author Stan
 */
public class CastingRuleAnyAs implements ICastingRule {

    private final ZenType type;

    public CastingRuleAnyAs(ZenType type) {
        this.type = type;
    }

    @Override
    public void compile(IEnvironmentMethod method) {
        method.getOutput().constant(Type.getType(type.toJavaClass()));
        method.getOutput().invokeInterface(IAny.class, "as", Class.class, Object.class);
    }

    @Override
    public ZenType getInputType() {
        return ZenType.ANY;
    }

    @Override
    public ZenType getResultingType() {
        return type;
    }
}
