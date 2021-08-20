package com.blamejared.crafttweaker.impl.actions.blocks;

import com.blamejared.crafttweaker.api.actions.IUndoableAction;
import com.blamejared.crafttweaker.impl_native.blocks.ExpandBlock;
import net.minecraft.block.Block;
import net.minecraftforge.fml.LogicalSide;

import java.util.function.Consumer;
import java.util.function.Function;

public class ActionSetBlockProperty<T> implements IUndoableAction {
    
    private final Block block;
    private final String propertyName;
    private final T newValue;
    private final T oldValue;
    private final Consumer<T> valueSetter;
    private Function<T, String> valueNameGetter;
    
    public ActionSetBlockProperty(Block block, String propertyName, T newValue, T oldValue, Consumer<T> valueSetter) {
        
        this.block = block;
        this.propertyName = propertyName;
        this.newValue = newValue;
        this.oldValue = oldValue;
        this.valueSetter = valueSetter;
        this.valueNameGetter = Object::toString;
    }
    
    public ActionSetBlockProperty(Block block, String propertyName, T newValue, T oldValue, Consumer<T> valueSetter, Function<T, String> valueNameGetter) {
        
        this.block = block;
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
        
        return "Set the value of " + propertyName + " on " + ExpandBlock.getCommandString(block) + " to: '" + (newValue == null ? "null" : this.valueNameGetter.apply(newValue)) + "'";
    }
    
    @Override
    public void undo() {
        
        this.valueSetter.accept(oldValue);
    }
    
    @Override
    public String describeUndo() {
        
        return "Reset the value of " + propertyName + " on " + ExpandBlock.getCommandString(block) + " to: '" + (newValue == null ? "null" : this.valueNameGetter.apply(oldValue)) + "'";
    }
    
    @Override
    public boolean shouldApplyOn(LogicalSide side) {
        
        return shouldApplySingletons();
    }
    
}