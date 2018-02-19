package stanhebben.zenscript.symbols;

import stanhebben.zenscript.expression.partial.*;
import stanhebben.zenscript.type.natives.IJavaMethod;
import stanhebben.zenscript.util.ZenPosition;

/**
 * @author Stanneke
 */
public class SymbolJavaStaticGetter implements IZenSymbol {
    
    private final IJavaMethod method;
    
    public SymbolJavaStaticGetter(IJavaMethod method) {
        this.method = method;
    }
    
    @Override
    public IPartialExpression instance(ZenPosition position) {
        return new PartialStaticGetter(position, method);
    }
}
