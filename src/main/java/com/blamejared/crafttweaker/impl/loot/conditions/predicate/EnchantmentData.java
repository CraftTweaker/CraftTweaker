package com.blamejared.crafttweaker.impl.loot.conditions.predicate;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.impl.loot.conditions.IntRange;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.advancements.criterion.EnchantmentPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.enchantment.Enchantment;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.conditions.predicate.EnchantmentData")
@Document("vanilla/api/loot/conditions/predicate/EnchantmentData")
public final class EnchantmentData {
    private IntRange levels;

    public EnchantmentData() {}

    @ZenCodeType.Method
    public EnchantmentData withLevels(final int min, final int max) {
        this.levels = new IntRange(min, max);
        return this;
    }

    @ZenCodeType.Method
    public EnchantmentData withLevels(final int value) {
        return this.withLevels(value, value);
    }

    boolean isAny() {
        return this.levels == null;
    }

    public EnchantmentPredicate toVanilla(final Enchantment associatedEnchantment) {
        if (associatedEnchantment == null && this.isAny()) return EnchantmentPredicate.ANY;
        return new EnchantmentPredicate(associatedEnchantment, this.toVanilla(this.levels));
    }

    private MinMaxBounds.IntBound toVanilla(final IntRange range) {
        return range == null? MinMaxBounds.IntBound.UNBOUNDED : range.toVanillaIntBound();
    }
}
