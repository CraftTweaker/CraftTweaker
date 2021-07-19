package com.blamejared.crafttweaker.impl_native.event.entity.player.interact;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.entity.Entity;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import org.openzen.zencode.java.ZenCodeType;

/**
 * This event is fired on both sides whenever the player right clicks an entity.
 *
 * @docEvent canceled will cause the entity to not be interacted with
 */
@ZenRegister
@Document("vanilla/api/event/entity/player/interact/MCEntityInteractEvent")
@NativeTypeRegistration(value = PlayerInteractEvent.EntityInteract.class, zenCodeName = "crafttweaker.api.event.entity.player.interact.MCEntityInteractEvent")
public class ExpandPlayerEntityInteractEvent {
    
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("target")
    public static Entity getTarget(PlayerInteractEvent.EntityInteract internal) {
        
        return internal.getTarget();
    }
    
}
