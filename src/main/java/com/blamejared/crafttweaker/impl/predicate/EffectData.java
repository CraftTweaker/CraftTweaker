package com.blamejared.crafttweaker.impl.predicate;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.advancements.criterion.MobEffectsPredicate;
import org.openzen.zencode.java.ZenCodeType;

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
        this.amplifier = IntRangePredicate.unlimited();
        this.duration = IntRangePredicate.unlimited();
        this.ambient = TriState.UNSET;
        this.visible = TriState.UNSET;
    }

    @ZenCodeType.Method
    public EffectData withMinimumAmplifier(final int min) {
        this.amplifier = IntRangePredicate.lowerBounded(min);
        return this;
    }

    @ZenCodeType.Method
    public EffectData withMaximumAmplifier(final int max) {
        this.amplifier = IntRangePredicate.upperBounded(max);
        return this;
    }

    @ZenCodeType.Method
    public EffectData withBoundedAmplifier(final int min, final int max) {
        this.amplifier = IntRangePredicate.bounded(min, max);
        return this;
    }

    @ZenCodeType.Method
    public EffectData withExactAmplifier(final int value) {
        return this.withBoundedAmplifier(value, value);
    }

    @ZenCodeType.Method
    public EffectData withMinimumDuration(final int min) {
        this.duration = IntRangePredicate.lowerBounded(min);
        return this;
    }

    @ZenCodeType.Method
    public EffectData withMaximumDuration(final int max) {
        this.duration = IntRangePredicate.upperBounded(max);
        return this;
    }

    @ZenCodeType.Method
    public EffectData withBoundedDuration(final int min, final int max) {
        this.duration = IntRangePredicate.bounded(min, max);
        return this;
    }

    @ZenCodeType.Method
    public EffectData withExactDuration(final int value) {
        return this.withBoundedDuration(value, value);
    }

    @ZenCodeType.Method
    public EffectData withAmbient() {
        this.ambient = TriState.TRUE;
        return this;
    }

    @ZenCodeType.Method
    public EffectData withoutAmbient() {
        this.ambient = TriState.FALSE;
        return this;
    }

    @ZenCodeType.Method
    public EffectData withVisibility() {
        this.visible = TriState.TRUE;
        return this;
    }

    @ZenCodeType.Method
    public EffectData withInvisibility() {
        this.visible = TriState.FALSE;
        return this;
    }

    @Override
    public boolean isAny() {
        return this.amplifier.isAny() && this.duration.isAny() && this.ambient == TriState.UNSET && this.visible == TriState.UNSET;
    }

    @Override
    public MobEffectsPredicate.InstancePredicate toVanilla() {
        return new MobEffectsPredicate.InstancePredicate(this.amplifier.toVanillaPredicate(), this.duration.toVanillaPredicate(), this.ambient.toBoolean(), this.visible.toBoolean());
    }
}
