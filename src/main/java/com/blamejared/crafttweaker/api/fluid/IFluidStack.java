package com.blamejared.crafttweaker.api.fluid;

import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker.api.brackets.*;
import com.blamejared.crafttweaker.impl.fluid.*;
import com.blamejared.crafttweaker_annotations.annotations.*;
import net.minecraftforge.fluids.*;
import org.openzen.zencode.java.*;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.fluid.IFluidStack")
@Document("vanilla/api/fluid/IFluidStack")
@ZenWrapper(wrappedClass = "net.minecraftforge.fluids.FluidStack", displayStringFormat = "%.getCommandString()", creationMethodFormat = "new MCFluidStack(%s)", implementingClass = "com.blamejared.crafttweaker.impl.fluid.MCFluidStack")
public interface IFluidStack extends CommandStringDisplayable {
    
    /**
     * Sets the fluid amount in MilliBuckets (MB)
     *
     * @param amount The amount to multiply this stack
     * @return A new stack, or this stack, depending if this stack is mutable
     */
    @ZenCodeType.Method
    IFluidStack setAmount(int amount);
    
    
    /**
     * Sets the fluid amount in MilliBuckets (MB)
     *
     * @param amount The amount to multiply this stack
     * @return A new stack, or this stack, depending if this stack is mutable
     */
    @ZenCodeType.Operator(ZenCodeType.OperatorType.MUL)
    IFluidStack multiply(int amount);
    
    /**
     * Makes this stack mutable
     *
     * @return A new Stack, that is mutable.
     */
    @ZenCodeType.Method
    IFluidStack mutable();
    
    /**
     * Copies the stack. Only needed when mutable stacks are involved.
     *
     * @return A new stack, that contains the same info as this one
     */
    @ZenCodeType.Method
    IFluidStack copy();
    
    /**
     * Retrieves this fluid stack's fluid.
     * @return The fluid.
     */
    @ZenCodeType.Getter("fluid")
    MCFluid getFluid();
    
    /**
     * Moddevs, use this to get the Vanilla version.
     */
    FluidStack getInternal();
}
