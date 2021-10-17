package com.blamejared.crafttweaker.impl.commands.crafttweaker.conflict;

import com.blamejared.crafttweaker.CraftTweaker;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.CraftTweakerRegistry;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.impl.brackets.RecipeTypeBracketHandler;
import com.blamejared.crafttweaker.impl.commands.CTRecipeTypeArgument;
import com.blamejared.crafttweaker.impl.commands.CommandUtilities;
import com.blamejared.crafttweaker.impl.helper.ThreadingHelper;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.thread.EffectiveSide;
import org.apache.logging.log4j.util.TriConsumer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public final class ConflictCommand {
    
    private static final ExecutorService OFF_THREAD_SERVICE = Executors.newFixedThreadPool(1, r -> {
        
        final Thread t = new Thread(r, CraftTweaker.MODID + ":conflict_resolution_thread");
        t.setDaemon(true); // We don't want to prevent MC from shutting down if this thread is still processing
        t.setContextClassLoader(ConflictCommand.class.getClassLoader());
        return t;
    });
    
    private ConflictCommand() {}
    
    public static void registerConflictCommands(final TriConsumer<LiteralArgumentBuilder<CommandSource>, String, String> registerCustomCommand) {
        
        registerCustomCommand.accept(
                Commands.literal("conflicts")
                        .then(Commands.argument("type", CTRecipeTypeArgument.INSTANCE)
                                .executes(context -> ConflictCommand.conflicts(
                                        context.getSource().asPlayer(),
                                        DescriptiveFilter.of(context.getArgument("type", IRecipeManager.class))
                                )))
                        .then(Commands.literal("hand")
                                .executes(context -> ConflictCommand.conflicts(
                                        context.getSource().asPlayer(),
                                        DescriptiveFilter.of(context.getSource().asPlayer().getHeldItemMainhand())
                                )))
                        .executes(context -> ConflictCommand.conflicts(context.getSource().asPlayer(), DescriptiveFilter.of())),
                "conflicts",
                "Identifies and reports conflicts between various recipes"
        );
    }

    private static int conflicts(final PlayerEntity player, final DescriptiveFilter filter) {
    
        CommandUtilities.send(
                // TODO("Translation text component")
                new StringTextComponent(String.format("Conflict testing%s has begun: ", filter.description()))
                        .mergeStyle(TextFormatting.GREEN)
                        .append(new StringTextComponent("do not /reload the server or quit the world in the meantime").mergeStyle(TextFormatting.RED)),
                player
        );
        
        runConflicts(player, player.world.getRecipeManager(), filter);
        
        return 0;
    }
    
    private static void runConflicts(final PlayerEntity player, final RecipeManager manager, final DescriptiveFilter filter) {
        
        // Cloning the map to avoid /reload messing up with CMEs when looping on it from off-thread
        // Also, this deep copies only the two maps: the recipe type, RL, and recipe objects are not also deep copied
        final Map<IRecipeType<?>, Map<ResourceLocation, IRecipe<?>>> recipes = deepCopy(manager.recipes, filter);
        final LogicalSide side = EffectiveSide.get();
        CompletableFuture.supplyAsync(() -> computeConflicts(recipes), OFF_THREAD_SERVICE)
                .thenAcceptAsync(message -> dispatchMessageTo(message, player, side), OFF_THREAD_SERVICE);
    }
    
    private static Map<IRecipeType<?>, Map<ResourceLocation, IRecipe<?>>> deepCopy(final Map<IRecipeType<?>, Map<ResourceLocation, IRecipe<?>>> original, final DescriptiveFilter filter) {
        
        final Map<IRecipeType<?>, Map<ResourceLocation, IRecipe<?>>> clone = new HashMap<>();
        
        original.forEach((type, map) -> {
            
            final Map<ResourceLocation, IRecipe<?>> cloneMap = clone.computeIfAbsent(type, it -> new HashMap<>());
            map.entrySet().stream().filter(filter).forEach(it -> cloneMap.put(it.getKey(), it.getValue()));
        });
        
        return clone;
    }
    
    private static String computeConflicts(final Map<IRecipeType<?>, Map<ResourceLocation, IRecipe<?>>> recipes) {
        
        return recipes.entrySet()
                .stream()
                .flatMap(ConflictCommand::computeConflictsFor)
                .map(it -> "- " + it)
                .collect(Collectors.joining("\n"));
    }
    
    private static Stream<String> computeConflictsFor(final Map.Entry<IRecipeType<?>, Map<ResourceLocation, IRecipe<?>>> entry) {
        
        final IRecipeManager manager = RecipeTypeBracketHandler.getOrDefault(entry.getKey());
        
        if (manager == null) return Stream.of();
        
        final List<Map.Entry<ResourceLocation, IRecipe<?>>> recipes = new ArrayList<>(entry.getValue().entrySet());
        final RecipeLongIterator iterator = new RecipeLongIterator(recipes.size());
        final int characteristics = Spliterator.ORDERED | Spliterator.SORTED | Spliterator.NONNULL | Spliterator.IMMUTABLE;
        
        return StreamSupport.longStream(Spliterators.spliterator(iterator, iterator.estimateLength(), characteristics), false)
                .filter(it -> conflictsWith(manager, recipes.get(RecipeLongIterator.first(it)).getValue(), recipes.get(RecipeLongIterator.second(it)).getValue()))
                .mapToObj(it -> formatConflict(manager, recipes.get(RecipeLongIterator.first(it)).getKey(), recipes.get(RecipeLongIterator.second(it)).getKey()));
    }
    
    private static <T extends IRecipe<?>> boolean conflictsWith(final IRecipeManager manager, final T first, final IRecipe<?> second) {
        
        return first != second && CraftTweakerRegistry.getHandlerFor(first).isThereConflictBetween(manager, first, second);
    }
    
    private static String formatConflict(final IRecipeManager manager, final ResourceLocation firstName, final ResourceLocation secondName) {
        
        return String.format("Recipes '%s' and '%s' in type '%s' have conflicting inputs", firstName, secondName, manager.getCommandString());
    }
    
    private static void dispatchMessageTo(final String message, final PlayerEntity player, final LogicalSide side) {
        
        ThreadingHelper.runOnMainThread(side, () -> {
            try {
                CraftTweakerAPI.logDump(message.isEmpty()? "No conflicts identified" : message);
                CommandUtilities.send(CommandUtilities.color("Conflict testing completed: results are in crafttweaker.log", TextFormatting.GREEN), player);
            } catch (final Exception e) {
                
                try {
                    
                    CraftTweakerAPI.logThrowing("An error occurred while reporting conflicts, hopefully it does not happen again", e);
                } catch (final Exception another) {
                    
                    e.addSuppressed(another);
                    e.printStackTrace(System.err); // It's not going to be useful if the logging throws errors, but at least we can say we tried
                }
            }
        });
    }
}
