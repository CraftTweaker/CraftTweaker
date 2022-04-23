package com.blamejared.crafttweaker.natives.loot;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
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
     * @param level The level the loot will be rolled in.
     *
     * @return A new builder.
     */
    @ZenCodeType.StaticExpansionMethod
    public static LootContext.Builder create(ServerLevel level) {
        
        return new LootContext.Builder(level);
    }
    
    /**
     * Supplies a {@link Random} source to the built context.
     *
     * @param random The random source to provide.
     *
     * @return This builder for chaining purposes.
     *
     * @docParam random level.random
     */
    @ZenCodeType.Method
    public static LootContext.Builder withRandom(LootContext.Builder internal, Random random) {
        
        return internal.withRandom(random);
    }
    
    /**
     * Supplies a seed to be passed into a new {@link Random}.
     *
     * @param seed The seed to use.
     *
     * @return This builder for chaining purposes.
     *
     * @docParam seed 0
     */
    @ZenCodeType.Method
    public static LootContext.Builder withOptionalRandomSeed(LootContext.Builder internal, long seed) {
        
        return internal.withOptionalRandomSeed(seed);
    }
    
    /**
     * Supplies either a seed to be passed into a new {@link Random} or if the seed is {@code 0} use the given {@link Random}
     *
     * @param seed   The seed to use.
     * @param random The random source to provide.
     *
     * @return This builder for chaining purposes.
     *
     * @docParam seed 1
     * @docParam random level.random
     */
    @ZenCodeType.Method
    public static LootContext.Builder withOptionalRandomSeed(LootContext.Builder internal, long seed, Random random) {
        
        return internal.withOptionalRandomSeed(seed, random);
    }
    
    /**
     * Provides the given luck value to the context.
     *
     * @param luck The luck value to use.
     *
     * @return This builder for chaining purposes.
     *
     * @docParam luck 0.5
     */
    @ZenCodeType.Method
    public static LootContext.Builder withLuck(LootContext.Builder internal, float luck) {
        
        return internal.withLuck(luck);
    }
    
    /**
     * Provides a parameter to this builder.
     *
     * @param contextParam The param to add.
     * @param actor        The actor used by the param.
     * @param <T>          The type of actor that the param uses.
     *
     * @return This builder for chaining purposes.
     *
     * @docParam contextParam LootContextParams.origin()
     * @docParam actor new Vec3(1, 2, 3);
     */
    @ZenCodeType.Method
    public static <T> LootContext.Builder withParameter(LootContext.Builder internal, Class<T> tClass, LootContextParam<T> contextParam, T actor) {
        
        return internal.withParameter(contextParam, actor);
    }
    
    /**
     * Provides an optional parameter to this builder.
     *
     * @param contextParam The param to add.
     * @param actor        The optional actor used by the param.
     * @param <T>          The type of actor that the param uses.
     *
     * @return This builder for chaining purposes.
     *
     * @docParam contextParam LootContextParams.origin()
     * @docParam actor new Vec3(1, 2, 3);
     */
    @ZenCodeType.Method
    public static <T> LootContext.Builder withOptionalParameter(LootContext.Builder internal, Class<T> tClass, LootContextParam<T> contextParam, @ZenCodeType.Nullable T actor) {
        
        return internal.withOptionalParameter(contextParam, actor);
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
     * Gets the provided value of the parameter.
     *
     * @param contextParam The {@link LootContextParam} to get the value of.
     * @param <T>          The type that the {@link LootContextParam} uses
     *
     * @return The found value.
     *
     * @docParam contextParam LootContextParams.origin()
     */
    @ZenCodeType.Method
    public static <T> T getParameter(LootContext.Builder internal, Class<T> tClass, LootContextParam<T> contextParam) {
        
        return internal.getParameter(contextParam);
    }
    
    /**
     * Gets the provided value of the optional parameter.
     *
     * @param contextParam The {@link LootContextParam} to get the value of.
     * @param <T>          The type that the {@link LootContextParam} uses
     *
     * @return The value if found, null otherwise.
     *
     * @docParam contextParam LootContextParams.origin()
     */
    @ZenCodeType.Method
    @ZenCodeType.Nullable
    public static <T> T getOptionalParameter(LootContext.Builder internal, Class<T> tClass, LootContextParam<T> contextParam) {
        
        return internal.getOptionalParameter(contextParam);
    }
    
    /**
     * Creates a new {@link LootContext} with the given {@link LootContextParamSet}.
     *
     * <p> The given {@link LootContextParamSet} is used to determine what values are required for the context to be used.
     *
     * @param contextParamSet The {@link LootContextParamSet} to use.
     *
     * @return a new {@link LootContext}
     *
     * @docParam contextParamSet LootContextParamSets.gift()
     */
    @ZenCodeType.Method
    public static LootContext create(LootContext.Builder internal, LootContextParamSet contextParamSet) {
        
        return internal.create(contextParamSet);
    }
    
}
