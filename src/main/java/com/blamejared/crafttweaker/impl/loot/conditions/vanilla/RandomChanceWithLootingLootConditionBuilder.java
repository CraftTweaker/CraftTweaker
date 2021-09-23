package com.blamejared.crafttweaker.impl.loot.conditions.vanilla;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.loot.conditions.ILootCondition;
import com.blamejared.crafttweaker.impl.loot.conditions.ILootConditionTypeBuilder;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

/**
 * Builder for the 'RandomChanceWithLooting' loot condition.
 *
 * This condition can be used to draw a random number between 0.0 (inclusive) and 1.0 (exclusive). This number will then
 * be checked against the specified chance multiplied by the looting modifier provided by the
 * {@link net.minecraft.loot.LootContext}. The condition will then pass only if the drawn number is less than the
 * computed one. Effectively, this allows the creation of a condition that will only run a certain percentage of times,
 * as specified by the chance value, with this percentage steadily increasing the higher the looting modifier is.
 *
 * A 'RandomChanceWithLooting' condition is well-formed if the chance is between 0.0 and 1.0 (both exclusive) and the
 * looting modifier is different from 0.0. If that's not the case, then the 'RandomChanceWithLooting' will either always
 * fail, if the chance is less than or equal to 0.0, always pass, if the chance is higher than or equal to 1.0, or be
 * equivalent to a 'RandomChance' loot condition, if the looting modifier is 0.0.
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.conditions.vanilla.RandomChanceWithLooting")
@Document("vanilla/api/loot/conditions/vanilla/RandomChanceWithLooting")
public final class RandomChanceWithLootingLootConditionBuilder implements ILootConditionTypeBuilder {
    
    private float chance;
    private float lootingMultiplier;
    
    RandomChanceWithLootingLootConditionBuilder() {}
    
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
    public RandomChanceWithLootingLootConditionBuilder withChance(final float chance) {
        
        this.chance = chance;
        return this;
    }
    
    /**
     * Sets the looting modifier of the loot condition.
     *
     * The given number must be different from 0.0, otherwise the resulting loot condition won't be well-formed.
     * Negative values have to be treated with care, as they cannot be heuristically proven to cause the loot table to
     * never pass, but may lead to such scenarios.
     *
     * This parameter is <strong>required</strong>.
     *
     * @param lootingMultiplier The looting modifier of the condition.
     *
     * @return This builder for chaining.
     */
    @ZenCodeType.Method
    public RandomChanceWithLootingLootConditionBuilder withLootingMultiplier(final float lootingMultiplier) {
        
        this.lootingMultiplier = lootingMultiplier;
        return this;
    }
    
    @Override
    public ILootCondition finish() {
        
        if(this.chance >= 1.0) {
            CraftTweakerAPI.logWarning(
                    "Chance in a 'RandomChanceWithLooting' condition is a number that is equal to or higher than 1.0 (currently: %f): this condition will always match!",
                    this.chance
            );
        } else if(this.chance <= 0.0) {
            CraftTweakerAPI.logWarning(
                    "Chance in a 'RandomChanceWithLooting' condition is a number that is equal to or lower than 0.0 (currently: %f): this condition will never match!",
                    this.chance
            );
        }
        if(this.lootingMultiplier == 0.0) {
            CraftTweakerAPI.logWarning("Looting modifier in a 'RandomChanceWithLooting' condition is 0.0: this condition is equivalent to a 'RandomChance' condition");
        }
        return context -> context.getRandom()
                .nextFloat() < (this.chance + context.getLootingModifier() * this.lootingMultiplier);
    }
    
}
