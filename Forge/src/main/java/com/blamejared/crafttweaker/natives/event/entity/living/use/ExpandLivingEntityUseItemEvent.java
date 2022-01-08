package com.blamejared.crafttweaker.natives.event.entity.living.use;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.platform.Services;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import org.openzen.zencode.java.ZenCodeType;


/**
 * LivingEntityUseItemEvent is fired when an event involving using an item occures..
 *
 * This event is fired for all types of item use phases, it is generally advised to use the specific events
 * to target a specific phase instead of this event.
 */
@ZenRegister
@Document("vanilla/api/event/entity/living/use/LivingEntityUseItemEvent")
@NativeTypeRegistration(value = LivingEntityUseItemEvent.class, zenCodeName = "crafttweaker.api.event.entity.living.use.LivingEntityUseItemEvent")
public class ExpandLivingEntityUseItemEvent {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("item")
    public static IItemStack getItem(LivingEntityUseItemEvent internal) {
        
        return Services.PLATFORM.createMCItemStack(internal.getItem());
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("duration")
    public static int getDuration(LivingEntityUseItemEvent internal) {
        
        return internal.getDuration();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Setter("duration")
    public static void setDuration(LivingEntityUseItemEvent internal, int duration) {
        
        internal.setDuration(duration);
    }
    
}
