package com.blamejared.crafttweaker;

import com.blamejared.crafttweaker.api.item.conditions.IIngredientConditionSerializer;
import com.blamejared.crafttweaker.api.item.transformed.IIngredientTransformerSerializer;
import com.blamejared.crafttweaker.impl.ingredients.conditions.ConditionAnyDamage;
import com.blamejared.crafttweaker.impl.ingredients.conditions.ConditionCustom;
import com.blamejared.crafttweaker.impl.ingredients.conditions.ConditionDamaged;
import com.blamejared.crafttweaker.impl.ingredients.transform.TransformCustom;
import com.blamejared.crafttweaker.impl.ingredients.transform.TransformDamage;
import com.blamejared.crafttweaker.impl.ingredients.transform.TransformReplace;
import com.blamejared.crafttweaker.impl.ingredients.transform.TransformReuse;
import com.blamejared.crafttweaker.impl.item.conditions.IngredientConditionedSerializer;
import com.blamejared.crafttweaker.impl.item.transformed.IngredientTransformedSerializer;
import com.mojang.serialization.Lifecycle;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.SimpleRegistry;
import net.minecraftforge.common.crafting.CraftingHelper;

public class CraftTweakerRegistries {
    
    public static SimpleRegistry<IIngredientTransformerSerializer<?>> REGISTRY_TRANSFORMER_SERIALIZER;
    public static IngredientTransformedSerializer INGREDIENT_TRANSFORMED_SERIALIZER;
    
    public static SimpleRegistry<IIngredientConditionSerializer<?>> REGISTRY_CONDITIONER_SERIALIZER;
    public static IngredientConditionedSerializer INGREDIENT_CONDITIONED_SERIALIZER;
    
    public static TransformReplace.TransformReplaceTransformerSerializer TRANSFORM_REPLACE_SERIALIZER;
    public static TransformDamage.TransformDamageSerializer TRANSFORM_DAMAGE_SERIALIZER;
    public static TransformCustom.TransformCustomSerializer TRANSFORM_CUSTOM_SERIALIZER;
    public static TransformReuse.TransformerReuseSerializer TRANSFORM_REUSE_SERIALIZER;
    
    
    public static ConditionDamaged.ConditionDamagedSerializer CONDITION_DAMAGE_SERIALIZER;
    public static ConditionAnyDamage.ConditionAnyDamagedSerializer CONDITION_ANY_DAMAGE_SERIALIZER;
    public static ConditionCustom.ConditionCustomSerializer CONDITION_CUSTOM_SERIALIZER;
    
    public static void init() {
        ResourceLocation transformerSerializerRL = new ResourceLocation(CraftTweaker.MODID, "transformer_serializer");
        ResourceLocation conditionSerializerRL = new ResourceLocation(CraftTweaker.MODID, "condition_serializer");
        
        MutableRegistry registry = (MutableRegistry) Registry.REGISTRY;
        REGISTRY_TRANSFORMER_SERIALIZER = (SimpleRegistry<IIngredientTransformerSerializer<?>>) registry.register(RegistryKey.func_240904_a_(transformerSerializerRL), new SimpleRegistry<>(RegistryKey.func_240904_a_(transformerSerializerRL), Lifecycle.stable()), Lifecycle.stable());
        REGISTRY_CONDITIONER_SERIALIZER = (SimpleRegistry<IIngredientConditionSerializer<?>>) registry.register(RegistryKey.func_240904_a_(conditionSerializerRL), new SimpleRegistry<>(RegistryKey.func_240904_a_(conditionSerializerRL), Lifecycle.stable()), Lifecycle.stable());
        INGREDIENT_TRANSFORMED_SERIALIZER = new IngredientTransformedSerializer();
        INGREDIENT_CONDITIONED_SERIALIZER = new IngredientConditionedSerializer();
        
        CraftingHelper.register(new ResourceLocation(CraftTweaker.MODID, "ingredient_transformed_serializer"), INGREDIENT_TRANSFORMED_SERIALIZER);
        CraftingHelper.register(new ResourceLocation(CraftTweaker.MODID, "ingredient_conditioned_serializer"), INGREDIENT_CONDITIONED_SERIALIZER);
        
        TRANSFORM_REPLACE_SERIALIZER = new TransformReplace.TransformReplaceTransformerSerializer();
        REGISTRY_TRANSFORMER_SERIALIZER.register(RegistryKey.func_240903_a_(REGISTRY_TRANSFORMER_SERIALIZER.func_243578_f(), TRANSFORM_REPLACE_SERIALIZER.getType()), TRANSFORM_REPLACE_SERIALIZER, Lifecycle.stable());
        
        TRANSFORM_DAMAGE_SERIALIZER = new TransformDamage.TransformDamageSerializer();
        REGISTRY_TRANSFORMER_SERIALIZER.register(RegistryKey.func_240903_a_(REGISTRY_TRANSFORMER_SERIALIZER.func_243578_f(), TRANSFORM_DAMAGE_SERIALIZER.getType()), TRANSFORM_DAMAGE_SERIALIZER, Lifecycle.stable());
        
        TRANSFORM_CUSTOM_SERIALIZER = new TransformCustom.TransformCustomSerializer();
        REGISTRY_TRANSFORMER_SERIALIZER.register(RegistryKey.func_240903_a_(REGISTRY_TRANSFORMER_SERIALIZER.func_243578_f(), TRANSFORM_CUSTOM_SERIALIZER.getType()), TRANSFORM_CUSTOM_SERIALIZER, Lifecycle.stable());
        
        TRANSFORM_REUSE_SERIALIZER = new TransformReuse.TransformerReuseSerializer();
        REGISTRY_TRANSFORMER_SERIALIZER.register(RegistryKey.func_240903_a_(REGISTRY_TRANSFORMER_SERIALIZER.func_243578_f(), TRANSFORM_REUSE_SERIALIZER.getType()), TRANSFORM_REUSE_SERIALIZER, Lifecycle.stable());
        
        
        CONDITION_DAMAGE_SERIALIZER = new ConditionDamaged.ConditionDamagedSerializer();
        REGISTRY_CONDITIONER_SERIALIZER.register(RegistryKey.func_240903_a_(REGISTRY_CONDITIONER_SERIALIZER.func_243578_f(), CONDITION_DAMAGE_SERIALIZER.getType()), CONDITION_DAMAGE_SERIALIZER, Lifecycle.stable());
        
        CONDITION_ANY_DAMAGE_SERIALIZER = new ConditionAnyDamage.ConditionAnyDamagedSerializer();
        REGISTRY_CONDITIONER_SERIALIZER.register(RegistryKey.func_240903_a_(REGISTRY_CONDITIONER_SERIALIZER.func_243578_f(), CONDITION_ANY_DAMAGE_SERIALIZER.getType()), CONDITION_ANY_DAMAGE_SERIALIZER, Lifecycle.stable());
        
        CONDITION_CUSTOM_SERIALIZER = new ConditionCustom.ConditionCustomSerializer();
        REGISTRY_CONDITIONER_SERIALIZER.register(RegistryKey.func_240903_a_(REGISTRY_CONDITIONER_SERIALIZER.func_243578_f(), CONDITION_CUSTOM_SERIALIZER.getType()), CONDITION_CUSTOM_SERIALIZER, Lifecycle.stable());
    }
}
