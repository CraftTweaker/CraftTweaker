package com.blamejared.crafttweaker.platform;

import com.blamejared.crafttweaker.CraftTweakerRegistries;
import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.ingredient.condition.serializer.ConditionAnyDamagedSerializer;
import com.blamejared.crafttweaker.api.ingredient.condition.serializer.ConditionCustomSerializer;
import com.blamejared.crafttweaker.api.ingredient.condition.serializer.ConditionDamagedSerializer;
import com.blamejared.crafttweaker.api.ingredient.serializer.IngredientAnySerializer;
import com.blamejared.crafttweaker.api.ingredient.serializer.IngredientConditionedSerializer;
import com.blamejared.crafttweaker.api.ingredient.serializer.IngredientListSerializer;
import com.blamejared.crafttweaker.api.ingredient.serializer.IngredientPartialTagSerializer;
import com.blamejared.crafttweaker.api.ingredient.serializer.IngredientTransformedSerializer;
import com.blamejared.crafttweaker.api.ingredient.transform.serializer.TransformCustomSerializer;
import com.blamejared.crafttweaker.api.ingredient.transform.serializer.TransformDamageSerializer;
import com.blamejared.crafttweaker.api.ingredient.transform.serializer.TransformReplaceSerializer;
import com.blamejared.crafttweaker.api.ingredient.transform.serializer.TransformerReuseSerializer;
import com.blamejared.crafttweaker.api.ingredient.type.IIngredientConditioned;
import com.blamejared.crafttweaker.api.ingredient.type.IIngredientTransformed;
import com.blamejared.crafttweaker.api.ingredient.type.IngredientAny;
import com.blamejared.crafttweaker.api.ingredient.type.IngredientConditioned;
import com.blamejared.crafttweaker.api.ingredient.type.IngredientList;
import com.blamejared.crafttweaker.api.ingredient.type.IngredientPartialTag;
import com.blamejared.crafttweaker.api.ingredient.type.IngredientTransformed;
import com.blamejared.crafttweaker.api.recipe.serializer.CTShapedRecipeSerializer;
import com.blamejared.crafttweaker.api.recipe.serializer.CTShapelessRecipeSerializer;
import com.blamejared.crafttweaker.api.recipe.serializer.ICTShapedRecipeBaseSerializer;
import com.blamejared.crafttweaker.api.recipe.serializer.ICTShapelessRecipeBaseSerializer;
import com.blamejared.crafttweaker.impl.script.ScriptSerializer;
import com.blamejared.crafttweaker.platform.services.IRegistryHelper;
import com.faux.ingredientextension.api.ingredient.IngredientHelper;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.List;

public class FabricRegistryHelper implements IRegistryHelper {
    
    @Override
    public void init() {
        
        CraftTweakerRegistries.RECIPE_TYPE_SCRIPTS = RecipeType.register(CraftTweakerConstants.rl("scripts")
                .toString());
        
        ResourceLocation transformerSerializerRL = CraftTweakerConstants.rl("transformer_serializer");
        ResourceLocation conditionSerializerRL = CraftTweakerConstants.rl("condition_serializer");
        
        CraftTweakerRegistries.REGISTRY_TRANSFORMER_SERIALIZER = registerVanillaRegistry(transformerSerializerRL);
        CraftTweakerRegistries.REGISTRY_CONDITIONER_SERIALIZER = registerVanillaRegistry(conditionSerializerRL);
        
        registerSerializer(CraftTweakerRegistries.REGISTRY_TRANSFORMER_SERIALIZER, TransformReplaceSerializer.INSTANCE);
        registerSerializer(CraftTweakerRegistries.REGISTRY_TRANSFORMER_SERIALIZER, TransformDamageSerializer.INSTANCE);
        registerSerializer(CraftTweakerRegistries.REGISTRY_TRANSFORMER_SERIALIZER, TransformCustomSerializer.INSTANCE);
        registerSerializer(CraftTweakerRegistries.REGISTRY_TRANSFORMER_SERIALIZER, TransformerReuseSerializer.INSTANCE);
        
        registerSerializer(CraftTweakerRegistries.REGISTRY_CONDITIONER_SERIALIZER, ConditionDamagedSerializer.INSTANCE);
        registerSerializer(CraftTweakerRegistries.REGISTRY_CONDITIONER_SERIALIZER, ConditionAnyDamagedSerializer.INSTANCE);
        registerSerializer(CraftTweakerRegistries.REGISTRY_CONDITIONER_SERIALIZER, ConditionCustomSerializer.INSTANCE);
        
        Registry.register(Registry.RECIPE_SERIALIZER, CraftTweakerConstants.rl("shapeless"), CTShapelessRecipeSerializer.INSTANCE);
        Registry.register(Registry.RECIPE_SERIALIZER, CraftTweakerConstants.rl("shaped"), CTShapedRecipeSerializer.INSTANCE);
        Registry.register(Registry.RECIPE_SERIALIZER, CraftTweakerConstants.rl("script"), ScriptSerializer.INSTANCE);
        
        Registry.register(IngredientHelper.INGREDIENT_SERIALIZER_REGISTRY, CraftTweakerConstants.rl("any"), IngredientAnySerializer.INSTANCE);
        Registry.register(IngredientHelper.INGREDIENT_SERIALIZER_REGISTRY, CraftTweakerConstants.rl("list"), IngredientListSerializer.INSTANCE);
        Registry.register(IngredientHelper.INGREDIENT_SERIALIZER_REGISTRY, CraftTweakerConstants.rl("conditioned"), IngredientConditionedSerializer.INSTANCE);
        Registry.register(IngredientHelper.INGREDIENT_SERIALIZER_REGISTRY, CraftTweakerConstants.rl("transformed"), IngredientTransformedSerializer.INSTANCE);
        Registry.register(IngredientHelper.INGREDIENT_SERIALIZER_REGISTRY, CraftTweakerConstants.rl("partial_tag"), IngredientPartialTagSerializer.INSTANCE);
    }
    
    @Override
    public ICTShapedRecipeBaseSerializer getCTShapedRecipeSerializer() {
        
        return CTShapedRecipeSerializer.INSTANCE;
    }
    
    @Override
    public ICTShapelessRecipeBaseSerializer getCTShapelessRecipeSerializer() {
        
        return CTShapelessRecipeSerializer.INSTANCE;
    }
    
    @Override
    public Ingredient getIngredientAny() {
        
        return IngredientAny.INSTANCE;
    }
    
    @Override
    public Ingredient getIngredientList(List<Ingredient> children) {
        
        return new IngredientList(children);
    }
    
    @Override
    public <T extends IIngredient> Ingredient getIngredientConditioned(IIngredientConditioned<T> conditioned) {
        
        return new IngredientConditioned<>(conditioned);
    }
    
    @Override
    public <T extends IIngredient> Ingredient getIngredientTransformed(IIngredientTransformed<T> transformed) {
        
        return new IngredientTransformed<>(transformed);
    }
    
    @Override
    public Ingredient getIngredientPartialTag(ItemStack stack) {
        
        return new IngredientPartialTag(stack);
    }
    
    
}
