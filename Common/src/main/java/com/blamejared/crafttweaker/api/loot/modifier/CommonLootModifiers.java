package com.blamejared.crafttweaker.api.loot.modifier;


import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.util.random.Percentaged;
import com.blamejared.crafttweaker.natives.item.ExpandItemStack;
import com.blamejared.crafttweaker.natives.loot.ExpandLootContext;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.Util;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.storage.loot.LootContext;
import org.openzen.zencode.java.ZenCodeType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Holds a set of implementations of {@link ILootModifier} of common usage.
 *
 * <p>These can be used freely instead of rewriting the same code more than once. They are also guaranteed to behave
 * correctly.</p>
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.modifier.CommonLootModifiers")
@Document("vanilla/api/loot/modifier/CommonLootModifiers")
public final class CommonLootModifiers {
    
    private interface DropsFormula {
        
        DropsFormula ORE_DROPS = (amount, level, random) -> level <= 0 ? amount : amount * Math.max(0, random.nextInt(level + 2) - 1) + 1;
        
        static DropsFormula binomial(final int extra, final float p) {
            
            return (amount, level, random) -> amount + IntStream.range(0, level + extra)
                    .filter(ignore -> random.nextFloat() < p)
                    .map(it -> 1)
                    .sum();
        }
        
        static DropsFormula uniform(final int multiplier) {
            
            return (amount, level, random) -> amount + random.nextInt(multiplier * level + 1);
        }
        
        int apply(final int amount, final int level, final Random random);
        
    }
    
    private static final ILootModifier LOOT_CLEARING_MODIFIER = (loot, context) -> new ArrayList<>();
    
    //region Addition methods
    
    /**
     * Adds the given {@link IItemStack} to the drops.
     *
     * @param stack The stack to add
     *
     * @return An {@link ILootModifier} that carries out the operation.
     *
     * @docParam stack <item:minecraft:cobblestone>
     */
    @ZenCodeType.Method
    public static ILootModifier add(final IItemStack stack) {
        
        return stack.isEmpty() ? ILootModifier.DEFAULT : modifying((loot, context) -> loot.add(stack.copy()));
    }
    
    /**
     * Adds all the given {@link IItemStack} to the drops.
     *
     * @param stacks The stacks to add
     *
     * @return An {@link ILootModifier} that carries out the operation.
     *
     * @docParam stacks <item:minecraft:iron_ingot>, <item:minecraft:iron_nugget> * 5
     */
    @ZenCodeType.Method
    public static ILootModifier addAll(final IItemStack... stacks) {
        
        final List<IItemStack> stacksToAdd = filterEmpty(Arrays.stream(stacks)).toList();
        return modifying((loot, context) -> stacksToAdd.stream().map(IItemStack::copy).forEach(loot::add));
    }
    
    /**
     * Adds the given {@link Percentaged} {@link IItemStack} to the drops, according to the specified chances.
     *
     * <p>The chance will be computed on each modifier roll.</p>
     *
     * @param stack The stack to add.
     *
     * @return An {@link ILootModifier} that carries out the operation.
     *
     * @docParam stack <item:minecraft:gilded_blackstone> % 50
     */
    @ZenCodeType.Method
    public static ILootModifier addWithChance(final Percentaged<IItemStack> stack) {
        
        return isInvalidChance(stack) ? ILootModifier.DEFAULT : modifying((loot, context) -> chance(context.getRandom(), stack).ifPresent(loot::add));
    }
    
    /**
     * Adds the given {@link Percentaged} {@link IItemStack}s to the drops, according to the specified chances.
     *
     * <p>The chance will be computed on each modifier roll, and independently for each of the given stacks.</p>
     *
     * @param stacks The stacks to add.
     *
     * @return An {@link ILootModifier} that carries out the operation.
     *
     * @docParam stacks <item:minecraft:honey_bottle> % 50, <item:minecraft:dried_kelp> % 13
     */
    @SafeVarargs
    @ZenCodeType.Method
    public static ILootModifier addAllWithChance(final Percentaged<IItemStack>... stacks) {
        
        return chaining(Arrays.stream(stacks).map(CommonLootModifiers::addWithChance));
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
     * @param stack       The stack to add.
     *
     * @return An {@link ILootModifier} that carries out the operation.
     *
     * @docParam enchantment <enchantment:minecraft:fortune>
     * @docParam stack <item:minecraft:coal>
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
     * @param extra       An extra value that will be added to the tool's enchantment level.
     * @param p           The probability of the binomial distribution, between 0.0 and 1.0 (both exclusive).
     * @param stack       The stack to add.
     *
     * @return An {@link ILootModifier} that carries out the operation.
     *
     * @docParam enchantment <enchantment:minecraft:fortune>
     * @docParam extra 3
     * @docParam p 0.5714286
     * @docParam stack <item:minecraft:wheat_seeds>
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
     * @param multiplier  A multiplier that will be used in conjunction with the enchantment's level.
     * @param stack       The stack to add.
     *
     * @return An {@link ILootModifier} that carries out the operation.
     *
     * @docParam enchantment <enchantment:minecraft:fortune>
     * @docParam multiplier 1
     * @docParam stack <item:minecraft:glowstone_dust>
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
     * @param stacks      The stacks to add.
     *
     * @return An {@link ILootModifier} that carries out the operation.
     *
     * @docParam enchantment <enchantment:minecraft:fortune>
     * @docParam stacks <item:minecraft:coal>, <item:minecraft:diamond>
     */
    @ZenCodeType.Method
    public static ILootModifier addAllWithOreDropsBonus(final Enchantment enchantment, final IItemStack... stacks) {
        
        return chaining(filterEmpty(Arrays.stream(stacks)).map(it -> addWithOreDropsBonus(enchantment, it)));
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
     * @param extra       An extra value that will be added to the tool's enchantment level.
     * @param p           The probability of the binomial distribution, between 0.0 and 1.0 (both exclusive).
     * @param stacks      The stacks to add.
     *
     * @return An {@link ILootModifier} that carries out the operation.
     *
     * @docParam enchantment <enchantment:minecraft:fortune>
     * @docParam extra 3
     * @docParam p 0.5714286
     * @docParam stacks <item:minecraft:wheat_seeds>, <item:minecraft:carrot> * 9
     */
    @ZenCodeType.Method
    public static ILootModifier addAllWithBinomialBonus(final Enchantment enchantment, final int extra, final float p, final IItemStack... stacks) {
        
        return chaining(filterEmpty(Arrays.stream(stacks)).map(it -> addWithBinomialBonus(enchantment, extra, p, it)));
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
     * @param multiplier  A multiplier that will be used in conjunction with the enchantment's level.
     * @param stacks      The stacks to add.
     *
     * @return An {@link ILootModifier} that carries out the operation.
     *
     * @docParam enchantment <enchantment:minecraft:fortune>
     * @docParam multiplier 1
     * @docParam stacks <item:minecraft:glowstone_dust>, <item:minecraft:prismarine_crystals>
     */
    @ZenCodeType.Method
    public static ILootModifier addAllWithUniformBonus(final Enchantment enchantment, final int multiplier, final IItemStack... stacks) {
        
        return chaining(filterEmpty(Arrays.stream(stacks)).map(it -> addWithUniformBonus(enchantment, multiplier, it)));
    }
    
    /**
     * Adds the given {@link IItemStack} to the drops, with an amount chosen randomly between the given bounds.
     *
     * <p>Any original stack size given to this method is ignored; if an addition behavior is desired (as in random
     * chance <em>on top</em> of the original stack size), a combining loot modifier should be used instead.</p>
     *
     * @param stack The stack to add.
     * @param min   The minimum amount that this stack can be.
     * @param max   The maximum amount that this stack can be.
     *
     * @return An {@link ILootModifier} that carries out the operation.
     *
     * @docParam stack <item:minecraft:conduit>
     * @docParam min 2
     * @docParam max 9
     */
    @ZenCodeType.Method
    public static ILootModifier addWithRandomAmount(final IItemStack stack, final int min, final int max) {
        
        return stack.isEmpty() || max < min ? ILootModifier.DEFAULT : modifying((loot, context) -> loot.add(stack.copy()
                .setAmount(boundedRandom(context, min, max))));
    }
    //endregion
    
    //region Replacement methods
    
    /**
     * Replaces every instance of the targeted {@link IIngredient} with the replacement {@link IItemStack}.
     *
     * In this case, a simple matching procedure is used, where every stack that matches the given {@code target}
     * is replaced by the {@code replacement} without considering stack size. If stack size is to be preserved,
     * refer to {@link #replaceStackWith(IItemStack, IItemStack)}.
     *
     * @param target      The target to replace.
     * @param replacement The replacement to use.
     *
     * @return An {@link ILootModifier} that carries out the operation.
     *
     * @docParam target <tag:items:forge:ingots/iron>
     * @docParam replacement <item:minecraft:iron_ingot>
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
     *
     * @return An {@link ILootModifier} that carries out the operation.
     *
     * @docParam replacementMap { <tag:items:forge:gems/emerald> : <item:minecraft:emerald> }
     */
    @ZenCodeType.Method
    public static ILootModifier replaceAllWith(final Map<IIngredient, IItemStack> replacementMap) {
        
        return chaining(replacementMap.entrySet().stream().map(it -> replaceWith(it.getKey(), it.getValue())));
    }
    
    /**
     * Replaces every instance of the targeted {@link IItemStack} with the replacement {@link IItemStack},
     * proportionally.
     *
     * As an example, if the loot drops 5 carrots and this loot modifier runs with 2 carrots as the {@code target}
     * and 1 potato as the {@code replacement}, the loot will be modified to 2 potatoes and 1 carrot. This happens
     * because every 2-carrot stack will be actively replaced by a 1-potato stack, without exceptions.
     *
     * This loot modifier acts differently than {@link #replaceWith(IIngredient, IItemStack)}, where a simpler approach
     * is used.
     *
     * @param target      The target to replace.
     * @param replacement The replacement to use.
     *
     * @return An {@link ILootModifier} that carries out the operation.
     *
     * @docParam target <item:minecraft:carrots> * 2
     * @docParam replacement <item:minecraft:potatoes>
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
     *
     * @return An {@link ILootModifier} that carries out the operation.
     *
     * @docParam replacementMap { <item:minecraft:carrots> * 2 : <item:minecraft:potatoes> }
     */
    @ZenCodeType.Method
    public static ILootModifier replaceAllStacksWith(final Map<IItemStack, IItemStack> replacementMap) {
        
        return chaining(replacementMap.entrySet().stream().map(it -> replaceStackWith(it.getKey(), it.getValue())));
    }
    //endregion
    
    //region Removal methods
    
    /**
     * Removes every instance of the targeted {@link IIngredient} from the drops.
     *
     * @param target The {@link IIngredient} to remove.
     *
     * @return An {@link ILootModifier} that carries out the operation.
     *
     * @docParam target <tag:items:minecraft:creeper_drop_music_discs>
     */
    @ZenCodeType.Method
    public static ILootModifier remove(final IIngredient target) {
        
        return replaceWith(target, ExpandItemStack.asIItemStack(ItemStack.EMPTY));
    }
    
    /**
     * Removes every instance of all the targeted {@link IIngredient}s from the drops.
     *
     * @param targets The {@link IIngredient}s to remove.
     *
     * @return An {@link ILootModifier} that carries out the operation.
     *
     * @docParam targets <item:minecraft:bell>, <tag:items:minecraft:rails>
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
        
        return LOOT_CLEARING_MODIFIER;
    }
    //endregion
    
    //region Additional utility methods
    
    /**
     * Chains the given list of {@link ILootModifier}s to be executed one after the other.
     *
     * @param modifiers The modifier list.
     *
     * @return An {@link ILootModifier} that carries out the operation.
     *
     * @docParam modifiers CommonLootModifiers.clearLoot(), CommonLootModifiers.add(<item:minecraft:warped_hyphae>)
     */
    @ZenCodeType.Method
    public static ILootModifier chaining(final ILootModifier... modifiers) {
        
        return chaining(Arrays.stream(modifiers));
    }
    
    /**
     * Chains the given list of {@link ILootModifier}s together after having cleaned the original loot.
     *
     * @param modifiers The modifier list.
     *
     * @return An {@link ILootModifier} that carries out the operation.
     *
     * @docParam modifiers CommonLootModifiers.add(<item:minecraft:warped_hyphae>)
     */
    @ZenCodeType.Method
    public static ILootModifier clearing(final ILootModifier... modifiers) {
        
        return chaining(Stream.concat(Stream.of(clearLoot()), Arrays.stream(modifiers)));
    }
    //endregion
    
    //region Private utility stuff
    private static ILootModifier modifying(final BiConsumer<List<IItemStack>, LootContext> consumer) {
        
        return (loot, context) -> Util.make(loot, it -> consumer.accept(it, context));
    }
    
    private static ILootModifier streaming(final BiFunction<Stream<IItemStack>, LootContext, Stream<IItemStack>> consumer) {
        
        return (loot, context) -> consumer.apply(loot.stream(), context).collect(Collectors.toList());
    }
    
    private static ILootModifier chaining(final Stream<ILootModifier> chain) {
        
        return chain.reduce(ILootModifier.DEFAULT, (first, second) -> (loot, context) -> second.modify(first.modify(loot, context), context));
    }
    
    private static Stream<IItemStack> filterEmpty(final Stream<IItemStack> stream) {
        
        return stream.filter(it -> !it.isEmpty());
    }
    
    private static Stream<IItemStack> replacing(final Stream<IItemStack> stream, final IIngredient from, final IItemStack to) {
        
        return filterEmpty(stream.map(it -> from.matches(it) ? to.copy() : it));
    }
    
    private static Stream<IItemStack> replacingExactly(final Stream<IItemStack> stream, final IItemStack from, final IItemStack to) {
        
        return stream.flatMap(it -> filterEmpty((from.matches(it) ? replacingExactly(it, from, to) : Collections.singleton(it)).stream()));
    }
    
    private static List<IItemStack> replacingExactly(final IItemStack original, final IItemStack from, final IItemStack to) {
        
        return List.of(to.copy().setAmount(original.getAmount() / from.getAmount()), original.copy()
                .setAmount(original.getAmount() % from.getAmount()));
    }
    
    private static ILootModifier withBonus(final IItemStack drop, final Enchantment enchantment, final DropsFormula formula) {
        
        return drop.isEmpty() ? ILootModifier.DEFAULT : modifying((loot, context) -> loot.add(applyWithBonus(drop.copy(), enchantment, context, formula)));
    }
    
    private static IItemStack applyWithBonus(final IItemStack original, final Enchantment enchantment, final LootContext context, final DropsFormula formula) {
        
        return ifTool(original, context, tool -> withLevel(tool, enchantment, level -> original.setAmount(formula.apply(original.getAmount(), level, context.getRandom()))));
    }
    
    private static IItemStack ifTool(final IItemStack original, final LootContext context, final Function<IItemStack, IItemStack> toolConsumer) {
        
        return ifTool(original, ExpandLootContext.getTool(context), toolConsumer);
    }
    
    private static IItemStack ifTool(final IItemStack original, final IItemStack tool, final Function<IItemStack, IItemStack> toolConsumer) {
        
        return tool != null && tool.getInternal() != null && !tool.isEmpty() ? toolConsumer.apply(tool) : original;
    }
    
    private static IItemStack withLevel(final IItemStack tool, final Enchantment enchantment, final IntFunction<IItemStack> levelUser) {
        
        return levelUser.apply(EnchantmentHelper.getItemEnchantmentLevel(enchantment, tool.getInternal()));
    }
    
    private static boolean isInvalidChance(final Percentaged<IItemStack> stack) {
        
        return stack.getData().isEmpty() || stack.getPercentage() <= 0.0;
    }
    
    private static Optional<IItemStack> chance(final Random random, final Percentaged<IItemStack> stack) {
        
        return random.nextDouble() <= stack.getPercentage() ? Optional.of(stack.getData().copy()) : Optional.empty();
    }
    
    private static int boundedRandom(final LootContext context, final int min, final int max) {
        
        return context.getRandom().nextInt(max - min) + min;
    }
    //endregion
}

