package com.blamejared.crafttweaker.api.action.item;


import com.blamejared.crafttweaker.api.action.base.IUndoableAction;
import com.blamejared.crafttweaker.api.action.internal.CraftTweakerAction;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.zencode.IScriptLoadSource;

import java.util.function.Consumer;
import java.util.function.Function;

public class ActionSetItemProperty<T> extends CraftTweakerAction implements IUndoableAction {
    
    private final IItemStack item;
    private final String propertyName;
    private final T newValue;
    private final T oldValue;
    private final Consumer<T> valueSetter;
    private final Function<T, String> valueNameGetter;
    
    public ActionSetItemProperty(IItemStack item, String propertyName, T newValue, T oldValue, Consumer<T> valueSetter) {
        
        this.item = item;
        this.propertyName = propertyName;
        this.newValue = newValue;
        this.oldValue = oldValue;
        this.valueSetter = valueSetter;
        this.valueNameGetter = Object::toString;
    }
    
    public ActionSetItemProperty(IItemStack item, String propertyName, T newValue, T oldValue, Consumer<T> valueSetter, Function<T, String> valueNameGetter) {
        
        this.item = item;
        this.propertyName = propertyName;
        this.newValue = newValue;
        this.oldValue = oldValue;
        this.valueSetter = valueSetter;
        this.valueNameGetter = valueNameGetter;
    }
    
    @Override
    public void apply() {
        
        this.valueSetter.accept(newValue);
    }
    
    @Override
    public String describe() {
        
        return "Set the value of " + propertyName + " on " + getTargetCommandString() + " to: '" + (newValue == null ? "null" : this.valueNameGetter.apply(newValue)) + "'";
    }
    
    @Override
    public void undo() {
        
        this.valueSetter.accept(oldValue);
    }
    
    @Override
    public String describeUndo() {
        
        return "Reset the value of " + propertyName + " on " + getTargetCommandString() + " to: '" + (newValue == null ? "null" : this.valueNameGetter.apply(oldValue)) + "'";
    }
    
    public String getTargetCommandString() {
        
        return item.getCommandString();
    }
    
    @Override
    public boolean shouldApplyOn(IScriptLoadSource source) {
        
        return true;
    }
    
}