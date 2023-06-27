package com.blamejared.crafttweaker.natives.world;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.data.MapData;
import com.blamejared.crafttweaker.api.data.converter.tag.TagToDataConverter;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/world/BlockGetter")
@NativeTypeRegistration(value = BlockGetter.class, zenCodeName = "crafttweaker.api.world.BlockGetter")
public class ExpandBlockGetter {
    
    /**
     * Gets the tile entity data for a tile entity at a given position.
     *
     * @param pos The position of the tile entity.
     *
     * @return The data of the tile entity.
     *
     * @docParam pos new BlockPos(0, 1, 2)
     */
    @ZenCodeType.Method
    public static IData getBlockEntityData(BlockGetter internal, BlockPos pos) {
        
        BlockEntity te = internal.getBlockEntity(pos);
        return te == null ? new MapData() : TagToDataConverter.convert(te.saveWithoutMetadata());
    }
    
    /**
     * Gets the block state at a given position.
     *
     * @param pos The position to look up.
     *
     * @return The block state at the position.
     *
     * @docParam pos new BlockPos(0, 1, 2)
     */
    @ZenCodeType.Method
    public static BlockState getBlockState(BlockGetter internal, BlockPos pos) {
        
        return internal.getBlockState(pos);
    }
    
    @ZenCodeType.Method
    public static int getLightEmission(BlockGetter internal, BlockPos pos) {
        
        return internal.getLightEmission(pos);
    }
    
    @ZenCodeType.Getter("maxLightLevel")
    public static int getMaxLightLevel(BlockGetter internal) {
        
        return internal.getMaxLightLevel();
    }
    
    @ZenCodeType.Method
    public static double getBlockFloorHeight(BlockGetter internal, BlockPos pos) {
        
        return internal.getBlockFloorHeight(pos);
    }
    
}
