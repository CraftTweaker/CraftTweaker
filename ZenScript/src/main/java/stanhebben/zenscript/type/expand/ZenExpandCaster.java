package stanhebben.zenscript.type.expand;

import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.expression.*;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.type.casting.*;
import stanhebben.zenscript.type.natives.IJavaMethod;
import stanhebben.zenscript.util.*;

/**
 * @author Stanneke
 */
public class ZenExpandCaster {

    private final IJavaMethod method;

    public ZenExpandCaster(IJavaMethod method) {
        this.method = method;
    }

    public ZenType getTarget() {
        return method.getReturnType();
    }

    public void constructCastingRules(IEnvironmentGlobal environment, ICastingRuleDelegate rules) {
        ZenType type = method.getReturnType();
        rules.registerCastingRule(type, new CastingRuleStaticMethod(method));

        type.constructCastingRules(environment, new CastingRuleDelegateStaticMethod(rules, method), false);
    }

    public Expression cast(ZenPosition position, IEnvironmentGlobal environment, Expression expression) {
        return new ExpressionCallStatic(position, environment, method, expression);
    }

    public void compile(MethodOutput output) {
        method.invokeStatic(output);
    }
}
