package com.blamejared.crafttweaker.impl.loot.conditions.predicate;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.impl.tag.MCTag;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.fluid.Fluid;
import net.minecraft.tags.ITag;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.conditions.predicate.FluidPredicate")
@Document("vanilla/api/loot/conditions/predicate/FluidPredicate")
public final class FluidPredicate {
    private final StateBasedPredicateHelper helper;
    private Fluid fluid;
    private MCTag<Fluid> fluidTag;

    public FluidPredicate() {
        this.helper = new StateBasedPredicateHelper();
    }

    @ZenCodeType.Method
    public FluidPredicate withFluid(final Fluid fluid) {
        this.fluid = fluid;
        return this;
    }

    @ZenCodeType.Method
    public FluidPredicate withFluidTag(final MCTag<Fluid> fluidTag) {
        this.fluidTag = fluidTag;
        return this;
    }

    @ZenCodeType.Method
    public FluidPredicate withProperty(final String name, final String value) {
        this.helper.addExactProperty(name, value);
        return this;
    }

    @ZenCodeType.Method
    public FluidPredicate withProperty(final String name, final int value) {
        return this.withProperty(name, Integer.toString(value));
    }

    @ZenCodeType.Method
    public FluidPredicate withProperty(final String name, final boolean value) {
        return this.withProperty(name, Boolean.toString(value));
    }

    @ZenCodeType.Method
    public FluidPredicate withRangedProperty(final String name, @ZenCodeType.Nullable final String min, @ZenCodeType.Nullable final String max) {
        this.helper.addRangedProperty(name, min, max);
        return this;
    }

    @ZenCodeType.Method
    public FluidPredicate withRangedProperty(final String name, final int min, final int max) {
        return this.withRangedProperty(name, Integer.toString(min), Integer.toString(max));
    }

    @ZenCodeType.Method
    public FluidPredicate withUpperBoundedProperty(final String name, final int max) {
        return this.withRangedProperty(name, null, Integer.toString(max));
    }

    @ZenCodeType.Method
    public FluidPredicate withLowerBoundedProperty(final String name, final int min) {
        return this.withRangedProperty(name, Integer.toString(min), null);
    }

    boolean isAny() {
        return this.fluid == null && this.fluidTag == null && this.helper.isAny();
    }

    public net.minecraft.advancements.criterion.FluidPredicate toVanilla() {
        if (this.isAny()) return net.minecraft.advancements.criterion.FluidPredicate.ANY;
        return new net.minecraft.advancements.criterion.FluidPredicate(
                this.toVanilla(this.fluidTag),
                this.fluid,
                this.helper.toVanilla()
        );
    }

    @SuppressWarnings("unchecked")
    private <T> ITag<T> toVanilla(final MCTag<T> tag) {
        if (tag == null) return null;
        return (ITag<T>) tag.getInternal();
    }
}
