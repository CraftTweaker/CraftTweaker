package com.blamejared.crafttweaker.impl_native.world;

import org.openzen.zencode.java.ZenCodeType;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;

import net.minecraft.world.server.ServerWorld;

/**
 * Represents the logical (server) implementation of the world. These are not
 * limited to dedicated servers, they exist in single player worlds as part of
 * the integrated server.
 * 
 * @docParam this world
 */
@ZenRegister
@Document("vanilla/api/world/MCServerWorld")
@NativeTypeRegistration(value = ServerWorld.class, zenCodeName = "crafttweaker.api.world.ServerMCWorld")
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
     * "time set day" command.
     */
    @ZenCodeType.Method
    public static void setTimeToDay(ServerWorld internal) {
        internal.setDayTime(1000);
    }
    
    /**
     * Sets the time of the Minecraft day to noon. This is like using the
     * "time set noon" command.
     */
    @ZenCodeType.Method
    public static void setTimeToNoon(ServerWorld internal) {
        internal.setDayTime(6000);
    }
    
    /**
     * Sets the time of the Minecraft day to night. This is like using the
     * "time set night" command.
     */
    @ZenCodeType.Method
    public static void setTimeToNight(ServerWorld internal) {
        internal.setDayTime(6000);
    }
    
    /**
     * Sets the time of the Minecraft day to midnight. This is like using the
     * "time set midnight" command.
     */
    @ZenCodeType.Method
    public static void setTimeToMidnight(ServerWorld internal) {
        internal.setDayTime(18000);
    }
}