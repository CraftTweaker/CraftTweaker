package com.blamejared.crafttweaker.impl.loot.conditions.crafttweaker;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.loot.conditions.ILootCondition;
import com.blamejared.crafttweaker.impl.loot.conditions.ILootConditionTypeBuilder;
import com.blamejared.crafttweaker.impl_native.loot.ExpandLootContext;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraftforge.common.ToolType;
import org.apache.commons.lang3.ArrayUtils;
import org.openzen.zencode.java.ZenCodeType;

/**
 * Builder to create a 'ToolType' loot condition.
 *
 * <p>The condition passes only if the tool obtained from the {@link net.minecraft.loot.LootContext} has
 * the given tooltype.</p>
 *
 * <p>A 'ToolType' loot condition requires a block tag to be built.</p>
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.conditions.crafttweaker.ToolTypeCheck")
@Document("vanilla/api/loot/conditions/crafttweaker/ToolTypeCheck")
public class ToolTypeCheckLootConditionTypeBuilder implements ILootConditionTypeBuilder {
    private ToolType toolType;

    ToolTypeCheckLootConditionTypeBuilder() {}

    /**
     * Sets the {@link ToolType} that the condition must check.
     *
     * <p>This parameter is <strong>required</strong>.</p>
     *
     * @param toolType The tag to check.
     * @return This builder for chaining.
     */

    @ZenCodeType.Method
    public ToolTypeCheckLootConditionTypeBuilder withToolType(ToolType toolType) {
        this.toolType = toolType;
        return this;
    }

    @Override
    public ILootCondition finish() {
        if (this.toolType == null) {
            throw new IllegalStateException("A tag for a 'ToolType' condition must be specified");
        }
        return context -> {
            final IItemStack tool = ExpandLootContext.getTool(context);

            return tool != null && ArrayUtils.contains(tool.getToolTypes(), toolType);
        };
    }
}
