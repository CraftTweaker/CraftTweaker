package com.blamejared.crafttweaker;

import com.blamejared.crafttweaker.api.item.transformed.IIngredientTransformerSerializer;
import com.blamejared.crafttweaker.impl.ingredients.transform.*;
import com.blamejared.crafttweaker.impl.item.transformed.IngredientTransformedSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.SimpleRegistry;
import net.minecraftforge.common.crafting.CraftingHelper;

public class CraftTweakerRegistries {
    
    public static SimpleRegistry<IIngredientTransformerSerializer<?>> REGISTRY_TRANSFORMER_SERIALIZER;
    public static IngredientTransformedSerializer INGREDIENT_TRANSFORMED_SERIALIZER;
    
    public static TransformReplace.TransformReplaceTransformerSerializer TRANSFORM_REPLACE_SERIALIZER;
    public static TransformDamage.TransformDamageSerializer TRANSFORM_DAMAGE_SERIALIZER;
    public static TransformCustom.TransformCustomSerializer TRANSFORM_CUSTOM_SERIALIZER;
    public static TransformReuse.TransformerReuseSerializer TRANSFORM_REUSE_SERIALIZER;

    public static void init() {
        REGISTRY_TRANSFORMER_SERIALIZER = Registry.REGISTRY.register(new ResourceLocation(CraftTweaker.MODID, "transformer_serializer"), new SimpleRegistry<>());
        INGREDIENT_TRANSFORMED_SERIALIZER = new IngredientTransformedSerializer();
        CraftingHelper.register(new ResourceLocation(CraftTweaker.MODID, "ingredient_transformed_serializer"), INGREDIENT_TRANSFORMED_SERIALIZER);

        TRANSFORM_REPLACE_SERIALIZER = new TransformReplace.TransformReplaceTransformerSerializer();
        REGISTRY_TRANSFORMER_SERIALIZER.register(TRANSFORM_REPLACE_SERIALIZER.getType(), TRANSFORM_REPLACE_SERIALIZER);

        TRANSFORM_DAMAGE_SERIALIZER = new TransformDamage.TransformDamageSerializer();
        REGISTRY_TRANSFORMER_SERIALIZER.register(TRANSFORM_DAMAGE_SERIALIZER.getType(), TRANSFORM_DAMAGE_SERIALIZER);

        TRANSFORM_CUSTOM_SERIALIZER = new TransformCustom.TransformCustomSerializer();
        REGISTRY_TRANSFORMER_SERIALIZER.register(TRANSFORM_CUSTOM_SERIALIZER.getType(), TRANSFORM_CUSTOM_SERIALIZER);
    
        TRANSFORM_REUSE_SERIALIZER = new TransformReuse.TransformerReuseSerializer();
        REGISTRY_TRANSFORMER_SERIALIZER.register(TRANSFORM_REUSE_SERIALIZER.getType(), TRANSFORM_REUSE_SERIALIZER);
    }
}
