package com.blamejared.crafttweaker.natives.event.entity.living.spawn;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.data.MapData;
import com.blamejared.crafttweaker.api.data.converter.tag.TagToDataConverter;
import com.blamejared.crafttweaker.api.event.ForgeEventCancellationCarrier;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.ForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.BaseSpawner;
import net.minecraftforge.event.entity.living.MobSpawnEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("forge/api/event/entity/living/spawn/FinalizeMobSpawnEvent")
@NativeTypeRegistration(value = MobSpawnEvent.FinalizeSpawn.class, zenCodeName = "crafttweaker.forge.api.event.entity.living.spawn.FinalizeMobSpawnEvent")
public class ExpandMobFinalizeSpawnEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<MobSpawnEvent.FinalizeSpawn> BUS = IEventBus.cancelable(
            MobSpawnEvent.FinalizeSpawn.class,
            ForgeEventBusWire.of(),
            ForgeEventCancellationCarrier.of()
    );
    
    @ZenCodeType.Getter("difficulty")
    public static DifficultyInstance getDifficulty(MobSpawnEvent.FinalizeSpawn internal) {
        
        return internal.getDifficulty();
    }
    
    @ZenCodeType.Setter("difficulty")
    public static void setDifficulty(MobSpawnEvent.FinalizeSpawn internal, DifficultyInstance inst) {
        
        internal.setDifficulty(inst);
    }
    
    @ZenCodeType.Getter("spawnType")
    public static MobSpawnType getSpawnType(MobSpawnEvent.FinalizeSpawn internal) {
        
        return internal.getSpawnType();
    }
    
    //    public static @Nullable SpawnGroupData getSpawnData(MobSpawnEvent.FinalizeSpawn internal) {
    //
    //        return internal.getSpawnData();
    //    }
    //
    //    public static void setSpawnData(MobSpawnEvent.FinalizeSpawn internal, @Nullable SpawnGroupData data) {
    //
    //        internal.setSpawnData(data);
    //    }
    @ZenCodeType.Nullable
    @ZenCodeType.Getter("spawnTag")
    public static IData getSpawnTag(MobSpawnEvent.FinalizeSpawn internal) {
        
        return TagToDataConverter.convert(internal.getSpawnTag());
    }
    
    @ZenCodeType.Setter("spawnTag")
    public static void setSpawnTag(MobSpawnEvent.FinalizeSpawn internal, @ZenCodeType.Nullable IData tag) {
        
        if(!(tag instanceof MapData map)) {
            throw new IllegalArgumentException("Unable to set the spawn tag to a non map data type!");
        }
        internal.setSpawnTag(map.getInternal());
    }
    
    @ZenCodeType.Nullable
    @ZenCodeType.Getter("spawner")
    public static BaseSpawner getSpawner(MobSpawnEvent.FinalizeSpawn internal) {
        
        return internal.getSpawner();
    }
    
    @ZenCodeType.Setter("spawnCancelled")
    public static void setSpawnCancelled(MobSpawnEvent.FinalizeSpawn internal, boolean cancel) {
        
        internal.setSpawnCancelled(cancel);
    }
    
    @ZenCodeType.Getter("isSpawnCancelled")
    public static boolean isSpawnCancelled(MobSpawnEvent.FinalizeSpawn internal) {
        
        return internal.isSpawnCancelled();
    }
    
}
