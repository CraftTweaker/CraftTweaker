package com.blamejared.crafttweaker.impl.loot.conditions.vanilla;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.loot.conditions.ILootCondition;
import com.blamejared.crafttweaker.impl.loot.conditions.CTLootConditionBuilder;
import com.blamejared.crafttweaker.impl.loot.conditions.ILootConditionTypeBuilder;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Builder to create an 'Alternative' loot condition.
 *
 * This loot condition tests all sub-conditions alternatively, passing as soon as one of the sub-condition passes.
 * Effectively, this means that all sub-conditions get merged with an 'OR' connector between them. This loot condition
 * thus acts as the vanilla counterpart of the
 * {@link com.blamejared.crafttweaker.impl.loot.conditions.crafttweaker.OrLootConditionTypeBuilder} loot condition.
 * Differently from the one, though, the behavior of this loot condition may drift away from the raw 'Or' behavior if
 * and when the base game alters its own condition.
 *
 * This loot condition should have two or more sub-conditions. An 'Alternative' loot condition with a single element
 * behaves as if it were replaced with the sub-condition itself. An 'Alternative' loot condition without any
 * sub-condition always fails.
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.conditions.vanilla.Alternative")
@Document("vanilla/api/loot/conditions/vanilla/Alternative")
public final class AlternativeLootConditionTypeBuilder implements ILootConditionTypeBuilder {
    private final CTLootConditionBuilder parent;
    private final List<ILootCondition> subConditions;

    AlternativeLootConditionTypeBuilder(final CTLootConditionBuilder parent) {
        this.parent = parent;
        this.subConditions = new ArrayList<>();
    }

    /**
     * Adds a new condition to the list of sub-conditions that will get merged together into an the alternatives.
     *
     * At least two sub-conditions should be added to obtain a well-built and well-behaved 'Alternative' loot condition.
     *
     * @param reifiedType The type of condition to add. It must extend {@link ILootConditionTypeBuilder}
     * @param lender A consumer that allows configuration of the given condition.
     * @param <T> The known type of the condition itself.
     * @return This condition for chaining.
     */
    @ZenCodeType.Method
    public <T extends ILootConditionTypeBuilder> AlternativeLootConditionTypeBuilder add(final Class<T> reifiedType, final Consumer<T> lender) {
        final ILootCondition subCondition = this.parent.make(reifiedType, "Alternative", lender);
        if (subCondition != null) this.subConditions.add(subCondition);
        return this;
    }

    @Override
    public ILootCondition finish() {
        if (this.subConditions.isEmpty()) {
            CraftTweakerAPI.logWarning("An 'Alternative' loot condition has no conditions: this will never match!");
        } else if (this.subConditions.size() == 1) {
            CraftTweakerAPI.logWarning("An 'Alternative' loot condition has only one condition: this is equivalent to the condition itself");
        }
        return context -> this.subConditions.stream().anyMatch(it -> it.test(context));
    }
}
