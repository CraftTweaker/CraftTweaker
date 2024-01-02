package com.blamejared.crafttweaker.natives.event.anvil;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.NeoForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.neoforged.neoforge.event.entity.player.AnvilRepairEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("neoforge/api/event/anvil/AnvilRepairEvent")
@NativeTypeRegistration(value = AnvilRepairEvent.class, zenCodeName = "crafttweaker.neoforge.api.event.anvil.AnvilRepairEvent")
public class ExpandAnvilRepairEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<AnvilRepairEvent> BUS = IEventBus.direct(
            AnvilRepairEvent.class,
            NeoForgeEventBusWire.of()
    );
    
    @ZenCodeType.Getter("output")
    public static IItemStack getOutput(AnvilRepairEvent internal) {
        
        return IItemStack.of(internal.getOutput());
    }
    
    @ZenCodeType.Getter("left")
    public static IItemStack getLeft(AnvilRepairEvent internal) {
        
        return IItemStack.of(internal.getLeft());
    }
    
    @ZenCodeType.Getter("right")
    public static IItemStack getRight(AnvilRepairEvent internal) {
        
        return IItemStack.of(internal.getRight());
    }
    
    @ZenCodeType.Getter("breakChance")
    public static float getBreakChance(AnvilRepairEvent internal) {
        
        return internal.getBreakChance();
    }
    
    @ZenCodeType.Setter("breakChance")
    public static void setBreakChance(AnvilRepairEvent internal, float breakChance) {
        
        internal.setBreakChance(breakChance);
    }
    
}
