package com.blamejared.crafttweaker.platform;

import com.blamejared.crafttweaker.CraftTweakerRegistries;
import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.command.argument.IItemStackArgument;
import com.blamejared.crafttweaker.api.command.argument.RecipeTypeArgument;
import com.blamejared.crafttweaker.api.ingredient.type.IIngredientAny;
import com.blamejared.crafttweaker.api.ingredient.type.IIngredientConditioned;
import com.blamejared.crafttweaker.api.ingredient.type.IIngredientList;
import com.blamejared.crafttweaker.api.ingredient.type.IIngredientTransformed;
import com.blamejared.crafttweaker.api.ingredient.vanilla.CraftTweakerIngredients;
import com.blamejared.crafttweaker.api.ingredient.vanilla.serializer.IngredientPartialTagSerializer;
import com.blamejared.crafttweaker.api.recipe.serializer.CTShapedRecipeSerializer;
import com.blamejared.crafttweaker.api.recipe.serializer.CTShapelessRecipeSerializer;
import com.blamejared.crafttweaker.impl.loot.condition.LootTableIdRegexCondition;
import com.blamejared.crafttweaker.impl.script.ScriptRecipeType;
import com.blamejared.crafttweaker.impl.script.ScriptSerializer;
import com.blamejared.crafttweaker.platform.services.IRegistryHelper;
import net.minecraft.commands.synchronization.ArgumentTypeInfos;
import net.minecraft.commands.synchronization.SingletonArgumentInfo;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.javafmlmod.FMLJavaModLoadingContext;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

import java.util.function.Consumer;

public class NeoForgeRegistryHelper implements IRegistryHelper {
    
    
    @Override
    public void init() {
        
        FMLJavaModLoadingContext.get()
                .getModEventBus()
                .addListener((Consumer<NewRegistryEvent>) newRegistry -> CraftTweakerRegistries.init());
        
        // Thank you (neo)forge, very cool.
        FMLJavaModLoadingContext.get().getModEventBus().addListener((Consumer<RegisterEvent>) event -> {
            if(Registries.BLOCK.equals(event.getRegistryKey())) {
                Registry.register(
                        BuiltInRegistries.LOOT_CONDITION_TYPE,
                        new ResourceLocation(CraftTweakerConstants.MOD_ID, "loot_table_id_regex"),
                        LootTableIdRegexCondition.LOOT_TABLE_ID_REGEX
                );
            } else if(Registries.RECIPE_SERIALIZER.equals(event.getRegistryKey())) {
                event.register(Registries.RECIPE_SERIALIZER, helper -> {
                    helper.register(CraftTweakerConstants.rl("shapeless"), CTShapelessRecipeSerializer.INSTANCE);
                    helper.register(CraftTweakerConstants.rl("shaped"), CTShapedRecipeSerializer.INSTANCE);
                    helper.register(CraftTweakerConstants.rl("script"), ScriptSerializer.INSTANCE);
                });
            } else if(Registries.RECIPE_TYPE.equals(event.getRegistryKey())) {
                event.register(Registries.RECIPE_TYPE, helper -> {
                    helper.register(ScriptRecipeType.INSTANCE.id(), ScriptRecipeType.INSTANCE);
                });
            } else if(Registries.COMMAND_ARGUMENT_TYPE.equals(event.getRegistryKey())) {
                event.register(Registries.COMMAND_ARGUMENT_TYPE, helper -> {
                    helper.register(RecipeTypeArgument.ID, ArgumentTypeInfos.registerByClass(RecipeTypeArgument.class, SingletonArgumentInfo.contextFree(RecipeTypeArgument::get)));
                    helper.register(IItemStackArgument.ID, ArgumentTypeInfos.registerByClass(IItemStackArgument.class, SingletonArgumentInfo.contextFree(IItemStackArgument::get)));
                });
            } else if(NeoForgeRegistries.Keys.INGREDIENT_TYPES.equals(event.getRegistryKey())) {
                event.register(NeoForgeRegistries.Keys.INGREDIENT_TYPES, helper -> {
                    helper.register(IIngredientAny.ID, CraftTweakerIngredients.Types.ANY);
                    helper.register(IIngredientList.ID, CraftTweakerIngredients.Types.LIST);
                    helper.register(IIngredientTransformed.ID, CraftTweakerIngredients.Types.TRANSFORMED);
                    helper.register(IIngredientConditioned.ID, CraftTweakerIngredients.Types.CONDITIONED);
                    helper.register(IngredientPartialTagSerializer.ID, CraftTweakerIngredients.Types.PARTIAL_TAG);
                });
            }
        });
        
    }
    
}
