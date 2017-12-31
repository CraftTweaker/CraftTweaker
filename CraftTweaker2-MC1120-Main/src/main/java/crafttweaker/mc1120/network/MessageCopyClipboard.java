package crafttweaker.mc1120.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

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
        data = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, data);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IMessage onMessage(MessageCopyClipboard message, MessageContext ctx) {
        Minecraft.getMinecraft().addScheduledTask(() -> handle(message, ctx));
        return null;
    }

    private void handle(MessageCopyClipboard message, MessageContext ctx) {
        if (Desktop.isDesktopSupported()) {
            StringSelection stringSelection = new StringSelection(message.getData());
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);
        }
    }
}
