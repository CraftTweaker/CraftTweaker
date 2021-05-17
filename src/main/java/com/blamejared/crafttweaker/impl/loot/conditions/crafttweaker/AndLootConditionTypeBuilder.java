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
 * Builder to create an 'AND' loot condition.
 *
 * All sub-conditions added to this loot condition must match in order for this condition to pass. Effectively, this
 * means all sub-conditions get merged with an 'AND' connector between them.
 *
 * This loot condition should have two or more sub-conditions. An 'And' loot condition with a single element behaves
 * as if it were replaced with the sub-condition itself. An 'And' loot condition without any sub-conditions passes
 * directly.
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.conditions.crafttweaker.And")
@Document("vanilla/api/loot/conditions/crafttweaker/And")
public final class AndLootConditionTypeBuilder implements ILootConditionTypeBuilder {
    private final CTLootConditionBuilder parent;
    private final List<ILootCondition> subConditions;
    
    AndLootConditionTypeBuilder(final CTLootConditionBuilder parent) {
        this.parent = parent;
        this.subConditions = new ArrayList<>();
    }
    
    /**
     * Adds a new condition to the list of sub-conditions that will get merged together into an 'AND' condition.
     *
     * At least two sub-conditions should be added to obtain a well-built and well-behaved 'And' loot condition.
     *
     * @param reifiedType The type of condition to add. It must extend {@link ILootConditionTypeBuilder}
     * @param lender A consumer that allows configuration of the given condition.
     * @param <T> The known type of the condition itself.
     * @return This condition for chaining.
     */
    @ZenCodeType.Method
    public <T extends ILootConditionTypeBuilder> AndLootConditionTypeBuilder add(final Class<T> reifiedType, final Consumer<T> lender) {
        final ILootCondition subCondition = this.parent.make(reifiedType, "And", lender);
        if (subCondition != null) this.subConditions.add(subCondition);
        return this;
    }
    
    @Override
    public ILootCondition finish() {
        if (this.subConditions.isEmpty()) {
            CraftTweakerAPI.logWarning("An 'And' loot condition has no conditions: this will always match!");
        } else if (this.subConditions.size() == 1) {
            CraftTweakerAPI.logWarning("An 'And' loot condition has only one condition: this is equivalent to the condition itself");
        }
        return context -> this.subConditions.stream().allMatch(it -> it.test(context));
    }
}
