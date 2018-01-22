package stanhebben.zenscript.symbols;

import stanhebben.zenscript.compiler.*;
import stanhebben.zenscript.expression.partial.*;
import stanhebben.zenscript.util.ZenPosition;

import java.util.Arrays;

public class SymbolScriptReference implements IZenSymbol {
    
    private final EnvironmentClass environmentScript;
    private final IPartialExpression instance;
    
    
    SymbolScriptReference(EnvironmentClass environmentScript, boolean hasSubs) {
        this.environmentScript = environmentScript;
        this.instance = hasSubs ? new PartialScriptDirectory() : new PartialScriptFile(environmentScript);
    }
    
    @Override
    public IPartialExpression instance(ZenPosition position) {
        return instance;
    }
    
    public static IPartialExpression addSymbol(EnvironmentClass environmentScript, IEnvironmentGlobal environmentGlobal, String[] scriptFilenameSplit) {
        if (scriptFilenameSplit.length == 0) throw new UnsupportedOperationException("Cannot work with an empty file name!");
        String name = scriptFilenameSplit[0];
        if (scriptFilenameSplit.length == 1) {
            SymbolScriptReference symbol = new SymbolScriptReference(environmentScript, false);
            environmentGlobal.putValue(name, symbol, null);
            return symbol.instance;
        }
        String[] newFileNameSplit = Arrays.copyOfRange(scriptFilenameSplit, 1, scriptFilenameSplit.length);
        
        if (environmentGlobal.getValue(name, null) == null) {
            environmentGlobal.putValue(name, new SymbolScriptReference(environmentScript, true), null);
        }
        PartialScriptDirectory dirInst = (PartialScriptDirectory) environmentGlobal.getValue(name, null);
        dirInst.addSub(newFileNameSplit[0], addSymbol(environmentScript, environmentGlobal, newFileNameSplit));
        return dirInst;
    }
}
