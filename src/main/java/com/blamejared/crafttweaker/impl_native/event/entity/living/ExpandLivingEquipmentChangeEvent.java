package com.blamejared.crafttweaker.impl_native.event.entity.living;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.impl.item.MCItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.EventCancelable;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/event/entity/living/MCLivingEquipmentChangeEvent")
@EventCancelable
@NativeTypeRegistration(value = LivingEquipmentChangeEvent.class, zenCodeName = "crafttweaker.api.event.entity.living.MCLivingEquipmentChangeEvent")
public class ExpandLivingEquipmentChangeEvent {
    @ZenCodeType.Getter("slot")
    public static EquipmentSlotType getSlot(LivingEquipmentChangeEvent internal) {
        return internal.getSlot();
    }
    
    @ZenCodeType.Getter("from")
    public static IItemStack getFrom(LivingEquipmentChangeEvent internal) {
        return new MCItemStack(internal.getFrom());
    }
    
    @ZenCodeType.Getter("to")
    public static IItemStack getTo(LivingEquipmentChangeEvent internal) {
        return new MCItemStack(internal.getTo());
    }
}
