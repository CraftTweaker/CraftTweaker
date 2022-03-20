package com.blamejared.crafttweaker.natives.world.biome;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.mixin.common.access.world.biome.AccessBiome;
import com.blamejared.crafttweaker.platform.Services;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import com.blamejared.crafttweaker_annotations.annotations.TaggableElement;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/world/biome/Biome")
@NativeTypeRegistration(value = Biome.class, zenCodeName = "crafttweaker.api.world.biome.Biome")
@TaggableElement("minecraft:worldgen/biome")
public class ExpandBiome {
    
    @ZenCodeType.Getter("waterColor")
    public static int getWaterFloat(Biome internal) {
        
        return internal.getWaterColor();
    }
    
    @ZenCodeType.Getter("waterFogColor")
    public static int getWaterFogColor(Biome internal) {
        
        return internal.getWaterFogColor();
    }
    
    @ZenCodeType.Getter("downfall")
    public static float getDownFall(Biome internal) {
        
        return internal.getDownfall();
    }
    
    @ZenCodeType.Getter("isHumid")
    public static boolean isHumid(Biome internal) {
        
        return internal.isHumid();
    }
    
    @ZenCodeType.Getter("biomeCategory")
    public static String getBiomeCategory(Biome internal) {
        
        return ((AccessBiome) (Object) internal).crafttweaker$callGetBiomeCategory().getName().toLowerCase();
    }
    
    @ZenCodeType.Getter("doesRain")
    public static boolean doesRain(Biome internal) {
        
        return internal.getPrecipitation() == Biome.Precipitation.RAIN;
    }
    
    @ZenCodeType.Getter("doesSnow")
    public static boolean doesSnow(Biome internal) {
        
        return internal.getPrecipitation() == Biome.Precipitation.SNOW;
    }
    
    @ZenCodeType.Getter("rainType")
    public static String getRainType(Biome internal) {
        
        return internal.getPrecipitation().getName();
    }
    
    @ZenCodeType.Method
    public static boolean shouldFreeze(Biome internal, Level world, BlockPos pos) {
        
        return internal.shouldFreeze(world, pos);
    }
    
    @ZenCodeType.Method
    public static boolean shouldFreeze(Biome internal, Level world, BlockPos pos, boolean mustBeAtEdge) {
        
        return internal.shouldFreeze(world, pos, mustBeAtEdge);
    }
    
    @ZenCodeType.Method
    public static boolean shouldSnow(Biome internal, Level world, BlockPos pos) {
        
        return internal.shouldSnow(world, pos);
    }
    
    @ZenCodeType.Getter("registryName")
    public static ResourceLocation getRegistryName(Biome internal) {
        
        return Services.REGISTRY.getRegistryKey(internal);
    }
    
    @ZenCodeType.Getter("commandString")
    public static String getCommandString(Biome internal) {
        
        return "<biome:" + getRegistryName(internal) + ">";
    }
    
}
