package com.blamejared.crafttweaker.impl.network;

import com.blamejared.crafttweaker.CraftTweaker;
import com.blamejared.crafttweaker.impl.network.messages.MessageCopy;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class PacketHandler {
    
    public static SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(new ResourceLocation("crafttweaker:main"), () -> CraftTweaker.VERSION, CraftTweaker.VERSION::equals, CraftTweaker.VERSION::equals);
    
    private static int ID = 0;
    
    public static void init() {
        CHANNEL.registerMessage(ID++, MessageCopy.class, (messageCopy, packetBuffer) -> packetBuffer.writeString(messageCopy.toCopy), packetBuffer -> new MessageCopy(packetBuffer.readString()), (messageCopy, contextSupplier) -> contextSupplier.get().enqueueWork(() -> Minecraft.getInstance().keyboardListener.setClipboardString(messageCopy.toCopy)));
    }
    
    
}
