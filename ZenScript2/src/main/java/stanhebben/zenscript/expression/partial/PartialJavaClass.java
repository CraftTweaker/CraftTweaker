package stanhebben.zenscript.expression.partial;

import stanhebben.zenscript.compiler.*;
import stanhebben.zenscript.expression.*;
import stanhebben.zenscript.symbols.*;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.util.ZenPosition;

import java.lang.reflect.Type;

/**
 * @author Stanneke
 */
public class PartialJavaClass implements IPartialExpression {

    private final ZenPosition position;
    private final Type cls;

    public PartialJavaClass(ZenPosition position, Type cls) {
        this.position = position;
        this.cls = cls;
    }

    @Override
    public Expression eval(IEnvironmentGlobal environment) {
        environment.error(position, "cannot use class name as expression");
        return new ExpressionInvalid(position);
    }

    @Override
    public Expression assign(ZenPosition position, IEnvironmentGlobal environment, Expression other) {
        environment.error(position, "cannot assign to a class name");
        return new ExpressionInvalid(position);
    }

    @Override
    public IPartialExpression getMember(ZenPosition position, IEnvironmentGlobal environment, String name) {
        return environment.getType(cls).getStaticMember(position, environment, name);
    }

    @Override
    public Expression call(ZenPosition position, IEnvironmentMethod environment, Expression... values) {
        environment.error(position, "cannot call a class name");
        return new ExpressionInvalid(position);
    }

    @Override
    public IZenSymbol toSymbol() {
        return new SymbolJavaClass(cls);
    }

    @Override
    public ZenType getType() {
        return null;
    }

    @Override
    public ZenType toType(IEnvironmentGlobal environment) {
        return environment.getType(cls);
    }

    @Override
    public ZenType[] predictCallTypes(int numArguments) {
        return new ZenType[numArguments];
    }
}
