package crafttweaker.socket;

public abstract class SocketMessage<T extends SocketMessage> {
    
    public String messageType;
    
    public SocketMessage(String messageType) {
        this.messageType = messageType;
    }
    
    // Needed for gson
    private SocketMessage() {}
}
