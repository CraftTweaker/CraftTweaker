package minetweaker.mods.jei.network;

import io.netty.buffer.ByteBuf;
import minetweaker.mods.jei.JEIAddonPlugin;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.*;

import java.util.*;

public class MessageJEIHide implements IMessage, IMessageHandler<MessageJEIHide, IMessage> {
    
    private boolean hide;
    private ItemStack subTypes;
    
    public MessageJEIHide() {
    }
    
    public MessageJEIHide(boolean hide, ItemStack sybTypes) {
        this.hide = hide;
        this.subTypes = sybTypes;
    }
    
    @Override
    public void fromBytes(ByteBuf buf) {
        hide = buf.readBoolean();
        subTypes = ByteBufUtils.readItemStack(buf);
    }
    
    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeBoolean(hide);
        ByteBufUtils.writeItemStack(buf, subTypes);
    }
    
    @Override
    public IMessage onMessage(MessageJEIHide message, MessageContext ctx) {
        Minecraft.getMinecraft().addScheduledTask(() -> handle(message, ctx));
        return null;
    }
    
    private void handle(MessageJEIHide message, MessageContext ctx) {
        if(message.hide) {
            JEIAddonPlugin.itemRegistry.removeIngredientsAtRuntime(ItemStack.class, JEIAddonPlugin.getSubTypes(message.subTypes));
        } else {
            JEIAddonPlugin.itemRegistry.addIngredientsAtRuntime(ItemStack.class, JEIAddonPlugin.getSubTypes(message.subTypes));
        }
    }
    
}
