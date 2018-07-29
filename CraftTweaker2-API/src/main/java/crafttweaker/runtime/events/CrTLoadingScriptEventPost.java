package crafttweaker.runtime.events;

/**
 * Deprecated, use the newly added super class
 */
@Deprecated
public class CrTLoadingScriptEventPost extends CrTScriptLoadingEvent.Post {
    
    public CrTLoadingScriptEventPost(String fileName) {
        super(fileName);
    }
    
    @Override
    public String getFileName() {
        return super.getFileName();
    }
}
