package com.blamejared.crafttweaker.impl.loot.conditions.predicate;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.impl.loot.conditions.IntRange;
import com.blamejared.crafttweaker.impl.loot.conditions.TriState;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.advancements.criterion.MobEffectsPredicate;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.conditions.predicate.EffectData")
@Document("vanilla/api/loot/conditions/predicate/EffectData")
public final class EffectData {
    private IntRange amplifier;
    private IntRange duration;
    private TriState ambient;
    private TriState visible;

    public EffectData() {
        this.ambient = TriState.UNSET;
        this.visible = TriState.UNSET;
    }

    @ZenCodeType.Method
    public EffectData withAmplifier(final int min, final int max) {
        this.amplifier = new IntRange(min, max);
        return this;
    }

    @ZenCodeType.Method
    public EffectData withAmplifier(final int value) {
        return this.withAmplifier(value, value);
    }

    @ZenCodeType.Method
    public EffectData withDuration(final int min, final int max) {
        this.duration = new IntRange(min, max);
        return this;
    }

    @ZenCodeType.Method
    public EffectData withDuration(final int value) {
        return this.withDuration(value, value);
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

    boolean isAny() {
        return this.amplifier == null && this.duration == null && this.ambient == TriState.UNSET && this.visible == TriState.UNSET;
    }

    MobEffectsPredicate.InstancePredicate toVanillaEffectsPredicate() {
        if (this.isAny()) return new MobEffectsPredicate.InstancePredicate();
        return new MobEffectsPredicate.InstancePredicate(this.toVanilla(this.amplifier), this.toVanilla(this.duration), this.ambient.toBoolean(), this.visible.toBoolean());
    }

    private MinMaxBounds.IntBound toVanilla(final IntRange range) {
        if (range == null) return MinMaxBounds.IntBound.UNBOUNDED;
        return range.toVanillaIntBound();
    }
}
