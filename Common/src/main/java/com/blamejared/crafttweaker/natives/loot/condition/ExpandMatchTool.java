package com.blamejared.crafttweaker.natives.loot.condition;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/loot/condition/MatchToolLootCondition")
@NativeTypeRegistration(value = MatchTool.class, zenCodeName = "crafttweaker.api.loot.condition.MatchToolLootCondition")
public final class ExpandMatchTool {
    
    @ZenCodeType.StaticExpansionMethod
    public static LootItemCondition.Builder create(final ItemPredicate.Builder predicate) {
        
        return MatchTool.toolMatches(predicate);
    }
    
}
