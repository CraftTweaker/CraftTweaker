package com.blamejared.crafttweaker.impl.loot.condition;

import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.crafttweaker.impl.loot.ILootTableIdHolder;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

public record LootTableIdCondition(ResourceLocation tableId) implements LootItemCondition {
    
    public static final Codec<LootTableIdCondition> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("table_id").forGetter(LootTableIdCondition::tableId)
    ).apply(instance, LootTableIdCondition::new));
    
    public static final LootItemConditionType LOOT_TABLE_ID = new LootItemConditionType(CODEC);
    
    public static Builder builder(final ResourceLocation id) {
        
        return () -> new LootTableIdCondition(id);
    }
    
    @Override
    public LootItemConditionType getType() {
        
        return LOOT_TABLE_ID;
    }
    
    @Override
    public boolean test(final LootContext lootContext) {
        
        return this.tableId().equals(GenericUtil.<ILootTableIdHolder> uncheck(lootContext).crafttweaker$tableId());
    }
    
}
