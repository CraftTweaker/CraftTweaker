package com.blamejared.crafttweaker.impl.loot.condition;

import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.crafttweaker.impl.loot.ILootTableIdHolder;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

import java.util.regex.Pattern;

public record LootTableIdRegexCondition(Pattern regex) implements LootItemCondition {
    // Could this be written as a proper codec? Yes
    // Did I try? Yes
    // Did it work? Of course not, because Mojang generic madness
    // Do I want to fix it? No, thanks. Nobody uses this shit anyway, so who cares
    private static final class LootTableIdRegexConditionCodec implements Codec<LootTableIdRegexCondition> {
        
        @Override
        public <T> DataResult<Pair<LootTableIdRegexCondition, T>> decode(final DynamicOps<T> ops, final T input) {
            
            try {
                final JsonObject object = ops.convertTo(JsonOps.INSTANCE, input).getAsJsonObject();
                final Pattern regex = Pattern.compile(object.get("regex").getAsString());
                return DataResult.success(Pair.of(new LootTableIdRegexCondition(regex), input));
            } catch (final Exception e) {
                return DataResult.error(e::getMessage, Pair.of(null, input));
            }
        }
        
        @Override
        public <T> DataResult<T> encode(final LootTableIdRegexCondition input, final DynamicOps<T> ops, final T prefix) {
            
            final JsonObject object = new JsonObject();
            object.addProperty("regex", input.regex().toString());
            final T result = JsonOps.INSTANCE.convertTo(ops, object);
            return ops.mergeToPrimitive(prefix, result);
        }
        
    }
    
    public static final LootItemConditionType LOOT_TABLE_ID_REGEX = new LootItemConditionType(new LootTableIdRegexConditionCodec());
    
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
