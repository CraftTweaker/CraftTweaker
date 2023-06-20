package com.blamejared.crafttweaker.natives.event.entity.player;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.ForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.network.chat.Component;
import net.minecraftforge.event.entity.player.PlayerEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("forge/api/event/entity/player/TabListNameFormat")
@NativeTypeRegistration(value = PlayerEvent.TabListNameFormat.class, zenCodeName = "crafttweaker.forge.api.event.entity.player.TabListNameFormat")
public class ExpandPlayerTabListNameFormatEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<PlayerEvent.TabListNameFormat> BUS = IEventBus.direct(
            PlayerEvent.TabListNameFormat.class,
            ForgeEventBusWire.of()
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
