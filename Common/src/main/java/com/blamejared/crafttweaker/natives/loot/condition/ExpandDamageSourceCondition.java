package com.blamejared.crafttweaker.natives.loot.condition;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.advancements.critereon.DamageSourcePredicate;
import net.minecraft.world.level.storage.loot.predicates.DamageSourceCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/loot/condition/DamageSourceLootCondition")
@NativeTypeRegistration(value = DamageSourceCondition.class, zenCodeName = "crafttweaker.api.loot.condition.DamageSourceLootCondition")
public final class ExpandDamageSourceCondition {
    
    @ZenCodeType.StaticExpansionMethod
    public static LootItemCondition.Builder create(final DamageSourcePredicate.Builder predicate) {
        
        return DamageSourceCondition.hasDamageSource(predicate);
    }
    
}
