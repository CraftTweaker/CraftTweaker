package com.blamejared.crafttweaker.impl.network;

import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.impl.network.message.ClientMessages;
import com.blamejared.crafttweaker.impl.network.message.IMessage;
import net.neoforged.neoforge.network.INetworkDirection;
import net.neoforged.neoforge.network.NetworkEvent;
import net.neoforged.neoforge.network.NetworkRegistry;
import net.neoforged.neoforge.network.PlayNetworkDirection;
import net.neoforged.neoforge.network.simple.MessageFunctions;
import net.neoforged.neoforge.network.simple.SimpleChannel;
import org.jetbrains.annotations.Nullable;

public class PacketHandler {
    
    public static SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(CraftTweakerConstants.rl("main"), () -> CraftTweakerConstants.NETWORK_VERSION_STRING, CraftTweakerConstants.NETWORK_VERSION_STRING::equals, CraftTweakerConstants.NETWORK_VERSION_STRING::equals);
    
    
    private static int ID = 0;
    
    public static void init() {
        
        for(ClientMessages msg : ClientMessages.values()) {
            registerMessage(msg.getMessageClass(), buffer -> msg.getMessageFactory()
                    .apply(buffer), PlayNetworkDirection.PLAY_TO_CLIENT);
        }
    }
    
    private static <MSG extends IMessage<MSG>> void registerMessage(Class<MSG> messageType, MessageFunctions.MessageDecoder<MSG> decoder, @SuppressWarnings("SameParameterValue") @Nullable INetworkDirection<?> direction) {
        
        registerMessage(messageType, decoder, (messageCopy, contextSupplier) -> andHandling(contextSupplier, messageCopy::handle), direction);
    }
    
    private static <MSG extends IMessage<MSG>> void registerMessage(Class<MSG> messageType, MessageFunctions.MessageDecoder<MSG> decoder, MessageFunctions.MessageConsumer<MSG> messageConsumer, @Nullable INetworkDirection<?> direction) {
        
        CHANNEL.messageBuilder(messageType, ID++, direction)
                .encoder(IMessage::serialize)
                .decoder(decoder)
                .consumerNetworkThread(messageConsumer)
                .add();
    }
    
    private static void andHandling(final NetworkEvent.Context context, final Runnable enqueuedWork) {
        
        context.enqueueWork(enqueuedWork);
        context.setPacketHandled(true);
    }
    
}
