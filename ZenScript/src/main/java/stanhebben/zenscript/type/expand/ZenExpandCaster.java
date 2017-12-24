package stanhebben.zenscript.type.expand;

import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.expression.Expression;
import stanhebben.zenscript.expression.ExpressionCallStatic;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.type.casting.CastingRuleDelegateStaticMethod;
import stanhebben.zenscript.type.casting.CastingRuleStaticMethod;
import stanhebben.zenscript.type.casting.ICastingRuleDelegate;
import stanhebben.zenscript.type.natives.IJavaMethod;
import stanhebben.zenscript.util.MethodOutput;
import stanhebben.zenscript.util.ZenPosition;

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
