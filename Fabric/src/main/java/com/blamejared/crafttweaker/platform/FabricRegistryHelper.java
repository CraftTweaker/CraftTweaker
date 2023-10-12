package com.blamejared.crafttweaker.platform;

import com.blamejared.crafttweaker.CraftTweakerRegistries;
import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.command.argument.IItemStackArgument;
import com.blamejared.crafttweaker.api.command.argument.RecipeTypeArgument;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.ingredient.type.IIngredientConditioned;
import com.blamejared.crafttweaker.api.ingredient.type.IIngredientTransformed;
import com.blamejared.crafttweaker.api.ingredient.vanilla.CraftTweakerIngredients;
import com.blamejared.crafttweaker.api.recipe.serializer.CTShapedRecipeSerializer;
import com.blamejared.crafttweaker.api.recipe.serializer.CTShapelessRecipeSerializer;
import com.blamejared.crafttweaker.impl.loot.condition.LootTableIdCondition;
import com.blamejared.crafttweaker.impl.loot.condition.LootTableIdRegexCondition;
import com.blamejared.crafttweaker.impl.script.ScriptRecipeType;
import com.blamejared.crafttweaker.impl.script.ScriptSerializer;
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
        
        CustomIngredientSerializer.register(CraftTweakerIngredients.Serializers.ANY);
        CustomIngredientSerializer.register(CraftTweakerIngredients.Serializers.LIST);
        CustomIngredientSerializer.register(CraftTweakerIngredients.Serializers.CONDITIONED);
        CustomIngredientSerializer.register(CraftTweakerIngredients.Serializers.TRANSFORMED);
        CustomIngredientSerializer.register(CraftTweakerIngredients.Serializers.PARTIAL_TAG);
        
        ArgumentTypeRegistry.registerArgumentType(RecipeTypeArgument.ID, RecipeTypeArgument.class, SingletonArgumentInfo.contextFree(RecipeTypeArgument::get));
        ArgumentTypeRegistry.registerArgumentType(IItemStackArgument.ID, IItemStackArgument.class, SingletonArgumentInfo.contextFree(IItemStackArgument::get));
    }
    
}
