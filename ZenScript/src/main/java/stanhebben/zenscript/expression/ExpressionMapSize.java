package stanhebben.zenscript.expression;

import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.type.*;
import stanhebben.zenscript.util.ZenPosition;

import java.util.Map;

import static stanhebben.zenscript.util.ZenTypeUtil.internal;

/**
 * @author Stanneke
 */
public class ExpressionMapSize extends Expression {
    
    private final Expression map;
    
    public ExpressionMapSize(ZenPosition position, Expression map) {
        super(position);
        
        this.map = map;
    }
    
    @Override
    public ZenType getType() {
        return ZenTypeInt.INSTANCE;
    }
    
    @Override
    public void compile(boolean result, IEnvironmentMethod environment) {
        if(result) {
            map.compile(true, environment);
            environment.getOutput().invokeInterface(internal(Map.class), "size", "()I");
        }
    }
}
