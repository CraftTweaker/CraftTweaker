package com.blamejared.crafttweaker.impl_native.event.entity.player;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.impl.item.MCItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraftforge.event.entity.player.AnvilRepairEvent;
import org.openzen.zencode.java.ZenCodeType;

import javax.annotation.Nonnull;

@ZenRegister
@Document("vanilla/api/event/player/MCAnvilRepairEvent")
@NativeTypeRegistration(value = AnvilRepairEvent.class, zenCodeName = "crafttweaker.api.event.entity.player.MCAnvilRepairEvent")
public class ExpandAnvilRepairEvent {
    
    @Nonnull
    @ZenCodeType.Method
    @ZenCodeType.Getter("itemResult")
    public static IItemStack getItemResult(AnvilRepairEvent internal) {
        return new MCItemStack(internal.getItemResult());
    }
    
    @Nonnull
    @ZenCodeType.Method
    @ZenCodeType.Getter("itemInput")
    public static IItemStack getItemInput(AnvilRepairEvent internal) {
        return new MCItemStack(internal.getItemInput());
    }
    
    @Nonnull
    @ZenCodeType.Method
    @ZenCodeType.Getter("ingredientInput")
    public static IItemStack getIngredientInput(AnvilRepairEvent internal) {
        return new MCItemStack(internal.getIngredientInput());
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
