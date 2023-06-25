package com.blamejared.crafttweaker.natives.world.biome;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
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
        
        return Services.REGISTRY.biomes().getKey(internal);
    }
    
    @ZenCodeType.Getter("commandString")
    public static String getCommandString(Biome internal) {
        
        return "<biome:" + getRegistryName(internal) + ">";
    }
    
}
