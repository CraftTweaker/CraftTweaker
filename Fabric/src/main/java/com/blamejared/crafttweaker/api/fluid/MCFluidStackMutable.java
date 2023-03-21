package com.blamejared.crafttweaker.api.fluid;

import com.blamejared.crafttweaker.api.data.*;
import com.blamejared.crafttweaker.api.data.converter.tag.TagToDataConverter;
import com.blamejared.crafttweaker.impl.fluid.SimpleFluidStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.material.Fluid;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Objects;

public class MCFluidStackMutable implements IFluidStack {
    
    private final SimpleFluidStack stack;
    
    public MCFluidStackMutable(SimpleFluidStack stack) {
        
        this.stack = stack;
    }
    
    @Override
    public boolean isEmpty() {
        
        return getInternal().isEmpty();
    }
    
    @Override
    public long getAmount() {
        
        return getInternal().amount();
    }
    
    @Override
    public IFluidStack setAmount(int amount) {
        
        getInternal().amount(amount);
        return this;
    }
    
    @Override
    public boolean isImmutable() {
        
        return false;
    }
    
    @Override
    public Fluid getFluid() {
        
        return getInternal().fluid();
    }
    
    @Override
    public IFluidStack withTag(IData tag) {
        
        if(tag != null) {
            MapData map = new MapData(tag.asMap());
            getInternal().tag(map.getInternal());
        } else {
            getInternal().tag(null);
        }
        
        return this;
    }
    
    @Override
    public boolean hasTag() {
        
        return getInternal().tag() != null;
    }
    
    @Override
    public CompoundTag getInternalTag() {
        
        return getInternal().tag();
    }
    
    @Override
    public IData getTag() {
        
        return TagToDataConverter.convert(getInternal().tag());
    }
    
    @Override
    public SimpleFluidStack getInternal() {
        
        return stack;
    }
    
    @Override
    public SimpleFluidStack getImmutableInternal() {
        
        return stack.copy();
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
        
        final SimpleFluidStack thatStack = ((MCFluidStackMutable) o).getInternal();
        final SimpleFluidStack thisStack = getInternal();
        
        if(thisStack.isEmpty()) {
            return thatStack.isEmpty();
        }
        
        if(thisStack.amount() != thatStack.amount()) {
            return false;
        }
        
        if(!Objects.equals(thisStack.fluid(), thatStack.fluid())) {
            return false;
        }
        
        return Objects.equals(thisStack.tag(), thatStack.tag());
    }
    
    @Override
    public int hashCode() {
        
        return Objects.hash(getInternal().amount(), getInternal().fluid(), getInternal().tag());
    }
    
}
