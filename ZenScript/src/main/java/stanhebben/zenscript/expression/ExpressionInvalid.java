package stanhebben.zenscript.expression;

import org.objectweb.asm.Label;
import stanhebben.zenscript.compiler.*;
import stanhebben.zenscript.type.*;
import stanhebben.zenscript.util.ZenPosition;

/**
 * @author Stanneke
 */
public class ExpressionInvalid extends Expression {
    
    private final ZenType type;
    
    public ExpressionInvalid(ZenPosition position) {
        super(position);
        
        type = ZenTypeAny.INSTANCE;
    }
    
    public ExpressionInvalid(ZenPosition position, ZenType type) {
        super(position);
        
        this.type = type;
        
        // XXX: remove before release
        // throw new RuntimeException("Constructing invalid expression");
    }
    
    @Override
    public Expression getMember(ZenPosition position, IEnvironmentGlobal errors, String name) {
        return new ExpressionInvalid(position);
    }
    
    @Override
    public Expression cast(ZenPosition position, IEnvironmentGlobal errors, ZenType type) {
        return new ExpressionInvalid(position, type);
    }
    
    @Override
    public ZenType getType() {
        return type;
    }
    
    @Override
    public void compile(boolean result, IEnvironmentMethod environment) {
        if(result) {
            environment.getOutput().aConstNull();
        }
    }
    
    @Override
    public void compileIf(Label onElse, IEnvironmentMethod environment) {
        environment.getOutput().goTo(onElse);
    }
}
