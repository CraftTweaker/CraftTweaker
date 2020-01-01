package com.blamejared.crafttweaker.impl.world;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.impl.entity.MCEntityType;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.ZenWrapper;
import net.minecraft.world.biome.Biome;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.world.MCBiomeSpawnEntry")
@Document("crafttweaker/world/MCBiomeSpawnEntry")
@ZenWrapper(wrappedClass = "net.minecraft.world.biome.Biome.SpawnListEntry", conversionMethodFormat = "%s.getInternal()")
public class MCBiomeSpawnEntry {
    
    private final Biome.SpawnListEntry internal;
    
    public MCBiomeSpawnEntry(Biome.SpawnListEntry internal) {
        this.internal = internal;
    }
    
    
    @ZenCodeType.Constructor
    public MCBiomeSpawnEntry(MCEntityType entityType, int weight, int minGroupCount, int maxGroupCount) {
        this.internal = new Biome.SpawnListEntry(entityType.getInternal(), weight, minGroupCount, maxGroupCount);
    }
    
    @ZenCodeType.Getter("entityType")
    public MCEntityType getEntityType() {
        return new MCEntityType(getInternal().entityType);
    }
    
    @ZenCodeType.Getter("weight")
    public int getWeight() {
        return getInternal().itemWeight;
    }
    
    @ZenCodeType.Getter("minGroupCount")
    public int getMinGroupCount() {
        return getInternal().minGroupCount;
    }
    
    @ZenCodeType.Getter("maxGroupCount")
    public int getMaxGroupCount() {
        return getInternal().maxGroupCount;
    }
    
    public Biome.SpawnListEntry getInternal() {
        return internal;
    }
}
