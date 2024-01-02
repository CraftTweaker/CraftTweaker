package com.blamejared.crafttweaker.natives.event;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.NeoForgeEventCancellationCarrier;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.NeoForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.level.ExplosionEvent;
import org.openzen.zencode.java.ZenCodeType;

import java.util.List;

@ZenRegister
@Document("neoforge/api/event/ExplosionEvent")
@NativeTypeRegistration(value = ExplosionEvent.class, zenCodeName = "crafttweaker.neoforge.api.event.ExplosionEvent")
public class ExpandExplosionEvent {
    
    @ZenCodeType.Getter("level")
    public static Level getLevel(ExplosionEvent internal) {
        
        return internal.getLevel();
    }
    
    @ZenCodeType.Getter("explosion")
    public static Explosion getExplosion(ExplosionEvent internal) {
        
        return internal.getExplosion();
    }
    
    @ZenEvent
    @ZenRegister
    @Document("neoforge/api/event/StartExplosionEvent")
    @NativeTypeRegistration(value = ExplosionEvent.Start.class, zenCodeName = "crafttweaker.neoforge.api.event.StartExplosionEvent")
    public static class ExpandStartExplosionEvent {
        
        @ZenEvent.Bus
        public static final IEventBus<ExplosionEvent.Start> BUS = IEventBus.cancelable(
                ExplosionEvent.Start.class,
                NeoForgeEventBusWire.of(),
                NeoForgeEventCancellationCarrier.of()
        );
        
    }
    
    @ZenEvent
    @ZenRegister
    @Document("neoforge/api/event/DetonateExplosionEvent")
    @NativeTypeRegistration(value = ExplosionEvent.Detonate.class, zenCodeName = "crafttweaker.neoforge.api.event.DetonateExplosionEvent")
    public static class ExpandDetonateExplosionEvent {
        
        @ZenEvent.Bus
        public static final IEventBus<ExplosionEvent.Detonate> BUS = IEventBus.direct(
                ExplosionEvent.Detonate.class,
                NeoForgeEventBusWire.of()
        );
        
        @ZenCodeType.Getter("affectedBlocks")
        public static List<BlockPos> getAffectedBlocks(ExplosionEvent.Detonate internal) {
            
            return internal.getAffectedBlocks();
        }
        
        @ZenCodeType.Getter("affectedEntities")
        public static List<Entity> getAffectedEntities(ExplosionEvent.Detonate internal) {
            
            return internal.getAffectedEntities();
        }
        
    }
    
}
