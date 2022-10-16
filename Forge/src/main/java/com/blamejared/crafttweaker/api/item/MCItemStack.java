package com.blamejared.crafttweaker.api.item;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.item.MCItemStack")
@Document("forge/api/item/MCItemStack")
public class MCItemStack implements ForgeItemStack {
    
    // TODO move this somewhere else
    public static Supplier<MCItemStack> EMPTY = () -> new MCItemStack(ItemStack.EMPTY);
    private final ItemStack internal;
    
    public MCItemStack(ItemStack internal) {
        
        this.internal = internal;
    }
    
    @Override
    public IItemStack[] getItems() {
        
        return new IItemStack[] {this.copy()};
    }
    
    @Override
    public IItemStack copy() {
        
        return new MCItemStack(getInternal().copy());
    }
    
    @Override
    public IItemStack asMutable() {
        
        if(this == EMPTY.get() || this.getInternal() == ItemStack.EMPTY) { // Check both just in case
            // We don't want to allow mutations to the empty stack, so we just replace it with a stack of air. The game
            // treats air stacks as mostly the same as the empty stack, so this should be transparent to the user
            return new MCItemStackMutable(new ItemStack(Items.AIR));
        }
        return new MCItemStackMutable(getInternal());
    }
    
    @Override
    public IItemStack asImmutable() {
        
        return this;
    }
    
    @Override
    public boolean isImmutable() {
        
        return true;
    }
    
    @Override
    public ItemStack getInternal() {
        
        return internal;
    }
    
    @Override
    public IItemStack modify(Consumer<ItemStack> stackModifier) {
        
        ItemStack newStack = getInternal().copy();
        stackModifier.accept(newStack);
        return new MCItemStack(newStack);
    }
    
    
    @Override
    @ZenCodeType.Operator(ZenCodeType.OperatorType.EQUALS)
    public boolean equals(Object o) {
        
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        
        //Implemented manually instead of using ItemStack.areItemStacksEqual to ensure it
        // stays the same as hashCode even if MC's impl would change
        final ItemStack thatStack = ((MCItemStack) o).getInternal();
        final ItemStack thisStack = getInternal();
        
        if(thisStack.isEmpty()) {
            return thatStack.isEmpty();
        }
        
        if(thisStack.getCount() != thatStack.getCount()) {
            return false;
        }
        
        if(!Objects.equals(thisStack.getItem(), thatStack.getItem())) {
            return false;
        }
        
        if(!Objects.equals(thisStack.getTag(), thatStack.getTag())) {
            return false;
        }
        
        return thisStack.areCapsCompatible(thatStack);
    }
    
    @Override
    public int hashCode() {
        
        return Objects.hash(getInternal().getCount(), getInternal().getItem(), getInternal().getTag());
    }
    
    @Override
    public String toString() {
        
        return this.getCommandString();
    }
    
}
