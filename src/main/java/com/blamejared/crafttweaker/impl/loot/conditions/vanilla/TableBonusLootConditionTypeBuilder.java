package com.blamejared.crafttweaker.impl.loot.conditions.vanilla;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.loot.conditions.ILootCondition;
import com.blamejared.crafttweaker.impl.loot.conditions.ILootConditionTypeBuilder;
import com.blamejared.crafttweaker.impl_native.loot.ExpandLootContext;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Arrays;

/**
 * Builder for the 'TableBonus' loot condition.
 *
 * This condition can be used to draw a random number between 0.0 (inclusive) and 1.0 (exclusive). This number will then
 * be checked against an enchantment level-specific chance and the condition will pass only if the drawn number is less
 * than the given one. Effectively, this allows the creation of a condition that will only pass a certain number of
 * times, as specified by the percentage value.
 *
 * Specifying more chances than the maximum level of an enchantment will simply ignore the values that are too high for
 * the current enchantment. On the contrary, specifying less values will effectively duplicate the last value across all
 * remaining levels.
 *
 * A 'TableBonus' condition requires an enchantment and a set of chances to be built. Moreover, a well-formed
 * 'TableBonus' condition has at least one of the various chances set to a value between 0.0 and 1.0 (both exclusive).
 * If that's not the case, the 'TableBonus' will either always pass no matter the enchantment level, if the value is
 * equal to or higher than 1.0, or always fail no matter the enchantment level, if the value is equal to or less than
 * 0.0.
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.conditions.vanilla.TableBonus")
@Document("vanilla/api/loot/conditions/vanilla/TableBonus")
public final class TableBonusLootConditionTypeBuilder implements ILootConditionTypeBuilder {
    
    private Enchantment enchantment;
    private float[] chances;
    
    TableBonusLootConditionTypeBuilder() {}
    
    /**
     * Sets the enchantment that should be checked on the tool.
     *
     * This parameter is <p>required</p>.
     *
     * @param enchantment The enchantment to check.
     *
     * @return This builder for chaining.
     */
    @ZenCodeType.Method
    public TableBonusLootConditionTypeBuilder withEnchantment(final Enchantment enchantment) {
        
        this.enchantment = enchantment;
        return this;
    }
    
    /**
     * Sets the array of chances, which will be queried according to the level.
     *
     * Note that it is not required, although advised, to have enough elements to cover all possible levels of the
     * enchantment. The condition will automatically readjust the array as needed.
     *
     * A well-formed condition also requires that at least one of the values of the array be between 0.0 and 1.0,
     * excluding extremes, to ensure at least one level has a chance to match.
     *
     * This parameter is <p>required</p>.
     *
     * @param chances The chances to use depending on level.
     *
     * @return This builder for chaining.
     */
    @ZenCodeType.Method
    public TableBonusLootConditionTypeBuilder withChances(final float[] chances) {
        
        this.chances = chances;
        return this;
    }
    
    @Override
    public ILootCondition finish() {
        
        if(this.enchantment == null) {
            throw new IllegalStateException("Enchantment not set");
        }
        if(this.chances.length == 0) {
            throw new IllegalStateException("Unable to have an empty set of chances for a table bonus");
        }
        
        boolean foundHigherThanZero = false;
        boolean foundSmallerThanOne = false;
        
        for(float chance : this.chances) {
            if(chance < 1.0F) {
                foundSmallerThanOne = true;
            }
            if(chance > 0.0F) {
                foundHigherThanZero = true;
            }
        }
        
        if(!foundHigherThanZero) {
            CraftTweakerAPI.logWarning(
                    "The chance values in a 'TableBonus' condition are all less than or equal to 0.0: this will never match! Conditions: %s",
                    Arrays.toString(this.chances)
            );
        }
        if(!foundSmallerThanOne) {
            CraftTweakerAPI.logWarning(
                    "The chance values in a 'TableBonus' condition are all more than or equal to 1.0: this will always match! Conditions: %s",
                    Arrays.toString(this.chances)
            );
        }
        
        return context -> {
            final ItemStack stack = ExpandLootContext.getTool(context).getInternal();
            final int chancesIndex = stack.isEmpty() ? 0 : EnchantmentHelper.getEnchantmentLevel(this.enchantment, stack);
            final float chance = this.chances[Math.min(chancesIndex, this.chances.length - 1)];
            return context.getRandom().nextFloat() < chance;
        };
    }
    
}
