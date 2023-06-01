package com.blamejared.crafttweaker;


import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.ingredient.condition.serializer.*;
import com.blamejared.crafttweaker.api.ingredient.transform.serializer.*;
import com.blamejared.crafttweaker.mixin.AccessMappedRegistry;
import com.mojang.serialization.Lifecycle;
import net.minecraft.core.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.*;

public class CraftTweakerRegistries {
    
    public static MappedRegistry<IIngredientTransformerSerializer<?>> REGISTRY_TRANSFORMER_SERIALIZER;
    public static MappedRegistry<IIngredientConditionSerializer<?>> REGISTRY_CONDITIONER_SERIALIZER;
    
    public static void init() {
        
        if(BuiltInRegistries.REGISTRY instanceof AccessMappedRegistry registry) {
            boolean wasFrozen = registry.crafttweaker$isFrozen();
            registry.crafttweaker$setFrozen(false);
            REGISTRY_TRANSFORMER_SERIALIZER = registerRegistry(CraftTweakerConstants.rl("transformer_serializer"));
            REGISTRY_CONDITIONER_SERIALIZER = registerRegistry(CraftTweakerConstants.rl("condition_serializer"));
            registry.crafttweaker$setFrozen(wasFrozen);
        } else {
            throw new IllegalStateException("Unable to create new registries! Expected REGISTRY to be mapped, but was: '" + BuiltInRegistries.REGISTRY.getClass() + "'");
        }
        
        registerSerializer(REGISTRY_TRANSFORMER_SERIALIZER, TransformReplaceSerializer.INSTANCE);
        registerSerializer(REGISTRY_TRANSFORMER_SERIALIZER, TransformDamageSerializer.INSTANCE);
        registerSerializer(REGISTRY_TRANSFORMER_SERIALIZER, TransformCustomSerializer.INSTANCE);
        registerSerializer(REGISTRY_TRANSFORMER_SERIALIZER, TransformerReuseSerializer.INSTANCE);
        
        registerSerializer(REGISTRY_CONDITIONER_SERIALIZER, ConditionDamagedSerializer.INSTANCE);
        registerSerializer(REGISTRY_CONDITIONER_SERIALIZER, ConditionAnyDamagedSerializer.INSTANCE);
        registerSerializer(REGISTRY_CONDITIONER_SERIALIZER, ConditionCustomSerializer.INSTANCE);
    }
    
    @SuppressWarnings("rawtypes")
    private static <T> MappedRegistry<T> registerRegistry(ResourceLocation location) {
        
        WritableRegistry registry = (WritableRegistry) BuiltInRegistries.REGISTRY;
        ResourceKey<Registry<T>> regKey = ResourceKey.createRegistryKey(location);
        Lifecycle stable = Lifecycle.stable();
        MappedRegistry<T> mappedReg = new MappedRegistry<>(regKey, stable);
        registry.register(regKey, mappedReg, stable);
        return mappedReg;
    }
    
    private static void registerSerializer(MappedRegistry<IIngredientTransformerSerializer<?>> registry, IIngredientTransformerSerializer<?> serializer) {
        
        registry.register(ResourceKey.create(registry.key(), serializer.getType()), serializer, Lifecycle.stable());
    }
    
    private static void registerSerializer(MappedRegistry<IIngredientConditionSerializer<?>> registry, IIngredientConditionSerializer<?> serializer) {
        
        registry.register(ResourceKey.create(registry.key(), serializer.getType()), serializer, Lifecycle.stable());
    }
    
}
