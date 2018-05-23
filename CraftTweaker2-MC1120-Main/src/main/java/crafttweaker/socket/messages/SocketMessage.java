package crafttweaker.socket.messages;

public abstract class SocketMessage<T extends SocketMessage> {
    
    public String messageType;
    
    public SocketMessage(String messageType) {
        this.messageType = messageType;
    }
    
    // Needed for gson
    private SocketMessage() {
    }
}
