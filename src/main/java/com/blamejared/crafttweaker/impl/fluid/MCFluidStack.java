package com.blamejared.crafttweaker.impl.fluid;

import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.data.NBTConverter;
import com.blamejared.crafttweaker.api.fluid.*;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.impl.data.MapData;
import com.blamejared.crafttweaker.impl.item.MCItemStack;
import net.minecraft.fluid.*;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.*;
import org.openzen.zencode.java.*;

import java.util.*;

public class MCFluidStack implements IFluidStack {
    
    private final FluidStack stack;
    
    public MCFluidStack(FluidStack fluidStack) {
        
        stack = fluidStack;
    }
    
    
    @Override
    public String getCommandString() {
        
        final Fluid fluid = stack.getFluid();
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
        
        if(stack.getAmount() != 1) {
            builder.append(" * ").append(stack.getAmount());
        }
        return builder.toString();
    }
    
    @Override
    public IFluidStack setAmount(int amount) {
        
        final FluidStack copy = stack.copy();
        copy.setAmount(amount);
        return new MCFluidStack(copy);
    }
    
    @Override
    public IFluidStack multiply(int amount) {
        
        return setAmount(amount);
    }
    
    
    @Override
    public IFluidStack mutable() {
        
        return new MCFluidStackMutable(stack);
    }
    
    @Override
    public IFluidStack copy() {
        //We have to copy, in case someone calls ".copy().mutable"
        return new MCFluidStack(stack.copy());
    }
    
    @Override
    public Fluid getFluid() {
        
        return stack.getFluid();
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
    @ZenCodeType.Operator(ZenCodeType.OperatorType.EQUALS)
    public boolean equals(Object o) {
    
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        
        final FluidStack thatStack = ((MCFluidStack) o).stack;
        final FluidStack thisStack = this.stack;
        
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
        
        return Objects.hash(stack.getAmount(), stack.getFluid(), stack.getTag());
    }
    
}
