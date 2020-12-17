package com.blamejared.crafttweaker.impl_native.event.entity.player;

import com.blamejared.crafttweaker.api.annotations.NativeExpansion;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.DocumentAsType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.AnvilRepairEvent;
import org.openzen.zencode.java.ZenCodeType;

import javax.annotation.Nonnull;

@ZenRegister
@DocumentAsType
@NativeExpansion(AnvilRepairEvent.class)
public class ExpandAnvilRepairEvent {
    
    @Nonnull
    @ZenCodeType.Method
    @ZenCodeType.Getter("itemResult")
    public static ItemStack getItemResult(AnvilRepairEvent internal) {
        return internal.getItemResult();
    }
    
    @Nonnull
    @ZenCodeType.Method
    @ZenCodeType.Getter("itemInput")
    public static ItemStack getItemInput(AnvilRepairEvent internal) {
        return internal.getItemInput();
    }
    
    @Nonnull
    @ZenCodeType.Method
    @ZenCodeType.Getter("ingredientInput")
    public static ItemStack getIngredientInput(AnvilRepairEvent internal) {
        return internal.getIngredientInput();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("breakChance")
    public static float getBreakChance(AnvilRepairEvent internal) {
        return internal.getBreakChance();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Setter("breakChance")
    public static void setBreakChance(AnvilRepairEvent internal, float breakChance) {
        internal.setBreakChance(breakChance);
    }
}
