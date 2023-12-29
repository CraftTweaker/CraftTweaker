package com.blamejared.crafttweaker.natives.loot.condition.builder;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.level.storage.loot.predicates.AllOfCondition;
import net.minecraft.world.level.storage.loot.predicates.AnyOfCondition;
import net.minecraft.world.level.storage.loot.predicates.InvertedLootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/loot/condition/builder/LootConditionBuilder")
@NativeTypeRegistration(value = LootItemCondition.Builder.class, zenCodeName = "crafttweaker.api.loot.condition.builder.LootConditionBuilder")
public final class ExpandLootItemConditionBuilder {
    
    @ZenCodeType.Method
    public static LootItemCondition build(final LootItemCondition.Builder internal) {
        
        return internal.build();
    }
    
    @ZenCodeType.Method
    public static AnyOfCondition.Builder or(final LootItemCondition.Builder internal, final LootItemCondition.Builder other) {
        
        return internal.or(other);
    }
    
    @ZenCodeType.Method
    public static AllOfCondition.Builder and(final LootItemCondition.Builder internal, final LootItemCondition.Builder other) {
        
        return internal.and(other);
    }
    
    @ZenCodeType.Method
    public static InvertedLootItemCondition.Builder invert(final LootItemCondition.Builder internal) {
        
        return internal.invert();
    }
    
}
