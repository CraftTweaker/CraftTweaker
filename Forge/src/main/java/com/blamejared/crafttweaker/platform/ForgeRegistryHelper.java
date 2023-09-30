package com.blamejared.crafttweaker.platform;

import com.blamejared.crafttweaker.CraftTweakerRegistries;
import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.command.argument.IItemStackArgument;
import com.blamejared.crafttweaker.api.command.argument.RecipeTypeArgument;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.ingredient.serializer.*;
import com.blamejared.crafttweaker.api.ingredient.type.*;
import com.blamejared.crafttweaker.api.recipe.serializer.CTShapedRecipeSerializer;
import com.blamejared.crafttweaker.api.recipe.serializer.CTShapelessRecipeSerializer;
import com.blamejared.crafttweaker.impl.script.ScriptRecipeType;
import com.blamejared.crafttweaker.impl.script.ScriptSerializer;
import com.blamejared.crafttweaker.platform.services.IRegistryHelper;
import net.minecraft.commands.synchronization.ArgumentTypeInfos;
import net.minecraft.commands.synchronization.SingletonArgumentInfo;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.NewRegistryEvent;
import net.minecraftforge.registries.RegisterEvent;

import java.util.List;
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
            }
        });
        
        CraftingHelper.register(IIngredientAny.ID, IngredientAnySerializer.INSTANCE);
        CraftingHelper.register(IIngredientList.ID, IngredientListSerializer.INSTANCE);
        CraftingHelper.register(IIngredientTransformed.ID, IngredientTransformedSerializer.INSTANCE);
        CraftingHelper.register(IIngredientConditioned.ID, IngredientConditionedSerializer.INSTANCE);
        CraftingHelper.register(IngredientPartialTagSerializer.ID, IngredientPartialTagSerializer.INSTANCE);
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
