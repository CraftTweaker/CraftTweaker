package crafttweaker.runtime.events;

public class CrTLoadingScriptEventPre {
    
    private String fileName;
    
    public CrTLoadingScriptEventPre(String fileName) {
        this.fileName = fileName;
    }
    
    public String getFileName() {
        return fileName;
    }
}
