package com.blamejared.crafttweaker.natives.event;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ForgeEventCancellationCarrier;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.ForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.level.ExplosionEvent;
import org.openzen.zencode.java.ZenCodeType;

import java.util.List;

@ZenRegister
@Document("forge/api/event/ExplosionEvent")
@NativeTypeRegistration(value = ExplosionEvent.class, zenCodeName = "crafttweaker.forge.api.event.ExplosionEvent")
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
    @Document("forge/api/event/StartExplosionEvent")
    @NativeTypeRegistration(value = ExplosionEvent.Start.class, zenCodeName = "crafttweaker.forge.api.event.StartExplosionEvent")
    public static class ExpandStartExplosionEvent {
        
        @ZenEvent.Bus
        public static final IEventBus<ExplosionEvent.Start> BUS = IEventBus.cancelable(
                ExplosionEvent.Start.class,
                ForgeEventBusWire.of(),
                ForgeEventCancellationCarrier.of()
        );
        
    }
    
    @ZenEvent
    @ZenRegister
    @Document("forge/api/event/DetonateExplosionEvent")
    @NativeTypeRegistration(value = ExplosionEvent.Detonate.class, zenCodeName = "crafttweaker.forge.api.event.DetonateExplosionEvent")
    public static class ExpandDetonateExplosionEvent {
        
        @ZenEvent.Bus
        public static final IEventBus<ExplosionEvent.Detonate> BUS = IEventBus.direct(
                ExplosionEvent.Detonate.class,
                ForgeEventBusWire.of()
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
