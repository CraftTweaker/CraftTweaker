package com.blamejared.crafttweaker.natives.loot.condition;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/loot/condition/EntityPropertyLootCondition")
@NativeTypeRegistration(value = LootItemEntityPropertyCondition.class, zenCodeName = "crafttweaker.api.loot.condition.EntityPropertyLootCondition")
public final class ExpandLootItemEntityPropertyCondition {
    
    @ZenCodeType.StaticExpansionMethod
    public static LootItemCondition.Builder create(final LootContext.EntityTarget target) {
        
        return LootItemEntityPropertyCondition.entityPresent(target);
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static LootItemCondition.Builder create(final LootContext.EntityTarget target, final EntityPredicate.Builder predicate) {
        
        return LootItemEntityPropertyCondition.hasProperties(target, predicate);
    }
    
}
