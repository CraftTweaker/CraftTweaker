package com.blamejared.crafttweaker.natives.event.entity.player;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.NeoForgeEventCancellationCarrier;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.NeoForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("neoforge/api/event/entity/player/AttackEntityEvent")
@NativeTypeRegistration(value = AttackEntityEvent.class, zenCodeName = "crafttweaker.neoforge.api.event.entity.player.AttackEntityEvent")
public class ExpandAttackEntityEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<AttackEntityEvent> BUS = IEventBus.cancelable(
            AttackEntityEvent.class,
            NeoForgeEventBusWire.of(),
            NeoForgeEventCancellationCarrier.of()
    );
    
    @ZenCodeType.Getter("target")
    public static Entity getTarget(AttackEntityEvent internal) {
        
        return internal.getTarget();
    }
    
}
