package crafttweaker.runtime;

public class CrTLoadingStartedEvent {
    private String loaderName;
    private boolean isSyntaxCommand;
    
    public CrTLoadingStartedEvent(String loaderName, boolean isSyntaxCommand) {
        this.loaderName = loaderName;
        this.isSyntaxCommand = isSyntaxCommand;
    }
    
    public String getLoaderName() {
        return loaderName;
    }
    
    public boolean isSyntaxCommand() {
        return isSyntaxCommand;
    }
}
