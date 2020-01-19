package com.blamejared.crafttweaker.impl.world;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.impl.entity.MCEntityClassification;
import com.blamejared.crafttweaker.impl.util.MCBlockPos;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.ZenWrapper;
import net.minecraft.world.biome.Biome;
import org.openzen.zencode.java.ZenCodeType;

import java.util.List;
import java.util.stream.Collectors;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.world.MCBiome")
@Document("vanilla/api/world/MCBiome")
@ZenWrapper(wrappedClass = "net.minecraft.world.biome.Biome", conversionMethodFormat = "%s.getInternal()")
public class MCBiome {
    
    private final Biome internal;
    
    public MCBiome(Biome internal) {
        this.internal = internal;
    }
    
    @ZenCodeType.Getter("waterColor")
    public int getWaterFloat() {
        return getInternal().getWaterColor();
    }
    
    @ZenCodeType.Getter("waterFogColor")
    public int getWaterFogColor() {
        return getInternal().getWaterFogColor();
    }
    
    @ZenCodeType.Getter("defaultTemperature")
    public float getDefaultTemperature() {
        return getInternal().getDefaultTemperature();
    }
    
    @ZenCodeType.Getter("translationKey")
    public String getTranslationKey() {
        return getInternal().getTranslationKey();
    }
    
    @ZenCodeType.Getter("scale")
    public float getScale() {
        return getInternal().getScale();
    }
    
    @ZenCodeType.Getter("downfall")
    public float getDownFall() {
        return getInternal().getDownfall();
    }
    
    @ZenCodeType.Getter("depth")
    public float getDepth() {
        return getInternal().getDepth();
    }
    
    @ZenCodeType.Getter("spawningChange")
    public float getSpawningChance() {
        return getInternal().getSpawningChance();
    }
    
    @ZenCodeType.Getter("isHighHumidity")
    public boolean isHighHumidity() {
        return getInternal().isHighHumidity();
    }
    
    @ZenCodeType.Getter("isMutation")
    public boolean isMutation() {
        return getInternal().isMutation();
    }
    
    @ZenCodeType.Getter("category")
    public String getCategory() {
        return getInternal().getCategory().getName().toLowerCase();
    }
    
    @ZenCodeType.Getter("isTempOcean")
    public boolean isTempOcean() {
        return getInternal().getTempCategory() == Biome.TempCategory.OCEAN;
    }
    
    @ZenCodeType.Getter("isTempCold")
    public boolean isTempCold() {
        return getInternal().getTempCategory() == Biome.TempCategory.COLD;
    }
    
    @ZenCodeType.Getter("isTempMedium")
    public boolean isTempMedium() {
        return getInternal().getTempCategory() == Biome.TempCategory.MEDIUM;
    }
    
    @ZenCodeType.Getter("isTempWarm")
    public boolean isTempWarm() {
        return getInternal().getTempCategory() == Biome.TempCategory.WARM;
    }
    
    @ZenCodeType.Getter("doesRain")
    public boolean doesRain() {
        return getInternal().getPrecipitation() == Biome.RainType.RAIN;
    }
    
    @ZenCodeType.Getter("doesSnow")
    public boolean doesSnow() {
        return getInternal().getPrecipitation() == Biome.RainType.SNOW;
    }
    
    @ZenCodeType.Getter("rainType")
    public String getRainType() {
        return getInternal().getPrecipitation().getName();
    }
    
    @ZenCodeType.Getter("parent")
    public String getParent() {
        return getInternal().getParent();
    }
    
    @ZenCodeType.Method
    public String getTempCategory() {
        return getInternal().getTempCategory().getName().toLowerCase();
    }
    
    @ZenCodeType.Method
    public float getTemperature(MCBlockPos pos) {
        return getInternal().getTemperature(pos.getInternal());
    }
    
    @ZenCodeType.Method
    public List<MCBiomeSpawnEntry> getSpawns(MCEntityClassification classification) {
        return getInternal().getSpawns(classification.getInternal()).stream().map(MCBiomeSpawnEntry::new).collect(Collectors.toList());
    }
    
    /*
    TODO methods to add:
    - Requires a MCWorld
     boolean doesWaterFreeze(IWorldReader worldIn, BlockPos pos)
     boolean doesWaterFreeze(IWorldReader worldIn, BlockPos water, boolean mustBeAtEdge)
     boolean doesSnowGenerate(IWorldReader worldIn, BlockPos pos)
     */
    public Biome getInternal() {
        return internal;
    }
}
