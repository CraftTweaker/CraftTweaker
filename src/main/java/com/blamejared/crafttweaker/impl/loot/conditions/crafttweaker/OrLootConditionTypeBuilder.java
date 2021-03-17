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
