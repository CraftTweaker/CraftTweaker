package com.blamejared.crafttweaker.natives.event.entity.advancement;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.ForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.advancements.Advancement;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("forge/api/event/advancement/AdvancementEvent")
@NativeTypeRegistration(value = AdvancementEvent.class, zenCodeName = "crafttweaker.forge.api.event.advancement.AdvancementEvent")
public class ExpandAdvancementEvent {
    
    @ZenCodeType.Getter("advancement")
    public static Advancement getAdvancement(AdvancementEvent internal) {
        
        return internal.getAdvancement();
    }
    
}
