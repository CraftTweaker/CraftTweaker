package com.blamejared.crafttweaker.natives.event.entity.living;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.NeoForgeEventCancellationCarrier;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.NeoForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.living.AnimalTameEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("neoforge/api/event/entity/living/AnimalTameEvent")
@NativeTypeRegistration(value = AnimalTameEvent.class, zenCodeName = "crafttweaker.neoforge.api.event.entity.living.AnimalTameEvent")
public class ExpandAnimalTameEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<AnimalTameEvent> BUS = IEventBus.cancelable(
            AnimalTameEvent.class,
            NeoForgeEventBusWire.of(),
            NeoForgeEventCancellationCarrier.of()
    );
    
    @ZenCodeType.Getter("entity")
    public static Animal getEntity(AnimalTameEvent internal) {
        
        return internal.getAnimal();
    }
    
    @ZenCodeType.Getter("tamer")
    public static Player getTamer(AnimalTameEvent internal) {
        
        return internal.getTamer();
    }
    
}
