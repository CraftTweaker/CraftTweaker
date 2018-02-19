package stanhebben.zenscript.expression;

import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.util.ZenPosition;

public class ExpressionOrOr extends Expression {

    private final Expression a;
    private final Expression b;

    public ExpressionOrOr(ZenPosition position, Expression a, Expression b) {
        super(position);

        this.a = a;
        this.b = b;
    }

    @Override
    public ZenType getType() {
        return a.getType();
    }

    @Override
    public void compile(boolean result, IEnvironmentMethod environment) {
        // TODO: implement
    }
}
