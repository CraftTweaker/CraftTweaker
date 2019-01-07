package com.crafttweaker.crafttweaker.api.liquids;

import com.crafttweaker.crafttweaker.main.ingredients.IIngredient;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.fluids.FluidStack;

public class MCFluidStack implements IIngredient<FluidStack> {
    
    private final FluidStack stack;
    
    public MCFluidStack(FluidStack stack) {
        this.stack = stack;
    }
    
    @Override
    public FluidStack getInternal() {
        return stack;
    }
    
    @Override
    public Ingredient asVanillaIngredient() {
        return Ingredient.EMPTY;
    }
    
    @Override
    public String toBracketString() {
        return String.format("<liquid:%s>%s", this.stack.getFluid().getName(), this.stack.amount == 1 ? "" : " * " + this.stack.amount);
    }
}
