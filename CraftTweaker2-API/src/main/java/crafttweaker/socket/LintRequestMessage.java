package crafttweaker.socket;

public class LintRequestMessage extends SocketMessage {
    public String sourcePath;
    public String comment;
    
    public LintRequestMessage() {
        super("LintRequest");
    }
    
    @Override
    public void handleReceive() {
        System.out.println("Received me" + toString());
    }
    
    @Override
    public String toString() {
        return "LintRequestMessage{" + "sourcePath='" + sourcePath + '\'' + ", comment='" + comment + '\'' + '}';
    }
}
