package com.blamejared.crafttweaker.api.action.item;


import com.blamejared.crafttweaker.api.action.base.IUndoableAction;
import com.blamejared.crafttweaker.api.action.internal.CraftTweakerAction;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.attribute.ItemAttributeModifierBase;
import com.blamejared.crafttweaker.api.zencode.IScriptLoadSource;
import com.blamejared.crafttweaker.platform.Services;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.function.Consumer;

public class ActionModifyAttribute extends CraftTweakerAction implements IUndoableAction {
    
    private final IIngredient ingredient;
    private final Consumer<ItemAttributeModifierBase> consumer;
    
    public ActionModifyAttribute(IIngredient ingredient, Consumer<ItemAttributeModifierBase> consumer) {
        
        this.ingredient = ingredient;
        this.consumer = consumer;
    }
    
    @Override
    public void apply() {
        
        Services.EVENT.getAttributeModifiers()
                .computeIfAbsent(ingredient, ingredient1 -> new ArrayList<>())
                .add(consumer);
    }
    
    @Override
    public String describe() {
        
        return String.format("Modifying Attributes of: %s", ingredient.getCommandString());
    }
    
    @Override
    public void undo() {
        
        Services.EVENT.getAttributeModifiers().keySet().stream().filter(ingredient::contains).forEach(key -> {
            Services.EVENT.getAttributeModifiers().getOrDefault(key, new ArrayList<>()).removeIf(consumer::equals);
        });
    }
    
    @Override
    public String describeUndo() {
        
        return String.format("Undoing modification of Attributes on: %s", ingredient.getCommandString());
    }
    
    @Override
    public boolean shouldApplyOn(final IScriptLoadSource source, final Logger logger) {
        
        return true;
    }
    
}
