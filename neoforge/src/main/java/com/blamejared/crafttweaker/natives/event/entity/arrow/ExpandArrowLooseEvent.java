package com.blamejared.crafttweaker.natives.event.entity.arrow;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.NeoForgeEventCancellationCarrier;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.NeoForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.entity.player.ArrowLooseEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("neoforge/api/event/entity/arrow/ArrowLooseEvent")
@NativeTypeRegistration(value = ArrowLooseEvent.class, zenCodeName = "crafttweaker.neoforge.api.event.entity.arrow.ArrowLooseEvent")
public class ExpandArrowLooseEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<ArrowLooseEvent> BUS = IEventBus.cancelable(
            ArrowLooseEvent.class,
            NeoForgeEventBusWire.of(),
            NeoForgeEventCancellationCarrier.of()
    );
    
    @ZenCodeType.Getter("bow")
    public static IItemStack getBow(ArrowLooseEvent internal) {
        
        return IItemStack.of(internal.getBow());
    }
    
    @ZenCodeType.Getter("level")
    public static Level getLevel(ArrowLooseEvent internal) {
        
        return internal.getLevel();
    }
    
    @ZenCodeType.Getter("ammo")
    public static boolean hasAmmo(ArrowLooseEvent internal) {
        
        return internal.hasAmmo();
    }
    
    @ZenCodeType.Getter("charge")
    public static int getCharge(ArrowLooseEvent internal) {
        
        return internal.getCharge();
    }
    
    @ZenCodeType.Setter("charge")
    public static void setCharge(ArrowLooseEvent internal, int charge) {
        
        internal.setCharge(charge);
    }
    
}
