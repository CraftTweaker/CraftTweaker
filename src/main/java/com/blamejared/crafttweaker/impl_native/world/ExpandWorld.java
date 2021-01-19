package com.blamejared.crafttweaker.impl_native.world;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.data.NBTConverter;
import com.blamejared.crafttweaker.impl.data.MapData;
import com.blamejared.crafttweaker.impl.util.MCDirection;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import org.openzen.zencode.java.ZenCodeType;

/**
 * Worlds represent a dimension within the game. They are used to interact with 
 * the blocks, mobs, and other variables within that dimension.
 * 
 * @docParam this world
 */
@ZenRegister
@Document("vanilla/api/world/MCWorld")
@NativeTypeRegistration(value = World.class, zenCodeName = "crafttweaker.api.world.MCWorld")
public class ExpandWorld {
    
    /**
     * Checks if the world is remote. This is always true on the rendering
     * thread.
     * 
     * @return Whether or not the world is remote.
     */
    @ZenCodeType.Getter("remote")
    @ZenCodeType.Method
    public static boolean isRemote(World internal) {
        return internal.isRemote;
    }
    
    /**
     * Checks if it is day time in the world. Different dimensions will have
     * different logic for how this is determined.
     * 
     * @return Whether or not it is day time in the world.
     */
    @ZenCodeType.Getter("dayTime")
    public static boolean isDayTime(World internal) {
        return internal.isDaytime();
    }
    
    /**
     * Checks if it is night time in the world. Different dimensions will have
     * different logic for how this is determined.
     * 
     * @return Whether or not it is night time in the world.
     */
    @ZenCodeType.Getter("nightTime")
    public static boolean isNightTime(World internal) {
        return internal.isNightTime();
    }
    
    /**
     * Gets the current game time in ticks.
     * 
     * @return The current game time in ticks.
     */
    @ZenCodeType.Getter("gameTime")
    public static long getTime(World internal) {
        return internal.getGameTime();
    }
    
    /**
     * Gets the current time of the day in ticks.
     * 
     * @return The current time of the Minecraft day in ticks.
     */
    @ZenCodeType.Getter("timeOfDay")
    public static long getDayTime(World internal) {
        return internal.getGameTime();
    }
    
    /**
     * Gets the height of the sea level.
     * 
     * @return The height of the sea level.
     */
    @ZenCodeType.Getter("seaLevel")
    public static int getSeaLevel(World internal) {
        return internal.getSeaLevel();
    }
    
    /**
     * Checks if it is raining.
     * 
     * @return Whether or not it is raining.
     */
    @ZenCodeType.Getter("raining")
    public static boolean isRaining(World internal) {
        return internal.isRaining();
    }
    
    /**
     * Checks if there is a thunder storm.
     * 
     * @return Whether or not there is a thunder storm.
     */
    @ZenCodeType.Getter("thundering")
    public static boolean isThundering(World internal) {
        return internal.isThundering();
    }
    
    /**
     * Checks if hardcore mode is enabled.
     * 
     * @return Whether or not hardcore mode is enabled.
     */
    @ZenCodeType.Getter("hardcore")
    public static boolean isHardcore(World internal) {
        return internal.getWorldInfo().isHardcore();
    }
    
    /**
     * Gets the difficulty setting for the world.
     * 
     * @return The difficulty setting for the world.
     */
    @ZenCodeType.Getter("difficulty")
    public static String getDifficulty(World internal) {
        return internal.getWorldInfo().getDifficulty().getTranslationKey();
    }
    
    /**
     * Checks if the difficulty of the world has been locked.
     * 
     * @return Whether or not the world difficulty has been locked.
     */
    @ZenCodeType.Getter("difficultyLocked")
    public static boolean isDifficultyLocked(World internal) {
        return internal.getWorldInfo().isDifficultyLocked();
    }
    
    /**
     * Gets the registry name of the dimension this world represents.
     * 
     * @return The registry name of the dimension the world represents.
     */
    @ZenCodeType.Getter("dimension")
    public static String getDimension(World internal) {
        return internal.getDimensionKey().getRegistryName().toString();
    }
    
    /**
     * Checks if it is raining at a specific position. This can never be true
     * if the position does not have direct line of sight to the sky. 
     * 
     * @param pos The position to check.
     * @return Whether or not it is raining at the current position.
     * @docParam pos new BlockPos(0, 1, 2)
     */
    @ZenCodeType.Method
    public static boolean isRainingAt(World internal, BlockPos pos) {
        return internal.isRainingAt(pos);
    }
    
    /**
     * Gets the highest strong (direct) redstone signal of any neighboring block.
     * 
     * @param pos The position to check.
     * @return The highest strong (direct) redstone signal of all directly neighboring blocks.
     * @docParam pos new BlockPos(0, 1, 2)
     */
    @ZenCodeType.Method
    public static int getStrongPower(World internal, BlockPos pos) {
        return internal.getStrongPower(pos);
    }
    
    /**
     * Gets the redstone signal strength available to a position from a given direction.
     * 
     * @param pos The position to check.
     * @param direction The direction to query.
     * @return The redstone signal strength available from that direction.
     * @docParam pos new BlockPos(0, 1, 2)
     * @docParam direction <direction:north>
     */
    @ZenCodeType.Method
    public static int getRedstonePower(World internal, BlockPos pos, MCDirection direction) {
        return internal.getRedstonePower(pos, direction.getInternal());
    }
    
    /**
     * Gets the highest redstone signal available to a position from any of it's neighbors.
     * @param pos The position to check.
     * @return The highest redstone signal available to the position.
     * @docParam pos new BlockPos(0, 1, 2)
     */
    @ZenCodeType.Method
    public static int getRedstonePowerFromNeighbors(World internal, BlockPos pos) {
        return internal.getRedstonePowerFromNeighbors(pos);
    }
    
    /**
     * Gets the tile entity data for a tile entity at a given position.
     * 
     * @param pos The position of the tile entity.
     * @return The data of the tile entity.
     * @docParam pos new BlockPos(0, 1, 2)
     */
    @ZenCodeType.Method
    public static IData getTileData(World internal, BlockPos pos) {
        CompoundNBT nbt = new CompoundNBT();
        TileEntity te = internal.getTileEntity(pos);
        return te == null ? new MapData() : NBTConverter.convert(te.write(nbt));
    }
    
    /**
     * Sets the block and it's state at a given position.
     * 
     * @param pos The position to set the block at.
     * @param state The new state of the block.
     * @return Whether or not the block was changed.
     * @docParam pos new BlockPos(0, 1, 2)
     * @docParam state <blockstate:minecraft:iron_block>
     */
    @ZenCodeType.Method
    public static boolean setBlockState(World internal, BlockPos pos, BlockState state) {
        return internal.setBlockState(pos, state);
    }
    
    /**
     * Gets the block state at a given position.
     * 
     * @param pos The position to look up.
     * @return The block state at the position.
     * @docParam pos new BlockPos(0, 1, 2)
     */
    @ZenCodeType.Method
    public static BlockState getBlockState(World internal, BlockPos pos) {
        return internal.getBlockState(pos);
    }
    
    /**
     * Checks if a given position is receiving a redstone signal.
     * 
     * @param pos The position to check.
     * @return Whether or not the position is receiving a redstone signal.
     * @docParam pos new BlockPos(0, 1, 2)
     */
    @ZenCodeType.Method
    public static boolean isBlockPowered(World internal, BlockPos pos) {
        return internal.isBlockPowered(pos);
    }
    
    /**
     * Gets the biome at a given position.
     * 
     * @param pos The position to look up.
     * @return The biome at the given position.
     * @docParam pos new BlockPos(0, 1, 2)
     */
    @ZenCodeType.Method
    public static Biome getBiome(World internal, BlockPos pos) {
        return internal.getBiome(pos);
    }
}
