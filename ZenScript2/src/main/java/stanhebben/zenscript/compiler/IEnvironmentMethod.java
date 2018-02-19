package stanhebben.zenscript.compiler;

import stanhebben.zenscript.symbols.SymbolLocal;
import stanhebben.zenscript.util.MethodOutput;

/**
 * @author Stan
 */
public interface IEnvironmentMethod extends IEnvironmentClass {
    
    MethodOutput getOutput();
    
    int getLocal(SymbolLocal variable);
}
