package com.blamejared.crafttweaker.natives.loot.condition;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.ToolAction;
import net.neoforged.neoforge.common.loot.CanToolPerformAction;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("neoforge/api/loot/condition/CanToolPerformActionLootCondition")
@NativeTypeRegistration(value = CanToolPerformAction.class, zenCodeName = "crafttweaker.api.loot.condition.CanToolPerformActionLootCondition")
public final class ExpandCanToolPerformAction {
    
    @ZenCodeType.StaticExpansionMethod
    public static LootItemCondition.Builder create(final ToolAction action) {
        
        return CanToolPerformAction.canToolPerformAction(action);
    }
    
}

