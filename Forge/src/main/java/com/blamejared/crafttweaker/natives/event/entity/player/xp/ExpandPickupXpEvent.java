package com.blamejared.crafttweaker.natives.event.entity.player.xp;


import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
import org.openzen.zencode.java.ZenCodeType;

/**
 * This event is fired after the player collides with an experience orb, but before the player has been given the experience.
 * It can be cancelled, and no further processing will be done.
 *
 * @docParam this event
 * @docEvent canceled the xp will not change
 */
@ZenRegister
@Document("forge/api/event/entity/player/xp/PickupXpEvent")
@NativeTypeRegistration(value = PlayerXpEvent.PickupXp.class, zenCodeName = "crafttweaker.api.event.entity.player.xp.PickupXpEvent")
public class ExpandPickupXpEvent {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("orb")
    public static ExperienceOrb getOrb(PlayerXpEvent.PickupXp internal) {
        
        return internal.getOrb();
    }
    
}