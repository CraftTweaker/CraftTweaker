package com.blamejared.crafttweaker.impl.loot.conditions.crafttweaker;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.loot.conditions.ILootCondition;
import com.blamejared.crafttweaker.impl.loot.conditions.ILootConditionTypeBuilder;
import com.blamejared.crafttweaker.impl_native.loot.ExpandLootContext;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraftforge.common.ToolType;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Arrays;

/**
 * Builder to create a 'ToolType' loot condition.
 *
 * <p>This condition checks the tool that the {@link net.minecraft.loot.LootContext} reports as having been used to
 * break a block or perform any other action is of the given {@link ToolType}.</p>
 *
 * <p>The condition then passes if and only if a tool of the given type has been used to break the block.</p>
 *
 * <p>If additional properties of the tool should be checked, combine this condition with a
 * {@link com.blamejared.crafttweaker.impl.loot.conditions.vanilla.MatchToolLootConditionBuilder} condition without
 * specifying the type.</p>
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.conditions.crafttweaker.ToolType")
@Document("vanilla/api/loot/conditions/crafttweaker/ToolType")
public final class ToolTypeLootConditionTypeBuilder implements ILootConditionTypeBuilder {
    
    private ToolType type;
    
    ToolTypeLootConditionTypeBuilder() {}
    
    /**
     * Sets the {@link ToolType} that will be matched against the tool.
     *
     * <p>This parameter is <strong>required</strong>.</p>
     *
     * @param type The tool type to check.
     *
     * @return This builder for chaining.
     */
    @ZenCodeType.Method
    public ToolTypeLootConditionTypeBuilder withToolType(final ToolType type) {
        
        this.type = type;
        return this;
    }
    
    @Override
    public ILootCondition finish() {
        
        return context -> {
            final IItemStack tool = ExpandLootContext.getTool(context);
            return tool != null && !tool.isEmpty() && Arrays.stream(tool.getToolTypes())
                    .anyMatch(it -> it == this.type);
        };
    }
    
}
