package com.blamejared.crafttweaker;

import com.blamejared.crafttweaker.api.ingredient.serializer.IngredientAnySerializer;
import com.blamejared.crafttweaker.api.ingredient.serializer.IngredientListSerializer;
import com.blamejared.crafttweaker.api.ingredient.serializer.PartialNBTIngredientSerializer;
import com.blamejared.crafttweaker.api.item.conditions.IIngredientConditionSerializer;
import com.blamejared.crafttweaker.api.item.transformed.IIngredientTransformerSerializer;
import com.blamejared.crafttweaker.impl.ingredients.conditions.serializer.ConditionAnyDamagedSerializer;
import com.blamejared.crafttweaker.impl.ingredients.conditions.serializer.ConditionCustomSerializer;
import com.blamejared.crafttweaker.impl.ingredients.conditions.serializer.ConditionDamagedSerializer;
import com.blamejared.crafttweaker.impl.ingredients.transform.serializer.TransformCustomSerializer;
import com.blamejared.crafttweaker.impl.ingredients.transform.serializer.TransformDamageSerializer;
import com.blamejared.crafttweaker.impl.ingredients.transform.serializer.TransformReplaceSerializer;
import com.blamejared.crafttweaker.impl.ingredients.transform.serializer.TransformerReuseSerializer;
import com.blamejared.crafttweaker.impl.item.conditions.IngredientConditionedSerializer;
import com.blamejared.crafttweaker.impl.item.transformed.IngredientTransformedSerializer;
import com.blamejared.crafttweaker.impl.recipes.SerializerShaped;
import com.blamejared.crafttweaker.impl.recipes.SerializerShapeless;
import com.blamejared.crafttweaker.impl.script.SerializerScript;
import com.mojang.serialization.Lifecycle;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.SimpleRegistry;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class CraftTweakerRegistries {
    
    public static SimpleRegistry<IIngredientTransformerSerializer<?>> REGISTRY_TRANSFORMER_SERIALIZER;
    public static SimpleRegistry<IIngredientConditionSerializer<?>> REGISTRY_CONDITIONER_SERIALIZER;
    
    public static void init() {
        
        ForgeRegistries.RECIPE_SERIALIZERS.register(SerializerShapeless.INSTANCE);
        ForgeRegistries.RECIPE_SERIALIZERS.register(SerializerShaped.INSTANCE);
        ForgeRegistries.RECIPE_SERIALIZERS.register(SerializerScript.INSTANCE);
        
        ResourceLocation transformerSerializerRL = new ResourceLocation(CraftTweaker.MODID, "transformer_serializer");
        ResourceLocation conditionSerializerRL = new ResourceLocation(CraftTweaker.MODID, "condition_serializer");
        
        
        REGISTRY_TRANSFORMER_SERIALIZER = registerRegistry(transformerSerializerRL);
        REGISTRY_CONDITIONER_SERIALIZER = registerRegistry(conditionSerializerRL);
        
        //TODO - BREAKING (potentially): Rename these to just be "transformed" / "conditioned", no need for specificity
        CraftingHelper.register(new ResourceLocation(CraftTweaker.MODID, "ingredient_transformed_serializer"), IngredientTransformedSerializer.INSTANCE);
        CraftingHelper.register(new ResourceLocation(CraftTweaker.MODID, "ingredient_conditioned_serializer"), IngredientConditionedSerializer.INSTANCE);
        CraftingHelper.register(new ResourceLocation(CraftTweaker.MODID, "any"), IngredientAnySerializer.INSTANCE);
        CraftingHelper.register(new ResourceLocation(CraftTweaker.MODID, "partial_nbt"), PartialNBTIngredientSerializer.INSTANCE);
        CraftingHelper.register(new ResourceLocation(CraftTweaker.MODID, "list"), IngredientListSerializer.INSTANCE);
        
        registerSerializer(REGISTRY_TRANSFORMER_SERIALIZER, TransformReplaceSerializer.INSTANCE);
        registerSerializer(REGISTRY_TRANSFORMER_SERIALIZER, TransformDamageSerializer.INSTANCE);
        registerSerializer(REGISTRY_TRANSFORMER_SERIALIZER, TransformCustomSerializer.INSTANCE);
        registerSerializer(REGISTRY_TRANSFORMER_SERIALIZER, TransformerReuseSerializer.INSTANCE);
        
        
        registerSerializer(REGISTRY_CONDITIONER_SERIALIZER, ConditionDamagedSerializer.INSTANCE);
        registerSerializer(REGISTRY_CONDITIONER_SERIALIZER, ConditionAnyDamagedSerializer.INSTANCE);
        registerSerializer(REGISTRY_CONDITIONER_SERIALIZER, ConditionCustomSerializer.INSTANCE);
    }
    
    private static <T> SimpleRegistry<T> registerRegistry(ResourceLocation location) {
        
        MutableRegistry registry = (MutableRegistry) Registry.REGISTRY;
        RegistryKey<Registry<T>> regKey = RegistryKey.getOrCreateRootKey(location);
        Lifecycle stable = Lifecycle.stable();
        return (SimpleRegistry<T>) registry.register(regKey, new SimpleRegistry<>(regKey, stable), stable);
    }
    
    private static void registerSerializer(SimpleRegistry<IIngredientTransformerSerializer<?>> registry, IIngredientTransformerSerializer<?> serializer) {
        
        registry.register(RegistryKey.getOrCreateKey(registry.getRegistryKey(), serializer
                .getType()), serializer, Lifecycle.stable());
    }
    
    private static void registerSerializer(SimpleRegistry<IIngredientConditionSerializer<?>> registry, IIngredientConditionSerializer<?> serializer) {
        
        registry.register(RegistryKey.getOrCreateKey(registry.getRegistryKey(), serializer
                .getType()), serializer, Lifecycle.stable());
    }
    
}
