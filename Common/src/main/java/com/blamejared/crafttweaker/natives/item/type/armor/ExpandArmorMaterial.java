package com.blamejared.crafttweaker.natives.item.type.armor;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/item/type/armor/ArmorMaterial")
@NativeTypeRegistration(value = ArmorMaterial.class, zenCodeName = "crafttweaker.api.item.type.armor.ArmorMaterial")
public class ExpandArmorMaterial {
    
    @ZenCodeType.Method
    public static int getDurabilityForSlot(ArmorMaterial internal, EquipmentSlot slot) {
        
        return internal.getDurabilityForSlot(slot);
    }
    
    @ZenCodeType.Method
    public static int getDefenseForSlot(ArmorMaterial internal, EquipmentSlot slot) {
        
        return internal.getDefenseForSlot(slot);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("enchantmentValue")
    public static int getEnchantmentValue(ArmorMaterial internal) {
        
        return internal.getEnchantmentValue();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("equipSound")
    public static SoundEvent getEquipSound(ArmorMaterial internal) {
        
        return internal.getEquipSound();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("repairIngredient")
    public static IIngredient getRepairIngredient(ArmorMaterial internal) {
        
        return IIngredient.fromIngredient(internal.getRepairIngredient());
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("name")
    public static String getName(ArmorMaterial internal) {
        
        return internal.getName();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("toughness")
    public static float getToughness(ArmorMaterial internal) {
        
        return internal.getToughness();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("knockbackResistance")
    public static float getKnockbackResistance(ArmorMaterial internal) {
        
        return internal.getKnockbackResistance();
    }
    
}
