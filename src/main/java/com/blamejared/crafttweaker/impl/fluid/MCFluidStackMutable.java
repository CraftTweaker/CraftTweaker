package com.blamejared.crafttweaker.impl.fluid;

import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.data.NBTConverter;
import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.crafttweaker.impl.data.MapData;
import net.minecraft.fluid.Fluid;
import net.minecraftforge.fluids.FluidStack;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Objects;

public class MCFluidStackMutable implements IFluidStack {
    
    private final FluidStack stack;
    
    public MCFluidStackMutable(FluidStack stack) {
        
        this.stack = stack;
    }
    
    
    @Override
    public IFluidStack setAmount(int amount) {
        
        return multiply(amount);
    }
    
    @Override
    public IFluidStack multiply(int amount) {
        
        getInternal().setAmount(amount);
        return this;
    }
    
    @Override
    public IFluidStack mutable() {
        
        return this;
    }
    
    @Override
    public IFluidStack asImmutable() {
        
        return new MCFluidStack(getInternal().copy());
    }
    
    @Override
    public boolean isImmutable() {
        
        return false;
    }
    
    @Override
    public Fluid getFluid() {
        
        return getInternal().getFluid();
    }
    
    @Override
    public IFluidStack withTag(IData tag) {
        
        if(!(tag instanceof MapData)) {
            tag = new MapData(tag.asMap());
        }
        getInternal().setTag(((MapData) tag).getInternal());
        return this;
    }
    
    @Override
    public IData getTag() {
        
        return NBTConverter.convert(getInternal().getTag());
    }
    
    @Override
    public IFluidStack copy() {
        
        return new MCFluidStackMutable(getInternal().copy());
    }
    
    @Override
    public FluidStack getInternal() {
        
        return stack;
    }
    
    @Override
    public FluidStack getImmutableInternal() {
        
        return stack.copy();
    }
    
    @Override
    public String getCommandString() {
        
        final Fluid fluid = getInternal().getFluid();
        
        final StringBuilder stringBuilder = new StringBuilder("<fluid:");
        stringBuilder.append(fluid.getRegistryName());
        stringBuilder.append(">");
        
        if(getInternal().hasTag()) {
            MapData data = (MapData) NBTConverter.convert(getInternal().getTag()).copyInternal();
            if(!data.isEmpty()) {
                stringBuilder.append(".withTag(");
                stringBuilder.append(data.asString());
                stringBuilder.append(")");
            }
        }
        
        if(!isEmpty()) {
            if(getInternal().getAmount() != 1) {
                stringBuilder.append(" * ").append(getInternal().getAmount());
            }
        }
        
        return stringBuilder.toString();
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
        
        final FluidStack thatStack = ((MCFluidStackMutable) o).getInternal();
        final FluidStack thisStack = getInternal();
        
        if(thisStack.isEmpty()) {
            return thatStack.isEmpty();
        }
        
        if(thisStack.getAmount() != thatStack.getAmount()) {
            return false;
        }
        
        if(!Objects.equals(thisStack.getFluid(), thatStack.getFluid())) {
            return false;
        }
        
        return Objects.equals(thisStack.getTag(), thatStack.getTag());
    }
    
    @Override
    public int hashCode() {
        
        return Objects.hash(getInternal().getAmount(), getInternal().getFluid(), getInternal().getTag());
    }
    
}
