package stanhebben.zenscript.expression;

import stanhebben.zenscript.compiler.*;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.type.natives.*;
import stanhebben.zenscript.util.*;

/**
 * @author Stanneke
 */
public class ExpressionCallStatic extends Expression {
    
    private final IJavaMethod method;
    private final Expression[] arguments;
    
    public ExpressionCallStatic(ZenPosition position, IEnvironmentGlobal environment, IJavaMethod method, Expression... arguments) {
        super(position);
        
        this.method = method;
        this.arguments = JavaMethod.rematch(position, method, environment, arguments);
    }
    
    @Override
    public ZenType getType() {
        return method.getReturnType();
    }
    
    @Override
    public void compile(boolean result, IEnvironmentMethod environment) {
        MethodOutput output = environment.getOutput();
        
        for(Expression argument : arguments) {
            argument.compile(true, environment);
        }
        
        method.invokeStatic(output);
        
        if(method.getReturnType() != ZenType.VOID && !result) {
            output.pop(method.getReturnType().isLarge());
        }
    }
}
