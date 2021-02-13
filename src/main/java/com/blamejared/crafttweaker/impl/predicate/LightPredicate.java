package com.blamejared.crafttweaker.impl.predicate;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.predicate.LightPredicate")
@Document("vanilla/api/predicate/LightPredicate")
public final class LightPredicate extends IVanillaWrappingPredicate.AnyDefaulting<net.minecraft.advancements.criterion.LightPredicate> {
    private IntRangePredicate range;

    public LightPredicate() {
        super(net.minecraft.advancements.criterion.LightPredicate.ANY);
        this.range = IntRangePredicate.unbounded();
    }

    @ZenCodeType.Method
    public LightPredicate withMinimumLightLevel(final int min) {
        this.range = IntRangePredicate.mergeLowerBound(this.range, min);
        return this;
    }

    @ZenCodeType.Method
    public LightPredicate withMaximumLightLevel(final int max) {
        this.range = IntRangePredicate.mergeUpperBound(this.range, max);
        return this;
    }

    @ZenCodeType.Method
    public LightPredicate withBoundedLightLevel(final int min, final int max) {
        this.range = IntRangePredicate.bounded(min, max);
        return this;
    }

    @Override
    public boolean isAny() {
        return this.range.isAny();
    }

    @Override
    public net.minecraft.advancements.criterion.LightPredicate toVanilla() {
        return new net.minecraft.advancements.criterion.LightPredicate(this.range.toVanillaPredicate());
    }
}
