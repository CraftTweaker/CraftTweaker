package com.blamejared.crafttweaker.impl.predicate;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

import java.util.stream.Stream;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.predicate.DistancePredicate")
@Document("vanilla/api/predicate/DistancePredicate")
public final class DistancePredicate extends IVanillaWrappingPredicate.AnyDefaulting<net.minecraft.advancements.criterion.DistancePredicate> {
    private FloatRangePredicate absoluteDistance;
    private FloatRangePredicate horizontalDistance;
    private FloatRangePredicate xDistance;
    private FloatRangePredicate yDistance;
    private FloatRangePredicate zDistance;

    public DistancePredicate() {
        super(net.minecraft.advancements.criterion.DistancePredicate.ANY);
        this.absoluteDistance = FloatRangePredicate.unlimited();
        this.horizontalDistance = FloatRangePredicate.unlimited();
        this.xDistance = FloatRangePredicate.unlimited();
        this.yDistance = FloatRangePredicate.unlimited();
        this.zDistance = FloatRangePredicate.unlimited();
    }

    @ZenCodeType.Method
    public DistancePredicate withMinimumAbsoluteDistance(final float min) {
        this.absoluteDistance = FloatRangePredicate.lowerBounded(min);
        return this;
    }

    @ZenCodeType.Method
    public DistancePredicate withMaximumAbsoluteDistance(final float max) {
        this.absoluteDistance = FloatRangePredicate.upperBounded(max);
        return this;
    }

    @ZenCodeType.Method
    public DistancePredicate withRangedAbsoluteDistance(final float min, final float max) {
        this.absoluteDistance = FloatRangePredicate.bounded(min, max);
        return this;
    }

    @ZenCodeType.Method
    public DistancePredicate withMinimumHorizontalDistance(final float min) {
        this.horizontalDistance = FloatRangePredicate.lowerBounded(min);
        return this;
    }

    @ZenCodeType.Method
    public DistancePredicate withMaximumHorizontalDistance(final float max) {
        this.horizontalDistance = FloatRangePredicate.upperBounded(max);
        return this;
    }

    @ZenCodeType.Method
    public DistancePredicate withRangedHorizontalDistance(final float min, final float max) {
        this.horizontalDistance = FloatRangePredicate.bounded(min, max);
        return this;
    }

    @ZenCodeType.Method
    public DistancePredicate withMinimumX(final float min) {
        this.xDistance = FloatRangePredicate.lowerBounded(min);
        return this;
    }

    @ZenCodeType.Method
    public DistancePredicate withMaximumX(final float max) {
        this.xDistance = FloatRangePredicate.upperBounded(max);
        return this;
    }

    @ZenCodeType.Method
    public DistancePredicate withRangedX(final float min, final float max) {
        this.xDistance = FloatRangePredicate.bounded(min, max);
        return this;
    }

    @ZenCodeType.Method
    public DistancePredicate withMinimumY(final float min) {
        this.yDistance = FloatRangePredicate.lowerBounded(min);
        return this;
    }

    @ZenCodeType.Method
    public DistancePredicate withMaximumY(final float max) {
        this.yDistance = FloatRangePredicate.upperBounded(max);
        return this;
    }

    @ZenCodeType.Method
    public DistancePredicate withRangedY(final float min, final float max) {
        this.yDistance = FloatRangePredicate.bounded(min, max);
        return this;
    }

    @ZenCodeType.Method
    public DistancePredicate withMinimumZ(final float min) {
        this.zDistance = FloatRangePredicate.lowerBounded(min);
        return this;
    }

    @ZenCodeType.Method
    public DistancePredicate withMaximumZ(final float max) {
        this.zDistance = FloatRangePredicate.upperBounded(max);
        return this;
    }

    @ZenCodeType.Method
    public DistancePredicate withRangedZ(final float min, final float max) {
        this.zDistance = FloatRangePredicate.bounded(min, max);
        return this;
    }

    @Override
    public boolean isAny() {
        return Stream.of(this.absoluteDistance, this.horizontalDistance, this.xDistance, this.yDistance, this.zDistance)
                .allMatch(FloatRangePredicate::isAny);
    }

    @Override
    public net.minecraft.advancements.criterion.DistancePredicate toVanilla() {
        return new net.minecraft.advancements.criterion.DistancePredicate(
                this.xDistance.toVanillaPredicate(),
                this.yDistance.toVanillaPredicate(),
                this.zDistance.toVanillaPredicate(),
                this.horizontalDistance.toVanillaPredicate(),
                this.absoluteDistance.toVanillaPredicate()
        );
    }
}
