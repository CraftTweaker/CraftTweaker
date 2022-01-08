package com.blamejared.crafttweaker.natives.fluid;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.crafttweaker.api.fluid.MCFluidStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Expansion("crafttweaker.api.fluid.Fluid")
public class ExpandFluidForge {
    
    /**
     * Creates a new {@link IFluidStack} with the given amount of fluid.
     *
     * @return a new (immutable) {@link IFluidStack}
     *
     * @docParam amount 1000
     */
    @ZenCodeType.Operator(ZenCodeType.OperatorType.MUL)
    public static IFluidStack multiply(Fluid internal, int amount) {
        
        return makeStack(internal, amount);
    }
    
    
    /**
     * Creates a new {@link IFluidStack} with the given amount of fluid.
     *
     * @return a new (immutable) {@link IFluidStack}
     *
     * @docParam amount 1000
     */
    @ZenCodeType.Method
    public static IFluidStack makeStack(Fluid internal, int amount) {
        
        return new MCFluidStack(new FluidStack(internal, amount));
    }
    
}
