package crafttweaker.socket;

import crafttweaker.CraftTweakerAPI;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.*;

public class CrTSocketHandler {
    
    public static final int PORT = 24532;
    public static CrTSocketHandler INSTANCE = null;
    
    private NioEventLoopGroup bossGroup;
    private NioEventLoopGroup workerGroup;
    
    public CrTSocketHandler() {
        new Thread(this::handleServerSocket).start();
    }
    
    public static void enableSocket() {
        if(INSTANCE == null) {
            INSTANCE = new CrTSocketHandler();
            CraftTweakerAPI.logInfo("Started ZsLint Socket lint on Port " + PORT);
        }
    }
    
    private void handleServerSocket() {
        try {
            bossGroup = new NioEventLoopGroup(1);
            workerGroup = new NioEventLoopGroup();
            
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).childHandler(new CrTSocketInitializer()).option(ChannelOption.SO_BACKLOG, 512).handler(new LoggingHandler(LogLevel.INFO)).childOption(ChannelOption.SO_KEEPALIVE, true);
            
            Channel ch = b.bind(PORT).sync().channel();
            
            ch.closeFuture().sync();
        } catch(InterruptedException e) {
            CraftTweakerAPI.logError("Error while in Socket Thread", e);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
