package com.blamejared.crafttweaker.impl_native.event.entity;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.EventCancelable;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.EntityTravelToDimensionEvent;
import org.openzen.zencode.java.ZenCodeType;

/**
 * The event is fired before an Entity travels to a dimension.
 * <br>
 * `dimension` getter is the id of the dimension the entity is traveling to.
 */
@ZenRegister
@Document("vanilla/api/event/entity/MCEntityTravelToDimensionEvent")
@EventCancelable
@NativeTypeRegistration(value = EntityTravelToDimensionEvent.class, zenCodeName = "crafttweaker.api.event.entity.MCEntityTravelToDimensionEvent")
public class ExpandEntityTravelToDimensionEvent {
    @ZenCodeType.Getter("dimension")
    public static ResourceLocation getDimension(EntityTravelToDimensionEvent internal) {
        return internal.getDimension().getLocation();
    }
}
