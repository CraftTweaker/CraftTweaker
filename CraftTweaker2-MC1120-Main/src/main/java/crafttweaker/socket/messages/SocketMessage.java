package crafttweaker.socket.messages;

public abstract class SocketMessage<T extends SocketMessage<T>> {
    
    public String messageType;
    
    public SocketMessage(String messageType) {
        this.messageType = messageType;
    }
    
    // Needed for gson
    @SuppressWarnings("unused")
    private SocketMessage() {
    }
}
