package com.blamejared.crafttweaker.impl_native.item.tiered;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.item.IItemTier;
import net.minecraft.item.crafting.Ingredient;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/item/tiered/IItemTier")
@NativeTypeRegistration(value = IItemTier.class, zenCodeName = "crafttweaker.api.item.tiered.IItemTier")
public class ExpandIItemTier {
    
    /**
     * Gets this tier's max uses.
     *
     * @return This tier's max uses.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("maxUses")
    public static int getMaxUses(IItemTier internal) {
        
        return internal.getMaxUses();
    }
    
    /**
     * Gets this tier's efficiency.
     *
     * @return This tier's efficiency.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("efficiency")
    public static float getEfficiency(IItemTier internal) {
        
        return internal.getEfficiency();
    }
    
    /**
     * Gets this tier's attack damage.
     *
     * @return This tier's attack damage.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("attackDamage")
    public static float getAttackDamage(IItemTier internal) {
        
        return internal.getAttackDamage();
    }
    
    /**
     * Gets this tier's harvest level.
     *
     * @return This tier's harvest level.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("harvestLevel")
    public static int getHarvestLevel(IItemTier internal) {
        
        return internal.getHarvestLevel();
    }
    
    /**
     * Gets this tier's enchantability.
     *
     * @return This tier's enchantability.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("enchantability")
    public static int getEnchantability(IItemTier internal) {
        
        return internal.getEnchantability();
    }
    
    /**
     * Gets this tier's repair material.
     *
     * @return This tier's repair material.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("repairMaterial")
    public static Ingredient getRepairMaterial(IItemTier internal) {
        
        return internal.getRepairMaterial();
    }
    
}
