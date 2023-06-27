package com.blamejared.crafttweaker.natives.world;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.Biome;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/world/LevelReader")
@NativeTypeRegistration(value = LevelReader.class, zenCodeName = "crafttweaker.api.world.LevelReader")
public class ExpandLevelReader {
    
    
    @ZenCodeType.Method
    public static boolean hasChunk(LevelReader internal, int x, int z) {
        
        return internal.hasChunk(x, z);
    }
    
    @ZenCodeType.Getter("skyDarken")
    public static int getSkyDarken(LevelReader internal) {
        
        return internal.getSkyDarken();
    }
    
    /**
     * Gets the biome at a given position.
     *
     * @param pos The position to look up.
     *
     * @return The biome at the given position.
     *
     * @docParam pos new BlockPos(0, 1, 2)
     */
    @ZenCodeType.Method
    public static Biome getBiome(LevelReader internal, BlockPos pos) {
        
        return internal.getBiome(pos).value();
    }
    
    @ZenCodeType.Getter("isClientSide")
    public static boolean isClientSide(LevelReader internal) {
        
        return internal.isClientSide();
    }
    
    /**
     * Gets the height of the sea level.
     *
     * @return The height of the sea level.
     */
    @ZenCodeType.Getter("seaLevel")
    public static int getSeaLevel(LevelReader internal) {
        
        return internal.getSeaLevel();
    }
    
    /**
     * Checks if the block at a given position is empty.
     *
     * @param pos The position to look up.
     *
     * @return Whether the block is empty.
     *
     * @docParam pos new BlockPos(0, 1, 2)
     */
    @ZenCodeType.Method
    public static boolean isEmptyBlock(LevelReader internal, BlockPos pos) {
        
        return internal.isEmptyBlock(pos);
    }
    
    @ZenCodeType.Method
    public static boolean canSeeSkyFromBelowWater(LevelReader internal, BlockPos pos) {
        
        return internal.canSeeSkyFromBelowWater(pos);
    }
    
    @ZenCodeType.Method
    public static boolean isWaterAt(LevelReader internal, BlockPos pos) {
        
        return internal.isWaterAt(pos);
    }
    
}
