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
@ZenCodeType.Name("crafttweaker.api.loot.conditions.crafttweaker.And")
@Document("vanilla/api/loot/conditions/crafttweaker/And")
public final class AndLootConditionTypeBuilder implements ILootConditionTypeBuilder {
    private final CTLootConditionBuilder parent;
    private final List<ILootCondition> subConditions;
    
    AndLootConditionTypeBuilder(final CTLootConditionBuilder parent) {
        this.parent = parent;
        this.subConditions = new ArrayList<>();
    }
    
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
        }
        if (this.subConditions.size() == 1) {
            CraftTweakerAPI.logWarning("An 'And' loot condition has only one condition: this is equivalent to the condition itself");
        }
        return context -> this.subConditions.stream().allMatch(it -> it.test(context));
    }
}
