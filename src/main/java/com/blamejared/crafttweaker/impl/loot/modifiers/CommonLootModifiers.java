package com.blamejared.crafttweaker.impl.loot.modifiers;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.loot.modifiers.ILootModifier;
import com.blamejared.crafttweaker.api.util.IntegerRange;
import com.blamejared.crafttweaker.impl.item.MCItemStack;
import com.blamejared.crafttweaker.impl.item.MCWeightedItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.loot.LootContext;
import net.minecraft.util.Util;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Lazy;
import org.openzen.zencode.java.ZenCodeType;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Holds a set of implementations of {@link ILootModifier} of common usage.
 *
 * These can be used freely instead of rewriting the same code more than once. They are also guaranteed to behave
 * correctly.
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.modifiers.CommonLootModifiers")
@Document("vanilla/api/loot/modifiers/CommonLootModifiers")
public final class CommonLootModifiers {
    private static final Lazy<ILootModifier> IDENTITY = Lazy.concurrentOf(() -> (loot, context) -> loot);
    private static final Lazy<ILootModifier> LOOT_CLEARING_MODIFIER = Lazy.concurrentOf(() -> (loot, context) -> new ArrayList<>());
    private static final Lazy<Random> RANDOM = Lazy.concurrentOf(Random::new);

    // Addition methods
    /**
     * Adds the given {@link IItemStack} to the drops.
     *
     * @param stack The stack to add
     * @return An {@link ILootModifier} that carries out the operation.
     */
    @ZenCodeType.Method
    public static ILootModifier add(final IItemStack stack) {
        return stack.isEmpty() ? IDENTITY.get() : (loot, context) -> Util.make(new ArrayList<>(loot), it -> it.add(stack.copy()));
    }

    /**
     * Adds the given {@link IItemStack} with random amount to the drops
     * @param stack The stack to add
     * @param amountRange The range of the stack amount
     * @return An {@link ILootModifier} that carries out the operation.
     */
    @ZenCodeType.Method
    public static ILootModifier add(final IItemStack stack, IntegerRange amountRange) {
        if (stack.isEmpty()) return IDENTITY.get();
        if (amountRange.getMin() < 0) throw new IllegalArgumentException("Minimum must be more than 0");
        return ((loot, currentContext) -> {
            int amount = amountRange.getRandomValue(getRandom(currentContext.getWorld()));
            if (amount == 0) return loot;
            return Util.make(new ArrayList<>(loot), it -> it.add(stack.copy().setAmount(amount)));
        });
    }

    /**
     * Adds the given {@link MCWeightedItemStack} to the drops
     * @param stack The weighted stack to add
     * @return An {@link ILootModifier} that carries out the operation.
     */
    @ZenCodeType.Method
    public static ILootModifier add(final MCWeightedItemStack stack) {
        if (stack.getItemStack().isEmpty()) return IDENTITY.get();
        return ((loot, currentContext) -> {
            if (getRandom(currentContext.getWorld()).nextDouble() < stack.getWeight()) {
                return Util.make(new ArrayList<>(loot), it -> it.add(stack.getItemStack().copy()));
            } else {
                return loot;
            }
        });
    }

    /**
     * Adds all the given {@link IItemStack} to the drops.
     *
     * @param stacks The stacks to add
     * @return An {@link ILootModifier} that carries out the operation.
     */
    @ZenCodeType.Method
    public static ILootModifier addAll(final IItemStack... stacks) {
        final List<IItemStack> stacksToAdd = notEmpty(Arrays.stream(stacks)).collect(Collectors.toList());
        return (loot, context) -> Util.make(new ArrayList<>(loot), it -> it.addAll(stacksToAdd.stream().map(IItemStack::copy).collect(Collectors.toList())));
    }

    /**
     * Adds all the given {@link MCWeightedItemStack} to the drops.
     *
     * @param stacks The stacks to add
     * @return An {@link ILootModifier} that carries out the operation.
     */
    @ZenCodeType.Method
    public static ILootModifier addAll(final MCWeightedItemStack... stacks) {
        return chaining(Arrays.stream(stacks).map(CommonLootModifiers::add));
    }

    /**
     * Adds the given {@link IItemStack} with random amount to the drops
     * @param stacksMap A map of key-value pairs dictating the stacks with their amount range.
     * @return An {@link ILootModifier} that carries out the operation.
     */

    @ZenCodeType.Method
    public static ILootModifier addAll(final Map<IItemStack, IntegerRange> stacksMap) {
        return chaining(stacksMap.entrySet().stream().map(it -> add(it.getKey(), it.getValue())));
    }

    // Replacement methods
    /**
     * Replaces every instance of the targeted {@link IIngredient} with the replacement {@link IItemStack}.
     *
     * In this case, a simple matching procedure is used, where every stack that matches the given <code>target</code>
     * is replaced by the <code>replacement</code> without considering stack size. If stack size is to be preserved,
     * refer to {@link #replaceStackWith(IItemStack, IItemStack)}.
     *
     * @param target The target to replace.
     * @param replacement The replacement to use.
     * @return An {@link ILootModifier} that carries out the operation.
     */
    @ZenCodeType.Method
    public static ILootModifier replaceWith(final IIngredient target, final IItemStack replacement) {
        return streaming((loot, context) -> replacing(loot, target, replacement));
    }

    /**
     * Replaces every instance of the targeted {@link IIngredient}s with their corresponding replacement
     * {@link IItemStack}.
     *
     * In this case, a simple matching procedure is used, where every stack that matches the key of the pair is replaced
     * by the corresponding value, without considering stack size. If stack size is to be preserved, refer to
     * {@link #replaceAllStacksWith(Map)}.
     *
     * @param replacementMap A map of key-value pairs dictating the target to replace along with their replacement.
     * @return An {@link ILootModifier} that carries out the operation.
     */
    @ZenCodeType.Method
    public static ILootModifier replaceAllWith(final Map<IIngredient, IItemStack> replacementMap) {
        return chaining(replacementMap.entrySet().stream().map(it -> replaceWith(it.getKey(), it.getValue())));
    }
    
    /**
     * Replaces every instance of the targeted {@link IItemStack} with the replacement {@link IItemStack},
     * proportionally.
     *
     * As an example, if the loot drops 5 carrots and this loot modifier runs with 2 carrots as the <code>target</code>
     * and 1 potato as the <code>replacement</code>, the loot will be modified to 2 potatoes and 1 carrot. This happens
     * because every 2-carrot stack will be actively replaced by a 1-potato stack, without exceptions.
     *
     * This loot modifier acts differently than {@link #replaceWith(IIngredient, IItemStack)}, where a simpler approach
     * is used.
     *
     * @param target The target to replace.
     * @param replacement The replacement to use.
     * @return An {@link ILootModifier} that carries out the operation.
     */
    @ZenCodeType.Method
    public static ILootModifier replaceStackWith(final IItemStack target, final IItemStack replacement) {
        return streaming((loot, context) -> replacingExactly(loot, target, replacement));
    }
    
    /**
     * Replaces every instance of the targeted {@link IItemStack}s with the replacement {@link IItemStack}s,
     * proportionally.
     *
     * As an example, if the loot drops 5 carrots and this loot modifier runs with 2 carrots as the key of a pair and 1
     * potato as the corresponding value, the loot will be modified to 2 potatoes and 1 carrot. This happens because
     * every 2-carrot stack will be actively replaced by a 1-potato stack, without exceptions.
     *
     * This loot modifier acts differently than {@link #replaceAllWith(Map)}, where a simpler approach is used.
     *
     * @param replacementMap A map of key-value pairs dictating the target to replace along with their replacement.
     * @return An {@link ILootModifier} that carries out the operation.
     */
    @ZenCodeType.Method
    public static ILootModifier replaceAllStacksWith(final Map<IItemStack, IItemStack> replacementMap) {
        return chaining(replacementMap.entrySet().stream().map(it -> replaceStackWith(it.getKey(), it.getValue())));
    }

    // Removal methods
    /**
     * Removes every instance of the targeted {@link IIngredient} from the drops.
     *
     * @param target The {@link IIngredient} to remove.
     * @return An {@link ILootModifier} that carries out the operation.
     */
    @ZenCodeType.Method
    public static ILootModifier remove(final IIngredient target) {
        return replaceWith(target, MCItemStack.EMPTY.get());
    }

    /**
     * Removes every instance of all the targeted {@link IIngredient}s from the drops.
     *
     * @param targets The {@link IIngredient}s to remove.
     * @return An {@link ILootModifier} that carries out the operation.
     */
    @ZenCodeType.Method
    public static ILootModifier removeAll(final IIngredient... targets) {
        return chaining(Arrays.stream(targets).map(CommonLootModifiers::remove));
    }

    /**
     * Clears the entire drop list.
     *
     * @return An {@link ILootModifier} that carries out the operation.
     */
    @ZenCodeType.Method
    public static ILootModifier clearLoot() {
        return LOOT_CLEARING_MODIFIER.get();
    }

    // Additional utility methods
    /**
     * Chains the given list of {@link ILootModifier}s to be executed one after the other.
     *
     * @param modifiers The modifier list.
     * @return An {@link ILootModifier} that carries out the operation.
     */
    @ZenCodeType.Method
    public static ILootModifier chaining(final ILootModifier... modifiers) {
        return chaining(Arrays.stream(modifiers));
    }
    
    // Private utility stuff
    private static ILootModifier streaming(final BiFunction<Stream<IItemStack>, LootContext, Stream<IItemStack>> consumer) {
        return (loot, context) -> consumer.apply(loot.stream(), context).collect(Collectors.toList());
    }
    
    private static ILootModifier chaining(final Stream<ILootModifier> chain) {
        return chain.reduce(IDENTITY.get(), (first, second) -> (loot, context) -> second.applyModifier(first.applyModifier(loot, context), context));
    }
    
    private static Stream<IItemStack> notEmpty(final Stream<IItemStack> stream) {
        return stream.filter(it -> !it.isEmpty());
    }

    private static Stream<IItemStack> replacing(final Stream<IItemStack> stream, final IIngredient from, final IItemStack to) {
        return notEmpty(stream.map(it -> from.matches(it)? to.copy() : it));
    }
    
    private static Stream<IItemStack> replacingExactly(final Stream<IItemStack> stream, final IItemStack from, final IItemStack to) {
        return stream.flatMap(it -> notEmpty((from.matches(it)? replacingExactly(it, from, to) : Collections.singleton(it)).stream()));
    }
    
    private static List<IItemStack> replacingExactly(final IItemStack original, final IItemStack from, final IItemStack to) {
        return Arrays.asList(to.copy().setAmount(original.getAmount() / from.getAmount()), original.copy().setAmount(original.getAmount() % from.getAmount()));
    }

    private static Random getRandom(@Nullable World world) {
        return world == null ? RANDOM.get() : world.getRandom();
    }
}
