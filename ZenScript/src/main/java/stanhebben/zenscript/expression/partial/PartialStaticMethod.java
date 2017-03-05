package stanhebben.zenscript.expression.partial;

import stanhebben.zenscript.compiler.*;
import stanhebben.zenscript.expression.*;
import stanhebben.zenscript.symbols.*;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.type.natives.IJavaMethod;
import stanhebben.zenscript.util.ZenPosition;

import java.util.Arrays;

/**
 * @author Stanneke
 */
public class PartialStaticMethod implements IPartialExpression {

    private final ZenPosition position;
    private final IJavaMethod method;

    public PartialStaticMethod(ZenPosition position, IJavaMethod method) {
        this.position = position;
        this.method = method;
    }

    @Override
    public Expression eval(IEnvironmentGlobal environment) {
        environment.error(position, "not a valid expression");
        return new ExpressionInvalid(position);
    }

    @Override
    public Expression assign(ZenPosition position, IEnvironmentGlobal environment, Expression other) {
        environment.error(position, "cannot alter this final");
        return new ExpressionInvalid(position);
    }

    @Override
    public IPartialExpression getMember(ZenPosition position, IEnvironmentGlobal environment, String name) {
        environment.error(position, "methods have no members");
        return new ExpressionInvalid(position);
    }

    @Override
    public Expression call(ZenPosition position, IEnvironmentMethod environment, Expression... values) {
        if(method.accepts(environment, values)) {
            return new ExpressionCallStatic(position, environment, method, values);
        } else {
            environment.error(position, "parameter count mismatch: got " + values.length + " arguments");
            return new ExpressionInvalid(position, method.getReturnType());
        }
    }

    @Override
    public ZenType[] predictCallTypes(int numArguments) {
        return Arrays.copyOf(method.getParameterTypes(), numArguments);
    }

    @Override
    public IZenSymbol toSymbol() {
        return new SymbolJavaStaticMethod(method);
    }

    @Override
    public ZenType getType() {
        return method.getReturnType();
    }

    @Override
    public ZenType toType(IEnvironmentGlobal environment) {
        return ZenType.ANY;
    }
}
