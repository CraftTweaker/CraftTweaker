package com.blamejared.crafttweaker.impl.commands.crafttweaker;

import com.blamejared.crafttweaker.CraftTweaker;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.CraftTweakerRegistry;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.impl.brackets.RecipeTypeBracketHandler;
import com.blamejared.crafttweaker.impl.commands.CTRecipeTypeArgument;
import com.blamejared.crafttweaker.impl.commands.CommandUtilities;
import com.blamejared.crafttweaker.impl.item.MCItemStack;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.concurrent.ThreadTaskExecutor;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.LogicalSidedProvider;
import net.minecraftforge.fml.common.thread.EffectiveSide;
import org.apache.logging.log4j.util.TriConsumer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public final class ConflictCommands {
    
    private static final class DescriptiveFilter implements Predicate<Map.Entry<ResourceLocation, IRecipe<?>>> {
        
        private final Predicate<IRecipe<?>> delegate;
        private final String description;
        
        DescriptiveFilter(final Predicate<IRecipe<?>> delegate, final String description) {
            
            this.delegate = delegate;
            this.description = description;
        }
        
        @Override
        public boolean test(final Map.Entry<ResourceLocation, IRecipe<?>> recipe) {
        
            return this.delegate.test(recipe.getValue());
        }
    
        String description() {
            
            return this.description;
        }
    }
    
    private static final class RecipeIterator implements PrimitiveIterator.OfLong {
        
        private final int size;
        private final int half;
        
        private int currentLeft;
        private int currentRight;
        private boolean kill;
        
        RecipeIterator(final int size) {
            
            this.size = size;
            this.half = (int) Math.ceil(((double) this.size) / 2.0); // If size is odd, then we will have a duplicate rather than potentially "forgetting" a pairing.
            this.currentLeft = 0;
            this.currentRight = this.half;
            this.kill = false;
        }
        
        @Override
        public long nextLong() {
        
            if (this.kill) throw new NoSuchElementException();
            
            final long current = make(this.currentLeft, this.currentRight);
            if (++this.currentRight >= this.size) {
                if (++this.currentLeft >= this.half) this.kill = true;
                this.currentRight = this.half;
            }
            
            return current;
        }
        
        @Override
        public boolean hasNext() {
        
            return !this.kill;
        }
    
        static long make(final int left, final int right) {
            
            return (((long) left) << 32L) | ((long) right);
        }
        
        static int first(final long val) {
            
            return (int) (val >> 32L);
        }
        
        static int second(final long val) {
            
            return (int) val;
        }
    }
    
    private static final ExecutorService OFF_THREAD_SERVICE = Executors.newFixedThreadPool(1, r -> {
        
        final Thread t = new Thread(r, CraftTweaker.MODID + ":conflict_resolution_thread");
        t.setDaemon(true); // We don't want to prevent MC from shutting down if this thread is still processing
        t.setContextClassLoader(ConflictCommands.class.getClassLoader());
        return t;
    });
    
    private ConflictCommands() {}
    
    public static void registerConflictCommands(final TriConsumer<LiteralArgumentBuilder<CommandSource>, String, String> registerCustomCommand) {
    
        final Function<IRecipeManager, DescriptiveFilter> managerFilterMaker = manager -> {
            
            final IRecipeType<?> type = manager.getRecipeType();
            return new DescriptiveFilter(it -> it.getType() == type, " for type " + manager.getCommandString());
        };
        
        final Function<ItemStack, DescriptiveFilter> stackFilterMaker = hand ->
                new DescriptiveFilter(it -> compareStacks(it.getRecipeOutput(), hand), " for output " + new MCItemStack(hand).getCommandString());
        
        registerCustomCommand.accept(
                Commands.literal("conflicts")
                        .then(Commands.argument("type", CTRecipeTypeArgument.INSTANCE)
                                .executes(context -> ConflictCommands.conflicts(
                                        context.getSource().asPlayer(),
                                        managerFilterMaker.apply(context.getArgument("type", IRecipeManager.class))
                                )))
                        .then(Commands.literal("hand")
                                .executes(context -> ConflictCommands.conflicts(
                                        context.getSource().asPlayer(),
                                        stackFilterMaker.apply(context.getSource().asPlayer().getHeldItemMainhand())
                                )))
                        .executes(context -> ConflictCommands.conflicts(context.getSource().asPlayer(), new DescriptiveFilter(it -> true, ""))),
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
        
        final LogicalSide side = EffectiveSide.get();
        final Map<IRecipeType<?>, Map<ResourceLocation, IRecipe<?>>> recipes = deepCopy(manager.recipes, filter);
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
                .flatMap(ConflictCommands::computeConflictsFor)
                .collect(Collectors.joining("\n- ", "- ", ""));
    }
    
    private static Stream<String> computeConflictsFor(final Map.Entry<IRecipeType<?>, Map<ResourceLocation, IRecipe<?>>> entry) {
        
        final IRecipeManager manager = RecipeTypeBracketHandler.getOrDefault(entry.getKey());
        
        if (manager == null) return Stream.of();
        
        final List<Map.Entry<ResourceLocation, IRecipe<?>>> recipes = new ArrayList<>(entry.getValue().entrySet());
        final int characteristics = Spliterator.ORDERED | Spliterator.SORTED | Spliterator.NONNULL | Spliterator.IMMUTABLE;
        
        return StreamSupport.longStream(Spliterators.spliteratorUnknownSize(new RecipeIterator(recipes.size()), characteristics), false)
                .filter(it -> conflictsWith(manager, recipes.get(RecipeIterator.first(it)).getValue(), recipes.get(RecipeIterator.second(it)).getValue()))
                .mapToObj(it -> formatConflict(manager, recipes.get(RecipeIterator.first(it)).getKey(), recipes.get(RecipeIterator.second(it)).getKey()));
    }
    
    private static <T extends IRecipe<?>> boolean conflictsWith(final IRecipeManager manager, final T first, final IRecipe<?> second) {
        
        return first != second && CraftTweakerRegistry.getHandlerFor(first).conflictsWith(manager, first, second);
    }
    
    private static String formatConflict(final IRecipeManager manager, final ResourceLocation firstName, final ResourceLocation secondName) {
        
        return String.format("Recipes '%s' and '%s' in type '%s' have conflicting inputs", firstName, secondName, manager.getCommandString());
    }
    
    private static void dispatchMessageTo(final String message, final PlayerEntity player, final LogicalSide side) {
        
        runOnMainThread(side, () -> {
            try {
                CraftTweakerAPI.logDump("- ".equals(message)? "No conflicts identified" : message);
                CommandUtilities.send(CommandUtilities.color("Conflict testing completed: results are in the log", TextFormatting.GREEN), player);
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
    
    private static void runOnMainThread(final LogicalSide currentSide, final Runnable runnable) {
        
        final ThreadTaskExecutor<?> executor = LogicalSidedProvider.WORKQUEUE.get(currentSide);
        
        if (!executor.isOnExecutionThread()) {
            
            executor.deferTask(runnable);
        } else {
            
            runnable.run();
        }
    }
    
    // TODO("This is a copy of IItemStack#matches written to avoid object creation: find a way to avoid code duplication")
    private static boolean compareStacks(final ItemStack first, final ItemStack second) {
        
        if (first.isEmpty() != second.isEmpty()) return false;
        if (first.getItem() != second.getItem()) return false;
        if (first.getCount() > second.getCount()) return false;
        if (first.getDamage() != second.getDamage()) return false;
        
        final CompoundNBT firstTag = first.getTag();
        final CompoundNBT secondTag = second.getTag();
        
        // Note: different from original
        if (firstTag == null) return true;
        if (secondTag == null) return false;
        // The original code checks if they are both null and returns true if so, otherwise it converts both of them to
        // MapData and then checks again if the first tag is null. The only possibility is if firstTag is actually null,
        // so we can simplify the check. Also, if the first tag is not null, the second tag cannot be null, otherwise
        // there is no match. We can account for that too.
        
        return firstTag.equals(secondTag);
    }
}
