package crafttweaker.runtime;

import java.io.*;

/**
 * @author Stan
 */
public interface IScriptIterator {
    
    String getGroupName();
    
    boolean next();
    
    String getName();
    
    InputStream open() throws IOException;
    
    IScriptIterator copyCurrent();
}
