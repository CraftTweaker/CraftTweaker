package com.blamejared.crafttweaker.impl_native.event.entity.player.xp;


import org.openzen.zencode.java.ZenCodeType;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;

import net.minecraftforge.event.entity.player.PlayerXpEvent.LevelChange;

/**
 * This event is fired when the player's level count is changed. If the amount
 * is positive the levels are being added. If they are negative, levels are
 * being removed. This event takes place before the levels are changed. This
 * allows you to change the amount of levels, or cancel the change entirely.
 * 
 * @docParam this event
 * @docEvent canceled the xp level will not change
 */
@ZenRegister
@Document("vanilla/api/event/entity/player/xp/MCLevelChangeEvent")
@NativeTypeRegistration(value = LevelChange.class, zenCodeName = "crafttweaker.api.event.entity.player.xp.MCLevelChangeEvent")
public class ExpandLevelChangedEvent {
    
	/**
	 * Gets the amount of levels that the player's level is being changed by.
	 * 
	 * @return The amount of levels that the player's level is being changed by.
	 */
    @ZenCodeType.Getter("levels")
    public static int getLevels(LevelChange internal) {
        return internal.getLevels();
    }
    
    /**
     * Sets the amount of levels to change the player's level by.
     * 
     * @param levels The amount of levels that should be added to the player's
     *        level counter.
     *        
     * @docParam levels 5
     */
    @ZenCodeType.Setter("levels")
    public static void setLevels(LevelChange internal, int levels) {
        internal.setLevels(levels);
    }
}