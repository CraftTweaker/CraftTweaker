package com.blamejared.crafttweaker.impl.loot.conditions.crafttweaker;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.loot.conditions.ILootCondition;
import com.blamejared.crafttweaker.impl.loot.conditions.CTLootConditionBuilder;
import com.blamejared.crafttweaker.impl.loot.conditions.ILootConditionTypeBuilder;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Objects;
import java.util.function.Consumer;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.conditions.crafttweaker.Not")
@Document("vanilla/api/loot/conditions/crafttweaker/Not")
public final class NotLootConditionTypeBuilder implements ILootConditionTypeBuilder {
    private final CTLootConditionBuilder parent;
    private ILootCondition sub;

    NotLootConditionTypeBuilder(final CTLootConditionBuilder parent) {
        this.parent = parent;
    }

    @ZenCodeType.Method
    public NotLootConditionTypeBuilder withCondition(final ILootCondition condition) {
        this.sub = Objects.requireNonNull(condition);
        return this;
    }

    @ZenCodeType.Method
    public NotLootConditionTypeBuilder withCondition(final CTLootConditionBuilder builder) {
        final ILootCondition condition = builder.single();
        if (condition == null) throw new IllegalArgumentException("Loot condition builder must have a single condition");
        return this.withCondition(condition);
    }

    @ZenCodeType.Method
    public <T extends ILootConditionTypeBuilder> NotLootConditionTypeBuilder withCondition(final Class<T> reifiedType, final Consumer<T> lender) {
        return this.withCondition(this.parent.make(reifiedType, "Not", lender));
    }

    @Override
    public ILootCondition finish() {
        if (this.sub == null) throw new IllegalStateException("Missing condition to negate");
        return context -> !this.sub.test(context);
    }
}
