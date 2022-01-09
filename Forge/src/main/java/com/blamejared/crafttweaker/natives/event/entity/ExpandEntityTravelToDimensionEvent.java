package com.blamejared.crafttweaker.natives.event.entity;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.event.entity.EntityTravelToDimensionEvent;
import org.openzen.zencode.java.ZenCodeType;

/**
 * The event is fired before an Entity travels to a dimension.
 * <br>
 * `dimension` getter is the id of the dimension the entity is traveling to.
 *
 * @docEvent canceled the Entity will not travel to the dimension.
 */
@ZenRegister
@Document("forge/api/event/entity/EntityTravelToDimensionEvent")
@NativeTypeRegistration(value = EntityTravelToDimensionEvent.class, zenCodeName = "crafttweaker.api.event.entity.EntityTravelToDimensionEvent")
public class ExpandEntityTravelToDimensionEvent {
    
    @ZenCodeType.Getter("dimension")
    public static ResourceLocation getDimension(EntityTravelToDimensionEvent internal) {
        
        return internal.getDimension().location();
    }
    
}
