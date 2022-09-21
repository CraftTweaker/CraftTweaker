package com.blamejared.crafttweaker.natives.entity.type.misc;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.mixin.common.access.entity.AccessFallingBlockEntity;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.openzen.zencode.java.ZenCodeType;

/**
 * @docParam this FallingBlockEntity.fall(level, new BlockPos(1, 2, 3), <blockstate:minecraft:dirt>)
 */
@ZenRegister
@Document("vanilla/api/entity/type/misc/FallingBlockEntity")
@NativeTypeRegistration(value = FallingBlockEntity.class, zenCodeName = "crafttweaker.api.entity.type.misc.FallingBlockEntity")
public class ExpandFallingBlockEntity {
    
    /**
     * Spawns a new falling block entity at the given position with the given blockstate.
     *
     * @param level The level to spawn the entity in.
     * @param pos   The position to spawn the entity at.
     * @param state The blockstate of the falling block.
     *
     * @return The entity that was spawned.
     *
     * @docParam level level
     * @docParam pos new BlockPos(1, 2, 3)
     * @docParam state <blockstate:minecraft:dirt>
     */
    @ZenCodeType.StaticExpansionMethod
    public static FallingBlockEntity fall(Level level, BlockPos pos, BlockState state) {
        
        return FallingBlockEntity.fall(level, pos, state);
    }
    
    /**
     * Sets the position that this entity was spawned at.
     *
     * <p>this is mainly used for the rendering of the entity</p>
     *
     * @param pos the position that the entity was spawned at.
     *
     * @docParam pos new BlockPos(1, 2, 3)
     */
    @ZenCodeType.Method
    public static void setStartPos(FallingBlockEntity internal, BlockPos pos) {
        
        internal.setStartPos(pos);
    }
    
    /**
     * Gets the position that this entity was spawned at.
     *
     * @return The position that the entity was spawned at.
     */
    @ZenCodeType.Method
    public static BlockPos getStartPos(FallingBlockEntity internal) {
        
        return internal.getStartPos();
    }
    
    /**
     * Triggers the given {@link net.minecraft.world.level.block.Fallable}'s {@code onBrokenAfterFall} method using this entity.
     *
     * @param fallableBlock The fallable block.
     * @param position      The position that the block fell at.
     *
     * @docParam fallableBlock <block:minecraft:sand>
     * @docParam position new BlockPos(1, 2, 3)
     */
    @ZenCodeType.Method
    public static void callOnBrokenAfterFall(FallingBlockEntity internal, Block fallableBlock, BlockPos position) {
        
        internal.callOnBrokenAfterFall(fallableBlock, position);
    }
    
    /**
     * Sets that entities should be hurt by this block, as well as setting how much damage is done.
     *
     * @param damagePerDistance The damage done per distance fell.
     * @param maxDamage         The max amount of damage that can be caused by this entity.
     *
     * @docParam damagePerDistance 0.5
     * @docParam maxDamage 5
     */
    @ZenCodeType.Method
    public static void setHurtsEntities(FallingBlockEntity internal, float damagePerDistance, int maxDamage) {
        
        internal.setHurtsEntities(damagePerDistance, maxDamage);
    }
    
    /**
     * Gets the BlockState of this falling entity.
     *
     * @return The BlockState of this falling entity
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("blockstate")
    public static BlockState getBlockState(FallingBlockEntity internal) {
        
        return internal.getBlockState();
    }
    
    /**
     * Sets the BlockState of this falling entity.
     */
    @ZenCodeType.Method
    @ZenCodeType.Setter("blockstate")
    public static void setBlockState(FallingBlockEntity internal, BlockState state) {
        
        ((AccessFallingBlockEntity) internal).crafttweaker$setBlockState(state);
    }
    
}
