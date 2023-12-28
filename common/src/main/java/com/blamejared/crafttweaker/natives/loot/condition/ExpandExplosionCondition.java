package com.blamejared.crafttweaker.natives.loot.condition;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/loot/condition/ExplosionLootCondition")
@NativeTypeRegistration(value = ExplosionCondition.class, zenCodeName = "crafttweaker.api.loot.condition.ExplosionLootCondition")
public final class ExpandExplosionCondition {
    
    @ZenCodeType.StaticExpansionMethod
    public static LootItemCondition.Builder create() {
        
        return ExplosionCondition.survivesExplosion();
    }
    
}
