package com.blamejared.crafttweaker.impl.loot.modifiers;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.loot.modifiers.ILootModifier;
import com.blamejared.crafttweaker.impl.loot.CTLootManager;
import com.blamejared.crafttweaker.impl.loot.conditions.CTLootConditionBuilder;
import com.blamejared.crafttweaker.impl.loot.conditions.crafttweaker.ToolTypeLootConditionTypeBuilder;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraftforge.common.ToolType;
import org.openzen.zencode.java.ZenCodeType;

/**
 * Additional methods for ease of modification of loot tables that interact with a certain {@link ToolType}.
 */
@Document("vanilla/api/loot/modifiers/ToolTypeExpansion")
@ZenCodeType.Expansion("crafttweaker.api.tool.ToolType")
@ZenRegister
public class ModifierToolTypeExpansion {
    
    /**
     * Adds an {@link ILootModifier} for everything that gets broken by a tool with this tool type.
     *
     * @param internal The tool type that is used to break blocks.
     * @param name The name of the loot modifier.
     * @param modifier The loot modifier to add.
     */
    @ZenCodeType.Method
    public static void addToolModifier(final ToolType internal, final String name, final ILootModifier modifier) {
    
        CTLootManager.LOOT_MANAGER.getModifierManager().register(
                name,
                CTLootConditionBuilder.createForSingle(ToolTypeLootConditionTypeBuilder.class, tt -> tt.withToolType(internal)),
                modifier
        );
    }
}
