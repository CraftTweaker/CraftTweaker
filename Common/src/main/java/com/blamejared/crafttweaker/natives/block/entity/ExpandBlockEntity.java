package com.blamejared.crafttweaker.natives.block.entity;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.data.MapData;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/block/entity/BlockEntity")
@NativeTypeRegistration(value = BlockEntity.class, zenCodeName = "crafttweaker.api.block.entity.BlockEntity")
public class ExpandBlockEntity {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("level")
    @ZenCodeType.Nullable
    public static Level getLevel(BlockEntity internal) {
        
        return internal.getLevel();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("hasLevel")
    public static boolean hasLevel(BlockEntity internal) {
        
        return internal.hasLevel();
    }
    
    @ZenCodeType.Getter("data")
    public static IData getData(BlockEntity internal) {
        
        return new MapData(internal.saveWithoutMetadata());
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Setter("data")
    public static void setData(BlockEntity internal, IData data) {
        
        if(data instanceof MapData map) {
            internal.load(map.getInternal());
        } else {
            throw new IllegalArgumentException("Invalid data provided, expected MapData, received: '%s'".formatted(data));
        }
    }
    
    @ZenCodeType.Method
    public static void updateData(BlockEntity internal, IData data) {
        
        if(data instanceof MapData) {
            MapData mergedData = (MapData) getData(internal).merge(data);
            internal.load(mergedData.getInternal());
        } else {
            throw new IllegalArgumentException("Invalid data provided, expected MapData, received: '%s'".formatted(data));
        }
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("blockPos")
    public static BlockPos getBlockPos(BlockEntity internal) {
        
        return internal.getBlockPos();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("blockState")
    public static BlockState getBlockState(BlockEntity internal) {
        
        return internal.getBlockState();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("registryName")
    public static ResourceLocation getRegistryName(BlockEntity internal) {
        
        return Registry.BLOCK_ENTITY_TYPE.getKey(internal.getType());
    }
    
}
