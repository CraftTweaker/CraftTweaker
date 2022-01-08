package com.blamejared.crafttweaker.impl.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

import java.util.regex.Pattern;

@SuppressWarnings("ClassCanBeRecord")
public class LootTableIdRegexCondition implements LootItemCondition {
    
    public static final class Serializer implements net.minecraft.world.level.storage.loot.Serializer<LootTableIdRegexCondition> {
        
        @Override
        public void serialize(final JsonObject pJson, final LootTableIdRegexCondition pValue, final JsonSerializationContext pSerializationContext) {
            
            pJson.addProperty("regex", pValue.regex.toString());
        }
        
        @Override
        public LootTableIdRegexCondition deserialize(final JsonObject pJson, final JsonDeserializationContext pSerializationContext) {
            
            return new LootTableIdRegexCondition(Pattern.compile(GsonHelper.getAsString(pJson, "regex")));
        }
        
    }
    
    public static final LootItemConditionType LOOT_TABLE_ID_REGEX = new LootItemConditionType(new LootTableIdRegexCondition.Serializer());
    
    private final Pattern regex;
    
    private LootTableIdRegexCondition(final Pattern regex) {
        
        this.regex = regex;
    }
    
    public static Builder builder(final Pattern regex) {
        
        return () -> new LootTableIdRegexCondition(regex);
    }
    
    public static Builder builder(final String regex) {
        
        return builder(Pattern.compile(regex));
    }
    
    @Override
    public LootItemConditionType getType() {
        
        return LOOT_TABLE_ID_REGEX;
    }
    
    @Override
    public boolean test(final LootContext context) {
        
        return this.regex.matcher(context.getQueriedLootTableId().toString()).matches();
    }
    
}
