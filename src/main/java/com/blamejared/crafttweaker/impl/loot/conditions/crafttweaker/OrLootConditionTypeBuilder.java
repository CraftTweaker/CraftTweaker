package com.blamejared.crafttweaker.impl.loot.conditions.crafttweaker;

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
 * Builder to create an 'OR' loot condition.
 *
 * At least one of the sub-conditions added to this loot condition must pass to make this condition pass. Effectively,
 * this means all sub-conditions get merged with an 'OR' connector between them. This makes this loot condition
 * effectively a clone of
 * {@link com.blamejared.crafttweaker.impl.loot.conditions.vanilla.AlternativeLootConditionTypeBuilder} at the moment.
 * This ensures a more coherent experience in case vanilla's behavior changes in the future, while also allowing the
 * user to specify the logic gate directly.
 *
 * This loot condition should have two or more sub-conditions. An 'Or' loot condition with a single element behaves
 * as if it were replaced with the sub-condition itself. An 'Or' loot condition without any sub-conditions never
 * passes.
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.conditions.crafttweaker.Or")
@Document("vanilla/api/loot/conditions/crafttweaker/Or")
public final class OrLootConditionTypeBuilder implements ILootConditionTypeBuilder {
    private final CTLootConditionBuilder parent;
    private final List<ILootCondition> subConditions;

    OrLootConditionTypeBuilder(final CTLootConditionBuilder parent) {
        this.parent = parent;
        this.subConditions = new ArrayList<>();
    }

    /**
     * Adds a new condition to the list of sub-conditions that will get merged together into an 'OR' condition.
     *
     * At least two sub-conditions should be added to obtain a well-built and well-behaved 'Or' loot condition.
     *
     * @param reifiedType The type of condition to add. It must extend {@link ILootConditionTypeBuilder}
     * @param lender A consumer that allows configuration of the given condition.
     * @param <T> The known type of the condition itself.
     * @return This condition for chaining.
     */
    @ZenCodeType.Method
    public <T extends ILootConditionTypeBuilder> OrLootConditionTypeBuilder add(final Class<T> reifiedType, final Consumer<T> lender) {
        final ILootCondition subCondition = this.parent.make(reifiedType, "Or", lender);
        if (subCondition != null) this.subConditions.add(subCondition);
        return this;
    }

    @Override
    public ILootCondition finish() {
        if (this.subConditions.isEmpty()) {
            CraftTweakerAPI.logWarning("An 'Or' loot condition has no conditions: this will never match!");
        } else if (this.subConditions.size() == 1) {
            CraftTweakerAPI.logWarning("An 'Or' loot condition has only one condition: this is equivalent to the condition itself");
        }
        return context -> this.subConditions.stream().anyMatch(it -> it.test(context));
    }
}
