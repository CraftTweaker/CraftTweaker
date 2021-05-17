package com.blamejared.crafttweaker.impl.loot.conditions.vanilla;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.loot.conditions.ILootCondition;
import com.blamejared.crafttweaker.impl.loot.conditions.CTLootConditionBuilder;
import com.blamejared.crafttweaker.impl.loot.conditions.ILootConditionTypeBuilder;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * Builder to create an 'Inverted' loot condition.
 *
 * This loot condition tests the given sub-condition, then inverts its result, effectively passing if and only if the
 * sub-condition does not. Effectively, this means that the sub-condition gets a 'NOT' gate prepended to it. This loot
 * condition thus acts as the vanilla counterpart of the
 * {@link com.blamejared.crafttweaker.impl.loot.conditions.crafttweaker.NotLootConditionTypeBuilder} loot condition.
 * Differently from the one, though, the behavior of this loot condition may drift away from the raw 'Not' behavior if
 * and when the base game alters its own condition.
 *
 * An 'Inverted' condition requires a sub-condition to be built.
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.conditions.vanilla.Inverted")
@Document("vanilla/api/loot/conditions/vanilla/Inverted")
public final class InvertedLootConditionTypeBuilder implements ILootConditionTypeBuilder {
    private final CTLootConditionBuilder parent;
    private ILootCondition sub;

    InvertedLootConditionTypeBuilder(final CTLootConditionBuilder parent) {
        this.parent = parent;
    }
    
    /**
     * Sets the loot condition to invert.
     *
     * This parameter is <strong>required</strong>.
     *
     * @param condition The condition to invert.
     * @return This builder for chaining.
     */
    @ZenCodeType.Method
    public InvertedLootConditionTypeBuilder withCondition(final ILootCondition condition) {
        this.sub = Objects.requireNonNull(condition);
        return this;
    }

    /**
     * Sets the inverted condition to the one created with the given {@link CTLootConditionBuilder}.
     *
     * The builder must host a single loot condition. Builders with a different amount of conditions are not allowed.
     * The builder will be used to generate a loot condition that will then be used as sub-condition.
     *
     * This parameter is <strong>required</strong>.
     *
     * @param builder The builder to create a single {@link ILootCondition}.
     * @return This builder for chaining.
     */
    @ZenCodeType.Method
    public InvertedLootConditionTypeBuilder withCondition(final CTLootConditionBuilder builder) {
        final ILootCondition condition = builder.single();
        if (condition == null) throw new IllegalArgumentException("Loot condition builder must have a single condition");
        return this.withCondition(condition);
    }

    /**
     * Creates and builds the sub-condition that will then be inverted.
     *
     * @param reifiedType The type of condition to invert. It must extend {@link ILootConditionTypeBuilder}.
     * @param lender A consumer that allows configuration of the created condition.
     * @param <T> The known type of the condition itself.
     * @return This builder for chaining.
     */
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
