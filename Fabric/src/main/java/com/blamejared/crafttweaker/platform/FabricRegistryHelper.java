package com.blamejared.crafttweaker.platform;

import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.ingredient.serializer.IngredientAnySerializer;
import com.blamejared.crafttweaker.api.ingredient.serializer.IngredientConditionedSerializer;
import com.blamejared.crafttweaker.api.ingredient.serializer.IngredientListSerializer;
import com.blamejared.crafttweaker.api.ingredient.serializer.IngredientPartialTagSerializer;
import com.blamejared.crafttweaker.api.ingredient.serializer.IngredientTransformedSerializer;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;

public class FabricRegistryHelper implements IRegistryHelper {
    
    @Override
    public void initRegistries() {
        
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
