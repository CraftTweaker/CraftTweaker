package com.blamejared.crafttweaker.impl.command.type.conflict;

import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.bracket.custom.RecipeTypeBracketHandler;
import com.blamejared.crafttweaker.api.command.CommandUtilities;
import com.blamejared.crafttweaker.api.command.argument.RecipeTypeArgument;
import com.blamejared.crafttweaker.api.plugin.ICommandRegistrationHandler;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandlerRegistry;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.crafttweaker.mixin.common.access.recipe.AccessRecipeManager;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.function.ToIntBiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public final class ConflictCommand {
    
    private static final ExecutorService OFF_THREAD_SERVICE = Executors.newFixedThreadPool(1, r -> {
        
        final Thread t = new Thread(r, CraftTweakerConstants.MOD_ID + ":conflict_resolution_thread");
        t.setDaemon(true); // We don't want to prevent MC from shutting down if this thread is still processing
        t.setContextClassLoader(ConflictCommand.class.getClassLoader());
        return t;
    });
    
    private ConflictCommand() {}
    
    public static void registerCommands(final ICommandRegistrationHandler handler) {
        
        handler.registerRootCommand(
                "conflicts",
                Component.translatable("crafttweaker.command.description.conflicts"),
                builder ->
                        builder
                                .then(Commands.argument("type", RecipeTypeArgument.get())
                                        .executes(context -> conflicts(
                                                context.getSource().getPlayerOrException(),
                                                DescriptiveFilter.of(context.getArgument("type", IRecipeManager.class))
                                        )))
                                .then(Commands.literal("hand")
                                        .executes(context -> ifNotEmpty(
                                                context,
                                                (player, item) -> conflicts(player, DescriptiveFilter.of(item))
                                        )))
                                .executes(context -> conflicts(context.getSource()
                                        .getPlayerOrException(), DescriptiveFilter.of()))
        );
    }
    
    private static int ifNotEmpty(final CommandContext<CommandSourceStack> source, final ToIntBiFunction<Player, ItemStack> command) throws CommandSyntaxException {
        
        final ServerPlayer player = source.getSource().getPlayerOrException();
        final ItemStack stack = player.getMainHandItem();
        if(stack.isEmpty()) {
            
            CommandUtilities.send(Component.translatable("crafttweaker.command.conflict.hand.empty")
                    .withStyle(ChatFormatting.RED), player);
            return -1;
        }
        
        return command.applyAsInt(player, stack);
    }
    
    private static int conflicts(final Player player, final DescriptiveFilter filter) {
        
        CommandUtilities.send(
                Component.translatable("crafttweaker.command.conflict.begin", filter.description())
                        .withStyle(ChatFormatting.GREEN)
                        .append(Component.translatable("crafttweaker.command.conflict.warnings")
                                .withStyle(ChatFormatting.RED)),
                player
        );
        
        runConflicts(player, player.level().getRecipeManager(), filter);
        
        return 0;
    }
    
    private static void runConflicts(final Player player, final RecipeManager manager, final DescriptiveFilter filter) {
        
        // Cloning the map to avoid /reload messing up with CMEs when looping on it from off-thread
        // Also, this deep copies only the two maps: the recipe type, RL, and recipe objects are not also deep copied
        final Map<RecipeType<?>, Map<ResourceLocation, RecipeHolder<?>>> recipes = deepCopy(((AccessRecipeManager) manager).crafttweaker$getRecipes(), filter);
        CompletableFuture.supplyAsync(() -> computeConflicts(recipes), OFF_THREAD_SERVICE)
                .thenAcceptAsync(message -> dispatchCompletionTo(message, player), OFF_THREAD_SERVICE)
                .exceptionallyAsync(exception -> dispatchExceptionTo(exception, player), OFF_THREAD_SERVICE);
    }
    
    private static Map<RecipeType<?>, Map<ResourceLocation, RecipeHolder<?>>> deepCopy(final Map<RecipeType<?>, Map<ResourceLocation, RecipeHolder<?>>> original, final DescriptiveFilter filter) {
        
        final Map<RecipeType<?>, Map<ResourceLocation, RecipeHolder<?>>> clone = new HashMap<>();
        
        original.forEach((type, map) -> {
            
            final Map<ResourceLocation, RecipeHolder<?>> cloneMap = clone.computeIfAbsent(type, it -> new HashMap<>());
            map.entrySet().stream().filter(filter).forEach(it -> cloneMap.put(it.getKey(), it.getValue()));
        });
        
        return clone;
    }
    
    private static String computeConflicts(final Map<RecipeType<?>, Map<ResourceLocation, RecipeHolder<?>>> recipes) {
        
        return recipes.entrySet()
                .stream()
                .flatMap(ConflictCommand::computeConflictsFor)
                .map(it -> "- " + it)
                .collect(Collectors.joining("\n"));
    }
    
    private static Stream<String> computeConflictsFor(final Map.Entry<RecipeType<?>, Map<ResourceLocation, RecipeHolder<?>>> entry) {
        
        final IRecipeManager<?> manager = RecipeTypeBracketHandler.getOrDefault(entry.getKey());
        
        if(manager == null) {
            return Stream.empty();
        }
        
        final List<Map.Entry<ResourceLocation, RecipeHolder<?>>> recipes = new ArrayList<>(entry.getValue().entrySet());
        final RecipeLongIterator iterator = new RecipeLongIterator(recipes.size());
        final int characteristics = Spliterator.ORDERED | Spliterator.SORTED | Spliterator.NONNULL | Spliterator.IMMUTABLE;
        
        return StreamSupport.longStream(Spliterators.spliterator(iterator, iterator.estimateLength(), characteristics), false)
                .filter(it -> conflictsWith(manager, recipes, it))
                .mapToObj(it -> Map.entry(recipes.get(RecipeLongIterator.first(it)), recipes.get(RecipeLongIterator.second(it))))
                .map(it -> Map.entry(it.getKey().getKey(), it.getValue().getKey()))
                .map(it -> formatConflict(manager, it));
    }
    
    private static boolean conflictsWith(
            final IRecipeManager<?> manager,
            final List<Map.Entry<ResourceLocation, RecipeHolder<?>>> recipes,
            final long id
    ) {
        
        return conflictsWith(manager, recipes.get(RecipeLongIterator.first(id)).getValue(), recipes.get(RecipeLongIterator.second(id)).getValue());
    }
    
    private static <T extends Recipe<?>> boolean conflictsWith(final IRecipeManager<?> manager, final RecipeHolder<T> first, final RecipeHolder<?> second) {
        
        return first != second && IRecipeHandlerRegistry.getHandlerFor(first.value())
                .doesConflict(GenericUtil.uncheck(manager), first.value(), second.value());
    }
    
    private static String formatConflict(
            final IRecipeManager<?> manager,
            final Map.Entry<ResourceLocation, ResourceLocation> conflictNames
    ) {
        
        final ResourceLocation firstName = conflictNames.getKey();
        final ResourceLocation secondName = conflictNames.getValue();
        return String.format("Recipes '%s' and '%s' in type '%s' have conflicting inputs", firstName, secondName, manager.getCommandString());
    }
    
    private static void dispatchCompletionTo(final String message, final Player player) {
        
        onMainThread(player, () -> {
            try {
                CommandUtilities.COMMAND_LOGGER.info(message.isEmpty() ? "No conflicts identified" : message);
                CommandUtilities.send(CommandUtilities.openingLogFile(Component.translatable("crafttweaker.command.conflict.complete")
                        .withStyle(ChatFormatting.GREEN)), player);
            } catch(final Exception e) {
                try {
                    CommandUtilities.COMMAND_LOGGER.error("An error occurred while reporting conflicts, hopefully it does not happen again", e);
                } catch(final Exception another) {
                    e.addSuppressed(another);
                    e.printStackTrace(System.err); // It's not going to be useful if the logging throws errors, but at least we can say we tried
                }
            }
        });
    }
    
    private static Void dispatchExceptionTo(final Throwable exception, final Player player) {
        
        onMainThread(player, () -> {
            try {
                CommandUtilities.COMMAND_LOGGER.error("Unable to verify for conflicts due to an exception", exception);
                CommandUtilities.send(CommandUtilities.openingLogFile(Component.translatable("crafttweaker.command.conflict.error")
                        .withStyle(ChatFormatting.RED)), player);
            } catch(final Exception e) {
                try {
                    CommandUtilities.COMMAND_LOGGER.error("An error occurred while reporting conflicts, hopefully it does not happen again", e);
                } catch(final Exception another) {
                    e.addSuppressed(another);
                    e.printStackTrace(System.err); // It's not going to be useful if the logging throws errors, but at least we can say we tried
                }
            }
        });
        
        return null;
    }
    
    private static void onMainThread(final Player player, final Runnable runnable) {
        final Level level = player.level();
        assert !level.isClientSide(); // This is always true in commands
        
        Objects.requireNonNull(level.getServer(), "Is someone running server-bound commands on the client?").executeIfPossible(() -> {
            try {
                runnable.run();
            } catch (final RejectedExecutionException e) {
                OFF_THREAD_SERVICE.submit(runnable);
            }
        });
    }
    
}
