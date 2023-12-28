package com.blamejared.crafttweaker.natives.item;


import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.item.ItemStack;
import org.openzen.zencode.java.ZenCodeType;

/**
 * This is the vanilla ItemStack.
 * It is recommended that you use {@link IItemStack} whenever possible
 * <p>
 * They can be cast from each other, though.
 */
@ZenRegister
@Document("vanilla/api/item/ItemStack")
@NativeTypeRegistration(value = ItemStack.class, zenCodeName = "crafttweaker.api.item.ItemStack")
public class ExpandItemStack {
    
    @ZenCodeType.Method
    @ZenCodeType.Caster(implicit = true)
    public static IItemStack asIItemStack(ItemStack internal) {
        
        return IItemStack.of(internal);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Caster(implicit = true)
    public static IIngredient asIIngredient(ItemStack internal) {
        
        return asIItemStack(internal);
    }
    
}
