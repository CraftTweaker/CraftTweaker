package crafttweaker.mc1120.network;

import crafttweaker.mc1120.CraftTweaker;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import net.minecraftforge.fml.relauncher.*;

import java.awt.*;
import java.io.IOException;
import java.net.*;

/**
 * @author Stan
 */
public class MessageOpenBrowser implements IMessage, IMessageHandler<MessageOpenBrowser, IMessage> {
    
    
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
        url = ByteBufUtils.readUTF8String(buf);//UTF8.decode(ByteBuffer.wrap(buf.array())).toString().trim();
    }
    
    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, url);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
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
            CraftTweaker.LOG.error("Desktop not supported");
        }
    }
}
