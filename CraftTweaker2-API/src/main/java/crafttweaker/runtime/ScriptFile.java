package crafttweaker.runtime;

import crafttweaker.api.network.NetworkSide;
import crafttweaker.preprocessor.IPreprocessor;
import crafttweaker.runtime.providers.ScriptIteratorZip;

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
    
    private boolean isExecutionBlocked = false;
    private boolean isParsingBlocked = false;
    private boolean isCompileBlocked = false;
    
    private boolean isDebugEnabled = false;
    private boolean ignoreBracketErrors = false;
    
    private boolean isSyntaxCommand;
    
    private NetworkSide networkSide;
    
    public ScriptFile(ITweaker tweaker, IScriptIterator script, boolean isSyntaxCommand) {
        this.tweaker = tweaker;
        this.script = script;
        this.isSyntaxCommand = isSyntaxCommand;
    }
    
    public IScriptIterator getScript() {
        return script;
    }
    
    /**
     * Group name is with relative path to the file or ZIP file name
     */
    public String getGroupName() {
        return script.getGroupName();
    }
    
    /**
     * Actual name of the zs file
     */
    public String getName() {
        return script.getName();
    }
    
    /**
     * Gets the effective name that has every info in it that it can, like zip files don't use only the zip file name
     */
    public String getEffectiveName(){
        if (script instanceof ScriptIteratorZip) {
            ScriptIteratorZip scriptIterator = (ScriptIteratorZip) script;
            return getGroupName() + File.separator + scriptIterator.getCurrentName().replace('/', File.separatorChar);
        }else {
            return getGroupName();
        }
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
     * @return ITweaker instance of the current tweaker
     */
    public ITweaker getTweaker() {
        return tweaker;
    }
    
    /**
     * Getters and setters which alter the loading of the code
     */
    public boolean isExecutionBlocked() {
        return isExecutionBlocked;
    }
    
    public void setExecutionBlocked(boolean executionBlocked) {
        isExecutionBlocked = executionBlocked;
    }
    
    public boolean isParsingBlocked() {
        return isParsingBlocked;
    }
    
    public void setParsingBlocked(boolean parsingBlocked) {
        isParsingBlocked = parsingBlocked;
    }
    
    public boolean isCompileBlocked() {
        return isCompileBlocked;
    }
    
    public void setCompileBlocked(boolean compileBlocked) {
        isCompileBlocked = compileBlocked;
    }
    
    /**
     * Whether it is a by syntax command. Syntax commands only load the script, but don't execute it
     */
    public boolean isSyntaxCommand() {
        return isSyntaxCommand;
    }
    
    @Override
    public String toString() {
        return "{[" + priority + ":" + loaderName + "]: " + getEffectiveName() + (networkSide == null ? "" : "[Side: " + networkSide + "]")+ "}";
    }
    
    /**
     * Change whether you want to have bracket errors show up for the specified file
     */
    public boolean areBracketErrorsIgnored() {
        return ignoreBracketErrors;
    }
    
    public void setIgnoreBracketErrors(boolean ignoreBracketErrors) {
        this.ignoreBracketErrors = ignoreBracketErrors;
    }
    
    /**
     * Change whether you want to enable debug mode for the specified file
     */
    public boolean isDebugEnabled() {
        return isDebugEnabled;
    }
    
    public void setDebugEnabled(boolean debugEnabled) {
        isDebugEnabled = debugEnabled;
    }
    
    /**
     * Registers the file to be only loaded on this network side
     */
    public void setNetworkSide(NetworkSide networkSide){
        this.networkSide = networkSide;
    }
    
    /**
     * Returns whether it should be loaded on the side it was asked to be loaded on
     */
    public boolean shouldBeLoadedOn(NetworkSide proposedSide) {
        return networkSide == null || networkSide.equals(proposedSide);
    }
    
    
}
