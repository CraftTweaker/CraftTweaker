package stanhebben.zenscript.type.natives;

import stanhebben.zenscript.annotations.OperatorType;

/**
 * @author Stanneke
 */
public class ZenNativeOperator {
    
    private final OperatorType operator;
    private final IJavaMethod method;
    
    public ZenNativeOperator(OperatorType operator, IJavaMethod method) {
        this.operator = operator;
        this.method = method;
    }
    
    public OperatorType getOperator() {
        return operator;
    }
    
    public IJavaMethod getMethod() {
        return method;
    }
}
