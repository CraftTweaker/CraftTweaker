package crafttweaker.socket;

import io.netty.channel.*;
import io.netty.handler.codec.http.websocketx.*;

public class WebSocketHandler extends ChannelInboundHandlerAdapter {
    
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        
        if (msg instanceof WebSocketFrame) {
            System.out.println("This is a WebSocket frame");
            System.out.println("Client Channel : " + ctx.channel());
            if (msg instanceof BinaryWebSocketFrame) {
                System.out.println("BinaryWebSocketFrame Received : ");
                System.out.println( ((BinaryWebSocketFrame) msg).content() );
            } else if (msg instanceof TextWebSocketFrame) {
                System.out.println("TextWebSocketFrame Received : ");
                ctx.channel().writeAndFlush(new TextWebSocketFrame("Message recieved : " + ((TextWebSocketFrame) msg).text()));
                System.out.println( ((TextWebSocketFrame) msg).text() );
            } else if (msg instanceof PingWebSocketFrame) {
                System.out.println("PingWebSocketFrame Received : ");
                System.out.println( ((PingWebSocketFrame) msg).content());
            } else if (msg instanceof PongWebSocketFrame) {
                System.out.println("PongWebSocketFrame Received : ");
                System.out.println( ((PongWebSocketFrame) msg).content() );
            } else if (msg instanceof CloseWebSocketFrame) {
                System.out.println("CloseWebSocketFrame Received : ");
                System.out.println("ReasonText :" + ((CloseWebSocketFrame) msg).reasonText() );
                System.out.println("StatusCode : " + ((CloseWebSocketFrame) msg).statusCode() );
            } else {
                System.out.println("Unsupported WebSocketFrame");
            }
        }
    
        System.out.println("ctx = " + ctx);
        System.out.println("msg = " + msg);
    }
}
