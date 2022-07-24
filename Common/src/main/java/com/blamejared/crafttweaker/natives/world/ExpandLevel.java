package com.blamejared.crafttweaker.natives.world;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.data.MapData;
import com.blamejared.crafttweaker.api.data.base.converter.tag.TagToDataConverter;
import com.blamejared.crafttweaker.api.util.sequence.SequenceBuilder;
import com.blamejared.crafttweaker.api.util.sequence.SequenceType;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import com.google.common.base.Suppliers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.openzen.zencode.java.ZenCodeType;

import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

@ZenRegister
@Document("vanilla/api/world/Level")
@NativeTypeRegistration(value = Level.class, zenCodeName = "crafttweaker.api.world.Level")
public class ExpandLevel {
    
    /**
     * Creates a new {@link SequenceBuilder} for this level.
     *
     * <p>{@link SequenceBuilder}'s let you compose scripted events such as waiting 5 ticks, then setting the weather to rain.</p>
     *
     * @return A new {@link SequenceBuilder} for this level.
     *
     * @docParam data {version: "1.0.0"}
     */
    @ZenCodeType.Method
    public static SequenceBuilder<Level, MapData> sequence(Level internal, @ZenCodeType.Optional("new crafttweaker.api.data.MapData()") MapData data) {
        
        return sequence(internal, MapData.class, data);
    }
    
    /**
     * Creates a new {@link SequenceBuilder} for this level.
     *
     * <p>{@link SequenceBuilder}'s let you compose scripted events such as waiting 5 ticks, then setting the weather to rain.</p>
     *
     * @return A new {@link SequenceBuilder} for this level.
     *
     * @docParam data {version: "1.0.0"}
     * @docParam <T> crafttweaker.api.data.MapData
     */
    @ZenCodeType.Method
    public static <T> SequenceBuilder<Level, T> sequence(Level internal, Class<T> dataClass, T data) {
        
        return new SequenceBuilder<>(internal.isClientSide ? SequenceType.CLIENT_THREAD_LEVEL : SequenceType.SERVER_THREAD_LEVEL, Suppliers.memoize(() -> internal), data);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isClientSide")
    public static boolean isClientSide(Level internal) {
        
        return internal.isClientSide();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isDay")
    public static boolean isDay(Level internal) {
        
        return internal.isDay();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isNight")
    public static boolean isNight(Level internal) {
        
        return internal.isNight();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("gameTime")
    public static long getGametime(Level internal) {
        
        return internal.getGameTime();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("daytime")
    public static long getDayTime(Level internal) {
        
        return internal.getDayTime();
    }
    
    
    /**
     * Gets the height of the sea level.
     *
     * @return The height of the sea level.
     */
    @ZenCodeType.Getter("seaLevel")
    public static int getSeaLevel(Level internal) {
        
        return internal.getSeaLevel();
    }
    
    /**
     * Checks if it is raining.
     *
     * @return Whether it is raining.
     */
    @ZenCodeType.Getter("raining")
    public static boolean isRaining(Level internal) {
        
        return internal.isRaining();
    }
    
    /**
     * Sets the current rain level.
     *
     * @param level The new rain level between 0 and 1
     *
     * @docParam level 0.5
     */
    @ZenCodeType.Method
    @ZenCodeType.Setter("rainLevel")
    public static void setRainingLevel(Level internal, float level) {
        
        internal.setRainLevel(level);
    }
    
    /**
     * Checks if there is a thunder storm.
     *
     * @return Whether there is a thunder storm.
     */
    @ZenCodeType.Getter("thundering")
    public static boolean isThundering(Level internal) {
        
        return internal.isThundering();
    }
    
    /**
     * Checks if hardcore mode is enabled.
     *
     * @return Whether hardcore mode is enabled.
     */
    @ZenCodeType.Getter("hardcore")
    public static boolean isHardcore(Level internal) {
        
        return internal.getLevelData().isHardcore();
    }
    
    /**
     * Gets the difficulty setting for the world.
     *
     * @return The difficulty setting for the world.
     */
    @ZenCodeType.Getter("difficulty")
    public static String getDifficulty(Level internal) {
        
        return internal.getLevelData().getDifficulty().getKey();
    }
    
    /**
     * Checks if the difficulty of the world has been locked.
     *
     * @return Whether the world difficulty has been locked.
     */
    @ZenCodeType.Getter("difficultyLocked")
    public static boolean isDifficultyLocked(Level internal) {
        
        return internal.getLevelData().isDifficultyLocked();
    }
    
    /**
     * Gets the registry name of the dimension this world represents.
     *
     * @return The registry name of the dimension the world represents.
     */
    @ZenCodeType.Getter("dimension")
    public static ResourceLocation getDimension(Level internal) {
        
        return internal.dimension().location();
    }
    
    /**
     * Checks if it is raining at a specific position. This can never be true
     * if the position does not have direct line of sight to the sky.
     *
     * @param pos The position to check.
     *
     * @return Whether it is raining at the current position.
     *
     * @docParam pos new BlockPos(0, 1, 2)
     */
    @ZenCodeType.Method
    public static boolean isRainingAt(Level internal, BlockPos pos) {
        
        return internal.isRainingAt(pos);
    }
    
    /**
     * Gets the highest strong (direct) redstone signal of any neighboring block.
     *
     * @param pos The position to check.
     *
     * @return The highest strong (direct) redstone signal of all directly neighboring blocks.
     *
     * @docParam pos new BlockPos(0, 1, 2)
     */
    @ZenCodeType.Method
    public static int getDirectSignalTo(Level internal, BlockPos pos) {
        
        return internal.getDirectSignalTo(pos);
    }
    
    /**
     * Gets the redstone signal strength available to a position from a given direction.
     *
     * @param pos       The position to check.
     * @param direction The direction to query.
     *
     * @return The redstone signal strength available from that direction.
     *
     * @docParam pos new BlockPos(0, 1, 2)
     * @docParam direction <direction:north>
     */
    @ZenCodeType.Method
    public static int getSignal(Level internal, BlockPos pos, Direction direction) {
        
        return internal.getSignal(pos, direction);
    }
    
    /**
     * Gets the highest redstone signal available to a position from any of it's neighbors.
     *
     * @param pos The position to check.
     *
     * @return The highest redstone signal available to the position.
     *
     * @docParam pos new BlockPos(0, 1, 2)
     */
    @ZenCodeType.Method
    public static int getBestNeighborSignal(Level internal, BlockPos pos) {
        
        return internal.getBestNeighborSignal(pos);
    }
    
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
    public static MapData getBlockEntityData(Level internal, BlockPos pos) {
        
        BlockEntity te = internal.getBlockEntity(pos);
        return te == null ? new MapData() : TagToDataConverter.convertCompound(te.saveWithoutMetadata());
    }
    
    /**
     * Sets the block and its state at a given position.
     *
     * @param pos   The position to set the block at.
     * @param state The new state of the block.
     *
     * @return Whether the block was changed.
     *
     * @docParam pos new BlockPos(0, 1, 2)
     * @docParam state <blockstate:minecraft:iron_block>
     */
    @ZenCodeType.Method
    public static boolean setBlockAndUpdate(Level internal, BlockPos pos, BlockState state) {
        
        return internal.setBlockAndUpdate(pos, state);
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
    public static BlockState getBlockState(Level internal, BlockPos pos) {
        
        return internal.getBlockState(pos);
    }
    
    /**
     * Checks if a given position is receiving a redstone signal.
     *
     * @param pos The position to check.
     *
     * @return Whether the position is receiving a redstone signal.
     *
     * @docParam pos new BlockPos(0, 1, 2)
     */
    @ZenCodeType.Method
    public static boolean hasNeighborSignal(Level internal, BlockPos pos) {
        
        return internal.hasNeighborSignal(pos);
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
    public static Biome getBiome(Level internal, BlockPos pos) {
        
        return internal.getBiome(pos).value();
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
    public static boolean destroyBlock(Level internal, BlockPos pos, boolean doDrops) {
        
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
    public static boolean destroyBlock(Level internal, BlockPos pos, boolean doDrops, @ZenCodeType.Nullable Entity breaker) {
        
        return internal.destroyBlock(pos, doDrops, breaker);
    }
    
    /**
     * Triggers a predetermined event on the client. Using this on a server
     * or integrated server will send the event to all nearby players.
     *
     * @param eventId The ID of the event to play.
     * @param pos     The position of the event.
     * @param data    Four bytes of additional data encoded as an integer. This
     *                is generally unused.
     *
     * @docParam eventId 2005
     * @docParam pos new BlockPos(0, 1, 2)
     * @docParam data 0
     */
    @ZenCodeType.Method
    public static void globalLevelEvent(Level internal, int eventId, BlockPos pos, int data) {
        
        internal.globalLevelEvent(eventId, pos, data);
    }
    
    /**
     * Triggers a predetermined event on the client. Using this on a server
     * or integrated server will send the event to all nearby players.
     *
     * @param excluded An excluded player who will not receive the event.
     * @param eventId  The ID of the event to play.
     * @param pos      The position of the event.
     * @param data     Four bytes of additional data encoded as an integer. This
     *                 is generally unused.
     *
     * @docParam excluded player
     * @docParam eventId 2005
     * @docParam pos new BlockPos(0, 1, 2)
     * @docParam data 0
     */
    @ZenCodeType.Method
    public static void levelEvent(Level internal, @ZenCodeType.Nullable Player excluded, int eventId, BlockPos pos, int data) {
        
        internal.levelEvent(excluded, eventId, pos, data);
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
    public static boolean isEmptyBlock(Level internal, BlockPos pos) {
        
        return internal.isEmptyBlock(pos);
    }
    
    /**
     * Checks if the block at a given position is in a loaded chunk.
     *
     * @param pos The position to look up.
     *
     * @return Whether the position is in a loaded chunk.
     *
     * @docParam pos new BlockPos(0, 1, 2)
     */
    @ZenCodeType.Method
    public static boolean isLoaded(Level internal, BlockPos pos) {
        
        return internal.isLoaded(pos);
    }
    
    /**
     * Gets all entities in given area.
     *
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
    public static <T extends Entity> List<T> getEntitiesOfClass(Level internal, Class<T> typeOfT, double x1, double y1, double z1, double x2, double y2, double z2) {
        
        return internal.getEntitiesOfClass(typeOfT, new AABB(x1, y1, z1, x2, y2, z2));
    }
    
    /**
     * Gets all entities in given area, but the arguments are block poses.
     * If `pos2` is omitted, it will use `pos1.add(1, 1, 1)`
     *
     * @return all entities in given area
     *
     * @docParam pos1 new BlockPos(0, 1, 2)
     * @docParam pos2 new BlockPos(3, 4, 5)
     */
    @ZenCodeType.Method
    public static <T extends Entity> List<T> getEntitiesInArea(Level internal, Class<T> typeOfT, BlockPos pos1, @ZenCodeType.Optional BlockPos pos2) {
        
        AABB aabb = new AABB(pos1);
        if(pos2 != null) {
            aabb = new AABB(pos1, pos2);
        }
        return internal.getEntitiesOfClass(typeOfT, aabb);
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
    public static List<Entity> getEntities(Level internal, @ZenCodeType.Nullable Entity excludingEntity, double x1, double y1, double z1, double x2, double y2, double z2, Predicate<Entity> predicate) {
        
        return internal.getEntities(excludingEntity, new AABB(x1, y1, z1, x2, y2, z2), predicate);
    }
    
    /**
     * @docParam excludingEntity entity
     * @docParam predicate (entityIn) => entityIn.isInWater()
     * @docParam pos1 new BlockPos(0, 1, 2)
     * @docParam pos2 new BlockPos(3, 4, 5)
     */
    @ZenCodeType.Method
    public static List<Entity> getEntitiesInAreaExcluding(Level internal, @ZenCodeType.Nullable Entity excludingEntity, Predicate<Entity> predicate, BlockPos pos1, @ZenCodeType.Optional BlockPos pos2) {
        
        AABB aabb = new AABB(pos1);
        if(pos2 != null) {
            aabb = new AABB(pos1, pos2);
        }
        
        return internal.getEntities(excludingEntity, aabb, predicate);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Nullable
    public static BlockEntity getBlockEntity(Level internal, BlockPos pos) {
        
        return internal.getBlockEntity(pos);
    }
    
    /**
     * add an entity to the world, return if the entity is added successfully.
     */
    @ZenCodeType.Method
    public static boolean addFreshEntity(Level internal, Entity entity) {
        
        return internal.addFreshEntity(entity);
    }
    
    @ZenCodeType.Getter("random")
    public static Random getRandom(Level internal) {
        
        return internal.random;
    }
    
    /**
     * Creates a ray trace from one vector to the other vector, which will stop at a block or a fluid.
     *
     * @param startVec  a vector which describes the starting point
     * @param endVec    a vector which describes the direction and length we are searching in
     * @param blockMode the type of block that the ray trace would stop at.
     * @param fluidMode the type of fluid that the ray trace would stop at.
     * @param entity    the entity for selection context
     *
     * @return a {@link BlockHitResult} holding the result, the position and facing the ray stops.
     *
     * @docParam startVec new Vec3(0.0, 0.0, 0.0)
     * @docParam endVec new Vec3(1.1, 4.5, 1.4)
     * @docParam blockMode RayTraceBlockMode.OUTLINE
     * @docParam fluidMode RayTraceFluidMode.NONE
     * @docParam entity entity
     */
    @ZenCodeType.Method
    public static BlockHitResult rayTraceBlocks(Level internal, Vec3 startVec, Vec3 endVec, ClipContext.Block blockMode, ClipContext.Fluid fluidMode, @ZenCodeType.Optional Entity entity) {
        
        return internal.clip(new ClipContext(startVec, endVec, blockMode, fluidMode, entity));
    }
    
    
}
