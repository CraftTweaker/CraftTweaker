package com.blamejared.crafttweaker.platform;

import com.blamejared.crafttweaker.CraftTweakerRegistries;
import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.command.argument.*;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.ingredient.serializer.*;
import com.blamejared.crafttweaker.api.ingredient.type.*;
import com.blamejared.crafttweaker.api.recipe.serializer.*;
import com.blamejared.crafttweaker.impl.loot.condition.LootTableIdCondition;
import com.blamejared.crafttweaker.impl.loot.condition.LootTableIdRegexCondition;
import com.blamejared.crafttweaker.impl.script.*;
import com.blamejared.crafttweaker.platform.services.IRegistryHelper;
import net.fabricmc.fabric.api.command.v2.ArgumentTypeRegistry;
import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredientSerializer;
import net.minecraft.commands.synchronization.SingletonArgumentInfo;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;

public class FabricRegistryHelper implements IRegistryHelper {
    
    @Override
    public void init() {
        
        Registry.register(BuiltInRegistries.RECIPE_TYPE, ScriptRecipeType.INSTANCE.id(), ScriptRecipeType.INSTANCE);
        
        CraftTweakerRegistries.init();
        
        Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, CraftTweakerConstants.rl("shapeless"), CTShapelessRecipeSerializer.INSTANCE);
        Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, CraftTweakerConstants.rl("shaped"), CTShapedRecipeSerializer.INSTANCE);
        Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, CraftTweakerConstants.rl("script"), ScriptSerializer.INSTANCE);
        
        Registry.register(BuiltInRegistries.LOOT_CONDITION_TYPE, CraftTweakerConstants.rl("table_id"), LootTableIdCondition.LOOT_TABLE_ID);
        Registry.register(BuiltInRegistries.LOOT_CONDITION_TYPE, CraftTweakerConstants.rl("regex_table_id"), LootTableIdRegexCondition.LOOT_TABLE_ID_REGEX);
        
        CustomIngredientSerializer.register(IngredientAnySerializer.INSTANCE);
        CustomIngredientSerializer.register(IngredientListSerializer.INSTANCE);
        CustomIngredientSerializer.register(IngredientConditionedSerializer.INSTANCE);
        CustomIngredientSerializer.register(IngredientTransformedSerializer.INSTANCE);
        CustomIngredientSerializer.register(IngredientPartialTagSerializer.INSTANCE);
    
        ArgumentTypeRegistry.registerArgumentType(RecipeTypeArgument.ID, RecipeTypeArgument.class, SingletonArgumentInfo.contextFree(RecipeTypeArgument::get));
        ArgumentTypeRegistry.registerArgumentType(IItemStackArgument.ID, IItemStackArgument.class, SingletonArgumentInfo.contextFree(IItemStackArgument::get));
    }
    
    @Override
    public Ingredient getIngredientAny() {
        
        return IngredientAny.INSTANCE.toVanilla();
    }
    
    @Override
    public Ingredient getIngredientList(List<Ingredient> children) {
        
        return new IngredientList(children).toVanilla();
    }
    
    @Override
    public <T extends IIngredient> Ingredient getIngredientConditioned(IIngredientConditioned<T> conditioned) {
        
        return new IngredientConditioned<>(conditioned).toVanilla();
    }
    
    @Override
    public <T extends IIngredient> Ingredient getIngredientTransformed(IIngredientTransformed<T> transformed) {
        
        return new IngredientTransformed<>(transformed).toVanilla();
    }
    
    @Override
    public Ingredient getIngredientPartialTag(ItemStack stack) {
        
        return new IngredientPartialTag(stack).toVanilla();
    }
    
    
}
