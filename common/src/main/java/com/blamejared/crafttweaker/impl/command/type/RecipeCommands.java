package com.blamejared.crafttweaker.impl.command.type;

import com.blamejared.crafttweaker.api.bracket.custom.RecipeTypeBracketHandler;
import com.blamejared.crafttweaker.api.command.CommandUtilities;
import com.blamejared.crafttweaker.api.command.argument.RecipeTypeArgument;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.plugin.ICommandRegistrationHandler;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandlerRegistry;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.crafttweaker.api.util.ItemStackUtil;
import com.blamejared.crafttweaker.impl.helper.AccessibleElementsProvider;
import com.blamejared.crafttweaker.mixin.common.access.recipe.AccessRecipeManager;
import com.mojang.brigadier.Command;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class RecipeCommands {
    
    private RecipeCommands() {}
    
    public static void registerCommands(final ICommandRegistrationHandler handler) {
        
        handler.registerRootCommand(
                "recipes",
                Component.translatable("crafttweaker.command.description.recipes"),
                builder -> builder.executes(context -> dumpRecipes(context.getSource()))
        );
        handler.registerSubCommand(
                "recipes",
                "hand",
                Component.translatable("crafttweaker.command.description.recipes.hand"),
                builder -> builder.executes(context -> {
                    CommandSourceStack source = context.getSource();
                    final ServerPlayer player = source.getPlayerOrException();
                    
                    return RecipeCommands.dumpHand(source, player.getMainHandItem());
                })
        );
        handler.registerSubCommand(
                "recipes",
                "inventory",
                Component.translatable("crafttweaker.command.description.recipes.inventory"),
                builder -> builder.executes(context -> {
                    CommandSourceStack source = context.getSource();
                    final ServerPlayer player = source.getPlayerOrException();
                    
                    List<ItemStack> stacks = new ArrayList<>();
                    
                    IntStream.range(0, player.getInventory().getContainerSize())
                            .mapToObj(player.getInventory()::getItem)
                            .filter(itemStack -> !itemStack.isEmpty())
                            .forEach(itemStack -> {
                                if(stacks.stream().noneMatch(stack -> ItemStack.isSameItem(itemStack, stack))) {
                                    stacks.add(itemStack);
                                }
                            });
                    return RecipeCommands.dump(source, stacks);
                })
        );
        handler.registerSubCommand(
                "recipes",
                "manager",
                Component.translatable("crafttweaker.command.description.recipes.manager"),
                builder -> builder.then(Commands.argument("type", RecipeTypeArgument.get()).executes(context -> {
                    CommandSourceStack source = context.getSource();
                    return dumpRecipes(source, context.getArgument("type", IRecipeManager.class));
                }))
        );
    }
    
    private static int dumpRecipes(final CommandSourceStack source) {
        
        CommandUtilities.COMMAND_LOGGER.info("Dumping all recipes!");
        
        dumpRecipes(source, it -> true);
        
        CommandUtilities.send(source, CommandUtilities.openingLogFile(Component.translatable("crafttweaker.command.list.check.log", CommandUtilities.makeNoticeable(Component.translatable("crafttweaker.command.misc.recipes.list")), CommandUtilities.getFormattedLogFile())
                .withStyle(ChatFormatting.GREEN)));
        return Command.SINGLE_SUCCESS;
    }
    
    private static int dumpRecipes(final CommandSourceStack source, final IRecipeManager<?> manager) {
        
        CommandUtilities.COMMAND_LOGGER.info("Dumping recipes for manager " + manager.getCommandString() + "!");
        
        final RecipeType<?> type = manager.getRecipeType();
        dumpRecipes(source, it -> Objects.equals(it, type));
        
        CommandUtilities.send(source, CommandUtilities.openingLogFile(Component.translatable("crafttweaker.command.list.check.log", CommandUtilities.makeNoticeable(Component.translatable("crafttweaker.command.misc.recipes.list")), CommandUtilities.getFormattedLogFile())
                .withStyle(ChatFormatting.GREEN)));
        return Command.SINGLE_SUCCESS;
    }
    
    private static int dumpHand(final CommandSourceStack source, final ItemStack stack) {
        
        return dump(source, List.of(stack));
    }
    
    private static int dump(final CommandSourceStack source, final List<ItemStack> stacks) {
        
        for(ItemStack stack : stacks) {
            
            if(stack.isEmpty()) {
                // Only done because *a lot* of mods return empty ItemStacks as outputs
                CommandUtilities.send(source, Component.translatable("crafttweaker.command.recipes.hand.empty")
                        .withStyle(ChatFormatting.RED));
                return Command.SINGLE_SUCCESS;
            }
            
            final IItemStack workingStack = IItemStack.of(stack.copy()).setAmount(1);
            
            CommandUtilities.COMMAND_LOGGER.info("Dumping all recipes that output {}!", ItemStackUtil.getCommandString(workingStack.getInternal()));
            
            ((AccessRecipeManager) source.getLevel().getRecipeManager()).crafttweaker$getRecipes()
                    .forEach((recipeType, map) ->
                            dumpRecipe(recipeType, map.values(), it -> workingStack.matches(IItemStack.of(AccessibleElementsProvider.get()
                                    .registryAccess(it.value()::getResultItem))), true));
        }
        CommandUtilities.send(source, CommandUtilities.openingLogFile(Component.translatable("crafttweaker.command.list.check.log", CommandUtilities.makeNoticeable(Component.translatable("crafttweaker.command.misc.recipes.list")), CommandUtilities.getFormattedLogFile())
                .withStyle(ChatFormatting.GREEN)));
        return Command.SINGLE_SUCCESS;
    }
    
    private static void dumpRecipes(final CommandSourceStack source, final Predicate<RecipeType<?>> typeFilter) {
        
        ((AccessRecipeManager) source.getLevel().getRecipeManager()).crafttweaker$getRecipes()
                .entrySet()
                .stream()
                .filter(it -> typeFilter.test(it.getKey()))
                .forEach(it -> dumpRecipe(it.getKey(), it.getValue().values(), recipe -> true, false));
    }
    
    private static void dumpRecipe(final RecipeType<?> type, final Collection<RecipeHolder<?>> recipes, final Predicate<RecipeHolder<?>> filter, final boolean hideEmpty) {
        
        final IRecipeManager<?> manager = RecipeTypeBracketHandler.getOrDefault(type);
        if(manager == null) {
            // Scripts for example don't have a recipe manager
            return;
        }
        
        final String dumpResult = recipes.stream()
                .filter(filter)
                .sorted(Comparator.comparing(RecipeCommands::serializer).thenComparing(RecipeHolder::id))
                .map(it -> dump(GenericUtil.uncheck(manager), it))
                .collect(Collectors.joining("\n  "));
        
        if(hideEmpty && dumpResult.isEmpty()) {
            return;
        }
        
        CommandUtilities.COMMAND_LOGGER.info("Recipe type: '{}'\n  {}\n", manager.getCommandString(), dumpResult.isEmpty() ? "No recipe found" : dumpResult);
    }
    
    private static ResourceLocation serializer(final RecipeHolder<?> recipe) {
        
        return Objects.requireNonNull(BuiltInRegistries.RECIPE_SERIALIZER.getKey(recipe.value().getSerializer()));
    }
    
    private static <T extends Recipe<?>> String dump(final IRecipeManager<? super T> manager, final RecipeHolder<T> recipe) {
        
        return IRecipeHandlerRegistry.getHandlerFor(recipe)
                .dumpToCommandString(manager, AccessibleElementsProvider.get().registryAccess(), recipe);
    }
    
}
