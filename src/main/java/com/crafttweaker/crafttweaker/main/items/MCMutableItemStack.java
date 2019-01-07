package com.crafttweaker.crafttweaker.main.items;

import com.crafttweaker.crafttweaker.api.items.IItemStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

public class MCMutableItemStack implements IItemStack {
    
    private final ItemStack stack;
    
    public MCMutableItemStack(ItemStack stack) {
        this.stack = stack;
    }
    
    
    @Override
    public IItemStack mutable() {
        return this;
    }
    
    @Override
    public IItemStack updateTag() {
        return this;
    }
    
    @Override
    public IItemStack withAmount(int amount) {
        this.stack.setCount(amount);
        return this;
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
        return stack;
    }
    
    @Override
    public Ingredient asVanillaIngredient() {
        return Ingredient.fromStacks(stack);
    }
    
    @Override
    public String toBracketString() {
        return String.format("<%s:%d>", this.stack.getItem().delegate.name(), this.stack.getDamage());
    }
    
    @Override
    public IItemStack grow(int size) {
        this.stack.grow(size);
        return this;
    }
    
    @Override
    public IItemStack shrink(int size) {
        this.stack.shrink(size);
        return this;
    }
}
