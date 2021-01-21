package com.blamejared.crafttweaker.impl.loot.conditions.vanilla;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.loot.conditions.ILootCondition;
import com.blamejared.crafttweaker.impl.loot.conditions.ILootConditionTypeBuilder;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.conditions.vanilla.RandomChance")
@Document("vanilla/api/loot/conditions/vanilla/RandomChance")
public final class RandomChanceLootConditionBuilder implements ILootConditionTypeBuilder {
    private float chance;

    RandomChanceLootConditionBuilder() {}

    @ZenCodeType.Method
    public RandomChanceLootConditionBuilder withChance(final float chance) {
        this.chance = chance;
        return this;
    }

    @Override
    public ILootCondition finish() {
        if (this.chance >= 1.0) {
            CraftTweakerAPI.logWarning(
                    "Chance in a 'RandomChance' condition is a number that is equal to or higher than 1.0 (currently: {}): this condition will always pass!",
                    this.chance
            );
        }
        if (this.chance <= 0.0) {
            CraftTweakerAPI.logWarning(
                    "Chance in a 'RandomChance' condition is a number that is equal to or lower than 0.0 (currently: {}): this condition will never pass!",
                    this.chance
            );
        }
        return context -> context.getRandom().nextFloat() < this.chance;
    }
}
