package com.blamejared.crafttweaker.api.fluid;

import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.data.LongData;
import com.blamejared.crafttweaker.api.data.MapData;
import com.blamejared.crafttweaker.api.data.converter.tag.TagToDataConverter;
import com.blamejared.crafttweaker.impl.fluid.SimpleFluidStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.material.Fluid;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Objects;

public class MCFluidStack implements IFluidStack {
    
    private final SimpleFluidStack stack;
    
    public MCFluidStack(SimpleFluidStack fluidStack) {
        
        this.stack = fluidStack;
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
        
        final SimpleFluidStack copy = getInternal().copy();
        copy.amount(amount);
        return IFluidStack.of(copy);
    }
    
    @Override
    public boolean isImmutable() {
        
        return true;
    }
    
    @Override
    public Fluid getFluid() {
        
        return getInternal().fluid();
    }
    
    @Override
    public IFluidStack withTag(@ZenCodeType.Nullable IData tag) {
        
        final SimpleFluidStack copy = getInternal().copy();
        if(tag != null) {
            MapData map = new MapData(tag.asMap());
            copy.tag(map.getInternal());
        } else {
            copy.tag(null);
        }
        
        return IFluidStack.of(copy);
    }
    
    @Override
    public boolean hasTag() {
        
        return getInternal().tag() != null;
    }
    
    @Override
    public IData getTag() {
        
        return TagToDataConverter.convert(getInternal().tag());
    }
    
    @Override
    public CompoundTag getInternalTag() {
        
        return getInternal().tag();
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
    public IData asIData() {
        
        IData data = IFluidStack.super.asIData();
        data.put("amount", new LongData(this.getAmount()));
        return data;
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
        
        final SimpleFluidStack thatStack = ((MCFluidStack) o).getInternal();
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
