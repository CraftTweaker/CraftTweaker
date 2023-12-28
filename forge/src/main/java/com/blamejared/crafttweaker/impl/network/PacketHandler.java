package com.blamejared.crafttweaker.impl.network;

import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.impl.network.message.ClientMessages;
import com.blamejared.crafttweaker.impl.network.message.IMessage;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.minecraftforge.network.Channel;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.SimpleChannel;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class PacketHandler {
    
    public static SimpleChannel CHANNEL = ChannelBuilder.named(CraftTweakerConstants.rl("main"))
            .acceptedVersions(Channel.VersionTest.exact(CraftTweakerConstants.NETWORK_VERSION))
            .networkProtocolVersion(CraftTweakerConstants.NETWORK_VERSION)
            .simpleChannel();
    
    private static int ID = 0;
    
    public static void init() {
        
        for(ClientMessages msg : ClientMessages.values()) {
            registerMessage(msg.getMessageClass(), msg.getMessageFactory(), NetworkDirection.PLAY_TO_CLIENT);
        }
    }
    
    private static <MSG extends IMessage<MSG>> void registerMessage(Class<MSG> messageType, Function<FriendlyByteBuf, MSG> decoder, @SuppressWarnings("SameParameterValue") @Nullable NetworkDirection direction) {
        
        registerMessage(messageType, decoder, (messageCopy, contextSupplier) -> andHandling(contextSupplier, messageCopy::handle), direction);
    }
    
    private static <MSG extends IMessage<MSG>> void registerMessage(Class<MSG> messageType, Function<FriendlyByteBuf, MSG> decoder, BiConsumer<MSG, CustomPayloadEvent.Context> messageConsumer, @Nullable NetworkDirection direction) {
        
        CHANNEL.messageBuilder(messageType, ID++, direction)
                .encoder(IMessage::serialize)
                .decoder(decoder)
                .consumerNetworkThread(messageConsumer)
                .add();
    }
    
    private static void andHandling(final CustomPayloadEvent.Context context, final Runnable enqueuedWork) {
        
        context.enqueueWork(enqueuedWork);
        context.setPacketHandled(true);
    }
    
}
