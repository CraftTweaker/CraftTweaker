package com.blamejared.crafttweaker.natives.event.entity.player;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.NeoForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("neoforge/api/event/entity/player/PlayerNameFormatEvent")
@NativeTypeRegistration(value = PlayerEvent.NameFormat.class, zenCodeName = "crafttweaker.neoforge.api.event.entity.player.PlayerNameFormatEvent")
public class ExpandPlayerNameFormatEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<PlayerEvent.NameFormat> BUS = IEventBus.direct(
            PlayerEvent.NameFormat.class,
            NeoForgeEventBusWire.of()
    );
    
    @ZenCodeType.Getter("username")
    public static Component getUsername(PlayerEvent.NameFormat internal) {
        
        return internal.getUsername();
    }
    
    @ZenCodeType.Getter("displayName")
    public static Component getDisplayName(PlayerEvent.NameFormat internal) {
        
        return internal.getDisplayname();
    }
    
    @ZenCodeType.Setter("displayName")
    public static void setDisplayName(PlayerEvent.NameFormat internal, Component displayname) {
        
        internal.setDisplayname(displayname);
    }
    
}
