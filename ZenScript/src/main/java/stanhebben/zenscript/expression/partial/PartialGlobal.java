package stanhebben.zenscript.expression.partial;


import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.expression.Expression;
import stanhebben.zenscript.expression.ExpressionInvalid;
import stanhebben.zenscript.symbols.IZenSymbol;
import stanhebben.zenscript.symbols.SymbolGlobal;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.util.ZenPosition;

public class PartialGlobal implements IPartialExpression {
    private final ZenPosition position;
    private final SymbolGlobal value;

    public PartialGlobal(ZenPosition position, SymbolGlobal value) {
        this.position = position;
        this.value = value;
    }

    @Override
    public Expression eval(IEnvironmentGlobal environment) {
        return new Expression(position) {
            @Override
            public void compile(boolean result, IEnvironmentMethod environment) {
                environment.getOutput().getStaticField(value.getOwner(), value.getName(), value.getType().getSignature());
            }

            @Override
            public ZenType getType() {
                return value.getType();
            }
        };
    }

    @Override
    public Expression assign(ZenPosition position, IEnvironmentGlobal environment, Expression other) {
        environment.error(position, "cannot assign to a global value");
        return new ExpressionInvalid(position);
    }

    @Override
    public IPartialExpression getMember(ZenPosition position, IEnvironmentGlobal environment, String name) {
        return value.getType().getMember(position, environment, this, name);
    }

    @Override
    public Expression call(ZenPosition position, IEnvironmentMethod environment, Expression... values) {
        return value.getType().call(position, environment, eval(environment), values);
    }

    @Override
    public ZenType[] predictCallTypes(int numArguments) {
        return value.getType().predictCallTypes(numArguments);
    }

    @Override
    public IZenSymbol toSymbol() {
        return value;
    }

    @Override
    public ZenType getType() {
        return value.getType();
    }

    @Override
    public ZenType toType(IEnvironmentGlobal environment) {
        environment.error(position, "not a valid type");
        return ZenType.ANY;
    }
}
