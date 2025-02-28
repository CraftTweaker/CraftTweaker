package com.blamejared.crafttweaker.impl.network.packet;

import com.blamejared.crafttweaker.api.util.GenericUtil;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public enum ClientBoundPackets {
    COPY(new CustomPacketPayload.Type<>(ClientBoundCopyPacket.ID), ClientBoundCopyPacket.STREAM_CODEC),
    OPEN_FILE(new CustomPacketPayload.Type<>(ClientBoundSendOpenFileMessagePacket.ID), ClientBoundSendOpenFileMessagePacket.STREAM_CODEC),
    DATA(new CustomPacketPayload.Type<>(ClientBoundDataPacket.ID), ClientBoundDataPacket.STREAM_CODEC),
    ;
    
    private final CustomPacketPayload.Type<CraftTweakerPacket> type;
    private final StreamCodec<RegistryFriendlyByteBuf, CraftTweakerPacket> streamCodec;
    
    ClientBoundPackets(CustomPacketPayload.Type<CraftTweakerPacket> type, StreamCodec<RegistryFriendlyByteBuf, ? extends CraftTweakerPacket> streamCodec) {
        
        this.type = type;
        this.streamCodec = GenericUtil.uncheck(streamCodec);
    }
    
    public CustomPacketPayload.Type<CraftTweakerPacket> type() {
        
        return type;
    }
    
    public StreamCodec<RegistryFriendlyByteBuf, CraftTweakerPacket> streamCodec() {
        
        return streamCodec;
    }
    
}
