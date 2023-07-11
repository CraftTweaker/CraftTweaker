package com.blamejared.crafttweaker.natives.world;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelWriter;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/world/LevelWriter")
@NativeTypeRegistration(value = LevelWriter.class, zenCodeName = "crafttweaker.api.world.LevelWriter")
public class ExpandLevelWriter {
    
    
    public static boolean setBlock(LevelWriter internal, BlockPos pos, BlockState state, int flags, int recursionLeft) {
        
        return internal.setBlock(pos, state, flags, recursionLeft);
    }
    
    public static boolean setBlock(LevelWriter internal, BlockPos pos, BlockState state, int flags) {
        
        return internal.setBlock(pos, state, flags);
    }
    
    public static boolean removeBlock(LevelWriter internal, BlockPos pos, boolean isMoving) {
        
        return internal.removeBlock(pos, isMoving);
    }
    
    /**
     * Destroys a block within the world.
     *
     * @param pos     The position of the block.
     * @param doDrops Whether the block drops itself and it's loot.
     *
     * @return Whether the block was changed.
     *
     * @docParam pos new BlockPos(0, 1, 2)
     * @docParam doDrops true
     */
    @ZenCodeType.Method
    public static boolean destroyBlock(LevelWriter internal, BlockPos pos, boolean doDrops) {
        
        return internal.destroyBlock(pos, doDrops);
    }
    
    /**
     * Destroys a block within the world.
     *
     * @param pos     The position of the block.
     * @param doDrops Whether the block drops itself and it's loot.
     * @param breaker The entity to break the block.
     *
     * @return Whether the block was changed.
     *
     * @docParam pos new BlockPos(0, 1, 2)
     * @docParam doDrops true
     * @docParam breaker player
     */
    @ZenCodeType.Method
    public static boolean destroyBlock(LevelWriter internal, BlockPos pos, boolean doDrops, @ZenCodeType.Nullable Entity breaker) {
        
        return internal.destroyBlock(pos, doDrops, breaker);
    }
    
    public static boolean destroyBlock(LevelWriter internal, BlockPos pos, boolean dropBlock, @Nullable Entity entity, int recursionLeft) {
        
        return internal.destroyBlock(pos, dropBlock, entity, recursionLeft);
    }
    
    /**
     * add an entity to the world, return if the entity is added successfully.
     */
    @ZenCodeType.Method
    public static boolean addFreshEntity(LevelWriter internal, Entity entity) {
        
        return internal.addFreshEntity(entity);
    }
    
}
