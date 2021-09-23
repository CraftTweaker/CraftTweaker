package com.blamejared.crafttweaker.impl.actions.items;

import com.blamejared.crafttweaker.api.actions.IUndoableAction;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.impl.events.CTEventHandler;
import net.minecraftforge.event.ItemAttributeModifierEvent;
import net.minecraftforge.fml.LogicalSide;

import java.util.ArrayList;
import java.util.function.Consumer;

public class ActionModifyAttribute implements IUndoableAction {
    
    private IIngredient ingredient;
    private Consumer<ItemAttributeModifierEvent> consumer;
    
    public ActionModifyAttribute(IIngredient ingredient, Consumer<ItemAttributeModifierEvent> consumer) {
        
        this.ingredient = ingredient;
        this.consumer = consumer;
    }
    
    @Override
    public void apply() {
        
        CTEventHandler.ATTRIBUTE_MODIFIERS.computeIfAbsent(ingredient, ingredient1 -> new ArrayList<>()).add(consumer);
    }
    
    @Override
    public String describe() {
        
        return String.format("Modifying Attributes of: %s", ingredient.getCommandString());
    }
    
    @Override
    public void undo() {
        
        CTEventHandler.ATTRIBUTE_MODIFIERS.keySet()
                .stream()
                .filter(entry -> ingredient.contains(entry)).forEach(key -> {
                    CTEventHandler.ATTRIBUTE_MODIFIERS.get(key).removeIf(value -> consumer.equals(value));
                });
        
    }
    
    @Override
    public String describeUndo() {
        
        return String.format("Undoing modification of Attributes on: %s", ingredient.getCommandString());
    }
    
    @Override
    public boolean shouldApplyOn(LogicalSide side) {
        
        return shouldApplySingletons();
    }
    
}
