package com.blamejared.crafttweaker.impl.loot.condition;

import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.crafttweaker.impl.loot.ILootTableIdHolder;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

public record LootTableIdCondition(ResourceLocation tableId) implements LootItemCondition {
    // Could this be written as a proper codec? Yes
    // Did I try? Yes
    // Did it work? Of course not, because Mojang generic madness
    // Do I want to fix it? No, thanks. Nobody uses this shit anyway, so who cares
    private static final class LootTableIdConditionCodec implements Codec<LootTableIdCondition> {
        
        @Override
        public <T> DataResult<Pair<LootTableIdCondition, T>> decode(final DynamicOps<T> ops, final T input) {
            
            try {
                final JsonObject object = ops.convertTo(JsonOps.INSTANCE, input).getAsJsonObject();
                final ResourceLocation id = new ResourceLocation(object.get("table_id").getAsString());
                return DataResult.success(Pair.of(new LootTableIdCondition(id), input));
            } catch (final Exception e) {
                return DataResult.error(e::getMessage, Pair.of(null, input));
            }
        }
        
        @Override
        public <T> DataResult<T> encode(final LootTableIdCondition input, final DynamicOps<T> ops, final T prefix) {
            
            final JsonObject object = new JsonObject();
            object.addProperty("table_id", input.tableId().toString());
            final T result = JsonOps.INSTANCE.convertTo(ops, object);
            return ops.mergeToPrimitive(prefix, result);
        }
        
    }
    
    public static final LootItemConditionType LOOT_TABLE_ID = new LootItemConditionType(new LootTableIdConditionCodec());
    
    public static Builder builder(final ResourceLocation id) {
        
        return () -> new LootTableIdCondition(id);
    }
    
    @Override
    public LootItemConditionType getType() {
        
        return LOOT_TABLE_ID;
    }
    
    @Override
    public boolean test(final LootContext lootContext) {
        
        return this.tableId().equals(GenericUtil.<ILootTableIdHolder>uncheck(lootContext).crafttweaker$tableId());
    }
    
}
