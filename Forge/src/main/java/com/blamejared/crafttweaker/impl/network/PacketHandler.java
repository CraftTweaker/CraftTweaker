package com.blamejared.crafttweaker.impl.network;

import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.impl.network.message.ClientMessages;
import com.blamejared.crafttweaker.impl.network.message.IMessage;
import com.blamejared.crafttweaker.platform.Services;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class PacketHandler {
    
    public static SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(CraftTweakerConstants.rl("main"), Services.NETWORK::getNetworkVersion, Services.NETWORK.getNetworkVersion()::equals, Services.NETWORK.getNetworkVersion()::equals);
    
    private static int ID = 0;
    
    public static void init() {
        
        for(ClientMessages msg : ClientMessages.values()) {
            registerMessage(msg.getMessageClass(), msg.getMessageFactory(), NetworkDirection.PLAY_TO_CLIENT);
        }
    }
    
    private static <MSG extends IMessage<MSG>> void registerMessage(Class<MSG> messageType, Function<FriendlyByteBuf, MSG> decoder, @SuppressWarnings("SameParameterValue") @Nullable NetworkDirection direction) {
        
        registerMessage(messageType, decoder, (messageCopy, contextSupplier) -> andHandling(contextSupplier, messageCopy::handle), direction);
    }
    
    private static <MSG extends IMessage<MSG>> void registerMessage(Class<MSG> messageType, Function<FriendlyByteBuf, MSG> decoder, BiConsumer<MSG, Supplier<NetworkEvent.Context>> messageConsumer, @Nullable NetworkDirection direction) {
        
        CHANNEL.registerMessage(ID++, messageType, IMessage::serialize, decoder, messageConsumer, Optional.ofNullable(direction));
    }
    
    private static void andHandling(final Supplier<NetworkEvent.Context> contextSupplier, final Runnable enqueuedWork) {
        
        contextSupplier.get().enqueueWork(enqueuedWork);
        contextSupplier.get().setPacketHandled(true);
    }
    
}
