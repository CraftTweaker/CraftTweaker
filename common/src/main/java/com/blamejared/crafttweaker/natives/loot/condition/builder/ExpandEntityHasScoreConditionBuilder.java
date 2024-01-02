package com.blamejared.crafttweaker.natives.loot.condition.builder;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.level.storage.loot.IntRange;
import net.minecraft.world.level.storage.loot.predicates.EntityHasScoreCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/loot/condition/builder/EntityHasScoreLootConditionBuilder")
@NativeTypeRegistration(value = EntityHasScoreCondition.Builder.class, zenCodeName = "crafttweaker.api.loot.condition.builder.EntityHasScoreLootConditionBuilder")
public final class ExpandEntityHasScoreConditionBuilder {
    
    @ZenCodeType.Method
    public static EntityHasScoreCondition.Builder withScore(final EntityHasScoreCondition.Builder internal, final String name, final IntRange range) {
        
        return internal.withScore(name, range);
    }
    
}
