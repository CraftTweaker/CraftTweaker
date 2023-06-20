package com.blamejared.crafttweaker.natives.event.entity.use;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import org.openzen.zencode.java.ZenCodeType;


@ZenRegister
@Document("forge/api/event/entity/use/LivingEntityUseItemEvent")
@NativeTypeRegistration(value = LivingEntityUseItemEvent.class, zenCodeName = "crafttweaker.forge.api.event.entity.use.LivingEntityUseItemEvent")
public class ExpandLivingEntityUseItemEvent {
    
    @ZenCodeType.Getter("item")
    public static IItemStack getItem(LivingEntityUseItemEvent internal) {
        
        return IItemStack.of(internal.getItem());
    }
    
    @ZenCodeType.Getter("duration")
    public static int getDuration(LivingEntityUseItemEvent internal) {
        
        return internal.getDuration();
    }
    
    @ZenCodeType.Setter("duration")
    public static void setDuration(LivingEntityUseItemEvent internal, int duration) {
        
        internal.setDuration(duration);
    }
    
}
