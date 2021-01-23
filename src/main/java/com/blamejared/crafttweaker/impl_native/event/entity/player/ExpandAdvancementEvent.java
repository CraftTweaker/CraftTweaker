package com.blamejared.crafttweaker.impl_native.event.entity.player;


import org.openzen.zencode.java.ZenCodeType;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.player.AdvancementEvent;

/**
 * This event is fired every time the player earns an advancement. This happens
 * after the advancement has already been earned so it can not be prevented.
 * 
 * @docParam event
 */
@ZenRegister
@Document("vanilla/api/event/entity/player/MCAdvancementEvent")
@NativeTypeRegistration(value = AdvancementEvent.class, zenCodeName = "crafttweaker.api.event.entity.player.MCAdvancementEvent")
public class ExpandAdvancementEvent {
    
	/**
	 * Gets the ID of the advancement being unlocked.
	 * 
	 * @return The ID of the advancement being unlocked by the player.
	 */
    @ZenCodeType.Method
    @ZenCodeType.Getter("id")
    public static ResourceLocation getId(AdvancementEvent internal) {
        return internal.getAdvancement().getId();
    }
}