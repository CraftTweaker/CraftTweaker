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
import com.blamejared.crafttweaker.api.ingredient.vanilla.serializer.IngredientAnySerializer;
import com.blamejared.crafttweaker.api.ingredient.vanilla.serializer.IngredientConditionedSerializer;
import com.blamejared.crafttweaker.api.ingredient.vanilla.serializer.IngredientListSerializer;
import com.blamejared.crafttweaker.api.ingredient.vanilla.serializer.IngredientPartialTagSerializer;
import com.blamejared.crafttweaker.api.ingredient.vanilla.serializer.IngredientTransformedSerializer;
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
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.NewRegistryEvent;
import net.minecraftforge.registries.RegisterEvent;

import java.util.function.Consumer;

public class ForgeRegistryHelper implements IRegistryHelper {
    
    
    @Override
    public void init() {
        
        FMLJavaModLoadingContext.get()
                .getModEventBus()
                .addListener((Consumer<NewRegistryEvent>) newRegistry -> CraftTweakerRegistries.init());
        
        // Thank you forge, very cool.
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
            } else if(ForgeRegistries.Keys.INGREDIENT_SERIALIZERS.equals(event.getRegistryKey())) {
                event.register(ForgeRegistries.Keys.INGREDIENT_SERIALIZERS, helper -> {
                    helper.register(IIngredientAny.ID, CraftTweakerIngredients.Serializers.ANY);
                    helper.register(IIngredientList.ID, CraftTweakerIngredients.Serializers.LIST);
                    helper.register(IIngredientTransformed.ID, CraftTweakerIngredients.Serializers.TRANSFORMED);
                    helper.register(IIngredientConditioned.ID, CraftTweakerIngredients.Serializers.CONDITIONED);
                    helper.register(IngredientPartialTagSerializer.ID, CraftTweakerIngredients.Serializers.PARTIAL_TAG);
                });
            }
        });
        
    }
    
}
