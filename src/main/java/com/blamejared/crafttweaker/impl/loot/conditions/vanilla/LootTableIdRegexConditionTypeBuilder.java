package com.blamejared.crafttweaker.impl.loot.conditions.vanilla;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.loot.conditions.ILootCondition;
import com.blamejared.crafttweaker.impl.loot.conditions.ILootConditionTypeBuilder;
import com.blamejared.crafttweaker.impl_native.loot.ExpandLootContext;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

import java.util.regex.Pattern;

/**
 * Builder for a 'LootTableIdRegex' loot condition.
 *
 * This condition will pass if and only if the ID of the loot table currently being queried matches the target regex.
 *
 * While this condition is provided merely as a convenience, it is suggested not to rely on this condition only. Due to
 * backwards compatibility, some loot tables may in fact lack an ID or have overlapping ones. Other conditions should be
 * used instead.
 *
 * The 'LootTableIdRegex' condition requires a regex to be built.
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.conditions.vanilla.LootTableIdRegex")
@Document("vanilla/api/loot/conditions/vanilla/LootTableIdRegex")
public final class LootTableIdRegexConditionTypeBuilder implements ILootConditionTypeBuilder {
    private Pattern regex;

    LootTableIdRegexConditionTypeBuilder() {}

    /**
     * Sets the regex of the loot table that should be targeted.
     *
     * This parameter is <strong>required</strong>.
     *
     * @param regex The regex of the loot table to match.
     *
     * @return This builder for chaining.
     */
    @ZenCodeType.Method
    public LootTableIdRegexConditionTypeBuilder withRegex(String regex) {
        this.regex = Pattern.compile(regex);
        return this;
    }

    @Override
    public ILootCondition finish() {
        if(this.regex == null) {
            throw new IllegalStateException("Unable to have a 'LootTableIdRegex' condition without a regex");
        }
        return context -> regex.matcher(ExpandLootContext.getLootTableId(context).toString()).matches();
    }
}
