package com.blamejared.crafttweaker.natives.event.entity.player;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.platform.Services;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraftforge.event.entity.player.AnvilRepairEvent;
import org.openzen.zencode.java.ZenCodeType;

import javax.annotation.Nonnull;

@ZenRegister
@Document("forge/api/event/entity/player/AnvilRepairEvent")
@NativeTypeRegistration(value = AnvilRepairEvent.class, zenCodeName = "crafttweaker.api.event.entity.player.AnvilRepairEvent")
public class ExpandAnvilRepairEvent {
    
    @Nonnull
    @ZenCodeType.Method
    @ZenCodeType.Getter("itemResult")
    public static IItemStack getItemResult(AnvilRepairEvent internal) {
        
        return Services.PLATFORM.createMCItemStack(internal.getItemResult());
    }
    
    @Nonnull
    @ZenCodeType.Method
    @ZenCodeType.Getter("itemInput")
    public static IItemStack getItemInput(AnvilRepairEvent internal) {
        
        return Services.PLATFORM.createMCItemStack(internal.getItemInput());
    }
    
    @Nonnull
    @ZenCodeType.Method
    @ZenCodeType.Getter("ingredientInput")
    public static IItemStack getIngredientInput(AnvilRepairEvent internal) {
        
        return Services.PLATFORM.createMCItemStack(internal.getIngredientInput());
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
