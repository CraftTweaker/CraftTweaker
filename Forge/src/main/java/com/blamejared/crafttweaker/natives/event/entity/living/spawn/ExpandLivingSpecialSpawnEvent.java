package com.blamejared.crafttweaker.natives.event.entity.living.spawn;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ForgeEventCancellationCarrier;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.ForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.BaseSpawner;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("forge/api/event/entity/living/spawn/LivingSpecialSpawnEvent")
@NativeTypeRegistration(value = LivingSpawnEvent.SpecialSpawn.class, zenCodeName = "crafttweaker.forge.api.event.entity.living.spawn.LivingSpecialSpawnEvent")
public class ExpandLivingSpecialSpawnEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<LivingSpawnEvent.SpecialSpawn> BUS = IEventBus.cancelable(
            LivingSpawnEvent.SpecialSpawn.class,
            ForgeEventBusWire.of(),
            ForgeEventCancellationCarrier.of()
    );
    
    @ZenCodeType.Nullable
    @ZenCodeType.Getter("spawner")
    public static BaseSpawner getSpawner(LivingSpawnEvent.SpecialSpawn internal) {
        
        return internal.getSpawner();
    }
    
    @ZenCodeType.Getter("spawnReason")
    public static MobSpawnType getSpawnReason(LivingSpawnEvent.SpecialSpawn internal) {
        
        return internal.getSpawnReason();
    }
    
}
