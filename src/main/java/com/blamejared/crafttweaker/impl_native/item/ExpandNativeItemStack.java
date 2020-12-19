package com.blamejared.crafttweaker.impl_native.item;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker_annotations.annotations.NativeExpansion;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.impl.item.MCItemStack;
import com.blamejared.crafttweaker_annotations.annotations.DocumentAsType;
import net.minecraft.item.ItemStack;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@DocumentAsType
@NativeExpansion(ItemStack.class)
public class ExpandNativeItemStack {
    
    @ZenCodeType.Method
    public static void print(ItemStack _this) {
        CraftTweakerAPI.logInfo("%s", _this);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Caster(implicit = true)
    public static IItemStack asIItemStack(ItemStack _this) {
        return new MCItemStack(_this);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Caster(implicit = true)
    public static IIngredient asIIngredient(ItemStack _this) {
        return asIItemStack(_this);
    }
}
