package com.blamejared.crafttweaker.impl_native.world;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.data.NBTConverter;
import com.blamejared.crafttweaker.impl.data.MapData;
import com.blamejared.crafttweaker.impl.util.MCDirection;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.server.ServerWorld;
import org.openzen.zencode.java.ZenCodeType;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

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
    
    @ZenCodeType.Method
    @ZenCodeType.Caster(implicit = false)
    public static ServerWorld asServerWorld(World internal) {
        return (ServerWorld) internal;
    }
    
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
        return internal.getDimensionKey().getLocation().toString();
    }
    
    /**
     * Checks if it is raining at a specific position. This can never be true
     * if the position does not have direct line of sight to the sky. 
     * 
     * @param pos The position to check.
     * @return Whether or not it is raining at the current position.
     * 
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
     * 
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
     * 
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
     * 
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
     * 
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
     * 
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
     * 
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
     * 
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
     * 
     * @docParam pos new BlockPos(0, 1, 2)
     */
    @ZenCodeType.Method
    public static Biome getBiome(World internal, BlockPos pos) {
        return internal.getBiome(pos);
    }
    
    /**
     * Destroys a block within the world.
     * 
     * @param pos The position of the block.
     * @param doDrops Whether or not the block drops itself and it's loot.
     * @return Whether or not the block was changed.
     * 
     * @docParam pos new BlockPos(0, 1, 2)
     * @docParam doDrops true
     */
    @ZenCodeType.Method
    public static boolean destroyBlock(World internal, BlockPos pos, boolean doDrops) { 
    	return internal.destroyBlock(pos, doDrops);
    }
    
    /**
     * Destroys a block within the world.
     * 
     * @param pos The position of the block.
     * @param doDrops Whether or not the block drops itself and it's loot.
     * @param breaker The entity to break the block.
     * @return Whether or not the block was changed.
     * 
     * @docParam pos new BlockPos(0, 1, 2)
     * @docParam doDrops true
     * @docParam breaker player
     */
    @ZenCodeType.Method
    public static boolean destroyBlock(World internal, BlockPos pos, boolean doDrops, @Nullable Entity breaker) { 
    	return internal.destroyBlock(pos, doDrops, breaker);
    }

    /**
     * Triggers a predetermined event on the client. Using this on a server
     * or integrated server will send the event to all nearby players.
     * 
     * @param eventId The ID of the event to play.
     * @param pos The position of the event.
     * @param data Four bytes of additional data encoded as an integer. This
     *        is generally unused.
     *        
     * @docParam eventId 2005
     * @docParam pos new BlockPos(0, 1, 2)
     * @docParam data 0
     */
    @ZenCodeType.Method
    public static void playEvent(World internal, int eventId, BlockPos pos, int data) {
    	internal.playEvent(eventId, pos, data);
    }
    
    /**
     * Triggers a predetermined event on the client. Using this on a server
     * or integrated server will send the event to all nearby players.
     * 
     * @param excluded An excluded player who will not receive the event.
     * @param eventId The ID of the event to play.
     * @param pos The position of the event.
     * @param data Four bytes of additional data encoded as an integer. This
     *        is generally unused.
     *        
     * @docParam excluded player
     * @docParam eventId 2005
     * @docParam pos new BlockPos(0, 1, 2)
     * @docParam data 0
     */
    @ZenCodeType.Method
    public static void playEvent(World internal, @Nullable PlayerEntity excluded, int eventId, BlockPos pos, int data) {
    	internal.playEvent(excluded, eventId, pos, data);
    }
    
    /**
     * Checks if the block at a given position is air.
     * 
     * @param pos The position to look up.
     * @return Whether or not the block is air.
     * 
     * @docParam pos new BlockPos(0, 1, 2)
     */
    @ZenCodeType.Method
    public static boolean isAir(World internal, BlockPos pos) {
    	return internal.isAirBlock(pos);
    }
    
    /**
     * Checks if the block at a given position is in a loaded chunk.
     * 
     * @param pos The position to look up.
     * @return Whether or not the position is in a loaded chunk.
     * 
     * @docParam pos new BlockPos(0, 1, 2)
     */
    @ZenCodeType.Method
    public static boolean isLoaded(World internal, BlockPos pos) {
    	return internal.isBlockLoaded(pos);
    }
    
    /**
     * Gets all entities in given area.
     * @return all entities in given area.
     *
     * @docParam x1 1.0
     * @docParam y1 1.0
     * @docParam z1 1.0
     * @docParam x2 11.4
     * @docParam y2 11.4
     * @docParam z2 11.4
     */
    @ZenCodeType.Method
    public static List<Entity> getEntitiesInArea(World internal, double x1, double y1, double z1, double x2, double y2, double z2) {
        return internal.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(x1, y1, z1, x2, y2, z2));
    }
    
    /**
     * Gets all entities in given area, but the arguments are block poses.
     * If `pos2` is omitted, it will use `pos1.add(1, 1, 1)`
     * @return all entities in given area
     *
     * @docParam pos1 new BlockPos(0, 1, 2)
     * @docParam pos2 new BlockPos(3, 4, 5)
     */
    @ZenCodeType.Method
    public static List<Entity> getEntitiesInArea(World internal, BlockPos pos1, @ZenCodeType.Optional BlockPos pos2) {
    
        if(pos2 == null) {
            return internal.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(pos1));
        } else {
            return internal.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(pos1, pos2));
        }
    }
    
    /**
     * Gets all entities in given area, excluding the one passed into it.
     *
     * @param predicate the entity filter
     *
     * @docParam excludingEntity entity
     * @docParam x1 1.0
     * @docParam y1 1.0
     * @docParam z1 1.0
     * @docParam x2 11.4
     * @docParam y2 11.4
     * @docParam z2 11.4
     * @docParam predicate (entityIn) => entityIn.isInWater()
     */
    @ZenCodeType.Method
    public static List<Entity> getEntitiesInAreaExcluding(World internal, @ZenCodeType.Nullable Entity excludingEntity, double x1, double y1, double z1, double x2, double y2, double z2, Predicate<Entity> predicate) {
        
        return internal.getEntitiesInAABBexcluding(excludingEntity, new AxisAlignedBB(x1, y1, z1, x2, y2, z2), predicate);
    }
    
    /**
     * @docParam excludingEntity entity
     * @docParam predicate (entityIn) => entityIn.isInWater()
     * @docParam pos1 new BlockPos(0, 1, 2)
     * @docParam pos2 new BlockPos(3, 4, 5)
     */
    @ZenCodeType.Method
    public static List<Entity> getEntitiesInAreaExcluding(World internal, @ZenCodeType.Nullable Entity excludingEntity, Predicate<Entity> predicate, BlockPos pos1, @ZenCodeType.Optional BlockPos pos2) {
        
        if(pos2 == null) {
            return internal.getEntitiesInAABBexcluding(excludingEntity, new AxisAlignedBB(pos1), predicate);
        } else {
            return internal.getEntitiesInAABBexcluding(excludingEntity, new AxisAlignedBB(pos1, pos2), predicate);
        }
    }

    @ZenCodeType.Method
    @ZenCodeType.Nullable
    public static TileEntity getTileEntity(World internal, BlockPos pos) {
        return internal.getTileEntity(pos);
    }
    
    /**
     * add an entity to the world, return if the entity is added successfully.
     */
    @ZenCodeType.Method
    public static boolean addEntity(World internal, Entity entity) {
        
        return internal.addEntity(entity);
    }

    @ZenCodeType.Getter("random")
    public static Random getRandom(World internal) {
        return internal.rand;
    }
    
}