package com.blamejared.crafttweaker.natives.loot;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.mixin.common.access.loot.AccessLootContext;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Random;

/**
 * Creates a new {@link LootContext} using a builder style pattern.
 *
 * @docParam this new LootContextBuilder(level)
 */
@ZenRegister
@Document("vanilla/api/loot/LootContextBuilder")
@NativeTypeRegistration(value = LootContext.Builder.class, zenCodeName = "crafttweaker.api.loot.LootContextBuilder")
public final class ExpandLootContextBuilder {
    
    /**
     * Creates a new builder with the given level.
     *
     * @param params The params the loot will be rolled with.
     *
     * @return A new builder.
     */
    @ZenCodeType.StaticExpansionMethod
    public static LootContext.Builder create(LootParams params) {
        
        return new LootContext.Builder(params);
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static LootContext.Builder copy(LootContext context) {
        
        // TODO this doesn't copy the random, we don't have access to the original random seed, so I don't think we can ever copy the random?
        return new LootContext.Builder(((AccessLootContext) context).crafttweaker$getParams());
    }
    
    /**
     * Supplies a seed to be passed into a new {@link Random}.
     *
     * @param seed The optional seed to use, defaults to {@code 0}.
     *
     * @return This builder for chaining purposes.
     *
     * @docParam seed 1
     */
    @ZenCodeType.Method
    public static LootContext.Builder withOptionalRandomSeed(LootContext.Builder internal, @ZenCodeType.OptionalLong long seed) {
        
        return internal.withOptionalRandomSeed(seed);
    }
    
    /**
     * Gets the level that this builder uses.
     *
     * @return The level that this builder uses.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("level")
    public static ServerLevel getLevel(LootContext.Builder internal) {
        
        return internal.getLevel();
    }
    
    /**
     * Creates a new {@link LootContext} with the given {@link LootContextParamSet}.
     *
     * <p> The given {@link LootContextParamSet} is used to determine what values are required for the context to be used.
     *
     * @param key An optional random key used when no seed is provided.
     *
     * @return a new {@link LootContext}
     *
     * @docParam contextParamSet LootContextParamSets.gift()
     */
    @ZenCodeType.Method
    public static LootContext create(LootContext.Builder internal, @ZenCodeType.Optional ResourceLocation key) {
        
        return internal.create(key);
    }
    
}
