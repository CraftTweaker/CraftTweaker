package com.blamejared.crafttweaker.impl.loot.conditions.crafttweaker;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.loot.conditions.ILootCondition;
import com.blamejared.crafttweaker.impl.loot.conditions.CTLootConditionBuilder;
import com.blamejared.crafttweaker.impl.loot.conditions.ILootConditionTypeBuilder;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

/**
 * Builder to create a 'Delegate' loot condition.
 *
 * A delegate loot condition defers checking to another loot condition directly, effectively acting as a simple wrapper
 * around the delegated condition. This allows other conditions that may have been built prior to the construction of
 * this builder to be referenced and used directly.
 *
 * A 'Delegate' loot condition requires a delegate to be built.
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.conditions.crafttweaker.Delegate")
@Document("vanilla/api/loot/conditions/crafttweaker/Delegate")
public final class DelegateLootConditionTypeBuilder implements ILootConditionTypeBuilder {
    
    private ILootCondition delegate;
    
    DelegateLootConditionTypeBuilder() {}
    
    /**
     * Sets the delegate loot condition.
     *
     * This parameter is <strong>required</strong>.
     *
     * @param other The delegate loot condition.
     *
     * @return This builder for chaining.
     */
    @ZenCodeType.Method
    public DelegateLootConditionTypeBuilder withDelegate(final ILootCondition other) {
        
        this.delegate = other;
        return this;
    }
    
    /**
     * Sets the delegate to the loot condition created with the given {@link CTLootConditionBuilder}.
     *
     * The builder must host a single loot condition. Builders with a different amount of conditions are not allowed.
     * The builder will be used to generate a loot condition that will then be used as a delegate.
     *
     * This parameter is <strong>required</strong>.
     *
     * @param builder The builder to create a single {@link ILootCondition}.
     *
     * @return This builder for chaining.
     */
    @ZenCodeType.Method
    public DelegateLootConditionTypeBuilder withDelegate(final CTLootConditionBuilder builder) {
        
        final ILootCondition delegate = builder.single();
        if(delegate == null) {
            throw new IllegalArgumentException("Loot condition builder must have a single condition");
        }
        return this.withDelegate(delegate);
    }
    
    @Override
    public ILootCondition finish() {
        
        if(this.delegate == null) {
            throw new IllegalStateException("You must specify a delegate function for a 'Delegate' loot condition");
        }
        return this.delegate;
    }
    
}
