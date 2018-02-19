package crafttweaker.zenscript;

import stanhebben.zenscript.*;
import stanhebben.zenscript.compiler.*;
import stanhebben.zenscript.parser.Token;
import stanhebben.zenscript.symbols.IZenSymbol;

import java.util.List;

import static crafttweaker.zenscript.GlobalRegistry.resolveBracket;

public class CrTCompileEnvironment implements IZenCompileEnvironment {
    
    @Override
    public IZenErrorLogger getErrorLogger() {
        return GlobalRegistry.getErrors();
    }
    
    @Override
    public IZenSymbol getGlobal(String name) {
        if(GlobalRegistry.getGlobals().containsKey(name)) {
            return GlobalRegistry.getGlobals().get(name);
        } else {
            return GlobalRegistry.getRoot().get(name);
        }
    }
    
    @Override
    public IZenSymbol getBracketed(IEnvironmentGlobal environment, List<Token> tokens) {
        return resolveBracket(environment, tokens);
    }
    
    @Override
    public TypeRegistry getTypeRegistry() {
        return GlobalRegistry.getTypes();
    }
    
    @Override
    public TypeExpansion getExpansion(String type) {
        return GlobalRegistry.getExpansions().get(type);
    }
    
    @Override
    public void setRegistry(IZenRegistry registry) {
        //NO-OP
        throw new UnsupportedOperationException("Registry is not local");
    }
}