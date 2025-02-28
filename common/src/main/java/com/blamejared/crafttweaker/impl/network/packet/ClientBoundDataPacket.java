package com.blamejared.crafttweaker.impl.network.packet;

import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.data.converter.tag.TagToDataConverter;
import com.blamejared.crafttweaker.api.network.CTNetwork;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import java.util.Objects;

public record ClientBoundDataPacket(String id, IData data) implements CraftTweakerPacket {
    
    public static final ResourceLocation ID = CraftTweakerConstants.rl("data");
    public static final StreamCodec<RegistryFriendlyByteBuf, ClientBoundDataPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,
            ClientBoundDataPacket::id,
            ByteBufCodecs.TAG.map(TagToDataConverter::convert, IData::getInternal),
            ClientBoundDataPacket::data,
            ClientBoundDataPacket::new
    );
    
    @Override
    public void handle() {
        
        CTNetwork.INSTANCE.receive(id, data, Objects.requireNonNull(Minecraft.getInstance().player, "Unable to send to a null player"));
    }
    
    @Override
    public Type<? extends CustomPacketPayload> type() {
        
        return ClientBoundPackets.DATA.type();
    }
    
}
