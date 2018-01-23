package stanhebben.zenscript.symbols;

import stanhebben.zenscript.compiler.*;
import stanhebben.zenscript.expression.partial.*;
import stanhebben.zenscript.util.ZenPosition;

import java.util.Arrays;

public class SymbolScriptReference implements IZenSymbol {
    private final PartialScriptReference instance;
    
    
    SymbolScriptReference() {
        this.instance = new PartialScriptReference();
    }
    
    @Override
    public PartialScriptReference instance(ZenPosition position) {
        return instance;
    }
    
    public static PartialScriptReference getOrCreateReference(String name, IEnvironmentGlobal environmentGlobal) {
        IPartialExpression reference = environmentGlobal.getValue(name, null);
        if (reference == null) {
            environmentGlobal.putValue(name, new SymbolScriptReference(), null);
            reference = environmentGlobal.getValue(name, null);
        }
        return reference instanceof PartialScriptReference ? (PartialScriptReference) reference : null;
    }
}
