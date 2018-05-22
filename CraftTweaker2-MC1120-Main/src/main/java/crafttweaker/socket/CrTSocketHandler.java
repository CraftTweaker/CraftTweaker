package crafttweaker.socket;

import com.google.gson.*;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.socket.messages.*;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.*;

import java.io.*;
import java.lang.reflect.Type;
import java.net.*;
import java.security.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class CrTSocketHandler {
    
    public static final int PORT = 24532;
    public static final AtomicBoolean shouldRun = new AtomicBoolean(true);
    
    
    public CrTSocketHandler() {
        new Thread(this::handleServerSocket).start();

    }

    private void handleServerSocket() {
    
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
    
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new CrTSocketInitialiser())
                    .option(ChannelOption.SO_BACKLOG, 512)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            
            Channel ch = b.bind(PORT).sync().channel();
            
            ch.closeFuture().sync();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
    
    private void handleClientSocket(Socket socket) throws IOException {
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        
        String inputLine;
        
        StringBuilder builder = new StringBuilder();
        
        // Socket:
        while((inputLine = in.readLine()) != null) {
            builder.setLength(0);
            builder.append(inputLine).append("\n");
            
            while(in.ready() && (inputLine = in.readLine()) != null) {
                builder.append(inputLine).append("\n");
            }
            
            String message = builder.toString();
            
            System.out.println("message = " + message);
            if(message.startsWith("GET /")) {
                String[] splits = message.split("\n");
                
                String key = null;
                for(String split : splits) {
                    if(!split.startsWith("Sec-WebSocket-Key: "))
                        continue;
                    
                    key = split.substring(19).trim();
                }
                
                String secAccept = sha1base64(key + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11");
                
                String response = "HTTP/1.1 101 Switching Protocols\r\n" + "Upgrade: websocket\r\n" + "Connection: Upgrade\r\n" + "Sec-WebSocket-Accept: " + secAccept + "\r\n" + "Sec-WebSocket-Protocol: zslint\r\n";
                System.out.println("response = " + response);
                
                out.print(response);
                out.flush();
                
                continue;
            }
            
            
            if(!message.startsWith("{")) {
                System.out.println("Message is no valid json, skipping.");
                continue;
            }
            
            
            
            if(inputLine.equals("Bye."))
                break;
        }
    }
    
    
    private String sha1base64(String str) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
        } catch(NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return Base64.getEncoder().encodeToString(md.digest(str.getBytes()));
    }
}
