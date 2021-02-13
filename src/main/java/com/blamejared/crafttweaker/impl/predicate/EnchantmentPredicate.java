package com.blamejared.crafttweaker.impl.predicate;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.enchantment.Enchantment;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.predicate.EnchantmentPredicate")
@Document("vanilla/api/predicate/EnchantmentPredicate")
public final class EnchantmentPredicate extends IVanillaWrappingPredicate.AnyDefaulting<net.minecraft.advancements.criterion.EnchantmentPredicate> {
    private Enchantment enchantment;
    private IntRangePredicate levels;

    public EnchantmentPredicate() {
        super(net.minecraft.advancements.criterion.EnchantmentPredicate.ANY);
        this.levels = IntRangePredicate.unbounded();
    }

    @ZenCodeType.Method
    public EnchantmentPredicate withEnchantment(final Enchantment enchantment) {
        this.enchantment = enchantment;
        return this;
    }

    @ZenCodeType.Method
    public EnchantmentPredicate withMinimumLevel(final int min) {
        this.levels = IntRangePredicate.mergeLowerBound(this.levels, min);
        return this;
    }

    @ZenCodeType.Method
    public EnchantmentPredicate withMaximumLevel(final int max) {
        this.levels = IntRangePredicate.mergeUpperBound(this.levels, max);
        return this;
    }

    @ZenCodeType.Method
    public EnchantmentPredicate withRangedLevel(final int min, final int max) {
        this.levels = IntRangePredicate.bounded(min, max);
        return this;
    }

    @ZenCodeType.Method
    public EnchantmentPredicate withExactLevel(final int value) {
        return this.withRangedLevel(value, value);
    }

    @Override
    public boolean isAny() {
        return this.enchantment != null && this.levels.isAny();
    }

    @Override
    public net.minecraft.advancements.criterion.EnchantmentPredicate toVanilla() {
        return new net.minecraft.advancements.criterion.EnchantmentPredicate(this.enchantment, this.levels.toVanillaPredicate());
    }
}
