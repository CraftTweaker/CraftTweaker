package com.blamejared.crafttweaker.impl.predicate;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

import java.util.stream.Stream;

/**
 * Represents a predicate for distance.
 *
 * The predicate can effectively be used to check the distance between two coordinates in 3D-space. The distance will
 * firstly be checked separately against the x, y, and z bounds that have been specified. If those checks succeed, then
 * horizontal distance will be taken into consideration, measuring the distance between the two points only along the X
 * and Z axis. Lastly, the absolute distance considering all axis will be checked.
 *
 * Any of the above checks will be skipped if no value has been specified for any of them. By default, the predicate
 * will not add any restrictions on the distance between two points.
 */
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
        this.absoluteDistance = FloatRangePredicate.unbounded();
        this.horizontalDistance = FloatRangePredicate.unbounded();
        this.xDistance = FloatRangePredicate.unbounded();
        this.yDistance = FloatRangePredicate.unbounded();
        this.zDistance = FloatRangePredicate.unbounded();
    }

    /**
     * Sets the minimum value the absolute distance should be to <code>min</code>.
     *
     * If the absolute distance had already some bounds specified, then the minimum value of the bound will be
     * overwritten with the value specified in <code>min</code>. On the other hand, if the absolute distance didn't have
     * any bounds set, the minimum is set, leaving the upper end unbounded.
     *
     * The minimum value is inclusive, meaning that a value that is equal to <code>min</code> will pass the check.
     *
     * @param min The minimum value the absolute distance should be.
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public DistancePredicate withMinimumAbsoluteDistance(final float min) {
        this.absoluteDistance = FloatRangePredicate.mergeLowerBound(this.absoluteDistance, min);
        return this;
    }

    /**
     * Sets the maximum value the absolute distance should be to <code>max</code>.
     *
     * If the absolute distance had already some bounds specified, then the maximum value of the bound will be
     * overwritten with the value specified in <code>max</code>. On the other hand, if the absolute distance didn't have
     * any bounds set, the maximum is set, leaving the lower end unbounded.
     *
     * The maximum value is inclusive, meaning that a value that is equal to <code>max</code> will pass the check.
     *
     * @param max The maximum value the absolute distance should be.
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public DistancePredicate withMaximumAbsoluteDistance(final float max) {
        this.absoluteDistance = FloatRangePredicate.mergeUpperBound(this.absoluteDistance, max);
        return this;
    }

    /**
     * Sets both the minimum and maximum value the absolute distance should be to <code>min</code> and <code>max</code>
     * respectively.
     *
     * If the absolute distance had already some bounds specified, then they will be overwritten with the new values.
     *
     * Both minimum and maximum values are inclusive, meaning that a value that is equal to either <code>min</code> or
     * <code>max</code> will pass the check.
     *
     * @param min The minimum value the absolute distance should be.
     * @param max The maximum value the absolute distance should be.
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public DistancePredicate withRangedAbsoluteDistance(final float min, final float max) {
        this.absoluteDistance = FloatRangePredicate.bounded(min, max);
        return this;
    }

    /**
     * Sets the minimum value the horizontal distance should be to <code>min</code>.
     *
     * If the horizontal distance had already some bounds specified, then the minimum value of the bound will be
     * overwritten with the value specified in <code>min</code>. On the other hand, if the horizontal distance didn't
     * have any bounds set, the minimum is set, leaving the upper end unbounded.
     *
     * The minimum value is inclusive, meaning that a value that is equal to <code>min</code> will pass the check.
     *
     * @param min The minimum value the horizontal distance should be.
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public DistancePredicate withMinimumHorizontalDistance(final float min) {
        this.horizontalDistance = FloatRangePredicate.mergeLowerBound(this.horizontalDistance, min);
        return this;
    }

    /**
     * Sets the maximum value the horizontal distance should be to <code>max</code>.
     *
     * If the horizontal distance had already some bounds specified, then the maximum value of the bound will be
     * overwritten with the value specified in <code>max</code>. On the other hand, if the horizontal distance didn't
     * have any bounds set, the maximum is set, leaving the lower end unbounded.
     *
     * The maximum value is inclusive, meaning that a value that is equal to <code>max</code> will pass the check.
     *
     * @param max The maximum value the horizontal distance should be.
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public DistancePredicate withMaximumHorizontalDistance(final float max) {
        this.horizontalDistance = FloatRangePredicate.mergeUpperBound(this.horizontalDistance, max);
        return this;
    }

    /**
     * Sets both the minimum and maximum value the horizontal distance should be to <code>min</code> and
     * <code>max</code> respectively.
     *
     * If the horizontal distance had already some bounds specified, then they will be overwritten with the new values.
     *
     * Both minimum and maximum values are inclusive, meaning that a value that is equal to either <code>min</code> or
     * <code>max</code> will pass the check.
     *
     * @param min The minimum value the horizontal distance should be.
     * @param max The maximum value the horizontal distance should be.
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public DistancePredicate withRangedHorizontalDistance(final float min, final float max) {
        this.horizontalDistance = FloatRangePredicate.bounded(min, max);
        return this;
    }

    /**
     * Sets the minimum value the distance along the X axis should be to <code>min</code>.
     *
     * If the distance had already some bounds specified, then the minimum value of the bound will be overwritten with
     * the value specified in <code>min</code>. On the other hand, if the distance didn't have any bounds set, the
     * minimum is set, leaving the upper end unbounded.
     *
     * The minimum value is inclusive, meaning that a value that is equal to <code>min</code> will pass the check.
     *
     * @param min The minimum value the distance along the X axis should be.
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public DistancePredicate withMinimumX(final float min) {
        this.xDistance = FloatRangePredicate.mergeLowerBound(this.xDistance, min);
        return this;
    }

    /**
     * Sets the maximum value the distance along the X axis should be to <code>max</code>.
     *
     * If the distance had already some bounds specified, then the maximum value of the bound will be overwritten with
     * the value specified in <code>max</code>. On the other hand, if the distance didn't have any bounds set, the
     * maximum is set, leaving the lower end unbounded.
     *
     * The maximum value is inclusive, meaning that a value that is equal to <code>max</code> will pass the check.
     *
     * @param max The maximum value the distance along the X axis should be.
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public DistancePredicate withMaximumX(final float max) {
        this.xDistance = FloatRangePredicate.mergeUpperBound(this.xDistance, max);
        return this;
    }

    /**
     * Sets both the minimum and maximum value the distance along the X axis should be to <code>min</code> and
     * <code>max</code> respectively.
     *
     * If the distance had already some bounds specified, then they will be overwritten with the new values.
     *
     * Both minimum and maximum values are inclusive, meaning that a value that is equal to either <code>min</code> or
     * <code>max</code> will pass the check.
     *
     * @param min The minimum value the distance along the X axis should be.
     * @param max The maximum value the distance along the X axis should be.
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public DistancePredicate withRangedX(final float min, final float max) {
        this.xDistance = FloatRangePredicate.bounded(min, max);
        return this;
    }

    /**
     * Sets the minimum value the distance along the Y axis should be to <code>min</code>.
     *
     * If the distance had already some bounds specified, then the minimum value of the bound will be overwritten with
     * the value specified in <code>min</code>. On the other hand, if the distance didn't have any bounds set, the
     * minimum is set, leaving the upper end unbounded.
     *
     * The minimum value is inclusive, meaning that a value that is equal to <code>min</code> will pass the check.
     *
     * @param min The minimum value the distance along the Y axis should be.
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public DistancePredicate withMinimumY(final float min) {
        this.yDistance = FloatRangePredicate.mergeLowerBound(this.yDistance, min);
        return this;
    }

    /**
     * Sets the maximum value the distance along the Y axis should be to <code>max</code>.
     *
     * If the distance had already some bounds specified, then the maximum value of the bound will be overwritten with
     * the value specified in <code>max</code>. On the other hand, if the distance didn't have any bounds set, the
     * maximum is set, leaving the lower end unbounded.
     *
     * The maximum value is inclusive, meaning that a value that is equal to <code>max</code> will pass the check.
     *
     * @param max The maximum value the distance along the Y axis should be.
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public DistancePredicate withMaximumY(final float max) {
        this.yDistance = FloatRangePredicate.mergeUpperBound(this.yDistance, max);
        return this;
    }

    /**
     * Sets both the minimum and maximum value the distance along the Y axis should be to <code>min</code> and
     * <code>max</code> respectively.
     *
     * If the distance had already some bounds specified, then they will be overwritten with the new values.
     *
     * Both minimum and maximum values are inclusive, meaning that a value that is equal to either <code>min</code> or
     * <code>max</code> will pass the check.
     *
     * @param min The minimum value the distance along the Y axis should be.
     * @param max The maximum value the distance along the Y axis should be.
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public DistancePredicate withRangedY(final float min, final float max) {
        this.yDistance = FloatRangePredicate.bounded(min, max);
        return this;
    }

    /**
     * Sets the minimum value the distance along the Z axis should be to <code>min</code>.
     *
     * If the distance had already some bounds specified, then the minimum value of the bound will be overwritten with
     * the value specified in <code>min</code>. On the other hand, if the distance didn't have any bounds set, the
     * minimum is set, leaving the upper end unbounded.
     *
     * The minimum value is inclusive, meaning that a value that is equal to <code>min</code> will pass the check.
     *
     * @param min The minimum value the distance along the Z axis should be.
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public DistancePredicate withMinimumZ(final float min) {
        this.zDistance = FloatRangePredicate.mergeLowerBound(this.zDistance, min);
        return this;
    }

    /**
     * Sets the maximum value the distance along the Z axis should be to <code>max</code>.
     *
     * If the distance had already some bounds specified, then the maximum value of the bound will be overwritten with
     * the value specified in <code>max</code>. On the other hand, if the distance didn't have any bounds set, the
     * maximum is set, leaving the lower end unbounded.
     *
     * The maximum value is inclusive, meaning that a value that is equal to <code>max</code> will pass the check.
     *
     * @param max The maximum value the distance along the Z axis should be.
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public DistancePredicate withMaximumZ(final float max) {
        this.zDistance = FloatRangePredicate.mergeUpperBound(this.zDistance, max);
        return this;
    }

    /**
     * Sets both the minimum and maximum value the distance along the Z axis should be to <code>min</code> and
     * <code>max</code> respectively.
     *
     * If the distance had already some bounds specified, then they will be overwritten with the new values.
     *
     * Both minimum and maximum values are inclusive, meaning that a value that is equal to either <code>min</code> or
     * <code>max</code> will pass the check.
     *
     * @param min The minimum value the distance along the Z axis should be.
     * @param max The maximum value the distance along the Z axis should be.
     * @return This predicate for chaining.
     */
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
