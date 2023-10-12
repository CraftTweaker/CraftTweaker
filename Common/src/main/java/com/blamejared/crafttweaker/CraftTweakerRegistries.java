package com.blamejared.crafttweaker;

import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.ingredient.condition.serializer.ConditionAnyDamagedSerializer;
import com.blamejared.crafttweaker.api.ingredient.condition.serializer.ConditionCustomSerializer;
import com.blamejared.crafttweaker.api.ingredient.condition.serializer.ConditionDamagedAtLeastSerializer;
import com.blamejared.crafttweaker.api.ingredient.condition.serializer.ConditionDamagedAtMostSerializer;
import com.blamejared.crafttweaker.api.ingredient.condition.serializer.ConditionDamagedSerializer;
import com.blamejared.crafttweaker.api.ingredient.condition.serializer.IIngredientConditionSerializer;
import com.blamejared.crafttweaker.api.ingredient.transform.serializer.IIngredientTransformerSerializer;
import com.blamejared.crafttweaker.api.ingredient.transform.serializer.TransformCustomSerializer;
import com.blamejared.crafttweaker.api.ingredient.transform.serializer.TransformDamageSerializer;
import com.blamejared.crafttweaker.api.ingredient.transform.serializer.TransformReplaceSerializer;
import com.blamejared.crafttweaker.api.ingredient.transform.serializer.TransformReuseSerializer;
import com.blamejared.crafttweaker.mixin.common.access.registry.AccessMappedRegistry;
import com.mojang.serialization.Lifecycle;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.WritableRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

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
        registerSerializer(REGISTRY_TRANSFORMER_SERIALIZER, TransformReuseSerializer.INSTANCE);
        
        registerSerializer(REGISTRY_CONDITIONER_SERIALIZER, ConditionDamagedSerializer.INSTANCE);
        registerSerializer(REGISTRY_CONDITIONER_SERIALIZER, ConditionAnyDamagedSerializer.INSTANCE);
        registerSerializer(REGISTRY_CONDITIONER_SERIALIZER, ConditionCustomSerializer.INSTANCE);
        registerSerializer(REGISTRY_CONDITIONER_SERIALIZER, ConditionDamagedAtMostSerializer.INSTANCE);
        registerSerializer(REGISTRY_CONDITIONER_SERIALIZER, ConditionDamagedAtLeastSerializer.INSTANCE);
        
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
