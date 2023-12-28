package com.blamejared.crafttweaker.natives.loot.condition;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.predicates.ConditionReference;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/loot/condition/ConditionReferenceLootCondition")
@NativeTypeRegistration(value = ConditionReference.class, zenCodeName = "crafttweaker.api.loot.condition.ConditionReferenceLootCondition")
public final class ExpandConditionReference {
    
    @ZenCodeType.StaticExpansionMethod
    public static LootItemCondition.Builder create(final ResourceLocation name) {
        
        return ConditionReference.conditionReference(name);
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static LootItemCondition.Builder create(final String name) {
        
        return create(new ResourceLocation(name));
    }
    
}
