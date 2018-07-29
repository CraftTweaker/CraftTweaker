package crafttweaker.runtime.events;


/**
 * Deprecated, use the newly added super class
 */
@Deprecated
public class CrTLoadingScriptEventPre extends CrTScriptLoadingEvent.Pre {
    
    public CrTLoadingScriptEventPre(String fileName) {
        super(fileName);
    }
    
    @Override
    public String getFileName() {
        return super.getFileName();
    }
}
