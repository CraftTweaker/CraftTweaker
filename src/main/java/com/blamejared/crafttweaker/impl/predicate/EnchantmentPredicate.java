package com.blamejared.crafttweaker.impl.predicate;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.enchantment.Enchantment;
import org.openzen.zencode.java.ZenCodeType;

/**
 * Represents the predicate for an {@link Enchantment}.
 *
 * The predicate can be used to check various properties of a specific enchantment, such as its level.
 *
 * This predicate must specify an enchantment to apply to. By default, the enchantment check will pass irregardless of
 * the enchantment's level.
 */
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
    
    /**
     * Sets the enchantment that needs to be present, and whose data needs to be checked.
     *
     * This parameter is <strong>required</strong>.
     *
     * @param enchantment The enchantment that needs to be present.
     *
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public EnchantmentPredicate withEnchantment(final Enchantment enchantment) {
        
        this.enchantment = enchantment;
        return this;
    }
    
    /**
     * Sets the minimum value the level should be to <code>min</code>.
     *
     * If the level had already some bounds specified, then the minimum value of the bound will be overwritten with the
     * value specified in <code>min</code>. On the other hand, if the level didn't have any bounds set, the minimum is
     * set, leaving the upper end unbounded.
     *
     * The minimum value is inclusive, meaning that a value that is equal to <code>min</code> will pass the check.
     *
     * @param min The minimum value the level should be.
     *
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public EnchantmentPredicate withMinimumLevel(final int min) {
        
        this.levels = IntRangePredicate.mergeLowerBound(this.levels, min);
        return this;
    }
    
    /**
     * Sets the maximum value the level should be to <code>max</code>.
     *
     * If the level had already some bounds specified, then the maximum value of the bound will be overwritten with the
     * value specified in <code>max</code>. On the other hand, if the level didn't have any bounds set, the maximum is
     * set, leaving the lower end unbounded.
     *
     * The maximum value is inclusive, meaning that a value that is equal to <code>max</code> will pass the check.
     *
     * @param max The maximum value the level should be.
     *
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public EnchantmentPredicate withMaximumLevel(final int max) {
        
        this.levels = IntRangePredicate.mergeUpperBound(this.levels, max);
        return this;
    }
    
    /**
     * Sets both the minimum and maximum value the level should be to <code>min</code> and <code>max</code>
     * respectively.
     *
     * If the level had already some bounds specified, then they will be overwritten with the new values.
     *
     * Both minimum and maximum values are inclusive, meaning that a value that is equal to either <code>min</code> or
     * <code>max</code> will pass the check.
     *
     * @param min The minimum value the level should be.
     * @param max The maximum value the level should be.
     *
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public EnchantmentPredicate withRangedLevel(final int min, final int max) {
        
        this.levels = IntRangePredicate.bounded(min, max);
        return this;
    }
    
    /**
     * Sets the level to exactly match the given <code>value</code>.
     *
     * If the level had already some bounds specified, then they will be overwritten with the new value.
     *
     * @param value The exact value the level should be.
     *
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public EnchantmentPredicate withExactLevel(final int value) {
        
        return this.withRangedLevel(value, value);
    }
    
    @Override
    public boolean isAny() {
        
        return this.enchantment == null && this.levels.isAny();
    }
    
    @Override
    public net.minecraft.advancements.criterion.EnchantmentPredicate toVanilla() {
        
        if(this.enchantment == null) {
            throw new IllegalStateException("Unable to build an enchantment predicate without an enchantment");
        }
        return new net.minecraft.advancements.criterion.EnchantmentPredicate(this.enchantment, this.levels.toVanillaPredicate());
    }
    
}
