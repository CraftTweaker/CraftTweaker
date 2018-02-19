package stanhebben.zenscript.expression;

import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.util.ZenPosition;

/**
 * @author Stanneke
 */
public class ExpressionArgument extends Expression {
    
    private final int id;
    private final ZenType type;
    
    public ExpressionArgument(ZenPosition position, int id, ZenType type) {
        super(position);
        
        this.id = id;
        this.type = type;
    }
    
    @Override
    public ZenType getType() {
        return type;
    }
    
    @Override
    public void compile(boolean result, IEnvironmentMethod environment) {
        environment.getOutput().load(type.toASMType(), id);
    }
}
