package com.blamejared.crafttweaker.natives.event.entity.living.teleport;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.EntityTeleportEvent;
import org.openzen.zencode.java.ZenCodeType;

/**
 * EnderEntityTeleportEvent is fired before an Enderman or Shulker randomly teleports.
 *
 * @docEvent canceled the teleport won't happen.
 */
@ZenRegister
@Document("forge/api/event/entity/living/teleport/EnderEntityTeleportEvent")
@NativeTypeRegistration(value = EntityTeleportEvent.EnderEntity.class, zenCodeName = "crafttweaker.api.event.living.teleport.EnderEntityTeleportEvent")
public class ExpandEnderEntityTeleportEvent {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("entityLiving")
    public static LivingEntity getEntityLiving(EntityTeleportEvent.EnderEntity internal) {
        
        return internal.getEntityLiving();
    }
    
}
