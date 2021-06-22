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
import java.util.stream.IntStream;
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
    private interface DropsFormula {
        DropsFormula ORE_DROPS = (amount, level, random) -> level <= 0? amount : amount * Math.max(0, random.nextInt(level + 2) - 1) + 1;
        
        static DropsFormula binomial(final int extra, final float p) {
            return (amount, level, random) -> amount + IntStream.range(0, level + extra).filter(ignore -> random.nextFloat() < p).map(it -> 1).sum();
        }
        
        static DropsFormula uniform(final int multiplier) {
            return (amount, level, random) -> amount + random.nextInt(multiplier * level + 1);
        }
        
        int apply(final int amount, final int level, final Random random);
    }
    
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
     * <p>The formula used is based on the {@code ore_drops} formula used by vanilla, which multiplies the stack's
     * original count by a random number between 1 and the tool's enchantment level + 1. This is the formula used by
     * all vanilla ores to determine their drop count.</p>
     *
     * @param enchantment The enchantment to check for.
     * @param stack The stack to add.
     * @return An {@link ILootModifier} that carries out the operation.
     */
    @ZenCodeType.Method
    public static ILootModifier addWithOreDropsBonus(final Enchantment enchantment, final IItemStack stack) {
        return withBonus(stack, enchantment, DropsFormula.ORE_DROPS);
    }
    
    /**
     * Adds the given {@link IItemStack} to the drops, modifying its count based on the level of the given
     * {@link Enchantment} on the tool used, if available.
     *
     * <p>In case no tool is used to obtain the stack, then this loot modifier behaves exactly like
     * {@link #add(IItemStack)}.</p>
     *
     * <p>The formula used is based on the {@code binomial_with_bonus_count} formula used by vanilla. In this case, the
     * value of {@code extra} is added to the current tool's enchantment level; that determines the amount of times the
     * randomness will roll. Every roll that is higher than {@code p} will add one element to the stack. This is the
     * formula used by potatoes and carrots to determine their drop count.</p>
     *
     * @param enchantment The enchantment to check for.
     * @param extra An extra value that will be added to the tool's enchantment level.
     * @param p The probability of the binomial distribution, between 0.0 and 1.0 (both exclusive).
     * @param stack The stack to add.
     * @return An {@link ILootModifier} that carries out the operation.
     */
    @ZenCodeType.Method
    public static ILootModifier addWithBinomialBonus(final Enchantment enchantment, final int extra, final float p, final IItemStack stack) {
        return withBonus(stack, enchantment, DropsFormula.binomial(extra, p));
    }
    
    /**
     * Adds the given {@link IItemStack} to the drops, modifying its count based on the level of the given
     * {@link Enchantment} on the tool used, if available.
     *
     * <p>In case no tool is used to obtain the stack, then this loot modifier behaves exactly like
     * {@link #add(IItemStack)}.</p>
     *
     * <p>The formula used is based on the {@code uniform_bonus_count} formula used by vanilla. In this case, the
     * enchantment level is multiplied by {@code multiplier} and a random number is extracted between 0 and the result.
     * This number is then added to the original's stack count. This is the formula used by redstone ore and glowstone
     * to determine their drop count.</p>
     *
     * @param enchantment The enchantment to check for.
     * @param multiplier A multiplier that will be used in conjunction with the enchantment's level.
     * @param stack The stack to add.
     * @return An {@link ILootModifier} that carries out the operation.
     */
    @ZenCodeType.Method
    public static ILootModifier addWithUniformBonus(final Enchantment enchantment, final int multiplier, final IItemStack stack) {
        return withBonus(stack, enchantment, DropsFormula.uniform(multiplier));
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
     * <p>The formula used is based on the {@code ore_drops} formula used by vanilla, which multiplies the stack's
     * original count by a random number between 1 and the tool's enchantment level + 1. This is the formula used by
     * all vanilla ores to determine their drop count.</p>
     *
     * @param enchantment The enchantment to check for.
     * @param stacks The stacks to add.
     * @return An {@link ILootModifier} that carries out the operation.
     */
    @ZenCodeType.Method
    public static ILootModifier addAllWithOreDropsBonus(final Enchantment enchantment, final IItemStack... stacks) {
        return chaining(notEmpty(Arrays.stream(stacks)).map(it -> addWithOreDropsBonus(enchantment, it)));
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
     * <p>The formula used is based on the {@code binomial_with_bonus_count} formula used by vanilla. In this case, the
     * value of {@code extra} is added to the current tool's enchantment level; that determines the amount of times the
     * randomness will roll. Every roll that is higher than {@code p} will add one element to the stack. This is the
     * formula used by potatoes and carrots to determine their drop count.</p>
     *
     * @param enchantment The enchantment to check for.
     * @param extra An extra value that will be added to the tool's enchantment level.
     * @param p The probability of the binomial distribution, between 0.0 and 1.0 (both exclusive).
     * @param stacks The stacks to add.
     * @return An {@link ILootModifier} that carries out the operation.
     */
    @ZenCodeType.Method
    public static ILootModifier addAllWithBinomialBonus(final Enchantment enchantment, final int extra, final float p, final IItemStack... stacks) {
        return chaining(notEmpty(Arrays.stream(stacks)).map(it -> addWithBinomialBonus(enchantment, extra, p, it)));
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
     * <p>The formula used is based on the {@code uniform_bonus_count} formula used by vanilla. In this case, the
     * enchantment level is multiplied by {@code multiplier} and a random number is extracted between 0 and the result.
     * This number is then added to the original's stack count. This is the formula used by redstone ore and glowstone
     * to determine their drop count.</p>
     *
     * @param enchantment The enchantment to check for.
     * @param multiplier A multiplier that will be used in conjunction with the enchantment's level.
     * @param stacks The stacks to add.
     * @return An {@link ILootModifier} that carries out the operation.
     */
    @ZenCodeType.Method
    public static ILootModifier addAllWithUniformBonus(final Enchantment enchantment, final int multiplier, final IItemStack... stacks) {
        return chaining(notEmpty(Arrays.stream(stacks)).map(it -> addWithUniformBonus(enchantment, multiplier, it)));
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

    private static ILootModifier withBonus(final IItemStack drop, final Enchantment enchantment, final DropsFormula formula) {
        return drop.isEmpty()? IDENTITY.get() : (loot, context) -> Util.make(new ArrayList<>(loot), it -> it.add(applyWithBonus(drop.copy(), enchantment, context, formula)));
    }

    private static IItemStack applyWithBonus(final IItemStack original, final Enchantment enchantment, final LootContext context, final DropsFormula formula) {
        return ifTool(original, context, tool -> withLevel(tool, enchantment, level -> original.setAmount(formula.apply(original.getAmount(), level, context.getRandom()))));
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
}
