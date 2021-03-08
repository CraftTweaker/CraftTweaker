package com.blamejared.crafttweaker.impl.actions.brewing;

import com.blamejared.crafttweaker.api.actions.IUndoableAction;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.Potion;
import net.minecraftforge.common.brewing.IBrewingRecipe;
import net.minecraftforge.registries.IRegistryDelegate;

import java.lang.reflect.Field;
import java.util.List;

public abstract class ActionBrewingBase implements IUndoableAction {
    
    protected final List<IBrewingRecipe> recipes;
    
    private Field inputField;
    private Field reagentField;
    private Field outputField;
    
    protected ActionBrewingBase(List<IBrewingRecipe> recipes) {
        
        this.recipes = recipes;
        try {
            Class<?> mixPredicate = Class.forName("net.minecraft.potion.PotionBrewing$MixPredicate");
            inputField = mixPredicate.getDeclaredField("input");
            inputField.setAccessible(true);
            reagentField = mixPredicate.getDeclaredField("reagent");
            reagentField.setAccessible(true);
            outputField = mixPredicate.getDeclaredField("output");
            outputField.setAccessible(true);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    
    protected Ingredient getItemReagent(Object mixInstance) {
        
        try {
            return (Ingredient) reagentField.get(mixInstance);
        } catch(IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    protected IRegistryDelegate<Potion> getPotionInput(Object mixInstance) {
        
        try {
            return (IRegistryDelegate<Potion>) inputField.get(mixInstance);
        } catch(IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    protected IRegistryDelegate<Potion> getPotionOutput(Object mixInstance) {
        
        try {
            return (IRegistryDelegate<Potion>) outputField.get(mixInstance);
        } catch(IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
    
}
