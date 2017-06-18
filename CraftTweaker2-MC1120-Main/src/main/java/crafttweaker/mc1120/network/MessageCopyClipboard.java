package crafttweaker.mc1120.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.*;

import java.awt.*;
import java.awt.datatransfer.*;
import java.nio.ByteBuffer;

import static com.google.common.base.Charsets.UTF_8;

/**
 * @author Stan
 */
public class MessageCopyClipboard implements IMessage, IMessageHandler<MessageCopyClipboard, IMessage> {

    private String data;

    public MessageCopyClipboard() {
    }

    public MessageCopyClipboard(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        data = UTF_8.decode(ByteBuffer.wrap(buf.array())).toString().trim();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeBytes(UTF_8.encode(data).array());
    }
    
    @Override
    public IMessage onMessage(MessageCopyClipboard message, MessageContext ctx) {
        Minecraft.getMinecraft().addScheduledTask(() -> handle(message, ctx));
        return null;
    }
    
    private void handle(MessageCopyClipboard message, MessageContext ctx) {
        if(Desktop.isDesktopSupported()) {
            StringSelection stringSelection = new StringSelection(message.getData());
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);
        }
    }
}
