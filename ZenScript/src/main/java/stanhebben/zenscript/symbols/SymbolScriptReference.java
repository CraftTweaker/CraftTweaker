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
    
    public static PartialScriptReference getOrCreateReference(IEnvironmentGlobal environmentGlobal) {
        IPartialExpression reference = environmentGlobal.getValue("scripts", null);
        if (reference == null) {
            environmentGlobal.putValue("scripts", new SymbolScriptReference(), null);
            reference = environmentGlobal.getValue("scripts", null);
        }
        return (PartialScriptReference) reference;
    }
}
