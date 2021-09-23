package com.blamejared.crafttweaker.impl.loot.conditions;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.loot.conditions.ILootCondition;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

/**
 * Manages the configuration and creation of a particular condition.
 *
 * You should never need to reference this type. Refer to {@link CTLootConditionBuilder} for more information.
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.conditions.ILootConditionTypeBuilder")
@Document("vanilla/api/loot/conditions/ILootConditionTypeBuilder")
public interface ILootConditionTypeBuilder {
    
    /**
     * Terminates the configuration of the loot condition and uses the current status to create a specific instance of
     * {@link ILootCondition} that matches the parameters.
     *
     * If some mandatory values are needed, then the method may throw an exception.
     *
     * If some values may lead to a not well-formed or not well-behaved condition, the method may log some warnings or
     * throw an exception.
     *
     * You should never need to call this method. Refer to {@link CTLootConditionBuilder} for more information.
     *
     * @return The built {@link ILootCondition}.
     */
    @ZenCodeType.Method
    ILootCondition finish();
    
}
