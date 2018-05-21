package crafttweaker.socket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpMessage;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;

import java.net.URISyntaxException;

/**
 * Source: https://medium.com/@irunika/how-to-write-a-http-websocket-server-using-netty-f3c136adcba9
 */
public class HttpServerHandler extends ChannelInboundHandlerAdapter {
    
    private WebSocketServerHandshaker handshaker;
    
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        
        if (msg instanceof FullHttpMessage) {
            System.out.println("Full HTTP Message Received");
        }
        else if (msg instanceof HttpRequest) {
            HttpRequest httpRequest = (HttpRequest) msg;
            
            System.out.println("Http Request Received");
            
            HttpHeaders headers = httpRequest.headers();
            System.out.println("Connection : " +headers.get("Connection"));
            System.out.println("Upgrade : " + headers.get("Upgrade"));
            
            if (headers.get("Connection").equalsIgnoreCase("Upgrade") ||
                    headers.get("Upgrade").equalsIgnoreCase("WebSocket")) {
                
                //Adding new handler to the existing pipeline to handle WebSocket Messages
                ctx.pipeline().replace(this, "websocketHandler", new WebSocketHandler());
                
                System.out.println("WebSocketHandler added to the pipeline");
                
                System.out.println("Opened Channel : " + ctx.channel());
                
                System.out.println("Handshaking....");
                //Do the Handshake to upgrade connection from HTTP to WebSocket protocol
                handleHandshake(ctx, httpRequest);
                System.out.println("Handshake is done");
                
            }
        } else {
            System.out.println("Incoming request is unknown");
        }
        
    }
    
    /* Do the handshaking for WebSocket request */
    protected void handleHandshake(ChannelHandlerContext ctx, HttpRequest req) throws URISyntaxException {
        WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(getWebSocketURL(req),
                null, true);
        handshaker = wsFactory.newHandshaker(req);
        if (handshaker == null) {
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
        } else {
            handshaker.handshake(ctx.channel(), req);
        }
    }
    
    
    protected String getWebSocketURL(HttpRequest req) {
        System.out.println("Req URI : " + req.uri());
        String url =  "ws://" + req.headers().get("Host") + req.uri() ;
        System.out.println("Constructed URL : " + url);
        return url;
    }
    
}