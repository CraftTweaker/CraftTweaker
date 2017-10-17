package crafttweaker.preprocessor;

import crafttweaker.runtime.*;

/**
 * Event that is getting called each time a script gets loaded.
 * subscribe to it to alter the loading of the scripts
 *
 * @author BloodWorkXGaming
 */
public class CrTScriptLoadEvent {
    private ScriptFile scriptFile;
    
    public CrTScriptLoadEvent(ScriptFile scriptFile){
        this.scriptFile = scriptFile;
    }
    
    public ScriptFile getScriptFile() {
        return scriptFile;
    }
}
