package com.blamejared.crafttweaker.impl_native.event.entity.player;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.player.PlayerEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/event/player/MCPlayerEvent")
@NativeTypeRegistration(value = PlayerEvent.class, zenCodeName = "crafttweaker.api.event.entity.player.MCPlayerEvent")
public class ExpandPlayerEvent {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("player")
    public static PlayerEntity getPlayer(PlayerEvent internal) {
        return internal.getPlayer();
    }
}
