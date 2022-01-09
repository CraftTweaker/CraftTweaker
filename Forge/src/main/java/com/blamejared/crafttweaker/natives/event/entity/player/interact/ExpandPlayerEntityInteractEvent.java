package com.blamejared.crafttweaker.natives.event.entity.player.interact;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import org.openzen.zencode.java.ZenCodeType;

/**
 * This event is fired on both sides whenever the player right clicks an entity.
 *
 * @docEvent canceled will cause the entity to not be interacted with
 */
@ZenRegister
@Document("forge/api/event/entity/player/interact/EntityInteractEvent")
@NativeTypeRegistration(value = PlayerInteractEvent.EntityInteract.class, zenCodeName = "crafttweaker.api.event.entity.player.interact.EntityInteractEvent")
public class ExpandPlayerEntityInteractEvent {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("target")
    public static Entity getTarget(PlayerInteractEvent.EntityInteract internal) {
        
        return internal.getTarget();
    }
    
}
