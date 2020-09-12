package com.blamejared.crafttweaker.impl.fluid;

import com.blamejared.crafttweaker.api.fluid.*;
import net.minecraft.fluid.*;
import net.minecraftforge.fluids.*;

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
}
