/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc1710.liquid;

import minetweaker.api.data.IData;
import minetweaker.api.item.*;
import minetweaker.api.liquid.ILiquidDefinition;
import minetweaker.api.liquid.ILiquidStack;
import minetweaker.api.player.IPlayer;
import minetweaker.mc1710.data.NBTConverter;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;

import java.util.Collections;
import java.util.List;

/**
 * @author Stan
 */
public class MCLiquidStack implements ILiquidStack {
    private final FluidStack stack;
    private IData tag = null;

    public MCLiquidStack(FluidStack stack) {
        this.stack = stack;
    }

    private MCLiquidStack(FluidStack stack, IData tag) {
        this.stack = stack;
        this.tag = tag;
    }

    @Override
    public ILiquidDefinition getDefinition() {
        return new MCLiquidDefinition(stack.getFluid());
    }

    @Override
    public String getName() {
        return stack.getFluid().getName();
    }

    @Override
    public String getDisplayName() {
        return stack.getFluid().getLocalizedName();
    }

    @Override
    public int getAmount() {
        return stack.amount;
    }

    @Override
    public IData getTag() {
        if (tag == null) {
            if (stack.tag == null) {
                return null;
            }

            tag = NBTConverter.from(stack.tag, true);
        }

        return tag;
    }

    @Override
    public ILiquidStack withTag(IData data) {
        FluidStack result = new FluidStack(stack.getFluid(), stack.amount);
        result.tag = (NBTTagCompound) NBTConverter.from(data);
        return new MCLiquidStack(result, data.immutable());
    }

    @Override
    public ILiquidStack withAmount(int amount) {
        FluidStack result = new FluidStack(stack.getFluid(), amount);
        result.tag = stack.tag;
        return new MCLiquidStack(result, tag);
    }

    @Override
    public int getLuminosity() {
        return stack.getFluid().getLuminosity(stack);
    }

    @Override
    public int getDensity() {
        return stack.getFluid().getDensity(stack);
    }

    @Override
    public int getTemperature() {
        return stack.getFluid().getTemperature(stack);
    }

    @Override
    public int getViscosity() {
        return stack.getFluid().getViscosity(stack);
    }

    @Override
    public boolean isGaseous() {
        return stack.getFluid().isGaseous(stack);
    }

    @Override
    public Object getInternal() {
        return stack;
    }

    // ##################################
    // ### IIngredient implementation ###
    // ##################################

    @Override
    public String getMark() {
        return null;
    }

    @Override
    public List<IItemStack> getItems() {
        return Collections.emptyList();
    }

    @Override
    public List<ILiquidStack> getLiquids() {
        return Collections.<ILiquidStack>singletonList(this);
    }

    @Override
    public IIngredient amount(int amount) {
        return withAmount(amount);
    }

    @Override
    public IIngredient or(IIngredient ingredient) {
        return new IngredientOr(this, ingredient);
    }

    @Override
    public IIngredient transform(IItemTransformer transformer) {
        throw new UnsupportedOperationException("Liquid stack can't have transformers");
    }

    @Override
    public IIngredient only(IItemCondition condition) {
        throw new UnsupportedOperationException("Liquid stack can't have conditions");
    }

    @Override
    public IIngredient marked(String mark) {
        throw new UnsupportedOperationException("Liquid stack can't be marked");
    }

    @Override
    public boolean matches(IItemStack item) {
        return false;
    }

    @Override
    public boolean matches(ILiquidStack liquid) {
        return getDefinition().equals(liquid.getDefinition())
                && getAmount() <= liquid.getAmount();
    }

    @Override
    public boolean matchesExact(IItemStack item) {
        return false;
    }

    @Override
    public boolean contains(IIngredient ingredient) {
        if (!ingredient.getItems().isEmpty())
            return false;

        for (ILiquidStack liquid : ingredient.getLiquids()) {
            if (!matches(liquid))
                return false;
        }

        return false;
    }

    @Override
    public IItemStack applyTransform(IItemStack item, IPlayer byPlayer) {
        return item;
    }

    @Override
    public boolean hasTransformers() {
        return false;
    }
}
