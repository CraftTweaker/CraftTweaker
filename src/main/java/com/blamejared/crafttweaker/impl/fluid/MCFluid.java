package com.blamejared.crafttweaker.impl.fluid;

import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker.api.brackets.*;
import com.blamejared.crafttweaker.api.fluid.*;
import com.blamejared.crafttweaker_annotations.annotations.*;
import net.minecraft.fluid.*;
import net.minecraftforge.fluids.*;
import org.openzen.zencode.java.*;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.fluid.MCFluid")
@Document("vanilla/api/fluid/MCFluid")
@ZenWrapper(wrappedClass = "net.minecraft.fluid.Fluid", displayStringFormat = "%.getCommandString()")
public class MCFluid implements CommandStringDisplayable {
    
    private final Fluid fluid;
    
    public MCFluid(Fluid fluid) {
        this.fluid = fluid;
    }
    
    /**
     * Creates a new {@link IFluidStack} with the given amount of fluid.
     *
     * @return a new (immutable) {@link IFluidStack}
     * @docParam amount 1000
     */
    @ZenCodeType.Operator(ZenCodeType.OperatorType.MUL)
    public IFluidStack multiply(int amount) {
        return makeStack(amount);
    }
    
    /**
     * Creates a new {@link IFluidStack} with the given amount of fluid.
     *
     * @return a new (immutable) {@link IFluidStack}
     * @docParam amount 1000
     */
    @ZenCodeType.Method
    public IFluidStack makeStack(int amount) {
        return new MCFluidStack(new FluidStack(fluid, amount));
    }
    
    @Override
    public String getCommandString() {
        return "<fluid:" + fluid.getRegistryName() + ">.fluid";
    }
    
    /**
     * Moddevs, use this to get the Vanilla Fluid
     *
     * @return The Vanilla Fluid
     */
    public Fluid getInternal() {
        return fluid;
    }
    
    @Override
    public boolean equals(Object o) {
        if(this == o)
            return true;
        if(o == null || getClass() != o.getClass())
            return false;
        
        MCFluid mcFluid = (MCFluid) o;
        
        return fluid.equals(mcFluid.fluid);
    }
    
    @Override
    public int hashCode() {
        return fluid.hashCode();
    }
}
