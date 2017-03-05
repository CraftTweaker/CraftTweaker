package stanhebben.zenscript.expression;

import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.type.*;
import stanhebben.zenscript.util.ZenPosition;

/**
 * @author Stan
 */
public class ExpressionArrayGet extends Expression {
    
    private final Expression array;
    private final Expression index;
    private final ZenType baseType;
    
    public ExpressionArrayGet(ZenPosition position, Expression array, Expression index) {
        super(position);
        
        this.array = array;
        this.index = index;
        this.baseType = ((ZenTypeArray) array.getType()).getBaseType();
    }
    
    @Override
    public ZenType getType() {
        return baseType;
    }
    
    @Override
    public void compile(boolean result, IEnvironmentMethod environment) {
        array.compile(result, environment);
        index.compile(result, environment);
        
        if(result) {
            environment.getOutput().arrayLoad(baseType.toASMType());
        }
    }
}
