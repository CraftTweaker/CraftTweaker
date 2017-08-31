package crafttweaker.preprocessor;

import crafttweaker.runtime.ITweaker;

import java.util.List;

/**
 * Event that is getting called each time a script gets loaded.
 * subscribe to it to alter the loading of the scripts
 *
 * @author BloodWorkXGaming
 */
public class CrTScriptLoadEvent {
    /**
     * Optional Settings
     */
    public boolean isExecuteCanceled = false;
    public boolean enableDebug = false;
    public boolean isParsingCanceled;
    
    /**
     * Individual settings
     */
    private boolean isActuallyLoading;
    
    /**
     * Statics
     */
    public String fileName;
    public String className;
    public ITweaker tweaker;
    public List<IPreprocessor> affectingPreprocessorsList;
    
    public CrTScriptLoadEvent(boolean isActuallyLoading, String fileName, String className, ITweaker tweaker, List<IPreprocessor> affectingPreprocessorsList) {
        this.isActuallyLoading = isActuallyLoading;
        this.fileName = fileName;
        this.tweaker = tweaker;
        this.affectingPreprocessorsList = affectingPreprocessorsList;
    }
    
    public boolean isActuallyLoading() {
        return isActuallyLoading;
    }
}
