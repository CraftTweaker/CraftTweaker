package com.blamejared.crafttweaker.natives.event.entity.advancement;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.ForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import org.openzen.zencode.java.ZenCodeType;

@SuppressWarnings("deprecation")
@ZenRegister
@ZenEvent
@Document("forge/api/event/advancement/AdvancementProgressEvent")
@NativeTypeRegistration(value = AdvancementEvent.AdvancementProgressEvent.class, zenCodeName = "crafttweaker.forge.api.event.advancement.AdvancementProgressEvent")
public class ExpandAdvancementProgressEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<AdvancementEvent.AdvancementProgressEvent> BUS = IEventBus.direct(
            AdvancementEvent.AdvancementProgressEvent.class,
            ForgeEventBusWire.of()
    );
    
    @ZenCodeType.Getter("advancement")
    public static Advancement getAdvancement(AdvancementEvent.AdvancementProgressEvent internal) {
        
        return internal.getAdvancement();
    }
    
    @ZenCodeType.Getter("advancementProgress")
    public static AdvancementProgress getAdvancementProgress(AdvancementEvent.AdvancementProgressEvent internal) {
        
        return internal.getAdvancementProgress();
    }
    
    @ZenCodeType.Getter("criterionName")
    public static String getCriterionName(AdvancementEvent.AdvancementProgressEvent internal) {
        
        return internal.getCriterionName();
    }
    
    @ZenCodeType.Getter("progressType")
    public static AdvancementEvent.AdvancementProgressEvent.ProgressType getProgressType(AdvancementEvent.AdvancementProgressEvent internal) {
        
        return internal.getProgressType();
    }
    
}
