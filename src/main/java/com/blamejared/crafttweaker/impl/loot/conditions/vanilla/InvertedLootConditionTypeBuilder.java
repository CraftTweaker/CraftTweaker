package com.blamejared.crafttweaker.impl.loot.conditions.vanilla;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.loot.ILootCondition;
import com.blamejared.crafttweaker.impl.loot.conditions.CTLootConditionBuilder;
import com.blamejared.crafttweaker.impl.loot.conditions.ILootConditionTypeBuilder;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Objects;
import java.util.function.Consumer;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.conditions.vanilla.Inverted")
@Document("vanilla/api/loot/conditions/vanilla/Inverted")
public final class InvertedLootConditionTypeBuilder implements ILootConditionTypeBuilder {
    private final CTLootConditionBuilder parent;
    private ILootCondition sub;

    InvertedLootConditionTypeBuilder(final CTLootConditionBuilder parent) {
        this.parent = parent;
    }

    @ZenCodeType.Method
    public InvertedLootConditionTypeBuilder withCondition(final ILootCondition condition) {
        this.sub = Objects.requireNonNull(condition);
        return this;
    }

    @ZenCodeType.Method
    public InvertedLootConditionTypeBuilder withCondition(final CTLootConditionBuilder builder) {
        final ILootCondition condition = builder.single();
        if (condition == null) throw new IllegalArgumentException("Loot condition builder must have a single condition");
        return this.withCondition(condition);
    }

    @ZenCodeType.Method
    public <T extends ILootConditionTypeBuilder> InvertedLootConditionTypeBuilder withCondition(final Class<T> reifiedType, final Consumer<T> lender) {
        return this.withCondition(this.parent.make(reifiedType, "Inverted", lender));
    }

    @Override
    public ILootCondition finish() {
        if (this.sub == null) throw new IllegalStateException("Missing condition to invert");
        return context -> !this.sub.test(context);
    }
}
