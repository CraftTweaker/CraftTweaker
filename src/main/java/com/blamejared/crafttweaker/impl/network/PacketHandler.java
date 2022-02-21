package com.blamejared.crafttweaker.impl.network;

import com.blamejared.crafttweaker.CraftTweaker;
import com.blamejared.crafttweaker.impl.network.messages.MessageCopy;
import com.blamejared.crafttweaker.impl.network.messages.MessageOpen;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import java.util.Optional;
import java.util.function.Supplier;

public class PacketHandler {
    
    public static SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(new ResourceLocation("crafttweaker:main"), () -> CraftTweaker.VERSION, CraftTweaker.VERSION::equals, CraftTweaker.VERSION::equals);
    
    private static int ID = 0;
    
    public static void init() {
        
        CHANNEL.registerMessage(ID++, MessageCopy.class, (messageCopy, packetBuffer) -> packetBuffer.writeString(messageCopy.toCopy), packetBuffer -> new MessageCopy(packetBuffer.readString()), (messageCopy, contextSupplier) -> andHandling(contextSupplier, () -> Minecraft.getInstance().keyboardListener.setClipboardString(messageCopy.toCopy)), Optional.of(NetworkDirection.PLAY_TO_CLIENT));
        CHANNEL.registerMessage(ID++, MessageOpen.class, (messageOpen, packetBuffer) -> packetBuffer.writeString(messageOpen.path), packetBuffer -> new MessageOpen(packetBuffer.readString()), (messageOpen, contextSupplier) -> andHandling(contextSupplier, () -> {
            System.out.println(String.format("Could not open %s!", messageOpen.path));
        }), Optional.of(NetworkDirection.PLAY_TO_CLIENT));
    }
    
    private static void andHandling(final Supplier<NetworkEvent.Context> contextSupplier, final Runnable enqueuedWork) {
        
        contextSupplier.get().enqueueWork(enqueuedWork);
        contextSupplier.get().setPacketHandled(true);
    }
    
}
