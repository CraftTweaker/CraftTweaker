package com.blamejared.crafttweaker.impl.loot.modifiers;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.loot.modifiers.ILootModifier;
import com.blamejared.crafttweaker.impl.item.MCItemStack;
import com.blamejared.crafttweaker.impl_native.loot.ExpandLootContext;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.loot.LootContext;
import net.minecraft.util.Util;
import net.minecraftforge.common.util.Lazy;
import org.openzen.zencode.java.ZenCodeType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.IntFunction;
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

    // Addition methods
    /**
     * Adds the given {@link IItemStack} to the drops.
     *
     * @param stack The stack to add
     * @return An {@link ILootModifier} that carries out the operation.
     */
    @ZenCodeType.Method
    public static ILootModifier add(final IItemStack stack) {
        return stack.isEmpty()? IDENTITY.get() : (loot, context) -> Util.make(new ArrayList<>(loot), it -> it.add(stack.copy()));
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
     * Adds the given {@link IItemStack} to the drops, modifying its count based on the level of the given
     * {@link Enchantment} on the tool used, if available.
     *
     * <p>In case no tool is used to obtain the stack, then this loot modifier behaves exactly like
     * {@link #add(IItemStack)}.</p>
     *
     * @param enchantment The enchantment to check for.
     * @param stack The stack to add.
     * @return An {@link ILootModifier} that carries out the operation.
     */
    @ZenCodeType.Method
    public static ILootModifier addWithBonus(final Enchantment enchantment, final IItemStack stack) {
        return stack.isEmpty()? IDENTITY.get() : (loot, context) -> Util.make(new ArrayList<>(loot), it -> it.add(oreDropsBonus(stack.copy(), enchantment, context)));
    }
    
    /**
     * Adds the given {@link IItemStack}s to the drops, modifying their count based on the level of the given
     * {@link Enchantment} on the tool used, if available.
     *
     * <p>In case no tool is used to obtain the stack, then this loot modifier behaves exactly like
     * {@link #addAll(IItemStack...)}.</p>
     *
     * <p>The fortune modifier is applied separately for each {@link IItemStack}.</p>
     *
     * @param enchantment The enchantment to check for.
     * @param stacks The stacks to add.
     * @return An {@link ILootModifier} that carries out the operation.
     */
    @ZenCodeType.Method
    public static ILootModifier addAllWithBonus(final Enchantment enchantment, final IItemStack... stacks) {
        final List<IItemStack> targets = notEmpty(Arrays.stream(stacks)).collect(Collectors.toList());
        return (loot, context) -> Util.make(new ArrayList<>(loot), l -> l.addAll(targets.stream().map(it -> oreDropsBonus(it.copy(), enchantment, context)).collect(Collectors.toList())));
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

    private static IItemStack oreDropsBonus(final IItemStack original, final Enchantment enchantment, final LootContext context) {
        return ifTool(original, context, tool -> withLevel(tool, enchantment, level -> level <= 0? original : original.setAmount(oreDropsFormula(level, context.getRandom()))));
    }
    
    private static IItemStack ifTool(final IItemStack original, final LootContext context, final Function<IItemStack, IItemStack> toolConsumer) {
        return ifTool(original, ExpandLootContext.getTool(context), toolConsumer);
    }
    
    private static IItemStack ifTool(final IItemStack original, final IItemStack tool, final Function<IItemStack, IItemStack> toolConsumer) {
        return tool != null && tool.getInternal() != null && !tool.isEmpty()? toolConsumer.apply(tool) : original;
    }
    
    private static IItemStack withLevel(final IItemStack tool, final Enchantment enchantment, final IntFunction<IItemStack> levelUser) {
        return levelUser.apply(EnchantmentHelper.getEnchantmentLevel(enchantment, tool.getInternal()));
    }
    
    private static int oreDropsFormula(final int level, final Random random) {
        return Math.max(0, random.nextInt(level + 2) - 1) + 1;
    }
}
