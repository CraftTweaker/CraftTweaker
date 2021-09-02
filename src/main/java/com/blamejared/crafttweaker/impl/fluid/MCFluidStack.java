package com.blamejared.crafttweaker.impl.fluid;

import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.data.NBTConverter;
import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.crafttweaker.impl.data.MapData;
import net.minecraft.fluid.Fluid;
import net.minecraftforge.fluids.FluidStack;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Objects;
import java.util.function.Supplier;

public class MCFluidStack implements IFluidStack {
    
    public static Supplier<MCFluidStack> EMPTY = () -> new MCFluidStack(FluidStack.EMPTY);
    private final FluidStack stack;
    
    public MCFluidStack(FluidStack fluidStack) {
        
        this.stack = fluidStack;
    }
    
    
    @Override
    public String getCommandString() {
        
        final Fluid fluid = getInternal().getFluid();
        final StringBuilder builder = new StringBuilder().append("<fluid:")
                .append(fluid.getRegistryName())
                .append(">");
    
        if(getInternal().hasTag()) {
            MapData data = (MapData) NBTConverter.convert(getInternal().getTag()).copyInternal();
            if(!data.isEmpty()) {
                builder.append(".withTag(");
                builder.append(data.asString());
                builder.append(")");
            }
        }
    
        if(!isEmpty()) {
            if(getInternal().getAmount() != 1) {
                builder.append(" * ").append(getInternal().getAmount());
            }
        }
        return builder.toString();
    }
    
    @Override
    public IFluidStack setAmount(int amount) {
        
        final FluidStack copy = getInternal().copy();
        copy.setAmount(amount);
        return new MCFluidStack(copy);
    }
    
    @Override
    public IFluidStack multiply(int amount) {
        
        return setAmount(amount);
    }
    
    
    @Override
    public IFluidStack mutable() {
        
        return new MCFluidStackMutable(getInternal());
    }
    
    @Override
    public IFluidStack asImmutable() {
        
        return this;
    }
    
    @Override
    public boolean isImmutable() {
        
        return true;
    }
    
    @Override
    public IFluidStack copy() {
        //We have to copy, in case someone calls ".copy().mutable"
        return new MCFluidStack(getInternal().copy());
    }
    
    @Override
    public Fluid getFluid() {
        
        return getInternal().getFluid();
    }
    
    
    @Override
    public IFluidStack withTag(IData tag) {
        
        final FluidStack copy = getInternal().copy();
        if(!(tag instanceof MapData)) {
            tag = new MapData(tag.asMap());
        }
        copy.setTag(((MapData) tag).getInternal());
        return new MCFluidStack(copy);
    }
    
    @Override
    public IData getTag() {
        
        return NBTConverter.convert(getInternal().getTag());
    }
    
    @Override
    public FluidStack getInternal() {
        
        return stack;
    }
    
    @Override
    public FluidStack getImmutableInternal() {
        
        return stack;
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
        
        final FluidStack thatStack = ((MCFluidStack) o).getInternal();
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
