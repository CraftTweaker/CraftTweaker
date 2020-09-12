package com.blamejared.crafttweaker.impl.fluid;

import com.blamejared.crafttweaker.api.fluid.*;
import net.minecraft.fluid.*;
import net.minecraftforge.fluids.*;

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
    public MCFluid getFluid() {
        return new MCFluid(stack.getFluid());
    }
    
    @Override
    public FluidStack getInternal() {
        return stack;
    }
}
