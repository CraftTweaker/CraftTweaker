package com.blamejared.crafttweaker.impl_native.fluid;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.crafttweaker.impl.fluid.MCFluidStack;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.fluid.Fluid;
import net.minecraftforge.fluids.FluidStack;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@NativeTypeRegistration(value = Fluid.class, zenCodeName = "crafttweaker.api.fluid.MCFluid")
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
