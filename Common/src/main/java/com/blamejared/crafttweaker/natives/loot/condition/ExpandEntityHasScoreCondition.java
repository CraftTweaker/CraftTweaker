package com.blamejared.crafttweaker.natives.loot.condition;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.EntityHasScoreCondition;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/loot/condition/EntityHasScoreLootCondition")
@NativeTypeRegistration(value = EntityHasScoreCondition.class, zenCodeName = "crafttweaker.api.loot.condition.EntityHasScoreLootCondition")
public final class ExpandEntityHasScoreCondition {
    
    @ZenCodeType.StaticExpansionMethod
    public static EntityHasScoreCondition.Builder create(final LootContext.EntityTarget target) {
        
        return EntityHasScoreCondition.hasScores(target);
    }
    
}
