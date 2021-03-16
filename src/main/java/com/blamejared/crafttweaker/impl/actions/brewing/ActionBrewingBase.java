package com.blamejared.crafttweaker.impl.actions.brewing;

import com.blamejared.crafttweaker.api.actions.IUndoableAction;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.Potion;
import net.minecraftforge.common.brewing.IBrewingRecipe;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
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
            Class mixPredicate = Class.forName("net.minecraft.potion.PotionBrewing$MixPredicate");
            inputField = ObfuscationReflectionHelper.findField(mixPredicate, "field_185198_a");
            inputField.setAccessible(true);
            reagentField = ObfuscationReflectionHelper.findField(mixPredicate, "field_185199_b");
            reagentField.setAccessible(true);
            outputField = ObfuscationReflectionHelper.findField(mixPredicate, "field_185200_c");
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
