package crafttweaker.runtime;

import java.util.Iterator;

/**
 * @author Stan
 */
public interface IScriptProvider {
    
    Iterator<IScriptIterator> getScripts();
}
