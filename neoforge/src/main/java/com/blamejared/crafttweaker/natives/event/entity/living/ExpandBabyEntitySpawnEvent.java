package com.blamejared.crafttweaker.natives.event.entity.living;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.NeoForgeEventCancellationCarrier;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.NeoForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.living.BabyEntitySpawnEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("neoforge/api/event/entity/living/BabyEntitySpawnEvent")
@NativeTypeRegistration(value = BabyEntitySpawnEvent.class, zenCodeName = "crafttweaker.neoforge.api.event.entity.living.BabyEntitySpawnEvent")
public class ExpandBabyEntitySpawnEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<BabyEntitySpawnEvent> BUS = IEventBus.cancelable(
            BabyEntitySpawnEvent.class,
            NeoForgeEventBusWire.of(),
            NeoForgeEventCancellationCarrier.of()
    );
    
    @ZenCodeType.Getter("parentA")
    public static Mob getParentA(BabyEntitySpawnEvent internal) {
        
        return internal.getParentA();
    }
    
    @ZenCodeType.Getter("parentB")
    public static Mob getParentB(BabyEntitySpawnEvent internal) {
        
        return internal.getParentB();
    }
    
    @ZenCodeType.Nullable
    @ZenCodeType.Getter("causedByPlayer")
    public static Player getCausedByPlayer(BabyEntitySpawnEvent internal) {
        
        return internal.getCausedByPlayer();
    }
    
    @ZenCodeType.Nullable
    @ZenCodeType.Getter("child")
    public static AgeableMob getChild(BabyEntitySpawnEvent internal) {
        
        return internal.getChild();
    }
    
    @ZenCodeType.Setter("child")
    public static void setChild(BabyEntitySpawnEvent internal, AgeableMob proposedChild) {
        
        internal.setChild(proposedChild);
    }
    
}
