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
@Document("neoforge/api/event/entity/player/TabListNameFormat")
@NativeTypeRegistration(value = PlayerEvent.TabListNameFormat.class, zenCodeName = "crafttweaker.neoforge.api.event.entity.player.TabListNameFormat")
public class ExpandPlayerTabListNameFormatEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<PlayerEvent.TabListNameFormat> BUS = IEventBus.direct(
            PlayerEvent.TabListNameFormat.class,
            NeoForgeEventBusWire.of()
    );
    
    @ZenCodeType.Nullable
    @ZenCodeType.Getter("displayName")
    public static Component getDisplayName(PlayerEvent.TabListNameFormat internal) {
        
        return internal.getDisplayName();
    }
    
    @ZenCodeType.Setter("displayName")
    public static void setDisplayName(PlayerEvent.TabListNameFormat internal, @ZenCodeType.Nullable Component displayName) {
        
        internal.setDisplayName(displayName);
    }
    
}
