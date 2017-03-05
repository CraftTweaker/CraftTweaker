package stanhebben.zenscript.expression;

import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.type.*;
import stanhebben.zenscript.util.ZenPosition;

/**
 * @author Stanneke
 */
public class ExpressionArrayLength extends Expression {
    
    private final Expression value;
    
    public ExpressionArrayLength(ZenPosition position, Expression value) {
        super(position);
        
        this.value = value;
    }
    
    @Override
    public ZenType getType() {
        return ZenTypeInt.INSTANCE;
    }
    
    @Override
    public void compile(boolean result, IEnvironmentMethod environment) {
        environment.getOutput().arrayLength();
    }
}
