package com.blamejared.crafttweaker.natives.event.entity.advancement;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.ForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("forge/api/event/advancement/AdvancementEarnEvent")
@NativeTypeRegistration(value = AdvancementEvent.AdvancementEarnEvent.class, zenCodeName = "crafttweaker.forge.api.event.advancement.AdvancementEarnEvent")
public class ExpandAdvancementEarnEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<AdvancementEvent.AdvancementEarnEvent> BUS = IEventBus.direct(
            AdvancementEvent.AdvancementEarnEvent.class,
            ForgeEventBusWire.of()
    );
    
    @ZenCodeType.Getter("advancement")
    public static AdvancementHolder getAdvancement(AdvancementEvent.AdvancementEarnEvent internal) {
        
        return internal.getAdvancement();
    }
    
}
