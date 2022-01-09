package com.blamejared.crafttweaker.natives.event.entity;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.LightningBolt;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
import org.openzen.zencode.java.ZenCodeType;

/**
 * @docEvent canceled the entity is not struck by the lightening.
 */
@ZenRegister
@Document("forge/api/event/entity/EntityStruckByLightningEvent")
@NativeTypeRegistration(value = EntityStruckByLightningEvent.class, zenCodeName = "crafttweaker.api.event.entity.EntityStruckByLightningEvent")
public class ExpandEntityStruckByLightningEvent {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("lightning")
    public static LightningBolt getLightning(EntityStruckByLightningEvent internal) {
        
        return internal.getLightning();
    }
    
}
