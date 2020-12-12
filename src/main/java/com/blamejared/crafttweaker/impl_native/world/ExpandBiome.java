package com.blamejared.crafttweaker.impl_native.world;

import com.blamejared.crafttweaker.api.annotations.NativeExpansion;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.impl.util.MCBlockPos;
import net.minecraft.world.biome.Biome;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@NativeExpansion(Biome.class)
public class ExpandBiome {
    
    
    @ZenCodeType.Getter("waterColor")
    public static int getWaterFloat(Biome internal) {
        return internal.getWaterColor();
    }
    
    @ZenCodeType.Getter("waterFogColor")
    public static int getWaterFogColor(Biome internal) {
        return internal.getWaterFogColor();
    }
    
    //    @ZenCodeType.Getter("defaultTemperature")
    //    public static float getDefaultTemperature(Biome internal) {
    //        return internal.getDefaultTemperature();
    //    }
    
    //    @ZenCodeType.Getter("translationKey")
    //    public static String getTranslationKey(Biome internal) {
    //        return internal.getTranslationKey();
    //    }
    //
    @ZenCodeType.Getter("scale")
    public static float getScale(Biome internal) {
        return internal.getScale();
    }
    
    @ZenCodeType.Getter("downfall")
    public static float getDownFall(Biome internal) {
        return internal.getDownfall();
    }
    
    @ZenCodeType.Getter("depth")
    public static float getDepth(Biome internal) {
        return internal.getDepth();
    }
    
    //    @ZenCodeType.Getter("spawningChange")
    //    public static float getSpawningChance(Biome internal) {
    //        return internal.getSpawningChance();
    //    }
    
    @ZenCodeType.Getter("isHighHumidity")
    public static boolean isHighHumidity(Biome internal) {
        return internal.isHighHumidity();
    }
    
    //    @ZenCodeType.Getter("isMutation")
    //    public static boolean isMutation(Biome internal) {
    //        return internal.isMutation();
    //    }
    
    @ZenCodeType.Getter("category")
    public static String getCategory(Biome internal) {
        return internal.getCategory().getName().toLowerCase();
    }
    
    //    @ZenCodeType.Getter("isTempOcean")
    //    public static boolean isTempOcean(Biome internal) {
    //        return internal.getTempCategory() == Biome.TempCategory.OCEAN;
    //    }
    //
    //    @ZenCodeType.Getter("isTempCold")
    //    public static boolean isTempCold(Biome internal) {
    //        return internal.getTempCategory() == Biome.TempCategory.COLD;
    //    }
    //
    //    @ZenCodeType.Getter("isTempMedium")
    //    public static boolean isTempMedium(Biome internal) {
    //        return internal.getTempCategory() == Biome.TempCategory.MEDIUM;
    //    }
    //
    //    @ZenCodeType.Getter("isTempWarm")
    //    public static boolean isTempWarm(Biome internal) {
    //        return internal.getTempCategory() == Biome.TempCategory.WARM;
    //    }
    
    @ZenCodeType.Getter("doesRain")
    public static boolean doesRain(Biome internal) {
        return internal.getPrecipitation() == Biome.RainType.RAIN;
    }
    
    @ZenCodeType.Getter("doesSnow")
    public static boolean doesSnow(Biome internal) {
        return internal.getPrecipitation() == Biome.RainType.SNOW;
    }
    
    @ZenCodeType.Getter("rainType")
    public static String getRainType(Biome internal) {
        return internal.getPrecipitation().getName();
    }
    
    //    @ZenCodeType.Getter("parent")
    //    public static String getParent(Biome internal) {
    //        return internal.getParent();
    //    }
    //
    //    @ZenCodeType.Method
    //    public static String getTempCategory(Biome internal) {
    //        return internal.getTempCategory().getName().toLowerCase();
    //    }
    
    @ZenCodeType.Method
    public static float getTemperature(Biome internal, MCBlockPos pos) {
        return internal.getTemperature(pos.getInternal());
    }
    
    //    @ZenCodeType.Method
    //    public List<BiomeSpawnEntry> getSpawns(ExpandEntityClassification classification) {
    //        return internal.getSpawns(classification.getInternal()).stream().map(BiomeSpawnEntry::new).collect(Collectors.toList());
    //    }
    
    @ZenCodeType.Method
    public static boolean doesWaterFreeze(Biome internal, ExpandWorld world, MCBlockPos pos) {
        return internal.doesWaterFreeze(world.getInternal(), pos.getInternal());
    }
    
    @ZenCodeType.Method
    public static boolean doesWaterFreeze(Biome internal, ExpandWorld world, MCBlockPos pos, boolean mustBeAtEdge) {
        return internal.doesWaterFreeze(world.getInternal(), pos.getInternal(), mustBeAtEdge);
    }
    
    @ZenCodeType.Method
    public static boolean doesSnowFreeze(Biome internal, ExpandWorld world, MCBlockPos pos) {
        return internal.doesSnowGenerate(world.getInternal(), pos.getInternal());
    }
}
