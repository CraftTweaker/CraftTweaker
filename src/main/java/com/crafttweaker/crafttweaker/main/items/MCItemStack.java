package com.crafttweaker.crafttweaker.main.items;

import com.crafttweaker.crafttweaker.api.items.IItemStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

public class MCItemStack implements IItemStack {
    
    private final ItemStack stack;
    
    public MCItemStack(ItemStack stack) {
        this.stack = stack;
    }
    
    @Override
    public IItemStack mutable() {
        return new MCMutableItemStack(getInternal());
    }
    
    @Override
    public IItemStack updateTag() {
        final ItemStack stack = this.stack.copy();
        return new MCItemStack(stack);
    }
    
    @Override
    public IItemStack withAmount(int amount) {
        final ItemStack stack = getInternal().copy();
        stack.setCount(amount);
        return new MCItemStack(stack);
    }
    
    @Override
    public IItemStack grow(int amount) {
        final ItemStack copy = this.stack.copy();
        copy.grow(amount);
        return new MCItemStack(copy);
    }
    
    @Override
    public IItemStack shrink(int amount) {
        final ItemStack copy = this.stack.copy();
        copy.shrink(amount);
        return new MCItemStack(copy);
    }
    
    //    @Override
    //    public List<IItemStack> getItems() {
    //        return Collections.singletonList(this);
    //    }
    //
    //    @Override
    //    public List<ILiquidStack> getLiquids() {
    //        return FluidUtil.getFluidContained(stack)
    //                .map(MCLiquidStack::new)
    //                .map(Collections::<ILiquidStack>singletonList)
    //                .orElseGet(Collections::emptyList);
    //    }
    
    @Override
    public ItemStack getInternal() {
        return stack.copy();
    }
    
    @Override
    public Ingredient asVanillaIngredient() {
        return Ingredient.fromStacks(stack);
    }
    
    @Override
    public String toBracketString() {
        return String.format("<%s:%d>", this.stack.getItem().delegate.name(), this.stack.getDamage());
    }
}
