package stanhebben.zenscript.type.natives;

import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.expression.Expression;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.util.MethodOutput;

/**
 * @author Stan
 */
public interface IJavaMethod {
    
    boolean isStatic();
    
    boolean accepts(int numArguments);
    
    boolean accepts(IEnvironmentGlobal environment, Expression... arguments);
    
    int getPriority(IEnvironmentGlobal environment, Expression... arguments);
    
    void invokeVirtual(MethodOutput output);
    
    void invokeStatic(MethodOutput output);
    
    ZenType[] getParameterTypes();
    
    ZenType getReturnType();
    
    boolean isVarargs();
    
}
