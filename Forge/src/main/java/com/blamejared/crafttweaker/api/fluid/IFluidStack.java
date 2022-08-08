package com.blamejared.crafttweaker.api.fluid;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.bracket.CommandStringDisplayable;
import com.blamejared.crafttweaker.api.data.MapData;
import com.blamejared.crafttweaker.platform.Services;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import org.openzen.zencode.java.ZenCodeType;

import java.util.ArrayList;
import java.util.List;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.fluid.IFluidStack")
@Document("forge/api/fluid/IFluidStack")
public interface IFluidStack extends CommandStringDisplayable {
    
    /**
     * Gets the registry name for the fluid this stack is representing.
     *
     * @return A MCResourceLocation representing the registry name.
     */
    @ZenCodeType.Getter("registryName")
    default ResourceLocation getRegistryName() {
        
        return Registry.FLUID.getKey(getFluid());
    }
    
    /**
     * Checks if this IFluidStack, contains the given IFluidStack by checking if the fluids are the same, and if this fluid's amount is bigger than the given fluid's amount
     *
     * @param other other IFluidStack to compare against
     *
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
     *
     * @return A new stack, or this stack, depending if this stack is mutable
     *
     * @docParam amount 1000
     */
    @ZenCodeType.Method
    IFluidStack setAmount(int amount);
    
    
    /**
     * Sets the fluid amount in MilliBuckets (MB)
     *
     * @param amount The amount to multiply this stack
     *
     * @return A new stack, or this stack, depending if this stack is mutable
     *
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
    
    @ZenCodeType.Method
    IFluidStack asImmutable();
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isImmutable")
    boolean isImmutable();
    
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
     * Returns the NBT tag attached to this FluidStack.
     *
     * @return MapData of the FluidStack's NBT Tag, null if it doesn't exist.
     */
    @ZenCodeType.Getter("tag")
    @ZenCodeType.Method
    MapData getTag();
    
    /**
     * Sets the tag for the FluidStack.
     *
     * @param tag The tag to set.
     *
     * @return This FluidStack if it is mutable, a new one with the changed property otherwise
     *
     * @docParam tag {Display: {lore: ["Hello"]}}
     */
    @ZenCodeType.Method
    IFluidStack withTag(@ZenCodeType.Nullable MapData tag);
    
    /**
     * Returns true if this FluidStack has a Tag
     *
     * @return true if tag is present.
     */
    @ZenCodeType.Getter("hasTag")
    default boolean hasTag() {
        
        return getInternal().hasTag();
    }
    
    /**
     * Moddevs, use this to get the Vanilla version.
     */
    FluidStack getInternal();
    
    FluidStack getImmutableInternal();
    
    
    @ZenCodeType.Caster(implicit = true)
    default CTFluidIngredient asFluidIngredient() {
        
        return new CTFluidIngredient.FluidStackIngredient(this);
    }
    
    @ZenCodeType.Operator(ZenCodeType.OperatorType.OR)
    default CTFluidIngredient asList(CTFluidIngredient other) {
        
        List<CTFluidIngredient> elements = new ArrayList<>();
        elements.add(asFluidIngredient());
        elements.add(other);
        return new CTFluidIngredient.CompoundFluidIngredient(elements);
    }
    
}
