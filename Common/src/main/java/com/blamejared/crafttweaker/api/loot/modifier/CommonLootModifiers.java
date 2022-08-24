package com.blamejared.crafttweaker.api.loot.modifier;


import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.ingredient.IIngredientWithAmount;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.util.random.Percentaged;
import com.blamejared.crafttweaker.natives.loot.ExpandLootContext;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.openzen.zencode.java.ZenCodeType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
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
        
        if(stack.isEmpty()) {
            return ILootModifier.DEFAULT;
        }
        
        return (loot, context) -> {
            loot.add(stack.copy());
            return loot;
        };
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
        
        return chaining(Arrays.stream(stacks).map(CommonLootModifiers::add));
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
        
        if(isInvalidChance(stack)) {
            return ILootModifier.DEFAULT;
        }
        
        return (loot, context) -> {
            final IItemStack picked = pickStackBasedOnChance(context.getRandom(), stack);
            if(picked != null) {
                loot.add(picked);
            }
            return loot;
        };
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
        
        if(stack.isEmpty()) {
            return ILootModifier.DEFAULT;
        }
        
        return (loot, context) -> {
            final IItemStack tool = ExpandLootContext.getTool(context);
            
            if(tool == null || tool.getInternal() == null || tool.isEmpty()) {
                loot.add(stack.copy());
            } else {
                final int enchantmentLevel = EnchantmentHelper.getItemEnchantmentLevel(enchantment, tool.getInternal());
                final int oldAmount = stack.getAmount();
                final int newAmount = enchantmentLevel <= 0 ? oldAmount : oldAmount * Math.max(0, context.getRandom()
                        .nextInt(enchantmentLevel + 2) - 1) + 1;
                loot.add(stack.copy().setAmount(newAmount));
            }
            
            return loot;
        };
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
        
        if(stack.isEmpty()) {
            return ILootModifier.DEFAULT;
        }
        
        return (loot, context) -> {
            final IItemStack tool = ExpandLootContext.getTool(context);
            
            if(tool == null || tool.getInternal() == null || tool.isEmpty()) {
                loot.add(stack.copy());
            } else {
                final int enchantmentLevel = EnchantmentHelper.getItemEnchantmentLevel(enchantment, tool.getInternal());
                
                if(enchantmentLevel <= 0) {
                    loot.add(stack.copy());
                } else {
                    final RandomSource source = context.getRandom();
                    final int oldAmount = stack.getAmount();
                    
                    int additional = 0;
                    for(int i = 0, s = enchantmentLevel + extra; i < s; ++i) {
                        if(source.nextFloat() < p) {
                            ++additional;
                        }
                    }
                    
                    final int newAmount = oldAmount + additional;
                    loot.add(stack.copy().setAmount(newAmount));
                }
            }
            
            return loot;
        };
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
        
        if(stack.isEmpty()) {
            return ILootModifier.DEFAULT;
        }
        
        return (loot, context) -> {
            final IItemStack tool = ExpandLootContext.getTool(context);
            
            if(tool == null || tool.getInternal() == null || tool.isEmpty()) {
                loot.add(stack.copy());
            } else {
                final int enchantmentLevel = EnchantmentHelper.getItemEnchantmentLevel(enchantment, tool.getInternal());
                
                if(enchantmentLevel <= 0) {
                    loot.add(stack.copy());
                } else {
                    final int oldAmount = stack.getAmount();
                    final int newAmount = oldAmount + context.getRandom().nextInt(multiplier * enchantmentLevel + 1);
                    ;
                    loot.add(stack.copy().setAmount(newAmount));
                }
            }
            
            return loot;
        };
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
        
        return chaining(Arrays.stream(stacks).map(it -> addWithOreDropsBonus(enchantment, it)));
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
        
        return chaining(Arrays.stream(stacks).map(it -> addWithBinomialBonus(enchantment, extra, p, it)));
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
        
        return chaining(Arrays.stream(stacks).map(it -> addWithUniformBonus(enchantment, multiplier, it)));
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
        
        if(stack.isEmpty() || max < min) {
            return ILootModifier.DEFAULT;
        }
        
        return (loot, context) -> {
            final int amount = context.getRandom().nextInt(min, max);
            loot.add(stack.copy().setAmount(amount));
            return loot;
        };
    }
    //endregion
    
    //region Replacement methods
    
    /**
     * Replaces every instance of the targeted {@link IIngredient} with the replacement {@link IItemStack}.
     *
     * In this case, a simple matching procedure is used, where every stack that matches the given {@code target}
     * is replaced by the {@code replacement} without considering stack size. If stack size is to be preserved,
     * refer to {@link #replaceStackWith(IIngredientWithAmount, IItemStack)}.
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
        
        if(target.isEmpty()) {
            return ILootModifier.DEFAULT;
        }
        
        if(replacement.isEmpty()) {
            return remove(target);
        }
        
        return (loot, context) -> {
            boolean hasFound = false;
            List<IItemStack> newList = null;
            
            for(final IItemStack stack : loot) {
                if(hasFound) {
                    if(target.matches(stack)) {
                        newList.add(replacement.copy());
                    } else {
                        newList.add(stack);
                    }
                } else {
                    if(target.matches(stack)) {
                        hasFound = true;
                        newList = new ArrayList<>();
                        
                        for(final IItemStack other : loot) {
                            if(other == stack) {
                                break;
                            }
                            newList.add(other);
                        }
                        newList.add(replacement.copy());
                    }
                }
            }
            
            return newList == null ? loot : newList;
        };
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
     * Replaces every instance of the targeted {@link IIngredientWithAmount} with the replacement {@link IItemStack},
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
    public static ILootModifier replaceStackWith(final IIngredientWithAmount target, final IItemStack replacement) {
        
        final IIngredient ingredient = target.getIngredient();
        final int amount = target.getAmount();
        
        if(ingredient.isEmpty()) {
            return ILootModifier.DEFAULT;
        }
        
        if(amount <= 0) {
            return ILootModifier.DEFAULT;
        }
        
        if(replacement.isEmpty()) { // TODO("Unless we want some form of 'remove carrots but only if they're a multiple of 2'")
            return remove(target.getIngredient());
        }
        
        return (loot, context) -> {
            boolean hasFound = false;
            List<IItemStack> newList = null;
            
            for(final IItemStack stack : loot) {
                if(hasFound) {
                    final int stackAmount;
                    if(ingredient.matches(stack) && (stackAmount = stack.getAmount()) >= amount) {
                        final int newAmount = stackAmount / amount;
                        final int oldAmount = stackAmount % amount;
                        
                        newList.add(replacement.copy().setAmount(newAmount));
                        if(oldAmount > 0) {
                            newList.add(stack.setAmount(oldAmount));
                        }
                    } else {
                        newList.add(stack);
                    }
                } else {
                    final int stackAmount;
                    if(ingredient.matches(stack) && (stackAmount = stack.getAmount()) >= amount) {
                        hasFound = true;
                        newList = new ArrayList<>();
                        
                        for(final IItemStack other : loot) {
                            if(stack == other) {
                                break;
                            }
                            newList.add(other);
                        }
                        
                        final int newAmount = stackAmount / amount;
                        final int oldAmount = stackAmount % amount;
                        
                        newList.add(replacement.copy().setAmount(newAmount));
                        if(oldAmount > 0) {
                            newList.add(stack.setAmount(oldAmount));
                        }
                    }
                }
            }
            
            return newList == null ? loot : newList;
        };
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
    public static ILootModifier replaceAllStacksWith(final Map<IIngredientWithAmount, IItemStack> replacementMap) {
        
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
        
        if(target.isEmpty()) {
            return ILootModifier.DEFAULT;
        }
        
        return (loot, context) -> {
            loot.removeIf(target::matches);
            return loot;
        };
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
    private static boolean isInvalidChance(final Percentaged<IItemStack> stack) {
        
        return stack.getData().isEmpty() || stack.getPercentage() <= 0.0;
    }
    
    private static IItemStack pickStackBasedOnChance(final RandomSource random, final Percentaged<IItemStack> stack) {
        
        return random.nextDouble() <= stack.getPercentage() ? stack.getData().copy() : null;
    }
    
    private static ILootModifier chaining(final Stream<ILootModifier> chain) {
        
        return chain.reduce(
                ILootModifier.DEFAULT,
                (first, second) -> {
                    if(first == ILootModifier.DEFAULT) {
                        return second;
                    }
                    if(second == ILootModifier.DEFAULT) {
                        return first;
                    }
                    return (loot, context) -> second.modify(first.modify(loot, context), context);
                }
        );
    }
    //endregion
}

