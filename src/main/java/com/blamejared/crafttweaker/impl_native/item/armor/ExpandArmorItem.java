package com.blamejared.crafttweaker.impl_native.item.armor;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/item/armor/ArmorItem")
@NativeTypeRegistration(value = ArmorItem.class, zenCodeName = "crafttweaker.api.item.armor.ArmorItem")
public class ExpandArmorItem {
    
    /**
     * Gets the equipment slot that this armor item goes into.
     *
     * @return The EquipmentSlotType that thsi armor item goes into.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("equipmentSlot")
    public static EquipmentSlotType getEquipmentSlot(ArmorItem internal) {
        
        return internal.getEquipmentSlot();
    }
    
    /**
     * Gets the armor material of this Armor Item.
     *
     * @return The IArmorMaterial of this Armor Item.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("armorMaterial")
    public static IArmorMaterial getArmorMaterial(ArmorItem internal) {
        
        return internal.getArmorMaterial();
    }
    
    /**
     * Gets the damage reduce amount of this Armor Item. This is how much armor protection this item applies.
     *
     * @return The damage reduce amount of this Armor Item.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("damageReduceAmount")
    public static int getDamageReduceAmount(ArmorItem internal) {
        
        return internal.getDamageReduceAmount();
    }
    
    /**
     * Gets the toughness of this Armor Item.
     *
     * @return The toughness of this Armor Item.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("toughness")
    public static float getToughness(ArmorItem internal) {
        
        return internal.getToughness();
    }
    
}
