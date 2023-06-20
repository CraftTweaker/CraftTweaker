package com.blamejared.crafttweaker.natives.event.entity.player;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ForgeEventCancellationCarrier;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.ForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("forge/api/event/entity/player/AttackEntityEvent")
@NativeTypeRegistration(value = AttackEntityEvent.class, zenCodeName = "crafttweaker.forge.api.event.entity.player.AttackEntityEvent")
public class ExpandAttackEntityEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<AttackEntityEvent> BUS = IEventBus.cancelable(
            AttackEntityEvent.class,
            ForgeEventBusWire.of(),
            ForgeEventCancellationCarrier.of()
    );
    
    @ZenCodeType.Getter("target")
    public static Entity getTarget(AttackEntityEvent internal) {
        
        return internal.getTarget();
    }
    
}
