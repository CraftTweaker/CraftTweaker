package crafttweaker.runtime;

import crafttweaker.preprocessor.IPreprocessor;

import java.io.*;
import java.util.*;

public class ScriptFile {
    private IScriptIterator script;
    private List<IPreprocessor> affectingPreprocessors = new ArrayList<>();
    
    private ITweaker tweaker;
    
    /** Priority which can be assigned to script, within the same priority the load order is affected by filename*/
    private int priority = 0;
    
    /** Loader name gets provided so that it can only load specifc scripts, not all of them*/
    private String loaderName = "crafttweaker";
    
    public ScriptFile(ITweaker tweaker, IScriptIterator script) {
        this.tweaker = tweaker;
        this.script = script;
    }
    
    public IScriptIterator getScript() {
        return script;
    }
    
    public String getGroupName() {
        return script.getGroupName();
    }
    
    public String getName() {
        return script.getName();
    }
    
    /**
     * Gives InputStream of the script,
     * must be closed again or might cause problems down the line
     * @return Input stream of the file
     * @throws IOException
     */
    public InputStream open() throws IOException {
        return script.open();
    }
    
    /**
     * Delegates to the preprocessor List
     */
    public boolean add(IPreprocessor preprocessor) {
        return affectingPreprocessors.add(preprocessor);
    }
    
    public boolean addAll(Collection<? extends IPreprocessor> c) {
        return affectingPreprocessors.addAll(c);
    }
    
    public List<IPreprocessor> getAffectingPreprocessors() {
        return affectingPreprocessors;
    }
    
    /**
     * Change the priority
     */
    public int getPriority() {
        return priority;
    }
    
    public void setPriority(int priority) {
        this.priority = priority;
    }
    
    /**
     * Change the loader name to change whether it should be loaded or not
     */
    public String getLoaderName() {
        return loaderName;
    }
    
    public void setLoaderName(String loaderName) {
        this.loaderName = loaderName;
    }
    
    /**
     * Gets the tweaker which handles the loading of the current file
     * @return
     */
    public ITweaker getTweaker() {
        return tweaker;
    }
}
