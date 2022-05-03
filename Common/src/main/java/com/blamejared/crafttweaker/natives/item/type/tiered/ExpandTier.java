package com.blamejared.crafttweaker.natives.item.type.tiered;


import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.item.Tier;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/item/type/tiered/Tier")
@NativeTypeRegistration(value = Tier.class, zenCodeName = "crafttweaker.api.item.type.tiered.Tier")
public class ExpandTier {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("uses")
    public static int getUses(Tier internal) {
        
        return internal.getUses();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("speed")
    public static float getSpeed(Tier internal) {
        
        return internal.getSpeed();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("attackDamageBonus")
    public static float getAttackDamageBonus(Tier internal) {
        
        return internal.getAttackDamageBonus();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("level")
    public static int getLevel(Tier internal) {
        
        return internal.getLevel();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("enchantmentValue")
    public static int getEnchantmentValue(Tier internal) {
        
        return internal.getEnchantmentValue();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("repairIngredient")
    public static IIngredient getRepairIngredient(Tier internal) {
        
        return IIngredient.fromIngredient(internal.getRepairIngredient());
    }
    
}
