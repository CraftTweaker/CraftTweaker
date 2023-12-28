package com.blamejared.crafttweaker.natives.loot.condition;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/loot/condition/BlockStatePropertyLootCondition")
@NativeTypeRegistration(value = LootItemBlockStatePropertyCondition.class, zenCodeName = "crafttweaker.api.loot.condition.BlockStatePropertyLootCondition")
public final class ExpandLootItemBlockStatePropertyCondition {
    
    @ZenCodeType.StaticExpansionMethod
    public static LootItemBlockStatePropertyCondition.Builder create(final Block block) {
        
        return LootItemBlockStatePropertyCondition.hasBlockStateProperties(block);
    }
    
}
