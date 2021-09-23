package com.blamejared.crafttweaker.impl.loot.conditions.crafttweaker;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.loot.conditions.ILootCondition;
import com.blamejared.crafttweaker.impl.loot.conditions.CTLootConditionBuilder;
import com.blamejared.crafttweaker.impl.loot.conditions.ILootConditionTypeBuilder;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * Builder to create a 'Not' condition.
 *
 * The sub-condition added to a 'Not' condition will be passed through the equivalent of a NOT gate, effectively
 * inverting the result of the query. This makes this loot condition effectively a clone of
 * {@link com.blamejared.crafttweaker.impl.loot.conditions.vanilla.InvertedLootConditionTypeBuilder} at the moment. This
 * ensures a more coherent experience in case vanilla's behavior changes in the future, while also allowing the user to
 * specify the logic gate directly.
 *
 * A 'Not' condition requires a sub-condition to be built.
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.conditions.crafttweaker.Not")
@Document("vanilla/api/loot/conditions/crafttweaker/Not")
public final class NotLootConditionTypeBuilder implements ILootConditionTypeBuilder {
    
    private final CTLootConditionBuilder parent;
    private ILootCondition sub;
    
    NotLootConditionTypeBuilder(final CTLootConditionBuilder parent) {
        
        this.parent = parent;
    }
    
    /**
     * Sets the loot condition to negate.
     *
     * This parameter is <strong>required</strong>.
     *
     * @param condition The condition to negate.
     *
     * @return This builder for chaining.
     */
    @ZenCodeType.Method
    public NotLootConditionTypeBuilder withCondition(final ILootCondition condition) {
        
        this.sub = Objects.requireNonNull(condition);
        return this;
    }
    
    /**
     * Sets the negated condition to the one created with the given {@link CTLootConditionBuilder}.
     *
     * The builder must host a single loot condition. Builders with a different amount of conditions are not allowed.
     * The builder will be used to generate a loot condition that will then be used as sub-condition.
     *
     * This parameter is <strong>required</strong>.
     *
     * @param builder The builder to create a single {@link ILootCondition}.
     *
     * @return This builder for chaining.
     */
    @ZenCodeType.Method
    public NotLootConditionTypeBuilder withCondition(final CTLootConditionBuilder builder) {
        
        final ILootCondition condition = builder.single();
        if(condition == null) {
            throw new IllegalArgumentException("Loot condition builder must have a single condition");
        }
        return this.withCondition(condition);
    }
    
    /**
     * Creates and builds the sub-condition that will then be negated.
     *
     * @param reifiedType The type of condition to negate. It must extend {@link ILootConditionTypeBuilder}.
     * @param lender      A consumer that allows configuration of the created condition.
     * @param <T>         The known type of the condition itself.
     *
     * @return This builder for chaining.
     */
    @ZenCodeType.Method
    public <T extends ILootConditionTypeBuilder> NotLootConditionTypeBuilder withCondition(final Class<T> reifiedType, final Consumer<T> lender) {
        
        return this.withCondition(this.parent.make(reifiedType, "Not", lender));
    }
    
    @Override
    public ILootCondition finish() {
    
        if(this.sub == null) {
            throw new IllegalStateException("Missing condition to negate");
        }
        return context -> !this.sub.test(context);
    }
    
}
