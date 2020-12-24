package com.blamejared.crafttweaker.api.fluid;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.brackets.CommandStringDisplayable;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.ZenWrapper;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.fluid.IFluidStack")
@Document("vanilla/api/fluid/IFluidStack")
@ZenWrapper(wrappedClass = "net.minecraftforge.fluids.FluidStack", displayStringFormat = "%.getCommandString()", creationMethodFormat = "new MCFluidStack(%s)", implementingClass = "com.blamejared.crafttweaker.impl.fluid.MCFluidStack")
public interface IFluidStack extends CommandStringDisplayable {
    
    /**
     * Gets the registry name for the fluid this stack is representing.
     *
     * @return A MCResourceLocation representing the registry name.
     */
    @ZenCodeType.Getter("registryName")
    default ResourceLocation getRegistryName() {
        return getFluid().getRegistryName();
    }
    
    /**
     * Checks if this IFluidStack, contains the given IFluidStack by checking if the fluids are the same, and if this fluid's amount is bigger than the given fluid's amount
     *
     * @param other other IFluidStack to compare against
     * @return true if this fluid contains the other fluid
     */
    @ZenCodeType.Method
    @ZenCodeType.Operator(ZenCodeType.OperatorType.CONTAINS)
    default boolean containsOther(IFluidStack other) {
        return this.getInternal().containsFluid(other.getInternal());
    }
    
    /**
     * Gets whether or not this fluid stack is empty.
     *
     * @return {@code true} if this stack is empty, {@code false} otherwise.
     */
    @ZenCodeType.Getter("empty")
    default boolean isEmpty() {
        return getInternal().isEmpty();
    }
    
    /**
     * Gets the fluid amount in MilliBuckets (mB).
     *
     * @return The amount of this fluid
     */
    @ZenCodeType.Getter("amount")
    default int getAmount() {
        return getInternal().getAmount();
    }
    
    /**
     * Sets the fluid amount in MilliBuckets (mB)
     *
     * @param amount The amount to multiply this stack
     * @return A new stack, or this stack, depending if this stack is mutable
     * @docParam amount 1000
     */
    @ZenCodeType.Method
    IFluidStack setAmount(int amount);
    
    
    /**
     * Sets the fluid amount in MilliBuckets (MB)
     *
     * @param amount The amount to multiply this stack
     * @return A new stack, or this stack, depending if this stack is mutable
     * @docParam amount 1000
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
     *
     * @return The fluid.
     */
    @ZenCodeType.Getter("fluid")
    @ZenCodeType.Caster(implicit = true)
    Fluid getFluid();
    
    /**
     * Moddevs, use this to get the Vanilla version.
     */
    FluidStack getInternal();
}
