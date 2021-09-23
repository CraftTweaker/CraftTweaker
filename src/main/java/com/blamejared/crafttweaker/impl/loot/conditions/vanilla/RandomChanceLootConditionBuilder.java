package com.blamejared.crafttweaker.impl.loot.conditions.vanilla;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.loot.conditions.ILootCondition;
import com.blamejared.crafttweaker.impl.loot.conditions.ILootConditionTypeBuilder;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

/**
 * Builder for the 'RandomChance' loot condition.
 *
 * This condition can be used to draw a random number between 0.0 (inclusive) and 1.0 (exclusive). This number will then
 * be checked against the specified chance and the condition will pass only if the drawn number is less than the given
 * one. Effectively, this allows the creation of a condition that will only run a certain percentage of times, as
 * specified by the chance value.
 *
 * A 'RandomChance' condition is well-formed if the given value is between 0.0 and 1.0 (both exclusive). If that's not
 * the case, then the 'RandomChance' will either always fail, if the value is less than or equal to 0.0, or always pass,
 * if the value is higher than or equal to 1.0.
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.conditions.vanilla.RandomChance")
@Document("vanilla/api/loot/conditions/vanilla/RandomChance")
public final class RandomChanceLootConditionBuilder implements ILootConditionTypeBuilder {
    
    private float chance;
    
    RandomChanceLootConditionBuilder() {}
    
    /**
     * Sets the chance of the loot condition successfully passing the check.
     *
     * The number is to be treated as a percentage from 0.0 to 1.0. Only values between these two extremes are to be
     * considered valid. Any other value will cause the resulting loot condition not to be well-formed.
     *
     * This parameter is <strong>required</strong>.
     *
     * @param chance The success rate of the condition.
     *
     * @return This builder for chaining.
     */
    @ZenCodeType.Method
    public RandomChanceLootConditionBuilder withChance(final float chance) {
        
        this.chance = chance;
        return this;
    }
    
    @Override
    public ILootCondition finish() {
        
        if(this.chance >= 1.0) {
            CraftTweakerAPI.logWarning(
                    "Chance in a 'RandomChance' condition is a number that is equal to or higher than 1.0 (currently: %f): this condition will always match!",
                    this.chance
            );
        } else if(this.chance <= 0.0) {
            CraftTweakerAPI.logWarning(
                    "Chance in a 'RandomChance' condition is a number that is equal to or lower than 0.0 (currently: %f): this condition will never match!",
                    this.chance
            );
        }
        return context -> context.getRandom().nextFloat() < this.chance;
    }
    
}
