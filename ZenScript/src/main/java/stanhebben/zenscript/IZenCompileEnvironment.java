package stanhebben.zenscript;

import stanhebben.zenscript.compiler.*;
import stanhebben.zenscript.parser.Token;
import stanhebben.zenscript.symbols.IZenSymbol;

import java.util.List;

/**
 * @author Stanneke
 */
public interface IZenCompileEnvironment {
    
    IZenErrorLogger getErrorLogger();
    
    IZenSymbol getGlobal(String name);
    
    IZenSymbol getDollar(String name);
    
    IZenSymbol getBracketed(IEnvironmentGlobal environment, List<Token> tokens);
    
    TypeRegistry getTypeRegistry();
    
    TypeExpansion getExpansion(String type);
}
