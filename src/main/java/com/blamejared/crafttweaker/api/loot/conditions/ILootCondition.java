package com.blamejared.crafttweaker.api.loot.conditions;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.loot.LootContext;
import org.openzen.zencode.java.ZenCodeType;

/**
 * Represents a condition on a {@link LootContext}.
 *
 * This is effectively the same as <code>Predicate&lt;LootContext&gt;</code>.
 */
@FunctionalInterface
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.conditions.ILootCondition")
@Document("vanilla/api/loot/conditions/ILootCondition")
public interface ILootCondition {
    
    /**
     * Tests the context for a set of conditions.
     *
     * @param context The context to test
     *
     * @return Whether the context passes the condition set.
     */
    @ZenCodeType.Method
    boolean test(final LootContext context);
    
}
