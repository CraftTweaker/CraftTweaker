package com.blamejared.crafttweaker.impl_native.world;

import org.openzen.zencode.java.ZenCodeType;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;

import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

/**
 * Represents the logical (server) implementation of the world. These are not
 * limited to dedicated servers, they exist in single player worlds as part of
 * the integrated server.
 * 
 * @docParam this world as MCServerWorld
 */
@ZenRegister
@Document("vanilla/api/world/MCServerWorld")
@NativeTypeRegistration(value = ServerWorld.class, zenCodeName = "crafttweaker.api.world.MCServerWorld")
public class ExpandServerWorld {
	
	/**
	 * Sets the time of the Minecraft day.
	 * 
	 * @param time The new time of day. Should be between 0 and 24000.
	 * 
	 * @docParam time 6000
	 */
    @ZenCodeType.Setter("timeOfDay")
    public static void setDayTime(ServerWorld internal, long time) {
        internal.setDayTime(time);
    }

    /**
     * Sets the time of the Minecraft day to day. This is like using the
     * "time set day" command or setting the time to 1000.
     */
    @ZenCodeType.Method
    public static void setTimeToDay(ServerWorld internal) {
        internal.setDayTime(1000);
    }
    
    /**
     * Sets the time of the Minecraft day to noon. This is like using the
     * "time set noon" command or setting the time to 6000.
     */
    @ZenCodeType.Method
    public static void setTimeToNoon(ServerWorld internal) {
        internal.setDayTime(6000);
    }
    
    /**
     * Sets the time of the Minecraft day to night. This is like using the
     * "time set night" command or setting the time to 13000.
     */
    @ZenCodeType.Method
    public static void setTimeToNight(ServerWorld internal) {
        internal.setDayTime(13000);
    }
    
    /**
     * Sets the time of the Minecraft day to midnight. This is like using the
     * "time set midnight" command or setting the time to 18000.
     */
    @ZenCodeType.Method
    public static void setTimeToMidnight(ServerWorld internal) {
        internal.setDayTime(18000);
    }
    
    /**
     * Gets the random seed of the world.
     * 
     * @return The random seed of the world.
     */
    @ZenCodeType.Getter("seed")
    public static long getSeed(ServerWorld internal) {
    	return internal.getSeed();
    }
    
    /**
     * Checks if a position is within a village.
     * 
     * @param pos The position to look up.
     * @return Whether or not the position was inside a village.
     * 
     * @docParem pos new BlockPos(0, 1, 2)
     */
    @ZenCodeType.Method
    public static boolean isVillage(ServerWorld internal, BlockPos pos) {
    	return internal.isVillage(pos);
    }
    
    /**
     * Checks if a position is within an active raid.
     * 
     * @param pos The position to look up.
     * @return Whether or not the position was inside an active raid.
     * 
     * @docParam pos new BlockPos(0, 1, 2)
     */
    @ZenCodeType.Method
    public static boolean isRaid(ServerWorld internal, BlockPos pos) {
    	return internal.hasRaid(pos);
    }
    
    /**
     * Checks if a position is within a chunk that is considered a slime chunk.
     * 
     * @param pos The position to look up.
     * @return Whether or not the position was inside a slime chunk.
     * 
     * @docParam pos new BlockPos(0, 1, 2)
     */
    @ZenCodeType.Method
    public static boolean isSlimeChunk(ServerWorld internal, BlockPos pos) {
    	return SharedSeedRandom.seedSlimeChunk(pos.getX() >> 4, pos.getZ() >> 4, internal.getSeed(), 987234911L).nextInt(10) == 0;
    }
}