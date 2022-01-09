package com.blamejared.crafttweaker.natives.loot.condition;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.loot.LootTableIdCondition;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("forge/api/loot/condition/LootTableIdLootCondition")
@NativeTypeRegistration(value = LootTableIdCondition.class, zenCodeName = "crafttweaker.api.loot.condition.LootTableIdLootCondition")
public final class ExpandLootTableIdCondition {
    
    @ZenCodeType.StaticExpansionMethod
    public static LootTableIdCondition.Builder create(final ResourceLocation id) {
        
        return LootTableIdCondition.builder(id);
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static LootTableIdCondition.Builder create(final String id) {
        
        return create(new ResourceLocation(id));
    }
    
}
