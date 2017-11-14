package stanhebben.zenscript.expression;

import stanhebben.zenscript.annotations.OperatorType;
import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.type.*;
import stanhebben.zenscript.util.*;

public class ExpressionArithmeticUnary extends Expression {
    
    private final Expression base;
    private final OperatorType operator;
    
    public ExpressionArithmeticUnary(ZenPosition position, OperatorType operator, Expression base) {
        super(position);
        
        this.base = base;
        this.operator = operator;
    }
    
    @Override
    public ZenType getType() {
        return base.getType();
    }
    
    @Override
    public void compile(boolean result, IEnvironmentMethod environment) {
        base.compile(result, environment);
        
        MethodOutput output = environment.getOutput();
        if(result) {
            ZenType type = base.getType();
            if(type == ZenType.BOOL) {
                if(operator == OperatorType.NOT) {
                    output.iXorVs1();
                    return;
                }
            } else if(type == ZenTypeByte.INSTANCE || type == ZenTypeShort.INSTANCE || type == ZenTypeInt.INSTANCE) {
                if(operator == OperatorType.NOT) {
                    output.iNot();
                    return;
                } else if(operator == OperatorType.NEG) {
                    output.iNeg();
                    return;
                }
            } else if(type == ZenTypeLong.INSTANCE) {
                if(operator == OperatorType.NOT) {
                    output.lNot();
                    return;
                } else if(operator == OperatorType.NEG) {
                    output.lNeg();
                    return;
                }
            } else if(type == ZenTypeFloat.INSTANCE) {
                if(operator == OperatorType.NEG) {
                    output.fNeg();
                    return;
                }
            } else if(type == ZenTypeDouble.INSTANCE) {
                if(operator == OperatorType.NEG) {
                    output.fNeg();
                    return;
                }
            }
        }
        
        environment.error(getPosition(), "Invalid operation");
    }
}
