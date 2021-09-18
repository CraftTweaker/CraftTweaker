package com.blamejared.crafttweaker.impl.actions.blocks;

import com.blamejared.crafttweaker.api.actions.IUndoableAction;
import com.blamejared.crafttweaker.impl_native.blocks.ExpandBlock;
import com.blamejared.crafttweaker.impl_native.blocks.ExpandBlockState;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraftforge.fml.LogicalSide;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

public class ActionSetBlockProperty<T> implements IUndoableAction {
    
    private final Block block;
    private final String propertyName;
    private final T newValue;
    private final T oldValue;
    private final Consumer<T> valueSetter;
    private Function<T, String> valueNameGetter;
    private BlockState blockState;
    
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
    public boolean shouldApplyOn(LogicalSide side) {
        
        return shouldApplySingletons();
    }

    public String getTargetCommandString() {
        if (block == null) {
            if (blockState != null) {
                return ExpandBlockState.getCommandString(blockState);
            } else {
                throw new IllegalArgumentException("Both block and blockState are null!");
            }
        } else {
            return ExpandBlock.getCommandString(block);
        }
    }
}