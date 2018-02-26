package crafttweaker.runtime.events;

public class CrTLoadingScriptEventPost {
    
    private String fileName;
    
    public CrTLoadingScriptEventPost(String fileName) {
        this.fileName = fileName;
    }
    
    public String getFileName() {
        return fileName;
    }
}
