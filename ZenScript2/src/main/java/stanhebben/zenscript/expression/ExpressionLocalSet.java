package stanhebben.zenscript.expression;

import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.symbols.SymbolLocal;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.util.ZenPosition;

/**
 * @author Stanneke
 */
public class ExpressionLocalSet extends Expression {
    
    private final SymbolLocal variable;
    private final Expression value;
    
    public ExpressionLocalSet(ZenPosition position, SymbolLocal variable, Expression value) {
        super(position);
        
        this.variable = variable;
        this.value = value;
    }
    
    @Override
    public ZenType getType() {
        return variable.getType();
    }
    
    @Override
    public void compile(boolean result, IEnvironmentMethod environment) {
        int local = environment.getLocal(variable);
        
        value.compile(true, environment);
        if(result) {
            environment.getOutput().dup();
        }
        environment.getOutput().store(variable.getType().toASMType(), local);
    }
}
