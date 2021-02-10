package com.blamejared.crafttweaker.impl_native.event.entity.living;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.impl.item.MCItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("crafttweaker.api.event.entity.living.MCLivingEntityUseItemEvent")
@NativeTypeRegistration(value = LivingEntityUseItemEvent.class, zenCodeName = "crafttweaker.api.event.entity.living.MCLivingEntityUseItemEvent")
public class ExpandLivingEntityUseItemEvent {
    @ZenCodeType.Getter("item")
    public static IItemStack getItem(LivingEntityUseItemEvent internal) {
        return new MCItemStack(internal.getItem());
    }
    
    @ZenCodeType.Getter("duration")
    public static int getDuration(LivingEntityUseItemEvent internal) {
        return internal.getDuration();
    }
    
    @ZenCodeType.Setter("duration")
    public static void setDuration(LivingEntityUseItemEvent internal, int duration) {
        internal.setDuration(duration);
    }
    
    // TODO: specific events: Start Tick Stop Finish
}
