package com.blamejared.crafttweaker.natives.event.entity.advancement;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.NeoForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.advancements.AdvancementHolder;
import net.neoforged.neoforge.event.entity.player.AdvancementEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("neoforge/api/event/advancement/AdvancementEarnEvent")
@NativeTypeRegistration(value = AdvancementEvent.AdvancementEarnEvent.class, zenCodeName = "crafttweaker.neoforge.api.event.advancement.AdvancementEarnEvent")
public class ExpandAdvancementEarnEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<AdvancementEvent.AdvancementEarnEvent> BUS = IEventBus.direct(
            AdvancementEvent.AdvancementEarnEvent.class,
            NeoForgeEventBusWire.of()
    );
    
    @ZenCodeType.Getter("advancement")
    public static AdvancementHolder getAdvancement(AdvancementEvent.AdvancementEarnEvent internal) {
        
        return internal.getAdvancement();
    }
    
}
