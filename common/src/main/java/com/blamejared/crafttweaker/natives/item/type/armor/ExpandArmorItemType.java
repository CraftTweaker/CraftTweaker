package com.blamejared.crafttweaker.natives.item.type.armor;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.BracketEnum;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/item/type/armor/ArmorItemType")
@NativeTypeRegistration(value = ArmorItem.Type.class, zenCodeName = "crafttweaker.api.item.type.armor.ArmorItem.Type")
@BracketEnum("minecraft:armor/type")
public class ExpandArmorItemType {
    
    @ZenCodeType.Getter("name")
    public static String getName(ArmorItem.Type internal) {
        
        return internal.getName();
    }
    
    @ZenCodeType.Getter("slot")
    public static EquipmentSlot getSlot(ArmorItem.Type internal) {
        
        return internal.getSlot();
    }
    
    
}
