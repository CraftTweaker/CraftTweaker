package com.blamejared.crafttweaker.natives.item.type.armor;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/item/type/armor/ArmorItem")
@NativeTypeRegistration(value = ArmorItem.class, zenCodeName = "crafttweaker.api.item.type.armor.ArmorItem")
public class ExpandArmorItem {
    
    @ZenCodeType.Getter("equipmentSlot")
    public static EquipmentSlot getEquipmentSlot(ArmorItem internal) {
        
        return internal.getEquipmentSlot();
    }
    
    @ZenCodeType.Getter("material")
    public static ArmorMaterial getMaterial(ArmorItem internal) {
        
        return internal.getMaterial();
    }
    
    @ZenCodeType.Getter("defense")
    public static int getDefense(ArmorItem internal) {
        
        return internal.getDefense();
    }
    
    @ZenCodeType.Getter("toughness")
    public static float getToughness(ArmorItem internal) {
        
        return internal.getToughness();
    }
    
    @ZenCodeType.Nullable
    @ZenCodeType.Getter("equipSound")
    public static SoundEvent getEquipSound(ArmorItem internal) {
        
        return internal.getEquipSound();
    }
    
}
