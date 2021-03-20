package com.blamejared.crafttweaker.impl.predicate;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.advancements.criterion.MobEffectsPredicate;
import org.openzen.zencode.java.ZenCodeType;

/**
 * Holds the data an {@link net.minecraft.potion.Effect} should have to pass a parent predicate check.
 *
 * This predicate can check various properties of the effect, such as its duration or amplifier value. It is also able
 * to verify whether the effect has been applied by the ambient or by a potion and whether the effect has visible
 * particles or not.
 *
 * By default, no restrictions are placed on the effect's data.
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.predicate.EffectData")
@Document("vanilla/api/predicate/EffectData")
public final class EffectData extends IVanillaWrappingPredicate.AnyDefaulting<MobEffectsPredicate.InstancePredicate> {
    private IntRangePredicate amplifier;
    private IntRangePredicate duration;
    private TriState ambient;
    private TriState visible;

    public EffectData() {
        super(MobEffectsPredicate.InstancePredicate::new);
        this.amplifier = IntRangePredicate.unbounded();
        this.duration = IntRangePredicate.unbounded();
        this.ambient = TriState.UNSET;
        this.visible = TriState.UNSET;
    }

    /**
     * Sets the minimum value the amplifier should be to <code>min</code>.
     *
     * If the amplifier had already some bounds specified, then the minimum value of the bound will be overwritten with
     * the value specified in <code>min</code>. On the other hand, if the amplifier didn't have any bounds set, the
     * minimum is set, leaving the upper end unbounded.
     *
     * The minimum value is inclusive, meaning that a value that is equal to <code>min</code> will pass the check.
     *
     * @param min The minimum value the amplifier should be.
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public EffectData withMinimumAmplifier(final int min) {
        this.amplifier = IntRangePredicate.mergeLowerBound(this.amplifier, min);
        return this;
    }

    /**
     * Sets the maximum value the amplifier should be to <code>max</code>.
     *
     * If the amplifier had already some bounds specified, then the maximum value of the bound will be overwritten with
     * the value specified in <code>max</code>. On the other hand, if the amplifier didn't have any bounds set, the
     * maximum is set, leaving the lower end unbounded.
     *
     * The maximum value is inclusive, meaning that a value that is equal to <code>max</code> will pass the check.
     *
     * @param max The maximum value the amplifier should be.
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public EffectData withMaximumAmplifier(final int max) {
        this.amplifier = IntRangePredicate.mergeUpperBound(this.amplifier, max);
        return this;
    }

    /**
     * Sets both the minimum and maximum value the amplifier should be to <code>min</code> and <code>max</code>
     * respectively.
     *
     * If the amplifier had already some bounds specified, then they will be overwritten with the new values.
     *
     * Both minimum and maximum values are inclusive, meaning that a value that is equal to either <code>min</code> or
     * <code>max</code> will pass the check.
     *
     * @param min The minimum value the amplifier should be.
     * @param max The maximum value the amplifier should be.
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public EffectData withBoundedAmplifier(final int min, final int max) {
        this.amplifier = IntRangePredicate.bounded(min, max);
        return this;
    }

    /**
     * Sets the amplifier to exactly match the given <code>value</code>.
     *
     * If the amplifier had already some bounds specified, then they will be overwritten with the new value.
     *
     * @param value The exact value the amplifier should be.
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public EffectData withExactAmplifier(final int value) {
        return this.withBoundedAmplifier(value, value);
    }

    /**
     * Sets the minimum value the duration should be to <code>min</code>.
     *
     * If the duration had already some bounds specified, then the minimum value of the bound will be overwritten with
     * the value specified in <code>min</code>. On the other hand, if the duration didn't have any bounds set, the
     * minimum is set, leaving the upper end unbounded.
     *
     * The minimum value is inclusive, meaning that a value that is equal to <code>min</code> will pass the check.
     *
     * @param min The minimum value the duration should be.
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public EffectData withMinimumDuration(final int min) {
        this.duration = IntRangePredicate.mergeLowerBound(this.duration, min);
        return this;
    }

    /**
     * Sets the maximum value the duration should be to <code>max</code>.
     *
     * If the duration had already some bounds specified, then the maximum value of the bound will be overwritten with
     * the value specified in <code>max</code>. On the other hand, if the duration didn't have any bounds set, the
     * maximum is set, leaving the lower end unbounded.
     *
     * The maximum value is inclusive, meaning that a value that is equal to <code>max</code> will pass the check.
     *
     * @param max The maximum value the duration should be.
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public EffectData withMaximumDuration(final int max) {
        this.duration = IntRangePredicate.mergeUpperBound(this.duration, max);
        return this;
    }

    /**
     * Sets both the minimum and maximum value the duration should be to <code>min</code> and <code>max</code>
     * respectively.
     *
     * If the duration had already some bounds specified, then they will be overwritten with the new values.
     *
     * Both minimum and maximum values are inclusive, meaning that a value that is equal to either <code>min</code> or
     * <code>max</code> will pass the check.
     *
     * @param min The minimum value the duration should be.
     * @param max The maximum value the duration should be.
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public EffectData withBoundedDuration(final int min, final int max) {
        this.duration = IntRangePredicate.bounded(min, max);
        return this;
    }

    /**
     * Sets the duration to exactly match the given <code>value</code>.
     *
     * If the duration had already some bounds specified, then they will be overwritten with the new value.
     *
     * @param value The exact value the duration should be.
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public EffectData withExactDuration(final int value) {
        return this.withBoundedDuration(value, value);
    }

    /**
     * Indicates that the effect must be environmental.
     *
     * An example of such effect is the one applied by a beacon.
     *
     * If the predicate had already been set to check the opposite condition, the setting will be overwritten.
     *
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public EffectData withAmbient() {
        this.ambient = TriState.TRUE;
        return this;
    }

    /**
     * Indicates that the effect must not be environmental.
     *
     * If the predicate had already been set to check the opposite condition, the setting will be overwritten.
     *
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public EffectData withoutAmbient() {
        this.ambient = TriState.FALSE;
        return this;
    }

    /**
     * Indicates that the effect's particles must be visible.
     *
     * If the predicate had already been set to check the opposite condition, the setting will be overwritten.
     *
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public EffectData withVisibility() {
        this.visible = TriState.TRUE;
        return this;
    }

    /**
     * Indicates that the effect's particles must be invisible.
     *
     * If the predicate had already been set to check the opposite condition, the setting will be overwritten.
     *
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public EffectData withInvisibility() {
        this.visible = TriState.FALSE;
        return this;
    }

    @Override
    public boolean isAny() {
        return this.amplifier.isAny() && this.duration.isAny() && this.ambient.isUnset() && this.visible.isUnset();
    }

    @Override
    public MobEffectsPredicate.InstancePredicate toVanilla() {
        return new MobEffectsPredicate.InstancePredicate(this.amplifier.toVanillaPredicate(), this.duration.toVanillaPredicate(), this.ambient.toBoolean(), this.visible.toBoolean());
    }
}
