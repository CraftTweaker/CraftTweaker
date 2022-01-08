package com.blamejared.crafttweaker.natives.event.entity.living;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.item.MCItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/event/entity/living/LivingEquipmentChangeEvent")
@NativeTypeRegistration(value = LivingEquipmentChangeEvent.class, zenCodeName = "crafttweaker.api.event.entity.living.LivingEquipmentChangeEvent")
public class ExpandLivingEquipmentChangeEvent {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("slot")
    public static EquipmentSlot getSlot(LivingEquipmentChangeEvent internal) {
        
        return internal.getSlot();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("from")
    public static IItemStack getFrom(LivingEquipmentChangeEvent internal) {
        
        return new MCItemStack(internal.getFrom());
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("to")
    public static IItemStack getTo(LivingEquipmentChangeEvent internal) {
        
        return new MCItemStack(internal.getTo());
    }
    
}
