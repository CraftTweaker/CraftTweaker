package com.blamejared.crafttweaker.api.fluid;

import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.data.MapData;
import com.blamejared.crafttweaker.api.data.converter.tag.TagToDataConverter;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidStack;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Objects;

public class MCFluidStackMutable implements IFluidStack {

    private final FluidStack stack;

    public MCFluidStackMutable(FluidStack stack) {

        this.stack = stack;
    }

    @Override
    public boolean isEmpty() {

        return getInternal().isEmpty();
    }

    @Override
    public long getAmount() {

        return getInternal().getAmount();
    }

    @Override
    public IFluidStack setAmount(int amount) {

        getInternal().setAmount(amount);
        return this;
    }

    @Override
    public boolean isImmutable() {

        return false;
    }

    @Override
    public Fluid getFluid() {

        return getInternal().getFluid();
    }

    @Override
    public IFluidStack withTag(IData tag) {

        if (tag != null) {
            MapData map = new MapData(tag.asMap());
            getInternal().setTag(map.getInternal());
        } else {
            getInternal().setTag(null);
        }

        return this;
    }

    @Override
    public boolean hasTag() {

        return getInternal().hasTag();
    }

    @Override
    public CompoundTag getInternalTag() {

        return getInternal().getTag();
    }

    @Override
    public IData getTag() {

        return TagToDataConverter.convert(getInternal().getTag());
    }

    @Override
    public FluidStack getInternal() {

        return stack;
    }

    @Override
    public FluidStack getImmutableInternal() {

        return stack.copy();
    }

    @Override
    @ZenCodeType.Operator(ZenCodeType.OperatorType.EQUALS)
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final FluidStack thatStack = ((MCFluidStackMutable) o).getInternal();
        final FluidStack thisStack = getInternal();

        if (thisStack.isEmpty()) {
            return thatStack.isEmpty();
        }

        if (thisStack.getAmount() != thatStack.getAmount()) {
            return false;
        }

        if (!Objects.equals(thisStack.getFluid(), thatStack.getFluid())) {
            return false;
        }

        return Objects.equals(thisStack.getTag(), thatStack.getTag());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getInternal().getAmount(), getInternal().getFluid(), getInternal().getTag());
    }

}
