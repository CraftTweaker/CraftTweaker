package com.blamejared.crafttweaker.natives.loot.condition.builder;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/loot/condition/builder/BlockStatePropertyLootConditionBuilder")
@NativeTypeRegistration(value = LootItemBlockStatePropertyCondition.Builder.class, zenCodeName = "crafttweaker.api.loot.condition.builder.BlockStatePropertyLootConditionBuilder")
public final class ExpandLootItemBlockStatePropertyConditionBuilder {
    
    @ZenCodeType.Method
    public static LootItemBlockStatePropertyCondition.Builder properties(final LootItemBlockStatePropertyCondition.Builder builder, final StatePropertiesPredicate.Builder predicate) {
        
        return builder.setProperties(predicate);
    }
    
    // TODO workaround for ZC JFITI issues
    @ZenCodeType.Caster(implicit = true)
    public static LootItemCondition asSupplier(LootItemBlockStatePropertyCondition.Builder internal) {
        
        return internal.build();
    }
    
}
