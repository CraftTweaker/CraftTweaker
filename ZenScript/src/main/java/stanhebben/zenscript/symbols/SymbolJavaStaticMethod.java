package stanhebben.zenscript.symbols;

import stanhebben.zenscript.expression.partial.*;
import stanhebben.zenscript.type.natives.IJavaMethod;
import stanhebben.zenscript.util.ZenPosition;

/**
 * @author Stanneke
 */
public class SymbolJavaStaticMethod implements IZenSymbol {
    
    private final IJavaMethod method;
    
    public SymbolJavaStaticMethod(IJavaMethod method) {
        this.method = method;
    }
    
    @Override
    public IPartialExpression instance(ZenPosition position) {
        return new PartialStaticMethod(position, method);
    }
    
    @Override
    public String toString() {
        return "SymbolJavaStaticMethod: " + method.toString();
    }
}
