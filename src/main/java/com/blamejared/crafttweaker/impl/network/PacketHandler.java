package com.blamejared.crafttweaker.impl.network;

import com.blamejared.crafttweaker.CraftTweaker;
import com.blamejared.crafttweaker.impl.network.messages.MessageCopy;
import com.blamejared.crafttweaker.impl.network.messages.MessageOpen;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import java.util.function.Supplier;

public class PacketHandler {
    
    public static SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(new ResourceLocation("crafttweaker:main"), () -> CraftTweaker.VERSION, CraftTweaker.VERSION::equals, CraftTweaker.VERSION::equals);
    
    private static int ID = 0;
    
    public static void init() {
        
        CHANNEL.registerMessage(ID++, MessageCopy.class, (messageCopy, packetBuffer) -> packetBuffer.writeString(messageCopy.toCopy), packetBuffer -> new MessageCopy(packetBuffer.readString()), (messageCopy, contextSupplier) -> andHandling(contextSupplier, () -> Minecraft.getInstance().keyboardListener.setClipboardString(messageCopy.toCopy)));
        CHANNEL.registerMessage(ID++, MessageOpen.class, (messageOpen, packetBuffer) -> packetBuffer.writeString(messageOpen.path), packetBuffer -> new MessageOpen(packetBuffer.readString()), (messageOpen, contextSupplier) -> andHandling(contextSupplier, () -> Util.getOSType()
                .openURI(messageOpen.path)));
    }
    
    private static void andHandling(final Supplier<NetworkEvent.Context> contextSupplier, final Runnable enqueuedWork) {
        
        contextSupplier.get().enqueueWork(enqueuedWork);
        contextSupplier.get().setPacketHandled(true);
    }
    
}
