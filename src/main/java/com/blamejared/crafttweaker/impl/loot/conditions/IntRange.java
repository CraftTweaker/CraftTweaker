package com.blamejared.crafttweaker.impl.loot.conditions;

import com.google.gson.JsonObject;
import net.minecraft.advancements.criterion.MinMaxBounds;

public final class IntRange {
    private final int min;
    private final int max;
    private final boolean exclude;

    public IntRange(final int min, final int max) {
        this(min, max, false);
    }

    public IntRange(final int min, final int max, final boolean exclude) {
        this.min = Math.min(min, max);
        this.max = Math.max(min, max);
        this.exclude = exclude;
    }

    public int getMin() {
        return this.min;
    }

    public int getMax() {
        return this.max;
    }

    public boolean match(final int target) {
        return this.min <= target && ((this.exclude && target < this.max) || (!this.exclude && target <= this.max));
    }

    public MinMaxBounds.IntBound toVanillaIntBound() {
        final JsonObject json = new JsonObject();
        json.addProperty("min", this.min);
        json.addProperty("max", this.max);
        return MinMaxBounds.IntBound.fromJson(json);
    }
}
