package crafttweaker.socket;

public interface IRequestMessage<T extends SocketMessage> {
    T handleReceive();
}
