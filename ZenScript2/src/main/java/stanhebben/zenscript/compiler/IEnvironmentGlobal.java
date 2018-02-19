package stanhebben.zenscript.compiler;

import stanhebben.zenscript.*;
import stanhebben.zenscript.expression.partial.IPartialExpression;
import stanhebben.zenscript.symbols.IZenSymbol;
import stanhebben.zenscript.util.ZenPosition;

import java.util.Set;

/**
 * @author Stan
 */
public interface IEnvironmentGlobal extends ITypeRegistry, IZenErrorLogger {
    
    IZenCompileEnvironment getEnvironment();
    
    TypeExpansion getExpansion(String name);
    
    String makeClassName();
    
    boolean containsClass(String name);
    
    Set<String> getClassNames();
    
    byte[] getClass(String name);
    
    void putClass(String name, byte[] data);
    
    IPartialExpression getValue(String name, ZenPosition position);
    
    void putValue(String name, IZenSymbol value, ZenPosition position);
}
