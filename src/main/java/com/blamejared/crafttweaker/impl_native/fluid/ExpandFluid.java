package com.blamejared.crafttweaker.impl_native.fluid;

import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker.api.fluid.*;
import com.blamejared.crafttweaker.impl.fluid.MCFluidStack;
import com.blamejared.crafttweaker_annotations.annotations.*;
import net.minecraft.fluid.*;
import net.minecraftforge.fluids.*;
import org.openzen.zencode.java.*;

@ZenRegister
@DocumentAsType
@NativeExpansion(Fluid.class)
public class ExpandFluid {
    
    /**
     * Creates a new {@link IFluidStack} with the given amount of fluid.
     *
     * @return a new (immutable) {@link IFluidStack}
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
     * @docParam amount 1000
     */
    @ZenCodeType.Method
    public static IFluidStack makeStack(Fluid internal, int amount) {
        return new MCFluidStack(new FluidStack(internal, amount));
    }
    
    @ZenCodeType.Getter("commandString")
    public static String getCommandString(Fluid internal) {
        return "<fluid:" + internal.getRegistryName() + ">.fluid";
    }
    
}
