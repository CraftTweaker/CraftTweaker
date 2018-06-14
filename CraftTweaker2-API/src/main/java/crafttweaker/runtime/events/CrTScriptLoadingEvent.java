package crafttweaker.runtime.events;

public class CrTScriptLoadingEvent {
    
    private final String fileName;
    
    public CrTScriptLoadingEvent(String fileName) {
        this.fileName = fileName;
    }
    
    public String getFileName() {
        return fileName;
    }
    
    
    public static class Pre extends CrTScriptLoadingEvent {
        
        public Pre(String fileName) {
            super(fileName);
        }
    }
    
    public static class Post extends CrTScriptLoadingEvent {
        
        public Post(String fileName) {
            super(fileName);
        }
    }
}
