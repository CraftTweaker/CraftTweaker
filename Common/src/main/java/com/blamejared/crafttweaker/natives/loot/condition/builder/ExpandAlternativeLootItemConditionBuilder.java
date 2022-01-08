package com.blamejared.crafttweaker.natives.loot.condition.builder;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.level.storage.loot.predicates.AlternativeLootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/loot/condition/builder/AlternativeLootConditionBuilder")
@NativeTypeRegistration(value = AlternativeLootItemCondition.Builder.class, zenCodeName = "crafttweaker.api.loot.condition.builder.AlternativeLootConditionBuilder")
public final class ExpandAlternativeLootItemConditionBuilder {
    
    @ZenCodeType.Method
    public static AlternativeLootItemCondition.Builder or(final AlternativeLootItemCondition.Builder internal, final LootItemCondition.Builder condition) {
        
        internal.or(condition);
        return internal;
    }
    
    
    // TODO workaround for ZC JFITI issues
    @ZenCodeType.Caster(implicit = true)
    public static LootItemCondition asSupplier(AlternativeLootItemCondition.Builder internal) {
        
        return internal.build();
    }
}
