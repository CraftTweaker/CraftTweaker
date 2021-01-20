package com.blamejared.crafttweaker.impl.predicate;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.impl.helper.CraftTweakerHelper;
import com.blamejared.crafttweaker.impl.tag.MCTag;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.fluid.Fluid;
import org.openzen.zencode.java.ZenCodeType;

import java.util.function.Consumer;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.predicate.FluidPredicate")
@Document("vanilla/api/predicate/FluidPredicate")
public final class FluidPredicate extends IVanillaWrappingPredicate.AnyDefaulting<net.minecraft.advancements.criterion.FluidPredicate> {
    private Fluid fluid;
    private MCTag<Fluid> fluidTag;
    private StatePropertiesPredicate states;

    public FluidPredicate() {
        super(net.minecraft.advancements.criterion.FluidPredicate.ANY);
        this.states = new StatePropertiesPredicate();
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
    public FluidPredicate withStatePropertiesPredicate(final Consumer<StatePropertiesPredicate> builder) {
        final StatePropertiesPredicate predicate = new StatePropertiesPredicate();
        builder.accept(predicate);
        this.states = predicate;
        return this;
    }

    @Override
    public boolean isAny() {
        return this.fluid == null && this.fluidTag == null && this.states.isAny();
    }

    @Override
    public net.minecraft.advancements.criterion.FluidPredicate toVanilla() {
        if (this.fluidTag != null && this.fluid != null) {
            CraftTweakerAPI.logWarning("'FluidPredicate' specifies both a fluid and a tag: the second will take precedence");
        }
        return new net.minecraft.advancements.criterion.FluidPredicate(
                this.fluidTag != null? CraftTweakerHelper.getITag(this.fluidTag) : null,
                this.fluid,
                this.states.toVanillaPredicate()
        );
    }
}
