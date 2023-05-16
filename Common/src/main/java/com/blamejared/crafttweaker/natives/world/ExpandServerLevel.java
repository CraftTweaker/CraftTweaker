package com.blamejared.crafttweaker.natives.world;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.level.CraftTweakerSavedData;
import com.blamejared.crafttweaker.api.level.CraftTweakerSavedDataHolder;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import org.openzen.zencode.java.ZenCodeType;

import java.util.List;
import java.util.function.Predicate;

@ZenRegister
@Document("vanilla/api/world/ServerLevel")
@NativeTypeRegistration(value = ServerLevel.class, zenCodeName = "crafttweaker.api.world.ServerLevel")
public class ExpandServerLevel {
    
    /**
     * Gets the custom data that is saved/loaded to/from disk when the level is saved/loaded to/from disk.
     *
     * <p>This lets you store data on this specific level, so data stored in the Overworld will not be accessible from the Nether.</p>
     *
     * @return The custom data for this world.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("customData")
    public static CraftTweakerSavedData getCustomData(ServerLevel internal) {
        
        return ((CraftTweakerSavedDataHolder) internal).crafttweaker$getSavedData();
    }
    
    /**
     * Sets the time of the Minecraft day.
     *
     * @param time The new time of day. Should be between 0 and 24000.
     *
     * @docParam time 6000
     */
    @ZenCodeType.Setter("dayTime")
    public static void setDayTime(ServerLevel internal, long time) {
        
        internal.setDayTime(time);
    }
    
    /**
     * Sets the time of the Minecraft day to day. This is like using the
     * "time set day" command or setting the time to 1000.
     */
    @ZenCodeType.Method
    public static void setTimeToDay(ServerLevel internal) {
        
        internal.setDayTime(1000);
    }
    
    /**
     * Sets the time of the Minecraft day to noon. This is like using the
     * "time set noon" command or setting the time to 6000.
     */
    @ZenCodeType.Method
    public static void setTimeToNoon(ServerLevel internal) {
        
        internal.setDayTime(6000);
    }
    
    /**
     * Sets the time of the Minecraft day to night. This is like using the
     * "time set night" command or setting the time to 13000.
     */
    @ZenCodeType.Method
    public static void setTimeToNight(ServerLevel internal) {
        
        internal.setDayTime(13000);
    }
    
    /**
     * Sets the time of the Minecraft day to midnight. This is like using the
     * "time set midnight" command or setting the time to 18000.
     */
    @ZenCodeType.Method
    public static void setTimeToMidnight(ServerLevel internal) {
        
        internal.setDayTime(18000);
    }
    
    /**
     * Gets the random seed of the world.
     *
     * @return The random seed of the world.
     */
    @ZenCodeType.Getter("seed")
    public static long getSeed(ServerLevel internal) {
        
        return internal.getSeed();
    }
    
    /**
     * Checks if a position is within a village.
     *
     * @param pos The position to look up.
     *
     * @return Whether the position was inside a village.
     *
     * @docParam pos new BlockPos(0, 1, 2)
     */
    @ZenCodeType.Method
    public static boolean isVillage(ServerLevel internal, BlockPos pos) {
        
        return internal.isVillage(pos);
    }
    
    /**
     * Checks if a position is within an active raid.
     *
     * @param pos The position to look up.
     *
     * @return Whether the position was inside an active raid.
     *
     * @docParam pos new BlockPos(0, 1, 2)
     */
    @ZenCodeType.Method
    public static boolean isRaided(ServerLevel internal, BlockPos pos) {
        
        return internal.isRaided(pos);
    }
    
    /**
     * Checks if a position is within a chunk that is considered a slime chunk.
     *
     * @param pos The position to look up.
     *
     * @return Whether the position was inside a slime chunk.
     *
     * @docParam pos new BlockPos(0, 1, 2)
     */
    @ZenCodeType.Method
    public static boolean isSlimeChunk(ServerLevel internal, BlockPos pos) {
        
        return WorldgenRandom.seedSlimeChunk(pos.getX() >> 4, pos.getZ() >> 4, internal.getSeed(), 987234911L)
                .nextInt(10) == 0;
    }
    
    @ZenCodeType.Getter("server")
    public static MinecraftServer getServer(ServerLevel internal) {
        
        return internal.getServer();
    }
    
    /**
     * Gets entities in the world that match the given Predicate and the given EntityType (if provided).
     *
     * @param predicate The predicate to check against.
     *
     * @return A List of Entities that match.
     *
     * @docParam predicate (entity as MCEntity) => entity.isImmuneToFire()
     * @docParam type <entitytype:minecraft:sheep>
     */
    @ZenCodeType.Method
    public static List<Entity> getEntities(ServerLevel internal, Predicate<Entity> predicate, @ZenCodeType.Optional EntityType<Entity> type) {
        
        return GenericUtil.uncheck(internal.getEntities(type, predicate));
    }
    
}
