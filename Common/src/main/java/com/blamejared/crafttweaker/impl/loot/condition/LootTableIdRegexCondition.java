package com.blamejared.crafttweaker.impl.loot.condition;

import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.crafttweaker.impl.loot.ILootTableIdHolder;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

import java.util.regex.Pattern;

public record LootTableIdRegexCondition(Pattern regex) implements LootItemCondition {
    
    public static final Codec<LootTableIdRegexCondition> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ExtraCodecs.PATTERN.fieldOf("regex").forGetter(LootTableIdRegexCondition::regex)
    ).apply(instance, LootTableIdRegexCondition::new));
    
    public static final LootItemConditionType LOOT_TABLE_ID_REGEX = new LootItemConditionType(CODEC);
    
    public static Builder builder(final Pattern id) {
        
        return () -> new LootTableIdRegexCondition(id);
    }
    
    public static Builder builder(final String id) {
        
        return builder(Pattern.compile(id));
    }
    
    @Override
    public LootItemConditionType getType() {
        
        return LOOT_TABLE_ID_REGEX;
    }
    
    @Override
    public boolean test(final LootContext lootContext) {
        
        return this.regex().matcher(GenericUtil.<ILootTableIdHolder>uncheck(lootContext).crafttweaker$tableId().toString()).matches();
    }
    
}
