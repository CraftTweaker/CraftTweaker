package com.blamejared.crafttweaker.impl.commands.crafttweaker;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.CraftTweakerRegistry;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.impl.brackets.RecipeTypeBracketHandler;
import com.blamejared.crafttweaker.impl.commands.CTCommands;
import com.blamejared.crafttweaker.impl.commands.CommandUtilities;
import com.blamejared.crafttweaker.impl.item.MCItemStackMutable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

import java.util.Collection;
import java.util.Comparator;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public final class RecipeCommands {
    
    private RecipeCommands() {}
    
    public static void registerRecipeCommands() {
        
        CTCommands.registerCommand(CTCommands.playerCommand("recipes", "Outputs information on all recipes.", RecipeCommands::dumpRecipes));
        CTCommands.registerCommand("recipes", CTCommands.playerCommand("hand", "Outputs information on all Recipes for the held item", RecipeCommands::dumpHand));
    }
    
    private static int dumpRecipes(final PlayerEntity player, final ItemStack stack) {
        
        CraftTweakerAPI.logInfo("Dumping all recipes!");
        
        player.world.getRecipeManager().recipes.forEach((recipeType, map) -> dumpRecipe(recipeType, map.values(), it -> true));
        
        CommandUtilities.send(CommandUtilities.color("Recipe list generated! Check the crafttweaker.log file!", TextFormatting.GREEN), player);
        return 0;
    }
    
    private static int dumpHand(final PlayerEntity player, final ItemStack stack) {
        
        if(stack.isEmpty()) {
            // Only done because *a lot* of mods return empty ItemStacks as outputs
            CommandUtilities.send(CommandUtilities.color("Cannot get recipes for an empty ItemStack!", TextFormatting.RED), player);
            return 0;
        }
        
        final IItemStack workingStack = new MCItemStackMutable(stack.copy()).setAmount(1);
        
        CraftTweakerAPI.logInfo("Dumping all recipes that output %s!", workingStack.getCommandString());
        
        player.world.getRecipeManager().recipes.forEach((recipeType, map) -> dumpRecipe(recipeType, map.values(), it -> workingStack.matches(new MCItemStackMutable(it.getRecipeOutput()))));
        
        CommandUtilities.send(CommandUtilities.color("Recipe list generated! Check the crafttweaker.log file!", TextFormatting.GREEN), player);
        return 0;
    }
    
    private static void dumpRecipe(final IRecipeType<?> type, final Collection<IRecipe<?>> recipes, final Predicate<IRecipe<?>> filter) {
        
        final IRecipeManager manager = RecipeTypeBracketHandler.getOrDefault(type);
        if(manager == null) {
            // Scripts for example don't have a recipe manager
            return;
        }
        
        final String dumpResult = recipes.stream()
                .filter(filter)
                .sorted(Comparator.comparing(RecipeCommands::serializer).thenComparing(IRecipe::getId))
                .map(it -> dump(manager, it))
                .collect(Collectors.joining("\n  "));
        
        CraftTweakerAPI.logDump("Recipe type: '%s'\n  %s\n", manager.getCommandString(), dumpResult.isEmpty() ? "No recipe found" : dumpResult);
    }
    
    private static ResourceLocation serializer(final IRecipe<?> recipe) {
        
        return Objects.requireNonNull(recipe.getSerializer().getRegistryName());
    }
    
    private static <T extends IRecipe<?>> String dump(final IRecipeManager manager, final T recipe) {
        
        return CraftTweakerRegistry.getHandlerFor(recipe).dumpToCommandString(manager, recipe);
    }
    
}
