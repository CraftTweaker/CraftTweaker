package crafttweaker.socket;

import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.util.CharsetUtil;

public class CrTSocketInitialiser extends ChannelInitializer<SocketChannel> {
    private static final String WEBSOCKET_PATH = "/";

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
    
        System.out.println("ch = " + ch.localAddress());
        System.out.println("ch = " + ch.parent());
        System.out.println("ch = " + ch.config());
        System.out.println("ch = " + ch.remoteAddress());
        
        pipeline.addLast("httpServerCodec", new HttpServerCodec());
        pipeline.addLast("httpHandler", new HttpServerHandler());
        
        /*pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new HttpObjectAggregator(65536));
        pipeline.addLast(new WebSocketServerCompressionHandler());
        pipeline.addLast(new WebSocketServerProtocolHandler(WEBSOCKET_PATH, null, true));
        // pipeline.addLast(new WebSocketIndexPageHandler(WEBSOCKET_PATH));
        // pipeline.addLast(new WebSocketFrameHandler());
        
        pipeline.addLast(LineBasedFrameDecoder.class.getName(), new LineBasedFrameDecoder(256));
        
        pipeline.addLast(StringDecoder.class.getName(), new StringDecoder(CharsetUtil.UTF_8));
        
        pipeline.addLast(JsonDecoder.class.getName(), new JsonDecoder<>(LintRequestMessage.class, String.class));
        
        
        pipeline.addLast("stdoutHandler", new SimpleChannelInboundHandler<LintRequestMessage>() {
            @Override
            protected void channelRead0(ChannelHandlerContext ctx, LintRequestMessage msg) throws Exception {
                System.out.println("Your message was: " + msg);
            }
        });*/
    }
}
