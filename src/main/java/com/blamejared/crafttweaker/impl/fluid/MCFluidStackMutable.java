package com.blamejared.crafttweaker.impl.fluid;

import com.blamejared.crafttweaker.api.fluid.*;
import net.minecraft.fluid.*;
import net.minecraftforge.fluids.*;
import org.openzen.zencode.java.*;

import java.util.*;

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
        stack.setAmount(amount);
        return this;
    }
    
    @Override
    public IFluidStack mutable() {
        return this;
    }
    
    @Override
    public MCFluid getFluid() {
        return new MCFluid(stack.getFluid());
    }
    
    @Override
    public IFluidStack copy() {
        return new MCFluidStackMutable(stack.copy());
    }
    
    @Override
    public FluidStack getInternal() {
        return stack;
    }
    
    @Override
    public String getCommandString() {
        final Fluid fluid = stack.getFluid();
        
        final StringBuilder stringBuilder = new StringBuilder("<fluid:");
        stringBuilder.append(fluid.getRegistryName());
        stringBuilder.append(">.mutable()");
        
        if(stack.getAmount() != 1) {
            stringBuilder.append(" * ");
            stringBuilder.append(stack.getAmount());
        }
        
        return stringBuilder.toString();
    }
    
    @Override
    @ZenCodeType.Operator(ZenCodeType.OperatorType.EQUALS)
    public boolean equals(Object o) {
        if(this == o)
            return true;
        if(o == null || getClass() != o.getClass())
            return false;
        
        final FluidStack thatStack = ((MCFluidStackMutable) o).stack;
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
