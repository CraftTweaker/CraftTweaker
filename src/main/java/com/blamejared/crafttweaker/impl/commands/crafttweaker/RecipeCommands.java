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
        
        CraftTweakerAPI.logInfo("Dumping all recipes that output %s!", new MCItemStackMutable(stack).getCommandString());
        
        final IItemStack workingStack = new MCItemStackMutable(stack).setAmount(1);
        
        player.world.getRecipeManager().recipes.forEach((recipeType, map) -> dumpRecipe(recipeType, map.values(), it -> workingStack.matches(new MCItemStackMutable(it.getRecipeOutput()))));
    
        CommandUtilities.send(CommandUtilities.color("Recipe list generated! Check the crafttweaker.log file!", TextFormatting.GREEN), player);
        return 0;
    }
    
    private static void dumpRecipe(final IRecipeType<?> type, final Collection<IRecipe<?>> recipes, final Predicate<IRecipe<?>> filter) {
        final IRecipeManager manager = RecipeTypeBracketHandler.getRecipeManager(type.toString());
        if (manager == null) {
            // Scripts for example don't have a recipe manager
            return;
        }
        
        final StringBuilder builder = new StringBuilder("Recipe type: '");
        builder.append(manager.getCommandString());
        builder.append("'\n");
    
        final long count = recipes.stream()
                .filter(filter)
                .sorted(Comparator.comparing(RecipeCommands::serializer).thenComparing(IRecipe::getId))
                .map(it -> dump(manager, it))
                .peek(it -> builder.append(it).append('\n'))
                .count();
        
        if (count > 0) {
            CraftTweakerAPI.logDump(builder.toString());
        } else {
            CraftTweakerAPI.logDump("No recipe found");
        }
    }
    
    private static ResourceLocation serializer(final IRecipe<?> recipe) {
        return Objects.requireNonNull(recipe.getSerializer().getRegistryName());
    }
    
    private static <T extends IRecipe<?>> String dump(final IRecipeManager manager, final T recipe) {
        return CraftTweakerRegistry.getHandlerFor(recipe).dumpToCommandString(manager, recipe);
    }
}
