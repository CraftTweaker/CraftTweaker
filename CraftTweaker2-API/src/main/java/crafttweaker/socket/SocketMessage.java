package crafttweaker.socket;

public abstract class SocketMessage {
    
    public String messageType;
    
    public SocketMessage(String messageType) {
        this.messageType = messageType;
    }
    
    public SocketMessage() {
    }
    
    public abstract void handleReceive();
}
