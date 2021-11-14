package com.blamejared.crafttweaker.impl.actions.brewing;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.actions.IUndoableAction;
import com.blamejared.crafttweaker.api.logger.LogLevel;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionBrewing;
import net.minecraftforge.common.brewing.IBrewingRecipe;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.lang.reflect.Field;
import java.util.List;

public abstract class ActionBrewingBase implements IUndoableAction {
    
    protected final List<IBrewingRecipe> recipes;
    
    private Field reagentField;
    
    @SuppressWarnings({"rawtypes"})
    protected ActionBrewingBase(List<IBrewingRecipe> recipes) {
        
        this.recipes = recipes;
        try {
            //Raw so that the type constraints on findField work properly
            Class mixPredicate = Class.forName("net.minecraft.potion.PotionBrewing$MixPredicate");
            reagentField = ObfuscationReflectionHelper.findField(mixPredicate, "field_185199_b");
            reagentField.setAccessible(true);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public boolean shouldApplyOn(LogicalSide side) {
        
        return shouldApplySingletons();
    }
    
    protected Ingredient getItemReagent(PotionBrewing.MixPredicate<Potion> mixInstance) {
        
        return getFromField(reagentField, mixInstance);
    }
    
    private <T> T getFromField(Field field, Object mixInstance) {
        
        try {
            return (T) field.get(mixInstance);
        } catch(IllegalAccessException e) {
            e.printStackTrace();//TODO: Remove me?
            CraftTweakerAPI.logger.throwing(LogLevel.DEBUG, "Could not get Field: ", e);
            return null;
        }
    }
    
}
