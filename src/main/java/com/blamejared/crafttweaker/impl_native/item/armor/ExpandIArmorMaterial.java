package com.blamejared.crafttweaker.impl_native.item.armor;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/item/armor/IArmorMaterial")
@NativeTypeRegistration(value = IArmorMaterial.class, zenCodeName = "crafttweaker.api.item.armor.IArmorMaterial")
public class ExpandIArmorMaterial {
    
    /**
     * Gets the default durability of Armor pieces made from this material in the given slot.
     *
     * @param slotIn The slot to get the value of.
     *
     * @return The default durability of the armor piece.
     */
    @ZenCodeType.Method
    public static int getDurability(IArmorMaterial internal, EquipmentSlotType slotIn) {
        
        return internal.getDurability(slotIn);
    }
    
    /**
     * Gets the armor value of this material in the given slot. This is how many armor pieces this material fills in the armor bar.
     *
     * @param slotIn The slot to get the value of.
     *
     * @return The armor value of this material in the given slot.
     */
    @ZenCodeType.Method
    public static int getDamageReductionAmount(IArmorMaterial internal, EquipmentSlotType slotIn) {
        
        return internal.getDamageReductionAmount(slotIn);
    }
    
    /**
     * Gets the enchantability of this material.
     *
     * @return The enchantability of this material.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("enchantability")
    public static int getEnchantability(IArmorMaterial internal) {
        
        return internal.getEnchantability();
    }
    
    /**
     * Gets the repair material of this material.
     *
     * @return The repair material of this material.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("repairMaterial")
    public static IIngredient getRepairMaterial(IArmorMaterial internal) {
        
        return IIngredient.fromIngredient(internal.getRepairMaterial());
    }
    
    /**
     * Gets the toughness of this material.
     *
     * @return The toughness of this material.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("toughness")
    public static float getToughness(IArmorMaterial internal) {
        
        return internal.getToughness();
    }
    
    /**
     * Gets the knockback resistance of this material.
     *
     * @return The knockback resistance of this material.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("knockbackResistance")
    public static float getKnockbackResistance(IArmorMaterial internal) {
        
        return internal.getKnockbackResistance();
    }
    
}
