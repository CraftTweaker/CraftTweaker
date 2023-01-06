package com.blamejared.crafttweaker.api.action.block;


import com.blamejared.crafttweaker.api.action.base.IUndoableAction;
import com.blamejared.crafttweaker.api.action.internal.CraftTweakerAction;
import com.blamejared.crafttweaker.api.zencode.IScriptLoadSource;
import com.blamejared.crafttweaker.natives.block.ExpandBlock;
import com.blamejared.crafttweaker.natives.block.ExpandBlockState;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

public class ActionSetBlockProperty<T> extends CraftTweakerAction implements IUndoableAction {
    
    private final Block block;
    private final String propertyName;
    private final T newValue;
    private final T oldValue;
    private final Consumer<T> valueSetter;
    private final Function<T, String> valueNameGetter;
    private final BlockState blockState;
    
    public ActionSetBlockProperty(Block block, String propertyName, T newValue, T oldValue, Consumer<T> valueSetter) {
        
        this.block = block;
        this.propertyName = propertyName;
        this.newValue = newValue;
        this.oldValue = oldValue;
        this.valueSetter = valueSetter;
        this.valueNameGetter = Object::toString;
        this.blockState = null;
    }
    
    public ActionSetBlockProperty(Block block, String propertyName, T newValue, T oldValue, Consumer<T> valueSetter, Function<T, String> valueNameGetter) {
        
        this.block = block;
        this.propertyName = propertyName;
        this.newValue = newValue;
        this.oldValue = oldValue;
        this.valueSetter = valueSetter;
        this.valueNameGetter = valueNameGetter;
        this.blockState = null;
    }
    
    public ActionSetBlockProperty(BlockState blockState, String propertyName, T newValue, T oldValue, Consumer<T> valueSetter, Function<T, String> valueNameGetter) {
        
        this.block = null;
        this.propertyName = propertyName;
        this.newValue = newValue;
        this.oldValue = oldValue;
        this.valueSetter = valueSetter;
        this.valueNameGetter = valueNameGetter;
        this.blockState = blockState;
    }
    
    public ActionSetBlockProperty(BlockState blockState, String propertyName, T newValue, T oldValue, Consumer<T> valueSetter) {
        
        this(blockState, propertyName, newValue, oldValue, valueSetter, Objects::toString);
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
    
    @Override
    public boolean shouldApplyOn(IScriptLoadSource source) {
        
        return true;
    }
    
    public String getTargetCommandString() {
        
        if(block == null) {
            if(blockState != null) {
                return ExpandBlockState.getCommandString(blockState);
            } else {
                throw new IllegalArgumentException("Both block and blockState are null!");
            }
        } else {
            return ExpandBlock.getCommandString(block);
        }
    }
    
}