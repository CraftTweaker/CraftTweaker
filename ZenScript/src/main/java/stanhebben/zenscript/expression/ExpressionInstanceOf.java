package stanhebben.zenscript.expression;

import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.util.ZenPosition;

import static stanhebben.zenscript.util.ZenTypeUtil.internal;

public class ExpressionInstanceOf extends Expression {
    
    private final Expression expression;
    private final ZenType type;
    
    public ExpressionInstanceOf(ZenPosition position, Expression expression, ZenType type) {
        super(position);
        this.expression = expression;
        this.type = type;
    }
    
    @Override
    public void compile(boolean result, IEnvironmentMethod environment) {
        if(!result)
            return;
        expression.compile(result, environment);
        environment.getOutput().instanceOf(internal(type.toJavaClass()));
    }
    
    @Override
    public ZenType getType() {
        return ZenType.BOOL;
    }
}
