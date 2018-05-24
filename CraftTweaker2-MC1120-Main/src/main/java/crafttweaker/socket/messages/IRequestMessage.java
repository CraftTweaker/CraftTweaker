package crafttweaker.socket.messages;

import io.netty.channel.ChannelHandlerContext;

public interface IRequestMessage<T extends SocketMessage> {
    
    T handleReceive(ChannelHandlerContext ctx);
}
