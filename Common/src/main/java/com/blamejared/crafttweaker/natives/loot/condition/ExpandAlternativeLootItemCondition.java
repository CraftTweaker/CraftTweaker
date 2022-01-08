package com.blamejared.crafttweaker.natives.loot.condition;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.level.storage.loot.predicates.AlternativeLootItemCondition;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/loot/condition/AlternativeLootCondition")
@NativeTypeRegistration(value = AlternativeLootItemCondition.class, zenCodeName = "crafttweaker.api.loot.condition.AlternativeLootCondition")
public final class ExpandAlternativeLootItemCondition {
    
    @ZenCodeType.StaticExpansionMethod
    public AlternativeLootItemCondition.Builder create() {
        
        return new AlternativeLootItemCondition.Builder();
    }
    
}
