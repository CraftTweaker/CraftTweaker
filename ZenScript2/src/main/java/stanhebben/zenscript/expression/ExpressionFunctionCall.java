package stanhebben.zenscript.expression;

import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.util.ZenPosition;

public class ExpressionFunctionCall extends Expression {
    
    private final Expression[] values;
    private final ZenType returnType;
    private final Expression receiver;
    private final String className;
    private final String descriptor;
    
    

    
    public ExpressionFunctionCall(ZenPosition position, Expression[] values, ZenType returnType, Expression receiver, String className, String descriptor) {
        super(position);
        this.values = values;
        this.returnType = returnType;
        this.receiver = receiver;
        this.className = className;
        this.descriptor = descriptor;
    }
    
    @Override
    public void compile(boolean result, IEnvironmentMethod environment) {
        receiver.compile(result, environment);
        for (Expression value : values) {
            value.compile(true, environment);
        }
        environment.getOutput().invokeVirtual(className, "accept", descriptor);
    }
    
    @Override
    public ZenType getType() {
        return returnType;
    }
}
