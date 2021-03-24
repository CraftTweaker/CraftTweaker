package com.blamejared.crafttweaker.impl.predicate;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.impl.helper.CraftTweakerHelper;
import com.blamejared.crafttweaker.impl.tag.MCTag;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.fluid.Fluid;
import org.openzen.zencode.java.ZenCodeType;

import java.util.function.Consumer;

/**
 * Represents a predicate for a {@link Fluid}.
 *
 * This predicate will match a fluid state with either the given {@link Fluid} or fluid tag ({@link MCTag}), with the
 * second check taking precedence over the first if they are both present. If this comparison succeeds, then the
 * predicate may also verify additional fluid state properties via the supplied {@link StatePropertiesPredicate}.
 *
 * By default, this predicate allows any fluid state to pass the checks without restrictions.
 */
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

    /**
     * Sets the fluid that this predicate should match.
     *
     * If a tag to match against has already been set, then the tag check will take precedence over this check.
     *
     * @param fluid The fluid the predicate should match.
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public FluidPredicate withFluid(final Fluid fluid) {
        this.fluid = fluid;
        return this;
    }

    /**
     * Sets the tag that this predicate should use for matching.
     *
     * The predicate will successfully match only if the supplied fluid state's fluid is contained within the given tag.
     *
     * Specifying both a tag and a fluid to match against will make the tag take precedence over the fluid.
     *
     * @param fluidTag The tag the predicate should use for matching.
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public FluidPredicate withFluidTag(final MCTag<Fluid> fluidTag) {
        this.fluidTag = fluidTag;
        return this;
    }

    /**
     * Creates and sets the {@link StatePropertiesPredicate} that will be matched against the fluid state's properties.
     *
     * Any changes that have already been made to the state properties predicate will be overwritten, effectively
     * replacing the previous one, if any.
     *
     * @param builder A consumer that will be used to configure the {@link StatePropertiesPredicate}.
     * @return This predicate for chaining.
     */
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
