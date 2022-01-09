package com.blamejared.crafttweaker.natives.event.entity.living.teleport;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.EntityTeleportEvent;
import org.openzen.zencode.java.ZenCodeType;

/**
 * ChorusFruitTeleportEvent is fired before a LivingEntity is teleported due to consuming Chorus Fruit.
 *
 * @docEvent canceled the teleport won't happen.
 */
@ZenRegister
@Document("forge/api/event/entity/living/teleport/ChorusFruitTeleportEvent")
@NativeTypeRegistration(value = EntityTeleportEvent.ChorusFruit.class, zenCodeName = "crafttweaker.api.event.living.teleport.ChorusFruitTeleportEvent")
public class ExpandChorusFruitTeleportEvent {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("entityLiving")
    public static LivingEntity getEntityLiving(EntityTeleportEvent.ChorusFruit internal) {
        
        return internal.getEntityLiving();
    }
    
}
