package com.blamejared.crafttweaker.impl.loot.conditions;

import com.google.gson.JsonObject;
import net.minecraft.advancements.criterion.MinMaxBounds;

public final class FloatRange {
    private final float min;
    private final float max;
    private final boolean exclude;

    public FloatRange(final float min, final float max) {
        this(min, max, false);
    }

    public FloatRange(final float min, final float max, final boolean exclude) {
        this.min = Math.min(min, max);
        this.max = Math.max(min, max);
        this.exclude = exclude;
    }

    public float getMin() {
        return this.min;
    }

    public float getMax() {
        return this.max;
    }

    public boolean match(final int target) {
        return this.min <= target && ((this.exclude && target < this.max) || (!this.exclude && target <= this.max));
    }

    public MinMaxBounds.FloatBound toVanillaFloatBound() {
        final JsonObject json = new JsonObject();
        json.addProperty("min", this.min);
        json.addProperty("max", this.max);
        return MinMaxBounds.FloatBound.fromJson(json);
    }
}
