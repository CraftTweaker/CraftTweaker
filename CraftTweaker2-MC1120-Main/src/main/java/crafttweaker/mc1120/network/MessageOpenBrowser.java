package crafttweaker.mc1120.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.*;

import java.awt.*;
import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 * @author Stan
 */
public class MessageOpenBrowser implements IMessage, IMessageHandler<MessageOpenBrowser, IMessage> {

    private static final Charset UTF8 = Charset.forName("utf-8");

    private String url;

    public MessageOpenBrowser() {

    }

    public MessageOpenBrowser(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        url = UTF8.decode(ByteBuffer.wrap(buf.array())).toString().trim();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeBytes(UTF8.encode(url).array());
    }
    
    @Override
    public IMessage onMessage(MessageOpenBrowser message, MessageContext ctx) {
        Minecraft.getMinecraft().addScheduledTask(() -> handle(message, ctx));
        return null;
    }
    
    private void handle(MessageOpenBrowser message, MessageContext ctx) {
        if(Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.browse(new URI(message.getUrl()));
            } catch(IOException | URISyntaxException ignored) {
            }
        } else {
            System.out.println("Desktop not supported");
        }
    }
}
