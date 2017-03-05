package stanhebben.zenscript.type.casting;

import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.type.natives.IJavaMethod;

/**
 * @author Stan
 */
public class CastingRuleVirtualMethod implements ICastingRule {

    private final IJavaMethod method;

    public CastingRuleVirtualMethod(IJavaMethod method) {
        this.method = method;
    }

    @Override
    public void compile(IEnvironmentMethod method) {
        this.method.invokeVirtual(method.getOutput());
    }

    @Override
    public ZenType getInputType() {
        return method.getParameterTypes()[0];
    }

    @Override
    public ZenType getResultingType() {
        return method.getReturnType();
    }
}
